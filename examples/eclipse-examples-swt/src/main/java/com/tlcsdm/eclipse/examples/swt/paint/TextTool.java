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

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A text drawing tool.
 */
public class TextTool extends BasicPaintSession implements PaintTool {
    private ToolSettings settings;
    private String drawText = PaintExample.getResourceString("tool.Text.settings.defaulttext");

    /**
     * Constructs a PaintTool.
     *
     * @param toolSettings the new tool settings
     * @param paintSurface the PaintSurface we will render on.
     */
    public TextTool(ToolSettings toolSettings, PaintSurface paintSurface) {
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
        return PaintExample.getResourceString("tool.Text.label");
    }

    /**
     * Activates the tool.
     */
    @Override
    public void beginSession() {
        getPaintSurface().setStatusMessage(PaintExample.getResourceString(
            "session.Text.message"));
    }

    /**
     * Deactivates the tool.
     */
    @Override
    public void endSession() {
        getPaintSurface().clearRubberbandSelection();
    }

    /**
     * Aborts the current operation.
     */
    @Override
    public void resetSession() {
        getPaintSurface().clearRubberbandSelection();
    }

    /**
     * Handles a mouseDown event.
     *
     * @param event the mouse event detail information
     */
    @Override
    public void mouseDown(MouseEvent event) {
        if (event.button == 1) {
            // draw with left mouse button
            getPaintSurface().commitRubberbandSelection();
        } else {
            // set text with right mouse button
            getPaintSurface().clearRubberbandSelection();
            Shell shell = getPaintSurface().getShell();
            final Shell dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
            dialog.setText(PaintExample.getResourceString("tool.Text.dialog.title"));
            dialog.setLayout(new GridLayout());
            Label label = new Label(dialog, SWT.NONE);
            label.setText(PaintExample.getResourceString("tool.Text.dialog.message"));
            label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
            final Text field = new Text(dialog, SWT.SINGLE | SWT.BORDER);
            field.setText(drawText);
            field.selectAll();
            field.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            Composite buttons = new Composite(dialog, SWT.NONE);
            GridLayout layout = new GridLayout(2, true);
            layout.marginWidth = 0;
            buttons.setLayout(layout);
            buttons.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
            Button ok = new Button(buttons, SWT.PUSH);
            ok.setText(PaintExample.getResourceString("OK"));
            ok.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
            ok.addSelectionListener(widgetSelectedAdapter(e -> {
                drawText = field.getText();
                dialog.dispose();
            }));
            Button cancel = new Button(buttons, SWT.PUSH);
            cancel.setText(PaintExample.getResourceString("Cancel"));
            cancel.addSelectionListener(widgetSelectedAdapter(e -> dialog.dispose()));
            dialog.setDefaultButton(ok);
            dialog.pack();
            dialog.open();
            Display display = dialog.getDisplay();
            while (!shell.isDisposed() && !dialog.isDisposed()) {
                if (!display.readAndDispatch()) display.sleep();
            }
        }
    }

    /**
     * Handles a mouseDoubleClick event.
     *
     * @param event the mouse event detail information
     */
    @Override
    public void mouseDoubleClick(MouseEvent event) {
    }

    /**
     * Handles a mouseUp event.
     *
     * @param event the mouse event detail information
     */
    @Override
    public void mouseUp(MouseEvent event) {
    }

    /**
     * Handles a mouseMove event.
     *
     * @param event the mouse event detail information
     */
    @Override
    public void mouseMove(MouseEvent event) {
        final PaintSurface ps = getPaintSurface();
        ps.setStatusCoord(ps.getCurrentPosition());
        ps.clearRubberbandSelection();
        ps.addRubberbandSelection(
            new TextFigure(settings.commonForegroundColor, settings.commonFont,
                drawText, event.x, event.y));
    }
}
