/*******************************************************************************
 * Copyright (c) 2006, 2014 IBM Corporation and others.
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

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;

/**
 * This tab demonstrates how an image can be flipped in various fashions.
 */
public class ImageFlipTab extends GraphicsTab {

    public ImageFlipTab(GraphicsExample example) {
        super(example);
    }

    @Override
    public String getCategory() {
        return GraphicsExample.getResourceString("Image"); //$NON-NLS-1$
    }

    @Override
    public String getText() {
        return GraphicsExample.getResourceString("Flip"); //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return GraphicsExample.getResourceString("FlipDescription"); //$NON-NLS-1$
    }

    @Override
    public void paint(GC gc, int width, int height) {
        if (!example.checkAdvancedGraphics()) return;
        Device device = gc.getDevice();

        Image image = GraphicsExample.loadImage(device, GraphicsExample.class, "houses.png");
        Rectangle bounds = image.getBounds();

        // top
        Transform transform = new Transform(device);
        transform.translate((width - bounds.width) / 2, (height - bounds.height) / 2);
        transform.scale(1, -1);
        gc.setTransform(transform);

        // draw the original image
        gc.drawImage(image, 0, 0);

        transform.dispose();

        // bottom
        transform = new Transform(device);
        transform.translate((width - bounds.width) / 2, 2 * bounds.height + (height - bounds.height) / 2);
        transform.scale(1, -1);
        gc.setTransform(transform);

        // draw the original image
        gc.drawImage(image, 0, 0);

        transform.dispose();

        // left
        transform = new Transform(device);
        transform.translate((width - bounds.width) / 2, (height - bounds.height) / 2);
        transform.scale(-1, 1);
        gc.setTransform(transform);

        // draw the original image
        gc.drawImage(image, 0, 0);

        transform.dispose();

        // right
        transform = new Transform(device);
        transform.translate(2 * bounds.width + (width - bounds.width) / 2, (height - bounds.height) / 2);
        transform.scale(-1, 1);
        gc.setTransform(transform);

        // draw the original image
        gc.drawImage(image, 0, 0);

        transform.dispose();

        gc.setTransform(null);
        gc.drawImage(image, (width - bounds.width) / 2, (height - bounds.height) / 2);
        image.dispose();
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

