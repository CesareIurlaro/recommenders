/**
 * Copyright (c) 2010 Darmstadt University of Technology.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 */
package org.eclipse.recommenders.internal.rcp.analysis;

import static org.eclipse.recommenders.commons.utils.Option.none;
import static org.eclipse.recommenders.internal.rcp.analysis.builder.ChangedCompilationUnitsFinder.findChangedCompilkationUnits;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.recommenders.commons.injection.InjectionService;
import org.eclipse.recommenders.commons.utils.Option;
import org.eclipse.recommenders.commons.utils.Tuple;
import org.eclipse.recommenders.commons.utils.annotations.Testing;
import org.eclipse.recommenders.internal.rcp.InterruptingProgressMonitor;
import org.eclipse.recommenders.internal.rcp.analysis.builder.CompilationUnitsFinder;
import org.eclipse.recommenders.internal.rcp.analysis.cp.IProjectClasspathAnalyzer;
import org.eclipse.recommenders.rcp.IArtifactStore;
import org.eclipse.recommenders.rcp.ICompilationUnitAnalyzer;
import org.eclipse.recommenders.rcp.RecommendersPlugin;
import org.eclipse.recommenders.rcp.analysis.IClassHierarchyService;
import org.eclipse.recommenders.rcp.utils.CountingProgressMonitor;
import org.eclipse.recommenders.rcp.utils.JavaElementResolver;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.ibm.wala.ipa.cha.IClassHierarchy;

/**
 * Incremental builder that triggers the {@link ICompilationUnitAnalyzer}s to
 * analyze changed java resources whenever required.
 */
@SuppressWarnings("rawtypes")
public class RecommendersBuilder extends IncrementalProjectBuilder {
    private static int ticksLastFullBuild = 100;

    private static int ticksLastIncrBuild = 2;

    private CountingProgressMonitor monitor;

    @Inject
    private IArtifactStore store;

    @Inject
    private Set<ICompilationUnitAnalyzer> analyzers;

    @Inject
    private IClassHierarchyService chaService;

    @Inject
    private JavaElementResolver javaElementResolver;

    public RecommendersBuilder() {
        // that's odd: builder extension point does not allow usage of extension
        // factories. Thus, we have to set up things manually.
        final InjectionService service = InjectionService.getInstance();
        service.injectMembers(this);
    }

    @Testing
    protected RecommendersBuilder(final IArtifactStore store, final Set<ICompilationUnitAnalyzer> analyzers,
            final IProjectClasspathAnalyzer cpAnalyzer) {
        this.store = store;
        this.analyzers = analyzers;
    }

    @Override
    protected IProject[] build(final int kind, final Map args, final IProgressMonitor monitor) throws CoreException {
        setMonitor(monitor);
        try {
            switch (kind) {
            case FULL_BUILD:
                performFullBuild();
                return null;
            case CLEAN_BUILD:
                performCleanBuild();
                return null;
            case INCREMENTAL_BUILD:
            case AUTO_BUILD:
            default:
                final IResourceDelta delta = getDelta(getProject());
                if (delta == null) {
                    performFullBuild();
                } else {
                    performIncrementalBuild(delta);
                }
                return null;

            }
        } finally {
            monitor.done();
        }
    }

    private void performFullBuild() throws CoreException {
        monitor.beginTask("Perform Full Build", ticksLastFullBuild);
        performCleanBuild();
        analyzeCompilationUnits(CompilationUnitsFinder.visitResource(getProject()));
        ticksLastFullBuild = monitor.actualWork;
    }

    private void performIncrementalBuild(final IResourceDelta delta) throws CoreException {
        monitor.beginTask("Incremental Build", ticksLastIncrBuild);
        final Tuple<List<ICompilationUnit>/* added */, List<ICompilationUnit> /* removed */> deltas = findChangedCompilkationUnits(delta);

        analyzeCompilationUnits(deltas.getFirst());
        removeCompilationUnit(deltas.getSecond());
        ticksLastIncrBuild = monitor.actualWork;
    }

    private void reportSkippingCompilationUnit(final ICompilationUnit cu) {
        monitor.subTask("Skipping " + cu.getElementName() + " because of syntax errors.");
    }

    private void removeCachedClassFromClassHierarchy(final ICompilationUnit cu) {
        final IClassHierarchy cha = chaService.getClassHierachy(cu);
        if (cha instanceof LazyClassHierarchy) {
            final LazyClassHierarchy lcha = (LazyClassHierarchy) cha;
            final IType primaryType = cu.findPrimaryType();
            if (primaryType != null) {
                lcha.remove(javaElementResolver.toRecType(primaryType));
            }
        }
    }

    private Option<?> safeAnalyzeCompilationUnit(final ICompilationUnit cu, final ICompilationUnitAnalyzer<?> analyzer,
            final IProgressMonitor monitor) {

        try {
            return analyzer.analyze(cu, monitor);
        } catch (final RuntimeException x) {
            logAnalyzerFailed(cu, analyzer, x);
            return none();
        }
    }

    private void removeCompilationUnit(final List<ICompilationUnit> cus) throws CoreException {
        for (final ICompilationUnit cu : cus) {
            monitor.subTask("Removing Analysis Artifacts for " + cu.getElementName());
            store.removeArtifacts(cu);
            monitor.worked(1);
        }
    }

    @Override
    protected void clean(final IProgressMonitor monitor) throws CoreException {
        try {
            setMonitor(monitor);
            performCleanBuild();
        } finally {
            monitor.done();
        }

    }

    private void setMonitor(final IProgressMonitor monitor) {
        this.monitor = new CountingProgressMonitor(monitor);
    }

    private void performCleanBuild() throws CoreException {
        monitor.subTask("Cleaning " + getProject().getName());
        store.cleanStore(getProject());
        monitor.worked(1);
    }

    private void analyzeCompilationUnits(final List<ICompilationUnit> cus) {

        final int size = cus.size();
        int counter = 0;
        for (final ICompilationUnit cu : cus) {
            if (monitor.isCanceled()) {
                return;
            }
            try {
                if (!cu.isStructureKnown()) {
                    reportSkippingCompilationUnit(cu);
                    return;
                }
            } catch (final JavaModelException e) {
                RecommendersPlugin.logWarning(e, "Couldn't analyze %s. Skipped.", cu.getElementName());
            }

            removeCachedClassFromClassHierarchy(cu);
            final String msg = String.format("Analyzing '%s/%s' (%d of %d)", cu.getJavaProject().getElementName(), cu
                    .getResource().getProjectRelativePath(), counter++, size);
            monitor.subTask(msg);
            final List<Object> artifacts = Lists.newLinkedList();
            for (final ICompilationUnitAnalyzer<?> analyzer : analyzers) {

                final InterruptingProgressMonitor monitor2 = new InterruptingProgressMonitor(monitor);
                final Option<?> artifact = safeAnalyzeCompilationUnit(cu, analyzer, monitor2);
                if (artifact.hasValue()) {
                    artifacts.add(artifact.get());
                }
            }
            store.storeArtifacts(cu, artifacts);
            monitor.worked(1);
        }
    }

    private void logAnalyzerFailed(final ICompilationUnit cu, final ICompilationUnitAnalyzer<?> analyzer,
            final Exception x) {
        final String analyzerName = analyzer.getClass().getSimpleName();
        final String cuName = cu.getElementName();
        RecommendersPlugin.logError(x, "Analyzer '%s' threw exception while analzying '%s'", analyzerName, cuName);
    }
}
