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
package org.eclipse.recommenders.rcp.utils;

import static com.google.common.base.Optional.*;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.eclipse.recommenders.internal.rcp.l10n.LogMessages.ERROR_FAILED_TO_RESOLVE_METHOD;
import static org.eclipse.recommenders.utils.Checks.cast;
import static org.eclipse.recommenders.utils.Logs.log;
import static org.eclipse.recommenders.utils.Throws.*;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.WildcardType;
import org.eclipse.recommenders.jdt.AstBindings;
import org.eclipse.recommenders.utils.names.IMethodName;
import org.eclipse.recommenders.utils.names.ITypeName;
import org.eclipse.recommenders.utils.names.Names;

import com.google.common.base.Optional;

public final class ASTNodeUtils {

    private ASTNodeUtils() {
        // Not meant to be instantiated
    }

    /**
     * Returns the names top-level identifier, i.e., for "java.lang.String" --&gt; "String" and "String" --&gt; "String"
     *
     * @param name
     * @return
     */
    public static SimpleName stripQualifier(Name name) {
        switch (name.getNodeType()) {
        case ASTNode.SIMPLE_NAME:
            return (SimpleName) name;
        case ASTNode.QUALIFIED_NAME:
            return ((QualifiedName) name).getName();
        default:
            throw throwUnreachable("Unknown subtype of name: '%s'", name.getClass()); //$NON-NLS-1$
        }
    }

    public static boolean sameSimpleName(final Type jdtParam, final ITypeName crParam) {
        String jdtTypeName = toSimpleName(jdtParam);
        String crSimpleName = toSimpleName(crParam);
        return jdtTypeName.equals(crSimpleName);
    }

    private static String toSimpleName(final ITypeName crParam) {
        String crSimpleName = Names.vm2srcSimpleTypeName(crParam);
        if (crSimpleName.contains("$")) { //$NON-NLS-1$
            crSimpleName = StringUtils.substringAfterLast(crSimpleName, "$"); //$NON-NLS-1$
        }
        return crSimpleName;
    }

    /**
     * Returns the simple name of a given class type, for primitive types their source names "int", "long" etc., for
     * arrays it returns the element type of the array, for wildcards their bound type, and for union types something
     * not meaningful.
     */
    private static String toSimpleName(Type type) {
        SimpleName name;
        switch (type.getNodeType()) {
        case ASTNode.SIMPLE_TYPE: {
            SimpleType t = cast(type);
            name = stripQualifier(t.getName());
            break;
        }
        case ASTNode.QUALIFIED_TYPE: {
            QualifiedType t = cast(type);
            name = stripQualifier(t.getName());
            break;
        }
        case ASTNode.PARAMETERIZED_TYPE: {
            ParameterizedType t = cast(type);
            return toSimpleName(t.getType());
        }
        case ASTNode.PRIMITIVE_TYPE: {
            PrimitiveType t = cast(type);
            return t.getPrimitiveTypeCode().toString();
        }
        case ASTNode.WILDCARD_TYPE: {
            WildcardType t = cast(type);
            return toSimpleName(t.getBound());
        }
        case ASTNode.UNION_TYPE: {
            // TODO: that will probably not work with any name matching...
            UnionType t = cast(type);
            return "UnionType" + t.types().toString(); //$NON-NLS-1$
        }
        case ASTNode.ARRAY_TYPE: {
            ArrayType t = cast(type);
            return toSimpleName(t.getElementType()) + repeat("[]", t.getDimensions()); //$NON-NLS-1$
        }
        default:
            throw throwUnreachable("No support for type '%s'", type); //$NON-NLS-1$
        }

        return name.getIdentifier();
    }

    public static int getLineNumberOfNodeStart(final CompilationUnit cuNode, final ASTNode node) {
        final int startPosition = node.getStartPosition();
        return cuNode.getLineNumber(startPosition);
    }

    public static int getLineNumberOfNodeEnd(final CompilationUnit cuNode, final ASTNode node) {
        final int endPosition = node.getStartPosition() + node.getLength();
        return cuNode.getLineNumber(endPosition);
    }

