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
package org.eclipse.recommenders.internal.completion.rcp.calls.wiring;

import java.io.IOException;

import org.eclipse.recommenders.injection.InjectionService;
import org.eclipse.recommenders.internal.completion.rcp.calls.store2.CallModelStore;
import org.eclipse.recommenders.utils.rcp.LoggingUtils;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;

public class CallsCompletionPlugin extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "org.eclipse.recommenders.completion.rcp.calls";

    private static CallsCompletionPlugin plugin;

    public static CallsCompletionPlugin getDefault() {
        return plugin;
    }

    @Override
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        // new ModelStoreInitializerJob().schedule(1000);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
        closeDependencyStore();
        plugin = null;
        super.stop(context);
    }

    private void closeDependencyStore() {
        try {
            final Injector injector = InjectionService.getInstance().getInjector();
            injector.getInstance(CallModelStore.class).close();
        } catch (final IOException e) {
            LoggingUtils.logError(e, this, "Exception while closing dependency store.");
        }
    }
}
