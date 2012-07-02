package com.soronthar.rpg.demiurge.legacy.gui.builder.panes;

import com.soronthar.rpg.adventure.scenery.objects.Actor;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Controller;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

import static com.soronthar.rpg.Utils.normalizePointToTile;


class PaintPanelMouseInputAdapter extends MouseInputAdapter {
    private Controller controller;

    public PaintPanelMouseInputAdapter(Controller controller) {
        this.controller = controller;
    }


    public void mouseClicked(MouseEvent e) {
        final Point point = normalizePointToTile(e.getPoint());

        if (SwingUtilities.isMiddleMouseButton(e) || (SwingUtilities.isLeftMouseButton(e) && e.isControlDown())) {
            final Actor specialObject = controller.getModel().getActiveScenery().getSpecialAt(point);
            if (specialObject instanceof JumpPoint) {
                final JDialog dialog = new JumpPointEditDialog((JumpPoint) specialObject,point,controller);
                dialog.setVisible(true);
            } else if (specialObject instanceof Sprite) {
                final JDialog dialog = new SpriteEditDialog((Sprite) specialObject,point,controller);
                dialog.setVisible(true);
            }
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

        if (controller.getPaintPanel().isEnabled()) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                drawTile(point);
            } else {
                removeTile(point);
            }
            controller.getModel().setPointerLocation(normalizePointToTile(e.getPoint()));
            forceRepaint(e);
        }
    }


    private void movePaintPointer(MouseEvent e) {
        if (controller.getPaintPanel().isEnabled()) {
            controller.getModel().setPointerLocation(normalizePointToTile(e.getPoint()));
            forceRepaint(e);
        }
    }


    private void hidePaintPointer(MouseEvent e) {
        if (controller.getPaintPanel().isEnabled()) {
            controller.getPaintPanel().getCanvas().hidePaintPointerEvent();
            forceRepaint(e);
        }
    }


    private void drawTile(Point point) {
        this.controller.getPaintPanel().controller.addTileToActiveSceneryAtPoint(normalizePointToTile(point));
    }

    private void removeTile(Point point) {
        this.controller.getPaintPanel().controller.removeTileAtPoint(normalizePointToTile(point));
    }

    private void forceRepaint(MouseEvent e) {
        ((Component) e.getSource()).getParent().getParent().repaint();
    }

}
