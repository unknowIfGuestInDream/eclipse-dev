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
 * A polyline drawing tool.
 */
public class PolyLineTool extends SegmentedPaintSession implements PaintTool {
    private ToolSettings settings;

    /**
     * Constructs a PolyLineTool.
     *
     * @param toolSettings the new tool settings
     * @param paintSurface the PaintSurface we will render on.
     */
    public PolyLineTool(ToolSettings toolSettings, PaintSurface paintSurface) {
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
     * Returns the name associated with this tool.
     *
     * @return the localized name of this tool
     */
    @Override
    public String getDisplayName() {
        return PaintExample.getResourceString("tool.PolyLine.label");
    }

    /*
     * Template methods for drawing
     */
    @Override
    protected Figure createFigure(Point[] points, int numPoints, boolean closed) {
        ContainerFigure container = new ContainerFigure();
        if (closed && settings.commonFillType != ToolSettings.ftNone && numPoints >= 3) {
            container.add(new SolidPolygonFigure(settings.commonBackgroundColor, points, numPoints));
        }
        if (!closed || settings.commonFillType != ToolSettings.ftSolid || numPoints < 3) {
            for (int i = 0; i < numPoints - 1; ++i) {
                final Point a = points[i];
                final Point b = points[i + 1];
                container.add(new LineFigure(settings.commonForegroundColor, settings.commonBackgroundColor, settings.commonLineStyle,
                    a.x, a.y, b.x, b.y));
            }
            if (closed) {
                final Point a = points[points.length - 1];
                final Point b = points[0];
                container.add(new LineFigure(settings.commonForegroundColor, settings.commonBackgroundColor, settings.commonLineStyle,
                    a.x, a.y, b.x, b.y));
            }
        }
        return container;
    }
}
