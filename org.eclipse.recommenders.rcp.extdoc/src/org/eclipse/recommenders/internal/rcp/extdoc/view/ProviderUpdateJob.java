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
package org.eclipse.recommenders.internal.rcp.extdoc.view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.recommenders.commons.selection.IJavaElementSelection;
import org.eclipse.recommenders.rcp.extdoc.IProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.progress.UIJob;

class ProviderUpdateJob extends UIJob {

    private final ProvidersTable table;
    private final TableItem item;
    private final IProvider provider;
    private final IJavaElementSelection selection;

    public ProviderUpdateJob(final ProvidersTable table, final TableItem item, final IJavaElementSelection selection) {
        super("Updating " + ((IProvider) ((Control) item.getData()).getData()).getProviderFullName());
        super.setPriority(UIJob.SHORT);
        this.table = table;
        this.item = item;
        provider = (IProvider) ((Control) item.getData()).getData();
        this.selection = selection;
    }

    @Override
    public IStatus runInUIThread(final IProgressMonitor monitor) {
        final boolean hasContent = provider.selectionChanged(selection);
        table.setContentVisible(item, hasContent);
        return Status.OK_STATUS;
    }
}
