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
package org.eclipse.recommenders.internal.analysis.analyzers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.recommenders.internal.analysis.codeelements.MethodDeclaration;
import org.eclipse.recommenders.internal.analysis.utils.MethodUtils;
import org.eclipse.recommenders.internal.analysis.utils.WalaNameUtils;

import com.google.inject.Singleton;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.Entrypoint;

@Singleton
public class FirstDeclarationMethodAnalyzer implements IMethodAnalyzer {
    @Override
    public void analyzeMethod(final Entrypoint entrypoint, final MethodDeclaration methodDecl,
            final IProgressMonitor monitor) {
        final IMethod method = entrypoint.getMethod();
        // filter constructor calls
        if (!MethodUtils.mayHaveSuperDeclaration(method)) {
            return;
        }
        final IMethod firstDeclaration = MethodUtils.findRootDeclaration(method);
        if (firstDeclaration != null && !sameMethods(method, firstDeclaration)) {
            methodDecl.firstDeclaration = WalaNameUtils.wala2recMethodName(firstDeclaration);
        }
    }

    private boolean sameMethods(final IMethod method, final IMethod other) {
        return method == other;
    }
}