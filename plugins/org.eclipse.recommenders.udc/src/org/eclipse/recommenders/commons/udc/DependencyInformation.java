/**
 * Copyright (c) 2010 Darmstadt University of Technology.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Johannes Lerch - initial API and implementation.
 */
package org.eclipse.recommenders.commons.udc;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.recommenders.utils.Version;

public class DependencyInformation {

    public String symbolicName;
    public Version version;
    public String jarFileFingerprint;
    public Date jarFileModificationDate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    public static DependencyInformation create(final String symbolicName, final Version version,
            final String jarFingerprint) {
        final DependencyInformation res = new DependencyInformation();
        res.symbolicName = symbolicName;
        res.version = version;
        res.jarFileFingerprint = jarFingerprint;
        return res;
    }
}