/*******************************************************************************
 * Copyright (c) 2006, 2016 IBM Corporation and others.
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

package com.tlcsdm.eclipse.examples.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;

/**
 * This tab demonstrates various line joins. It allows a user to choose from
 * bevel, miter and round.
 */
public class LineJoinTab extends GraphicsTab {

    private Combo joinCb;
    private Button colorButton;
    private GraphicsBackground shapeColor;
    private Menu menu;
    private int[] joinValues = new int[]{SWT.JOIN_BEVEL, SWT.JOIN_MITER, SWT.JOIN_ROUND};

    public LineJoinTab(GraphicsExample example) {
        super(example);
    }

    @Override
    public String getCategory() {
        return GraphicsExample.getResourceString("Lines"); //$NON-NLS-1$
    }

    @Override
    public String getText() {
        return GraphicsExample.getResourceString("LineJoin"); //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return GraphicsExample.getResourceString("LineJoinDescription"); //$NON-NLS-1$
    }

    @Override
    public void dispose() {
        if (menu != null) {
            menu.dispose();
            menu = null;
        }
    }

    @Override
    public void createControlPanel(Composite parent) {

        // create drop down combo for choosing clipping
        Composite comp = new Composite(parent, SWT.NONE);
        comp.setLayout(new GridLayout(2, false));

        new Label(comp, SWT.CENTER).setText(GraphicsExample
            .getResourceString("LineJoin")); //$NON-NLS-1$
        joinCb = new Combo(comp, SWT.DROP_DOWN);
        joinCb.add(GraphicsExample
            .getResourceString("bevel")); //$NON-NLS-1$
        joinCb.add(GraphicsExample
            .getResourceString("miter")); //$NON-NLS-1$
        joinCb.add(GraphicsExample
            .getResourceString("round")); //$NON-NLS-1$
        joinCb.select(1);
        joinCb.addListener(SWT.Selection, event -> example.redraw());

        // color menu
        ColorMenu cm = new ColorMenu();
        cm.setPatternItems(example.checkAdvancedGraphics());
        menu = cm.createMenu(parent.getParent(), gb -> {
            shapeColor = gb;
            colorButton.setImage(gb.getThumbNail());
            example.redraw();
        });

        // initialize the shape color to the 4th item in the menu (green)
        shapeColor = (GraphicsBackground) menu.getItem(3).getData();

        // color button
        comp = new Composite(parent, SWT.NONE);
        comp.setLayout(new GridLayout(2, false));

        colorButton = new Button(comp, SWT.PUSH);
        colorButton.setText(GraphicsExample
            .getResourceString("Color")); //$NON-NLS-1$
        colorButton.setImage(shapeColor.getThumbNail());
        colorButton.addListener(SWT.Selection, event -> {
            final Button button = (Button) event.widget;
            final Composite parent1 = button.getParent();
            Rectangle bounds = button.getBounds();
            Point point = parent1.toDisplay(new Point(bounds.x, bounds.y));
            menu.setLocation(point.x, point.y + bounds.height);
            menu.setVisible(true);
        });

    }

    @Override
    public void paint(GC gc, int width, int height) {
        if (!example.checkAdvancedGraphics()) return;
        Device device = gc.getDevice();

        gc.setLineWidth(20);
        gc.setLineJoin(joinValues[joinCb.getSelectionIndex()]);

        // set the foreground color or pattern
        Pattern pattern = null;
        if (shapeColor.getBgColor1() != null) {
            gc.setForeground(shapeColor.getBgColor1());
        } else if (shapeColor.getBgImage() != null) {
            pattern = new Pattern(device, shapeColor.getBgImage());
            gc.setForegroundPattern(pattern);
        }

        // draw the shape
        Path path = new Path(device);
        path.moveTo(width / 2, 25);
        path.lineTo(2 * width / 3, height / 3);
        path.lineTo(width - 25, height / 2);
        path.lineTo(2 * width / 3, 2 * height / 3);
        path.lineTo(width / 2, height - 25);
        path.lineTo(width / 3, 2 * height / 3);
        path.lineTo(25, height / 2);
        path.lineTo(width / 3, height / 3);
        path.lineTo(width / 2, 25);
        path.close();
        gc.drawPath(path);
        path.dispose();

        if (pattern != null) pattern.dispose();
    }

}


