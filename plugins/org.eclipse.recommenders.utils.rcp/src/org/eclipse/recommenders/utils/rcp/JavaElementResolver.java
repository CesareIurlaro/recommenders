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
package org.eclipse.recommenders.utils.rcp;

import static org.eclipse.recommenders.utils.Checks.ensureIsNotNull;
import static org.eclipse.recommenders.utils.Throws.throwUnhandledException;

import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.internal.codeassist.impl.AssistSourceMethod;
import org.eclipse.jdt.internal.codeassist.impl.AssistSourceType;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.jdt.internal.corext.util.SearchUtils;
import org.eclipse.jdt.internal.corext.util.SuperTypeHierarchyCache;
import org.eclipse.recommenders.utils.Names;
import org.eclipse.recommenders.utils.names.IMethodName;
import org.eclipse.recommenders.utils.names.IName;
import org.eclipse.recommenders.utils.names.ITypeName;
import org.eclipse.recommenders.utils.names.VmMethodName;
import org.eclipse.recommenders.utils.names.VmTypeName;
import org.eclipse.recommenders.utils.rcp.internal.RecommendersUtilsPlugin;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;

@SuppressWarnings("restriction")
public class JavaElementResolver {

    public static JavaElementResolver INSTANCE;

    public JavaElementResolver() {
        INSTANCE = this;
    }

    private final BiMap<IName, IJavaElement> cache = HashBiMap.create();
    public HashSet<IMethodName> failedRecMethods = Sets.newHashSet();
    public HashSet<ITypeName> failedRecTypes = Sets.newHashSet();

    public IType toJdtType(final ITypeName recType) {
        ensureIsNotNull(recType);
        if (failedRecTypes.contains(recType)) {
            return null;
        }

        IType jdtType = (IType) cache.get(recType);
        if (jdtType == null) {
            jdtType = resolveType(recType);

            if (jdtType != null) {
                registerRecJdtElementPair(recType, jdtType);
            } else {
                failedRecTypes.add(recType);
            }
        } else if (!jdtType.exists()) {
            // found in cache but not existing anymore?
            // restart resolution process:
            cache.remove(recType);
            return toJdtType(recType);
        }
        return jdtType;
    }

    public ITypeName toRecType(IType jdtType) {
        ensureIsNotNull(jdtType);
        jdtType = JdtUtils.resolveJavaElementProxy(jdtType);
        ITypeName recType = (ITypeName) cache.inverse().get(jdtType);
        if (recType == null) {
            String fullyQualifiedName = jdtType.getFullyQualifiedName();
            fullyQualifiedName = StringUtils.substringBefore(fullyQualifiedName, "<");
            recType = VmTypeName.get("L" + fullyQualifiedName.replace('.', '/'));
            registerRecJdtElementPair(recType, jdtType);
        }
        return recType;
    }

