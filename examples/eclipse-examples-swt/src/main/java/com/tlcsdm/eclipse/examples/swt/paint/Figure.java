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

import org.eclipse.swt.graphics.*;

/**
 * Superinterface for all drawing objects.
 * All drawing objects know how to render themselved to the screen and can draw a
 * temporary version of themselves for previewing the general appearance of the
 * object onscreen before it gets committed.
 */
public abstract class Figure {
    /**
     * Draws this object.
     *
     * @param fdc a parameter block specifying drawing-related information
     */
    public abstract void draw(FigureDrawContext fdc);

    /**
     * Computes the damaged screen region caused by drawing this object (imprecise), then
     * appends it to the supplied region.
     *
     * @param fdc    a parameter block specifying drawing-related information
     * @param region a region to which additional damage areas will be added
     */
    public abstract void addDamagedRegion(FigureDrawContext fdc, Region region);
}
