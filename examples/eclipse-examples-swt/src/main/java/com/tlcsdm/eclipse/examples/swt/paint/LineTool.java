/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
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

import org.eclipse.swt.graphics.Point;

/**
 * A line drawing tool
 */
public class LineTool extends DragPaintSession implements PaintTool {
    private ToolSettings settings;

    /**
     * Constructs a LineTool.
     *
     * @param toolSettings the new tool settings
     * @param paintSurface the PaintSurface we will render on.
     */
    public LineTool(ToolSettings toolSettings, PaintSurface paintSurface) {
        super(paintSurface);
        set(toolSettings);
    }

    /**
     * Sets the tool's settings.
     *
     * @param toolSettings the new tool settings
     */
    @Override
    public void set(ToolSettings toolSettings) {
        settings = toolSettings;
    }

    /**
     * Returns name associated with this tool.
     *
     * @return the localized name of this tool
     */
    @Override
    public String getDisplayName() {
        return PaintExample.getResourceString("tool.Line.label");
    }

    /*
     * Template methods for drawing
     */
    @Override
    protected Figure createFigure(Point a, Point b) {
        return new LineFigure(settings.commonForegroundColor, settings.commonBackgroundColor, settings.commonLineStyle,
            a.x, a.y, b.x, b.y);
    }
}
