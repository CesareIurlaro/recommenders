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
package org.eclipse.recommenders.internal.overrides.rcp;

public final class Constants {

    private Constants() {
        throw new IllegalStateException("Not meant to be instantiated"); //$NON-NLS-1$
    }

    /**
     * Bundle symbolic name of the o.e.r.calls.rcp bundle.
     */
    public static final String BUNDLE_NAME = "org.eclipse.recommenders.overrides.rcp"; //$NON-NLS-1$

    /**
     * minimum probability a proposal needs to have before displaying it in the UI.
     */
    public static final String PREF_MIN_PROPOSAL_PROBABILITY = "min_proposal_probability"; //$NON-NLS-1$

    /**
     * The maximal number of proposals recommended by the recommender.
     */
    public static final String PREF_MAX_NUMBER_OF_PROPOSALS = "max_number_of_proposals"; //$NON-NLS-1$

    /**
     * Preference key for enabling a proposal relevance adjustment.
     */
    public static final String PREF_UPDATE_PROPOSAL_RELEVANCE = "change_proposal_relevance"; //$NON-NLS-1$

    /**
     * Preference name for proposal icon decoration enablement.
     */
    public static final String PREF_DECORATE_PROPOSAL_ICON = "decorate_proposal_icons"; //$NON-NLS-1$

    /**
     * Preference name to decorate the completion proposal's display string.
     */
    public static final String PREF_DECORATE_PROPOSAL_TEXT = "decorate_proposal_text"; //$NON-NLS-1$
}
