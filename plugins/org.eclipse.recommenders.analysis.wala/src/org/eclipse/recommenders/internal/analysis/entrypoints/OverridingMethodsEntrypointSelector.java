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
package org.eclipse.recommenders.internal.analysis.entrypoints;

import static org.eclipse.recommenders.utils.Checks.ensureIsEmpty;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.recommenders.internal.analysis.utils.MethodUtils;

import com.google.common.collect.Lists;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.impl.DefaultEntrypoint;

/**
 * This class is stateful, thus, should be used only once.
 */
public class OverridingMethodsEntrypointSelector implements IEntrypointSelector {
    LinkedList<Entrypoint> entrypoints = Lists.newLinkedList();

    @Override
    public List<Entrypoint> selectEntrypoints(final IClass clazz) {
        ensureIsEmpty(entrypoints);
        for (final IMethod method : clazz.getDeclaredMethods()) {
            final IMethod overriddenMethod = searchOverriddenMethod(method);
            if (overriddenMethod != null) {
                createAndRegisterEntrypoint(method);
            }
        }
        return entrypoints;
    }

    private IMethod searchOverriddenMethod(final IMethod method) {
        return MethodUtils.findSuperImplementation(method);
    }

    private void createAndRegisterEntrypoint(final IMethod method) {
        final DefaultEntrypoint newEntrypoint = new DefaultEntrypoint(method, method.getClassHierarchy());
        entrypoints.add(newEntrypoint);
    }
}