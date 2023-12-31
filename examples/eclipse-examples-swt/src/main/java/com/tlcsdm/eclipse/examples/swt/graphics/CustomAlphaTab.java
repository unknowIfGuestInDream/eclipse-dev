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
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Spinner;

/**
 * This tab demonstrates the use of alpha blending. It allows a user to specify
 * a custom alpha value.
 */
public class CustomAlphaTab extends AnimatedGraphicsTab {

    private Spinner alphaSpinner;
    private Button colorButton;
    private GraphicsBackground background;
    private Menu menu;
    private int angle;

    public CustomAlphaTab(GraphicsExample example) {
        super(example);
    }

    @Override
    public String getCategory() {
        return GraphicsExample.getResourceString("Alpha"); //$NON-NLS-1$
    }

    @Override
    public String getText() {
        return GraphicsExample.getResourceString("CustomAlpha"); //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return GraphicsExample.getResourceString("CustomAlphaDescription"); //$NON-NLS-1$
    }

    @Override
    public void dispose() {
        if (menu != null) {
            menu.dispose();
            menu = null;
        }
    }

    /**
     * Creates the widgets used to control the drawing.
     */
    @Override
    public void createControlPanel(Composite parent) {
        super.createControlPanel(parent);

        // create drop down combo for choosing clipping
        Composite comp;

        // create spinner for line width
        comp = new Composite(parent, SWT.NONE);
        comp.setLayout(new GridLayout(2, false));
        new Label(comp, SWT.CENTER).setText(GraphicsExample
            .getResourceString("Alpha")); //$NON-NLS-1$
        alphaSpinner = new Spinner(comp, SWT.BORDER | SWT.WRAP);
        alphaSpinner.setMinimum(0);
        alphaSpinner.setMaximum(255);
        alphaSpinner.setSelection(127);
        alphaSpinner.addListener(SWT.Selection, event -> example.redraw());

        // color menu
        ColorMenu cm = new ColorMenu();
        cm.setPatternItems(example.checkAdvancedGraphics());
        menu = cm.createMenu(parent.getParent(), gb -> {
            background = gb;
            colorButton.setImage(gb.getThumbNail());
            example.redraw();
        });

        // initialize the background to the 5th item in the menu (blue)
        background = (GraphicsBackground) menu.getItem(4).getData();

        // color button
        comp = new Composite(parent, SWT.NONE);
        comp.setLayout(new GridLayout(2, false));

        colorButton = new Button(comp, SWT.PUSH);
        colorButton.setText(GraphicsExample
            .getResourceString("Color")); //$NON-NLS-1$
        colorButton.setImage(background.getThumbNail());
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
    public void next(int width, int height) {

        angle = (angle + 1) % 360;
    }

    @Override
    public void paint(GC gc, int width, int height) {
        if (!example.checkAdvancedGraphics()) return;
        Device device = gc.getDevice();

        Pattern pattern = null;
        if (background.getBgColor1() != null) {
            gc.setBackground(background.getBgColor1());
        } else if (background.getBgImage() != null) {
            pattern = new Pattern(device, background.getBgImage());
            gc.setBackgroundPattern(pattern);
        }

        gc.setAntialias(SWT.ON);
        gc.setAlpha(alphaSpinner.getSelection());

        // rotate on center
        Transform transform = new Transform(device);
        transform.translate(width / 2, height / 2);
        transform.rotate(-angle);
        transform.translate(-width / 2, -height / 2);
        gc.setTransform(transform);
        transform.dispose();

        // choose the smallest between height and width
        int diameter = (height < width) ? height : width;

        Path path = new Path(device);
        path.addArc((width - diameter / 5) / 2, (height - diameter / 5) / 2, diameter / 5, diameter / 5, 0, 360);
        path.addArc(5 * (width - diameter / 8) / 12, 4 * (height - diameter / 8) / 12, diameter / 8, diameter / 8, 0, 360);
        path.addArc(7 * (width - diameter / 8) / 12, 8 * (height - diameter / 8) / 12, diameter / 8, diameter / 8, 0, 360);
        path.addArc(6 * (width - diameter / 12) / 12, 3 * (height - diameter / 12) / 12, diameter / 12, diameter / 12, 0, 360);
        path.addArc(6 * (width - diameter / 12) / 12, 9 * (height - diameter / 12) / 12, diameter / 12, diameter / 12, 0, 360);
        path.addArc(11.5f * (width - diameter / 18) / 20, 5 * (height - diameter / 18) / 18, diameter / 18, diameter / 18, 0, 360);
        path.addArc(8.5f * (width - diameter / 18) / 20, 13 * (height - diameter / 18) / 18, diameter / 18, diameter / 18, 0, 360);
        path.addArc(62f * (width - diameter / 25) / 100, 32 * (height - diameter / 25) / 100, diameter / 25, diameter / 25, 0, 360);
        path.addArc(39f * (width - diameter / 25) / 100, 67 * (height - diameter / 25) / 100, diameter / 25, diameter / 25, 0, 360);

        gc.fillPath(path);
        path.dispose();

        if (pattern != null) pattern.dispose();
    }
}
