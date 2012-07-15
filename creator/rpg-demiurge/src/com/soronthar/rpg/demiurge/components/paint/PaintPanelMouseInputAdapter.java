package com.soronthar.rpg.demiurge.components.paint;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

import static com.soronthar.rpg.demiurge.CoordinateUtil.normalizePointToTile;


class PaintPanelMouseInputAdapter extends MouseInputAdapter {
    private boolean enabled = true;
    private PaintCanvasModel canvasModel;

    public PaintPanelMouseInputAdapter(PaintCanvasModel canvasModel) {
        this.canvasModel = canvasModel;
    }

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isMiddleMouseButton(e) || (SwingUtilities.isLeftMouseButton(e) && e.isControlDown())) {
            canvasModel.editSpecialAt(normalizePointToTile(e.getPoint()));
        } else {
            manipulateCanvas(e);
        }
    }


    public void mouseDragged(MouseEvent e) {
        manipulateCanvas(e);
    }

    /**
     * Moves the paint pointer inside the canvas.
     */
    public void mouseMoved(MouseEvent e) {
        //By checking that no button is actually beig pressed,
        //the same thing will not be rendered twice (once here and once in mouseDragged).
        if (e.getButton() != MouseEvent.NOBUTTON) return;

        movePaintPointer(e);
    }

    /**
     * Used to show the paint pointer when the canvas get the "focus". This has to be detected
     * manually (ie, focus is when the mouse enters the Canvas area) as the rendering of the
     * paint canvas is being controlled by the application.
     */
    public void mouseEntered(MouseEvent e) {
        movePaintPointer(e);
    }


    /**
     * Used to hide the paint pointer when the canvas loses the "focus". This has to be detected
     * manually (ie, focus is lost when the mouse leaves the Canvas area) as the rendering of the
     * paint canvas is being controlled by the application,
     */
    public void mouseExited(MouseEvent e) {
        hidePaintPointer(e);
    }


    private void manipulateCanvas(MouseEvent e) {
        final Point point = normalizePointToTile(e.getPoint());

        if (enabled) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                drawTile(point);
            } else {
                removeTile(point);
            }
            canvasModel.setPointerLocation(normalizePointToTile(e.getPoint()));
        }
    }


    private void movePaintPointer(MouseEvent e) {
        if (enabled) {
            canvasModel.setPointerLocation(normalizePointToTile(e.getPoint()));
        }
    }


    private void hidePaintPointer(MouseEvent e) {
        if (enabled) {
            canvasModel.registerAction(PaintCanvasModel.Action.HIDE_POINTER, normalizePointToTile(e.getPoint()));
        }
    }


    private void drawTile(Point point) {
        canvasModel.registerAction(PaintCanvasModel.Action.DRAW, normalizePointToTile(point));
    }

    private void removeTile(Point point) {
        canvasModel.registerAction(PaintCanvasModel.Action.ERASE, normalizePointToTile(point));
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
