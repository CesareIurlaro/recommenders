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
package completion.templates;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class CompletionOnUnqualifiedSimpleTypeInMethodBody extends Dialog {

    @Override
	protected Control createDialogArea(final Composite parent) {
		// give patterns and import button then
		// ensure Button is not imported as SWT or AWT Button before
		Button<^Space|dynamic.*55.*%>
		return null;
	}

    private CompletionOnUnqualifiedSimpleTypeInMethodBody() {
        super((IShellProvider) null);
    }
}