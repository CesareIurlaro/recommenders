/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 */
package org.eclipse.recommenders.internal.types.rcp.l10n;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.recommenders.utils.Logs;
import org.eclipse.recommenders.utils.Logs.DefaultLogMessage;
import org.eclipse.recommenders.utils.Logs.ILogMessage;
import org.osgi.framework.Bundle;

public final class LogMessages extends DefaultLogMessage {

    private static int code = 1;

    private static final Bundle BUNDLE = Logs.getBundle(LogMessages.class);

    public static final ILogMessage ERROR_ACCESSING_SEARCHINDEX_FAILED = new LogMessages(IStatus.ERROR,
            Messages.LOG_ERROR_ACCESSING_SEARCHINDEX_FAILED);
    public static final ILogMessage ERROR_ACCESSING_TYPE_HIERARCHY = new LogMessages(IStatus.ERROR,
            Messages.LOG_ERROR_ACCESSING_TYPE_HIERARCHY);
    public static final ILogMessage ERROR_CLOSING_PROJECT_TYPES_INDEXES = new LogMessages(IStatus.ERROR,
            Messages.LOG_ERROR_CLOSING_PROJECT_TYPES_INDEXES);

    public static final ILogMessage INFO_REINDEXING_REQUIRED = new LogMessages(IStatus.INFO,
            Messages.LOG_INFO_REINDEXING_REQUIRED);

    private LogMessages(int severity, String message) {
        super(severity, code++, message);
    }

    @Override
    public Bundle bundle() {
        return BUNDLE;
    }
}
