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

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;

/**
 * Tool Settings objects group tool-related configuration information.
 */
public class ToolSettings {
    public static final int ftNone = 0, ftOutline = 1, ftSolid = 2;

    /**
     * commonForegroundColor: current tool foreground colour
     */
    public Color commonForegroundColor;

    /**
     * commonBackgroundColor: current tool background colour
     */
    public Color commonBackgroundColor;

    /**
     * commonFont: current font
     */
    public Font commonFont;

    /**
     * commonFillType: current fill type
     * <p>One of ftNone, ftOutline, ftSolid.</p>
     */
    public int commonFillType = ftNone;

    /**
     * commonLineStyle: current line type
     */
    public int commonLineStyle = SWT.LINE_SOLID;

    /**
     * airbrushRadius: coverage radius in pixels
     */
    public int airbrushRadius = 10;

    /**
     * airbrushIntensity: average surface area coverage in region defined by radius per "jot"
     */
    public int airbrushIntensity = 30;

    /**
     * roundedRectangleCornerDiameter: the diameter of curvature of corners in a rounded rectangle
     */
    public int roundedRectangleCornerDiameter = 16;
}