    public static boolean haveSameNumberOfParameters(final List<SingleVariableDeclaration> jdtParams,
            final ITypeName[] crParams) {
        return crParams.length == jdtParams.size();
    }

    public static boolean sameSimpleName(final MethodDeclaration decl, final IMethodName crMethod) {
        final String methodName = decl.getName().toString();
        if (crMethod.isInit()) {
            final ITypeName declaringType = crMethod.getDeclaringType();
            final String className = declaringType.getClassName();
            final boolean sameMethodName = className.equals(methodName);
            return sameMethodName;
        }
        return methodName.equals(crMethod.getName());
    }

    public static boolean sameSimpleName(final MethodInvocation invoke, final IMethodName crMethod) {
        final String methodName = invoke.getName().toString();
        if (crMethod.isInit()) {
            final ITypeName declaringType = crMethod.getDeclaringType();
            final String className = declaringType.getClassName();
            final boolean sameMethodName = className.equals(methodName);
            return sameMethodName;
        }
        return methodName.equals(crMethod.getName());
    }

    public static boolean sameTypes(final List<Type> jdtTypes, final ITypeName[] crTypes) {
        for (int i = crTypes.length; --i > 0;) {
            final Type jdtType = jdtTypes.get(i);
            final ITypeName crType = crTypes[i];
            if (!sameType(jdtType, crType)) {
                return false;
            }
        }
        return true;
    }

    public static boolean sameType(final Type jdtType, final ITypeName crType) {
        if (jdtType == null || crType == null) {
            return false;
        }
        //
        if (jdtType.isArrayType() || jdtType.isPrimitiveType()) {
            return true;
        }
        if (jdtType.isSimpleType() && !sameSimpleName(jdtType, crType)) {
            return false;
        }
        final ITypeBinding jdtTypeBinding = jdtType.resolveBinding();
        return sameType(jdtTypeBinding, crType);
    }

    public static boolean sameType(final ITypeBinding jdtType, final ITypeName crType) {
        final Optional<ITypeName> opt = AstBindings.toTypeName(jdtType);
        if (opt.isPresent()) {
            return opt.get().equals(crType);
        }
        return false;
    }

    /**
     * Returns the closes parent ASTnode of the given node-class. Returns the input node if the node already is of the
     * requested type.
     */
    @SuppressWarnings("unchecked")
    public static <T extends ASTNode> Optional<T> getClosestParent(ASTNode node, final Class<T> nodeClass) {

        while (node != null) {
            if (nodeClass.isInstance(node)) {
                return (Optional<T>) of(node);
            }
            node = node.getParent();
        }
        return absent();
    }

    public static Optional<MethodDeclaration> find(final CompilationUnit cu, final IMethod method) {
        try {
            final ISourceRange nameRange = method.getNameRange();
            if (nameRange == null || nameRange.getOffset() == -1) {
                return useVisitor(cu, method);
            }
            final ASTNode node = NodeFinder.perform(cu, nameRange);
            return getClosestParent(node, MethodDeclaration.class);
        } catch (JavaModelException e) {
            log(ERROR_FAILED_TO_RESOLVE_METHOD, e, method, cu);
            return absent();
        }
    }

    private static Optional<MethodDeclaration> useVisitor(final CompilationUnit cu, final IMethod member) {

        return new Finder<Optional<MethodDeclaration>>() {

            private MethodDeclaration res;

            @Override
            public Optional<MethodDeclaration> call() {
                try {
                    cu.accept(this);
                } catch (final Exception e) {

                }
                return Optional.of(res);
            }

            @Override
            public boolean visit(final MethodDeclaration node) {
                final IMethodBinding b = node.resolveBinding();
                if (member.equals(b.getJavaElement())) {
                    res = node;
                    throwCancelationException();
                }
                return true;
            }
        }.call();

    }

    private abstract static class Finder<T> extends ASTVisitor implements Callable<T> {
    }
}
