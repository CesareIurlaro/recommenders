/**
 * Copyright (c) 2010, 2011 Darmstadt University of Technology.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Proksch - initial API and implementation
 */
package org.eclipse.recommenders.extdoc.rcp;

import org.eclipse.recommenders.utils.Throws;
import org.eclipse.swt.widgets.Display;

public abstract class Provider {

    private boolean isEnabled = true;

    private static class ExceptionHandler {
        private Exception e;

        private void setException(Exception e) {
            this.e = e;
        }

        private void throwExceptionIfExistent() {
            if (e != null) {
                Throws.throwUnhandledException(e);
            }
        }
    }

    protected void runSyncInUiThread(final Runnable runnable) {
        final ExceptionHandler handler = new ExceptionHandler();
        Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    handler.setException(e);
                }
            }
        });
        handler.throwExceptionIfExistent();
    }

    public abstract ProviderDescription getDescription();

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}