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
package org.eclipse.recommenders.internal.server.extdoc;

import org.eclipse.recommenders.rcp.extdoc.IProvider;
import org.eclipse.recommenders.rcp.extdoc.features.IStarsRatingsServer;
import org.eclipse.recommenders.server.extdoc.types.Rating;

abstract class AbstractRatingsServer implements IStarsRatingsServer {

    @Override
    public final int getAverageRating(final Object object, final IProvider provider) {
        final String providerId = provider.getClass().getSimpleName();
        final String objectId = String.valueOf(object.hashCode());
        final RatingSummary stats = true ? new RatingSummary() : Server.getProviderContent("stars", providerId,
                "object", objectId, RatingSummary.class);
        return stats.count == 0 ? 0 : stats.sum / stats.count;
    }

    @Override
    public final int getUserRating(final Object object, final IProvider provider) {
        return -1;
    }

    @Override
    public final void addRating(final Object object, final int stars, final IProvider provider) {
        final Rating rating = Rating.create(provider, object, stars);
        Server.post(rating);
    }

    private static final class RatingSummary {
        private int sum;
        private int count;
    }

}
