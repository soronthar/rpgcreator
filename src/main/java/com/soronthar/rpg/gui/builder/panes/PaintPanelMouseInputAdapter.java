package com.soronthar.rpg.gui.builder.panes;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

import static com.soronthar.rpg.Utils.normalizePointToTile;

/**
 * mousePressed and mouseReleased are used to identify which button is being used
 * when using a drag gesture, allowing to keep drawing or removing tiles as long
 * as the button is pressed.
 */
class PaintPanelMouseInputAdapter extends MouseInputAdapter {
    private PaintPanel paintPanel;
    private int clickButton = MouseEvent.NOBUTTON;

    public PaintPanelMouseInputAdapter(PaintPanel paintPanel) {
        this.paintPanel = paintPanel;
    }

    /**
     * Records which button is being pressed. This is used during the processing of a drag gesture
     * to determine the actionto take (draw tile or delete tile)
     *
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        clickButton = e.getButton();
    }

    /**
     * When the mouse is released, clean up the house.
     *
     * @param e
     */
    public void mouseReleased(MouseEvent e) {
        clickButton = MouseEvent.NOBUTTON;
    }

    public void mouseClicked(MouseEvent e) {
        manipulateCanvas(e, e.getButton());
    }

    public void mouseDragged(MouseEvent e) {
        manipulateCanvas(e, clickButton);
    }

    /**
     * Moves the paint pointer inside the canvas.
     *
     * @param e
     */
    public void mouseMoved(MouseEvent e) {
        //If a button is being pressed, then the mouseDragged method will be invoked.
        //By checking that no button is actually beig pressed,
        //the same thing will not be rendered twice (once here and once in mouseDragged).
        if (e.getButton() != MouseEvent.NOBUTTON) return;

        movePaintPointer(e);
    }

    /**
     * Used to show the paint pointer when the canvas get the "focus". This has to be detected
     * manually (ie, focusis when the mouse enterss the Canvas area) as the rendering of the
     * paint canvas is being controlled by the application,
     *
     * @param e
     */
    public void mouseEntered(MouseEvent e) {
        movePaintPointer(e);
    }


    /**
     * Used to hide the paint pointer when the canvas loses the "focus". This has to be detected
     * manually (ie, focus is lost when the mouse leaves the Canvas area) as the rendering of the
     * paint canvas is being controlled by the application,
     *
     * @param e
     */
    public void mouseExited(MouseEvent e) {
        hidePaintPointer(e);
    }


    private void manipulateCanvas(MouseEvent e, int mouseButton) {
        Point point = e.getPoint();
        if (paintPanel.isEnabled()) {
            if (mouseButton == MouseEvent.BUTTON1) {
                drawTile(point);
            } else {
                removeTile(point);
            }
            paintPanel.getCanvas().movePaintPointerTo(normalizePointToTile(point));
            forceRepaint(e);
        }
    }


    private void movePaintPointer(MouseEvent e) {
        if (paintPanel.isEnabled()) {
            paintPanel.getCanvas().movePaintPointerTo(normalizePointToTile(e.getPoint()));
            forceRepaint(e);
        }
    }


    private void hidePaintPointer(MouseEvent e) {
        if (paintPanel.isEnabled()) {
            paintPanel.getCanvas().hidePaintPointerEvent();
            forceRepaint(e);
        }
    }


    private void drawTile(Point point) {
        this.paintPanel.controller.addTileToActiveSceneryAtPoint(normalizePointToTile(point));
    }

    private void removeTile(Point point) {
        this.paintPanel.controller.removeTileAtPoint(normalizePointToTile(point));
    }

    private void forceRepaint(MouseEvent e) {
        ((Component) e.getSource()).getParent().getParent().repaint();
    }

}
