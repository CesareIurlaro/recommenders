/**
 * Copyright (c) 2011 Stefan Henss.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Henss - initial API and implementation.
 */
package org.eclipse.recommenders.commons.internal.selection;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

/**
 * Resolves the active java element's AST node for an invocation context.
 */
public final class AstNodeResolver {

    private static final ASTParser PARSER = ASTParser.newParser(AST.JLS3);

    /**
     * Private constructor to avoid instantiation of helper class.
     */
    private AstNodeResolver() {
    }

    /**
     * @param invocationContext
     *            The invocation context holding the compilation unit and
     *            location information.
     * @return The AST node for the active java element.
     */
    public static ASTNode resolveNode(final JavaContentAssistInvocationContext invocationContext) {
        if (invocationContext == null) {
            return null;
        }
        final ASTNode astRoot = resolveAst(invocationContext.getCompilationUnit());
        final int invocationOffset = invocationContext.getInvocationOffset();
        return NodeFinder.perform(astRoot, invocationOffset, 0);
    }

    /**
     * @param compilationUnit
     *            The compilation unit from which to extract the AST.
     * @return The compilation unit's AST.
     */
    private static ASTNode resolveAst(final ICompilationUnit compilationUnit) {
        PARSER.setResolveBindings(true);
        PARSER.setSource(compilationUnit);
        return PARSER.createAST(null);
    }

}
