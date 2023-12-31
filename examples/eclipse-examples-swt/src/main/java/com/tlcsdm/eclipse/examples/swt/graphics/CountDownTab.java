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
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 * This tab presents a count down from 5 to 1, then displays SWT.
 */
public class CountDownTab extends AnimatedGraphicsTab {

    final int startNumber = 5;            // number at which to start the countdown
    int nextNumber = startNumber;        // next number to be displayed
    int angle = -90;                    // angle used to rotate the bar

    Spinner lineWidthSpinner;            // spinner for line width
    Combo aliasCombo, lineCapCombo;        // combo for alias type and line cap
    int antialias, lineCap;                // antialias and linecap values
    int[] capValues = {SWT.CAP_FLAT, SWT.CAP_SQUARE, SWT.CAP_ROUND};
    int[] aliasValues = {SWT.DEFAULT, SWT.OFF, SWT.ON};

    /**
     * Constructor
     *
     * @param example A GraphicsExample
     */
    public CountDownTab(GraphicsExample example) {
        super(example);
    }

    /**
     * This method creates the controls specific to the tab. The call to the
     * createControlPanel method in the super class create the controls that are
     * defined in the super class.
     *
     * @param parent The parent composite
     */
    @Override
    public void createControlPanel(Composite parent) {
        super.createControlPanel(parent);

        if (nextNumber < 1)
            nextNumber = startNumber;

        // add selection listener to reset nextNumber after
        // the sequence has completed
        playItem.addListener(SWT.Selection, event -> {
            if (nextNumber < 1)
                nextNumber = startNumber;
        });

        Composite comp;
        GridLayout gridLayout = new GridLayout(2, false);

        // create spinner for line width
        comp = new Composite(parent, SWT.NONE);
        comp.setLayout(gridLayout);
        new Label(comp, SWT.CENTER).setText(GraphicsExample
            .getResourceString("LineWidth")); //$NON-NLS-1$
        lineWidthSpinner = new Spinner(comp, SWT.BORDER | SWT.WRAP);
        lineWidthSpinner.setSelection(20);
        lineWidthSpinner.setMinimum(1);
        lineWidthSpinner.setMaximum(100);
        lineWidthSpinner.addListener(SWT.Selection, event -> {
            if (!pauseItem.isEnabled()) {
                example.redraw();
            }
        });

        // create drop down combo for antialiasing
        comp = new Composite(parent, SWT.NONE);
        comp.setLayout(gridLayout);
        new Label(comp, SWT.CENTER).setText(GraphicsExample
            .getResourceString("Antialiasing")); //$NON-NLS-1$
        aliasCombo = new Combo(comp, SWT.DROP_DOWN);
        aliasCombo.add("DEFAULT");
        aliasCombo.add("OFF");
        aliasCombo.add("ON");
        aliasCombo.select(0);
        antialias = aliasValues[0];
        aliasCombo.addListener(SWT.Selection, event -> {
            antialias = aliasValues[aliasCombo.getSelectionIndex()];
            if (!pauseItem.isEnabled()) {
                example.redraw();
            }
        });

        // create drop down combo for line cap
        comp = new Composite(parent, SWT.NONE);
        comp.setLayout(gridLayout);
        new Label(comp, SWT.CENTER).setText(GraphicsExample
            .getResourceString("LineCap")); //$NON-NLS-1$
        lineCapCombo = new Combo(comp, SWT.DROP_DOWN);
        lineCapCombo.add("FLAT");
        lineCapCombo.add("SQUARE");
        lineCapCombo.add("ROUND");
        lineCapCombo.select(0);
        lineCap = capValues[0];
        lineCapCombo.addListener(SWT.Selection, event -> {
            lineCap = capValues[lineCapCombo.getSelectionIndex()];
            if (!pauseItem.isEnabled()) {
                example.redraw();
            }
        });
    }

    @Override
    public String getCategory() {
        return GraphicsExample.getResourceString("Misc"); //$NON-NLS-1$
    }

    @Override
    public String getText() {
        return GraphicsExample.getResourceString("Countdown"); //$NON-NLS-1$
    }

    @Override
    public String getDescription() {
        return GraphicsExample.getResourceString("CountdownDescription"); //$NON-NLS-1$
    }

    @Override
    public int getInitialAnimationTime() {
        return 28;
    }

    @Override
    public void next(int width, int height) {

        if (angle == 270) {
            nextNumber--;
            if (nextNumber < 1) {
                // stop animation
                setAnimation(false);
            }
            angle = -90;
        }
        angle += 10;
    }

    @Override
    public void paint(GC gc, int width, int height) {
        if (!example.checkAdvancedGraphics()) return;
        Device device = gc.getDevice();

        // diameter of the circle in pixels
        int diameter = ((width < height) ? width - 25 : height - 25);

        if (!getAnimation() && nextNumber == 0) {
            Font font = new Font(device, getPlatformFontFace(1), diameter / 2,
                SWT.NONE);
            gc.setFont(font);

            // display "SWT"
            gc.setForeground(device.getSystemColor(SWT.COLOR_DARK_BLUE));
            gc.setTextAntialias(SWT.ON);

            // determine the dimensions of the word
            String text = GraphicsExample.getResourceString("SWT");
            Point point = gc.stringExtent(text);
            int textWidth = point.x;
            int textHeight = point.y;
            gc.drawString(text, (width - textWidth) / 2,
                (height - textHeight) / 2, true);
            font.dispose();

        } else {

            Font font = new Font(device, getPlatformFontFace(0),
                6 * diameter / 10, SWT.NONE);
            gc.setFont(font);

            // set attributes from controls
            gc.setLineWidth(lineWidthSpinner.getSelection());
            gc.setLineCap(lineCap); // round line ends
            gc.setAntialias(antialias); // smooth jagged edges
            gc.setTextAntialias(antialias); // smooth jagged edges

            // draw the circles
            Path path = new Path(device);
            path.addArc((width - diameter) / 2, (height - diameter) / 2,
                diameter, diameter, 0, 360);
            path.addArc((width - diameter + 50) / 2,
                (height - diameter + 50) / 2, diameter - 50, diameter - 50,
                0, 360);
            gc.drawPath(path);
            gc.setBackground(device.getSystemColor(SWT.COLOR_RED));
            gc.fillPath(path);
            path.dispose();

            Point point = gc.stringExtent(Integer.toString(nextNumber));
            int textWidth = point.x;
            int textHeight = point.y;

            // draw the number
            gc.drawString(Integer.toString(nextNumber),
                (width - textWidth) / 2, (height - textHeight) / 2, true);

            // draw the rotating arm
            Transform transform = new Transform(device);
            transform.translate(width / 2, height / 2);
            transform.rotate(angle);
            gc.setTransform(transform);
            gc.setForeground(device.getSystemColor(SWT.COLOR_RED));
            gc.drawLine(0, 0, diameter / 2, 0);
            transform.dispose();

            font.dispose();
        }
    }

    /**
     * Returns the name of a valid font for the resident platform.
     *
     * @param index index is used to determine the appropriate font face
     */
    static String getPlatformFontFace(int index) {
        if (SWT.getPlatform() == "win32") {
            return new String[]{"Courier", "Impact"}[index];
        } else if (SWT.getPlatform() == "gtk") {
            return new String[]{"Courier", "Baekmuk Headline"}[index];
        } else {
            return new String[]{"Courier", "Verdana"}[index];
        }
    }
}
