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
package org.eclipse.recommenders.rcp.extdoc.features;

import org.eclipse.recommenders.commons.utils.names.IName;
import org.eclipse.recommenders.tests.commons.extdoc.ExtDocUtils;
import org.eclipse.recommenders.tests.commons.extdoc.ServerUtils;
import org.eclipse.recommenders.tests.commons.extdoc.TestUtils;

import org.junit.Test;

public final class CommentsCompositeTest {

    @Test
    public void testCommentsComposite() {
        for (final IName name : TestUtils.getDefaultNames()) {
            final CommentsComposite composite = CommentsComposite.create(ExtDocUtils.getShell(), name,
                    ExtDocUtils.getTestProvider(), ServerUtils.getGenericServer());
            composite.addComment("Test");
        }
    }
}
