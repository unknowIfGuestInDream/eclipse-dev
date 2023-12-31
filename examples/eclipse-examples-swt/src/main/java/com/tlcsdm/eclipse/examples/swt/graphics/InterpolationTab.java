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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This tab shows the effects of applying various interpolation algorithms to
 * images that have been stretched or shrunk.
 */
public class InterpolationTab extends GraphicsTab {

    Combo imageCb;    // combo for selecting images

    public InterpolationTab(GraphicsExample example) {
        super(example);
    }

    @Override
    public String getCategory() {
        return GraphicsExample.getResourceString("Image"); //$NON-NLS-1$
    }

    @Override
    public String getText() {
        return GraphicsExample.getResourceString("Interpolation"); //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return GraphicsExample.getResourceString("ImageInterpolationDesc"); //$NON-NLS-1$
    }

    @Override
    public void createControlPanel(Composite parent) {

        Composite comp;
        GridLayout gridLayout = new GridLayout(2, false);

        // create drop down combo
        comp = new Composite(parent, SWT.NONE);
        comp.setLayout(gridLayout);
        new Label(comp, SWT.CENTER).setText(GraphicsExample
            .getResourceString("Image")); //$NON-NLS-1$
        imageCb = new Combo(comp, SWT.DROP_DOWN);
        imageCb.add(GraphicsExample.getResourceString("House")); //$NON-NLS-1$
        imageCb.add(GraphicsExample.getResourceString("Question")); //$NON-NLS-1$
        imageCb.add(GraphicsExample.getResourceString("Task")); //$NON-NLS-1$
        imageCb.add(GraphicsExample.getResourceString("Font")); //$NON-NLS-1$
        imageCb.add(GraphicsExample.getResourceString("Cube")); //$NON-NLS-1$
        imageCb.add(GraphicsExample.getResourceString("SWT")); //$NON-NLS-1$
        imageCb.add(GraphicsExample.getResourceString("Ovals")); //$NON-NLS-1$
        imageCb.select(0);
        imageCb.addListener(SWT.Selection, event -> example.redraw());
    }

    @Override
    public void paint(GC gc, int width, int height) {
        if (!example.checkAdvancedGraphics()) return;
        Device device = gc.getDevice();

        float scaleX = 10f;
        float scaleY = 10f;
        Image image = null;
        switch (imageCb.getSelectionIndex()) {

            case 0:
                image = GraphicsExample.loadImage(device, GraphicsExample.class, "home_nav.gif");
                break;
            case 1:
                image = GraphicsExample.loadImage(device, GraphicsExample.class, "help.gif");
                break;
            case 2:
                image = GraphicsExample.loadImage(device, GraphicsExample.class, "task.gif");
                break;
            case 3:
                image = GraphicsExample.loadImage(device, GraphicsExample.class, "font.gif");
                break;
            case 4:
                image = GraphicsExample.loadImage(device, GraphicsExample.class, "cube.png");
                scaleX = 0.75f;
                scaleY = 0.5f;
                break;
            case 5:
                image = GraphicsExample.loadImage(device, GraphicsExample.class, "swt.png");
                scaleX = 0.4f;
                scaleY = 0.8f;
                break;
            case 6:
                image = GraphicsExample.loadImage(device, GraphicsExample.class, "ovals.png");
                scaleX = 1.1f;
                scaleY = 0.5f;
                break;
        }

        Rectangle bounds = image.getBounds();

        // draw the original image
        gc.drawImage(image, (width - bounds.width) / 2, 20);

        Font font = new Font(device, getPlatformFont(), 20, SWT.NORMAL);
        gc.setFont(font);

        // write some text below the original image
        String text = GraphicsExample.getResourceString("OriginalImg"); //$NON-NLS-1$
        Point size = gc.stringExtent(text);
        gc.drawString(text, (width - size.x) / 2, 25 + bounds.height, true);

        Transform transform = new Transform(device);
        transform.translate((width - (bounds.width * scaleX + 10) * 4) / 2, 25 + bounds.height + size.y +
            (height - (25 + bounds.height + size.y + bounds.height * scaleY)) / 2);
        transform.scale(scaleX, scaleY);

        // --- draw strings ---
        float[] point = new float[2];
        text = GraphicsExample.getResourceString("None"); //$NON-NLS-1$
        size = gc.stringExtent(text);
        point[0] = (scaleX * bounds.width + 5 - size.x) / (2 * scaleX);
        point[1] = bounds.height;
        transform.transform(point);
        gc.drawString(text, (int) point[0], (int) point[1], true);

        text = GraphicsExample.getResourceString("Low"); //$NON-NLS-1$
        size = gc.stringExtent(text);
        point[0] = (scaleX * bounds.width + 5 - size.x) / (2 * scaleX) + bounds.width;
        point[1] = bounds.height;
        transform.transform(point);
        gc.drawString(text, (int) point[0], (int) point[1], true);

        text = GraphicsExample.getResourceString("Default"); //$NON-NLS-1$
        size = gc.stringExtent(text);
        point[0] = (scaleX * bounds.width + 5 - size.x) / (2 * scaleX) + 2 * bounds.width;
        point[1] = bounds.height;
        transform.transform(point);
        gc.drawString(text, (int) point[0], (int) point[1], true);

        text = GraphicsExample.getResourceString("High"); //$NON-NLS-1$
        size = gc.stringExtent(text);
        point[0] = (scaleX * bounds.width + 5 - size.x) / (2 * scaleX) + 3 * bounds.width;
        point[1] = bounds.height;
        transform.transform(point);
        gc.drawString(text, (int) point[0], (int) point[1], true);

        gc.setTransform(transform);
        transform.dispose();

        // --- draw images ---

        // no interpolation
        gc.setInterpolation(SWT.NONE);
        gc.drawImage(image, 0, 0);

        // low interpolation
        gc.setInterpolation(SWT.LOW);
        gc.drawImage(image, bounds.width, 0);

        // default interpolation
        gc.setInterpolation(SWT.DEFAULT);
        gc.drawImage(image, 2 * bounds.width, 0);

        // high interpolation
        gc.setInterpolation(SWT.HIGH);
        gc.drawImage(image, 3 * bounds.width, 0);

        font.dispose();
        if (image != null) image.dispose();
    }

    /**
     * Returns the name of a valid font for the host platform.
     */
    static String getPlatformFont() {
        if (SWT.getPlatform() == "win32") {
            return "Arial";
        } else if (SWT.getPlatform() == "gtk") {
            return "Baekmuk Batang";
        } else {
            return "Verdana";
        }
    }
}
