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
package org.eclipse.recommenders.internal.rcp.extdoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.recommenders.rcp.extdoc.ExtDocPlugin;
import org.eclipse.recommenders.rcp.extdoc.IProvider;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class ProviderStore {

    private static final String EXTENSION_ID = "org.eclipse.recommenders.rcp.extdoc.provider";

    private final Map<IProvider, Integer> providers = new HashMap<IProvider, Integer>();

    public ProviderStore() {
        final IExtensionRegistry reg = Platform.getExtensionRegistry();
        for (final IConfigurationElement element : reg.getConfigurationElementsFor(EXTENSION_ID)) {
            try {
                final IProvider provider = (IProvider) element.createExecutableExtension("class");
                providers.put(provider, Integer.valueOf(element.getAttribute("priority")));
            } catch (final CoreException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public ImmutableList<IProvider> getProviders() {
        final List<IProvider> list = new ArrayList<IProvider>(providers.keySet());
        Collections.sort(list, new ProviderComparator(providers));
        return ImmutableList.copyOf(list);
    }

    public final void setProviderPriority(final IProvider provider, final int priority) {
        Preconditions.checkArgument(providers.containsKey(provider));
        Preconditions.checkArgument(priority > 0);
        ExtDocPlugin.getPreferences().putInt(getPreferenceId(provider), priority);
    }

    static int getPriorityFromPreferences(final IProvider provider) {
        return ExtDocPlugin.getPreferences().getInt(getPreferenceId(provider), -1);
    }

    private static String getPreferenceId(final IProvider provider) {
        return "priority" + provider.hashCode();
    }

    private static final class ProviderComparator implements Comparator<IProvider> {

        private final Map<IProvider, Integer> providers;

        ProviderComparator(final Map<IProvider, Integer> providers) {
            this.providers = providers;
        }

        @Override
        public int compare(final IProvider provider1, final IProvider provider2) {
            final int priorityPreference1 = getPriorityFromPreferences(provider1);
            final int priorityPreference2 = getPriorityFromPreferences(provider2);
            if (priorityPreference1 > -1 || priorityPreference2 > -1) {
                return Integer.valueOf(priorityPreference2).compareTo(Integer.valueOf(priorityPreference1));
            }
            return providers.get(provider2).compareTo(providers.get(provider1));
        }
    }

}
