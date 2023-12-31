/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
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

import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;

public class IntroTab extends AnimatedGraphicsTab {

    Font font;
    Image image;
    Random random = new Random();
    float x, y;
    float incX = 10.0f;
    float incY = 5.0f;
    int textWidth, textHeight;
    String text = GraphicsExample.getResourceString("SWT");

    public IntroTab(GraphicsExample example) {
        super(example);
    }

    @Override
    public void dispose() {
        if (image != null) image.dispose();
        image = null;
        if (font != null) font.dispose();
        font = null;
    }

    @Override
    public String getCategory() {
        return GraphicsExample.getResourceString("Introduction"); //$NON-NLS-1$
    }

    @Override
    public String getText() {
        return GraphicsExample.getResourceString("SWT"); //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return GraphicsExample.getResourceString("IntroductionDescription"); //$NON-NLS-1$
    }

    @Override
    public void next(int width, int height) {
        x += incX;
        y += incY;
        float random = (float) Math.random();
        if (x + textWidth > width) {
            x = width - textWidth;
            incX = random * -width / 16 - 1;
        }
        if (x < 0) {
            x = 0;
            incX = random * width / 16 + 1;
        }
        if (y + textHeight > height) {
            y = (height - textHeight) - 2;
            incY = random * -height / 16 - 1;
        }
        if (y < 0) {
            y = 0;
            incY = random * height / 16 + 1;
        }
    }

    @Override
    public void paint(GC gc, int width, int height) {
        if (!example.checkAdvancedGraphics()) return;
        Device device = gc.getDevice();
        if (image == null) {
            image = example.loadImage(device, "irmaos.jpg");
            Rectangle rect = image.getBounds();
            FontData fd = device.getSystemFont().getFontData()[0];
            font = new Font(device, fd.getName(), rect.height / 4, SWT.BOLD);
            gc.setFont(font);
            Point size = gc.stringExtent(text);
            textWidth = size.x;
            textHeight = size.y;
        }
        Path path = new Path(device);
        path.addString(text, x, y, font);
        gc.setClipping(path);
        Rectangle rect = image.getBounds();
        gc.drawImage(image, 0, 0, rect.width, rect.height, 0, 0, width, height);
        gc.setClipping((Rectangle) null);
        gc.setForeground(device.getSystemColor(SWT.COLOR_BLUE));
        gc.drawPath(path);
        path.dispose();
    }
}