    private IType resolveType(final ITypeName recType) {
        // TODO woah, what a hack just to find a nested/anonymous type... this
        // definitely needs refactoring!
        ensureIsNotNull(recType);
        if (recType.isArrayType()) {
            // TODO see https://bugs.eclipse.org/bugs/show_bug.cgi?id=339806
            // should throw an exception? or return an Array type?
            System.err.println("array type in JavaElementResolver. Decision  bug 339806 pending...?");
            return null;
        }

        if (recType.isNestedType()) {
            final ITypeName declaringType = recType.getDeclaringType();
            final IType parent = resolveType(declaringType);
            if (parent != null) {
                try {
                    for (final IType nested : parent.getTypes()) {
                        final String key = nested.getKey();
                        if (key.equals(recType.getIdentifier() + ";")) {
                            return nested;
                        }
                    }
                    for (final IMethod m : parent.getMethods()) {
                        for (final IJavaElement children : m.getChildren()) {
                            if (children instanceof IType) {
                                final IType nested = (IType) children;
                                final String key = nested.getKey();
                                if (key.equals(recType.getIdentifier() + ";")) {
                                    return nested;
                                }
                            }
                        }
                    }
                } catch (final Exception x) {
                    // final IType type =
                    // parent.getType(recType.getClassName());
                    System.out.println(parent);
                    return null;
                }
            }
            return null;
        }
        final IType[] res = new IType[1];
        final IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
        final SearchEngine search = new SearchEngine();
        final String srcTypeName = Names.vm2srcTypeName(recType.getIdentifier());
        final SearchPattern pattern = SearchPattern.createPattern(srcTypeName, IJavaSearchConstants.TYPE,
                IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
        try {
            search.search(pattern, SearchUtils.getDefaultSearchParticipants(), scope, new SearchRequestor() {

                @Override
                public void acceptSearchMatch(final SearchMatch match) throws CoreException {
                    res[0] = (IType) match.getElement();
                }
            }, null);
        } catch (final CoreException e) {
            throwUnhandledException(e);
        }
        return res[0];
    }

    private void registerRecJdtElementPair(final IName recName, final IJavaElement jdtElement) {
        ensureIsNotNull(recName);
        ensureIsNotNull(jdtElement);
        if (jdtElement instanceof AssistSourceType) {
            return;
        } else if (jdtElement instanceof AssistSourceMethod) {
            return;
        }
        cache.forcePut(recName, jdtElement);
        // XXX checkIsNull(put);
    }

    public IMethod toJdtMethod(final IMethodName recMethod) {
        ensureIsNotNull(recMethod);
        if (failedRecMethods.contains(recMethod)) {
            return null;
        }

        IMethod jdtMethod = (IMethod) cache.get(recMethod);
        if (jdtMethod == null) {
            jdtMethod = resolveMethod(recMethod);
            if (jdtMethod == null) {
                // if (!recMethod.isSynthetic()) {
                // System.err.printf("resolving %s failed. Is it an compiler generated constructor?\n.",
                // recMethod.getIdentifier());
                // }
                failedRecMethods.add(recMethod);
                return null;
            }
            registerRecJdtElementPair(recMethod, jdtMethod);
        } else if (!jdtMethod.exists()) {
            // found in cache but not existing anymore?
            // restart resolution process:
            cache.remove(recMethod);
            return toJdtMethod(recMethod);
        }
        return jdtMethod;
    }

    /**
     * Returns null if we fail to resolve all types used in the method
     * signature, for instance generic return types etc...
     */
    public IMethodName toRecMethod(IMethod jdtMethod) {
        ensureIsNotNull(jdtMethod);
        jdtMethod = JdtUtils.resolveJavaElementProxy(jdtMethod);
        IMethodName recMethod = (IMethodName) cache.inverse().get(jdtMethod);
        if (recMethod == null) {
            try {
                final IType jdtDeclaringType = jdtMethod.getDeclaringType();
                final ITypeName recDeclaringType = toRecType(jdtDeclaringType);
                //
                final String[] unresolvedParameterTypes = jdtMethod.getParameterTypes();
                final String[] resolvedParameterTypes = new String[unresolvedParameterTypes.length];
                for (int i = resolvedParameterTypes.length; i-- > 0;) {
                    resolvedParameterTypes[i] = JavaModelUtil.getResolvedTypeName(unresolvedParameterTypes[i],
                            jdtDeclaringType);
                }
                String resolvedReturnType = null;
                final String unresolvedReturnType = jdtMethod.getReturnType();
                try {
                    resolvedReturnType = JavaModelUtil.getResolvedTypeName(unresolvedReturnType, jdtDeclaringType);
                } catch (final JavaModelException e) {
                    RecommendersUtilsPlugin.log(e);
                }
                if (resolvedReturnType == null) {
                    RecommendersUtilsPlugin.logWarning("Failed to resolve return type '%s' of method %s.%s%s",
                            unresolvedReturnType, jdtDeclaringType.getFullyQualifiedName(), jdtMethod.getElementName(),
                            jdtMethod.getSignature());
                    return null;
                }
                final String methodSignature = Names.src2vmMethod(
                        jdtMethod.isConstructor() ? "<init>" : jdtMethod.getElementName(), resolvedParameterTypes,
                        resolvedReturnType);
                recMethod = VmMethodName.get(recDeclaringType.getIdentifier(), methodSignature);
                registerRecJdtElementPair(recMethod, jdtMethod);
            } catch (final Exception e) {
                e.printStackTrace();
                RecommendersUtilsPlugin.logError(e, "failed to resolve jdt method '%s'.", jdtMethod);
                return null;
            }
        }
        return recMethod;
    }

    private IMethod resolveMethod(final IMethodName recMethod) {
        ensureIsNotNull(recMethod);
        try {
            final IType jdtType = toJdtType(recMethod.getDeclaringType());
            if (!isSuccessfullyResolvedType(jdtType)) {
                return null;
            }
            final String[] jdtParamTypes = createJDTParameterTypeStrings(recMethod);
            final ITypeHierarchy hierarchy = SuperTypeHierarchyCache.getTypeHierarchy(jdtType);
            final IMethod jdtMethod = JavaModelUtil.findMethodInHierarchy(hierarchy, jdtType, recMethod.getName(),
                    jdtParamTypes, recMethod.isInit());
            return jdtMethod;
        } catch (final JavaModelException e) {
            throw throwUnhandledException(e);
        }
    }

    private boolean isSuccessfullyResolvedType(final IType jdtType) throws JavaModelException {
        return jdtType != null && jdtType.isStructureKnown();
    }

    private String[] createJDTParameterTypeStrings(final IMethodName method) {
        /*
         * Note, JDT expects declared-types (also declared array-types) given as
         * parameters to (i) use dots as separator, and (ii) end with a
         * semicolon. this conversion is done here:
         */
        final ITypeName[] paramTypes = method.getParameterTypes();
        final String[] jdtParamTypes = new String[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            jdtParamTypes[i] = createJdtParameterTypeString(paramTypes[i]);
        }
        return jdtParamTypes;
    }

    private String createJdtParameterTypeString(final ITypeName type) {
        final String identifier = type.getIdentifier();
        if (type.isDeclaredType()) {
            return identifier.replace('/', '.') + ";";
        } else if (type.isArrayType() && type.getArrayBaseType().isDeclaredType()) {
            return identifier.replace('/', '.') + ";";
        } else {
            return identifier;
        }
    }
}