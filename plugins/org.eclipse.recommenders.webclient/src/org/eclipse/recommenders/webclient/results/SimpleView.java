/**
 * Copyright (c) 2011 Sebastian Proksch.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Proksch - initial API and implementation
 */
package org.eclipse.recommenders.webclient.results;

/**
 * simple marker class to prevent tripple generic for documents wih simple string key
 */
public class SimpleView<T> extends GenericResultRowView<String, T, Object> {

}