/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tlcsdm.eclipse.examples.swt.paint;

import org.eclipse.swt.events.*;

/**
 * Manages an interactive paint session.
 * Note that the coordinates received via the listener interfaces are virtualized to zero-origin
 * relative to the painting surface.
 */
public interface PaintSession extends MouseListener, MouseMoveListener {
    /**
     * Returns the paint surface associated with this paint session
     *
     * @return the associated PaintSurface
     */
    public PaintSurface getPaintSurface();

    /**
     * Activates the session.
     * <p>
     * Note: When overriding this method, call super.beginSession() at method start.
     */
    public abstract void beginSession();

    /**
     * Deactivates the session.
     * <p>
     * Note: When overriding this method, call super.endSession() at method exit.
     */
    public abstract void endSession();

    /**
     * Resets the session.
     * Aborts any operation in progress.
     * <p>
     * Note: When overriding this method, call super.resetSession() at method exit.
     */
    public abstract void resetSession();

    /**
     * Returns the name associated with this tool.
     *
     * @return the localized name of this tool
     */
    public String getDisplayName();
}
