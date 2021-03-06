/**
 * Copyright (c) 2010, 2013 Darmstadt University of Technology.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 */
package org.eclipse.recommenders.internal.calls.rcp;

import static org.eclipse.recommenders.completion.rcp.PreferenceConstants.*;
import static org.eclipse.recommenders.internal.calls.rcp.Constants.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public final class PreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IEclipsePreferences node = DefaultScope.INSTANCE.getNode(BUNDLE_NAME);
        node.putInt(PREF_MAX_NUMBER_OF_PROPOSALS, 7);
        node.putInt(PREF_MIN_PROPOSAL_PERCENTAGE, 0);
        node.putBoolean(PREF_DECORATE_PROPOSAL_ICON, true);
        node.putBoolean(PREF_DECORATE_PROPOSAL_ICON, true);
        node.putBoolean(PREF_DECORATE_PROPOSAL_TEXT, true);
        node.putBoolean(PREF_UPDATE_PROPOSAL_RELEVANCE, true);
        node.putBoolean(PREF_HIGHLIGHT_USED_PROPOSALS, true);
    }
}
