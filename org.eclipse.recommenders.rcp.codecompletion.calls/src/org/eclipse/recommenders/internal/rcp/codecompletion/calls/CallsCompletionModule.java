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
package org.eclipse.recommenders.internal.rcp.codecompletion.calls;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.recommenders.internal.rcp.analysis.IRecommendersProjectLifeCycleListener;
import org.eclipse.recommenders.internal.rcp.codecompletion.calls.store.CallsModelIndex;
import org.eclipse.recommenders.internal.rcp.codecompletion.calls.store.FragmentIndex;
import org.eclipse.recommenders.internal.rcp.codecompletion.calls.store.IModelArchiveStore;
import org.eclipse.recommenders.internal.rcp.codecompletion.calls.store.ModelArchiveStore;
import org.eclipse.recommenders.internal.rcp.codecompletion.calls.store.ProjectModelFacadeFactory;
import org.eclipse.recommenders.internal.rcp.codecompletion.calls.store.ProjectServices;
import org.eclipse.recommenders.internal.rcp.views.recommendations.IRecommendationsViewContentProvider;
import org.osgi.framework.FrameworkUtil;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

public class CallsCompletionModule extends AbstractModule {

    public static final String CALLS_STORE_LOCATION = "calls.store.location";

    @Override
    protected void configure() {
        configureProjectServices();
        configureArchiveModelStore();
        configureRecommendationsViewPublisher();
    }

    private void configureProjectServices() {
        bind(ProjectServices.class).in(Scopes.SINGLETON);
        final Multibinder<IRecommendersProjectLifeCycleListener> multibinder = Multibinder.newSetBinder(binder(),
                IRecommendersProjectLifeCycleListener.class);
        multibinder.addBinding().to(ProjectServices.class);
    }

    private void configureArchiveModelStore() {
        bind(CallsModelIndex.class).in(Scopes.SINGLETON);
        bind(IModelArchiveStore.class).to(ModelArchiveStore.class).in(Scopes.SINGLETON);

        final IPath stateLocation = Platform.getStateLocation(FrameworkUtil.getBundle(getClass()));
        bind(File.class).annotatedWith(Names.named(CALLS_STORE_LOCATION)).toInstance(stateLocation.toFile());

        bind(File.class).annotatedWith(FragmentIndexFile.class).toInstance(
                new File(stateLocation.toFile(), "fragmentsIndex.json"));
        bind(FragmentIndex.class).in(Scopes.SINGLETON);
        install(new FactoryModuleBuilder().build(ProjectModelFacadeFactory.class));
    }

    private void configureRecommendationsViewPublisher() {
        Multibinder.newSetBinder(binder(), IRecommendationsViewContentProvider.class).addBinding()
                .to(RecommendationsViewPublisherForCalls.class);
    }

    @BindingAnnotation
    @Target(PARAMETER)
    @Retention(RUNTIME)
    public static @interface FragmentIndexFile {
    }
}
