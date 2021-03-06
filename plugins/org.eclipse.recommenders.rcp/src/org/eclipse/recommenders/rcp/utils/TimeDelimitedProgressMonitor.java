/**
 * Copyright (c) 2010, 2012 Darmstadt University of Technology.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 */
package org.eclipse.recommenders.rcp.utils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

@Deprecated
public class TimeDelimitedProgressMonitor implements IProgressMonitor {

    private final IProgressMonitor delegate;

    private final long start = System.currentTimeMillis();
    private final long limit;

    public TimeDelimitedProgressMonitor(IProgressMonitor delegate, long limitInMillis) {
        this.delegate = delegate == null ? new NullProgressMonitor() : delegate;
        limit = limitInMillis;
    }

    public TimeDelimitedProgressMonitor(long limitInMillis) {
        this(new NullProgressMonitor(), limitInMillis);
    }

    @Override
    public void beginTask(String name, int totalWork) {
        delegate.beginTask(name, totalWork);
    }

    @Override
    public void done() {
        delegate.done();
    }

    @Override
    public void internalWorked(double work) {
        delegate.internalWorked(work);
    }

    @Override
    public boolean isCanceled() {
        return delegate.isCanceled() || timedOut();
    }

    private boolean timedOut() {
        return System.currentTimeMillis() - start > limit;
    }

    @Override
    public void setCanceled(boolean value) {
        delegate.setCanceled(value);
    }

    @Override
    public void setTaskName(String name) {
        delegate.setTaskName(name);
    }

    @Override
    public void subTask(String name) {
        delegate.subTask(name);
    }

    @Override
    public void worked(int work) {
        delegate.worked(work);
    }
}
