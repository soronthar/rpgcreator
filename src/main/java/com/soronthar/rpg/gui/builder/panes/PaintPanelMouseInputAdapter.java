package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.model.JumpPoint;
import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.scenery.SceneryBag;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Vector;

import static com.soronthar.rpg.Utils.normalizePointToTile;

/**
 * mousePressed and mouseReleased are used to identify which button is being used
 * when using a drag gesture, allowing to keep drawing or removing tiles as long
 * as the button is pressed.
 */
class PaintPanelMouseInputAdapter extends MouseInputAdapter {
    private Controller controller;

    public PaintPanelMouseInputAdapter(Controller controller) {
        this.controller = controller;
    }


    public void mouseClicked(MouseEvent e) {
        manipulateCanvas(e);
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
            if (SwingUtilities.isMiddleMouseButton(e) || (SwingUtilities.isLeftMouseButton(e) && e.isControlDown())) {

                final SpecialObject specialObject = controller.getModel().getActiveScenery().getSpecialAt(point);


                JFrame ancestor = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, controller.getPaintPanel());
                final JDialog dialog = new JDialog(ancestor, "Edit", true);
                dialog.setLayout(new FlowLayout());
                if (specialObject instanceof JumpPoint) {
                    Project project = controller.getModel().getProject();
                    SceneryBag sceneries = project.getSceneries();
                    Vector vector = new Vector();
                    int i = 0;
                    int selected = 0;
                    for (Scenery scenery : sceneries) {
                        vector.add(scenery.getName());
                        if (scenery.getName().equals(((JumpPoint) specialObject).getTargetName())) {
                            selected = i;
                        }
                        i++;
                    }
                    final JComboBox combo = new JComboBox(vector);
                    combo.setSelectedIndex(selected);
                    JLabel label = new JLabel("Choose Scenery:");
                    dialog.add(label);
                    dialog.add(combo);
                    JButton button = new JButton("OK");
                    dialog.add(button);
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            controller.getModel().getActiveScenery().addJumpPoint(new JumpPoint(point, (String) combo.getSelectedItem()));
                            dialog.setVisible(false);
                        }
                    });
                    dialog.pack();
                    dialog.setVisible(true);
                }

            } else if (SwingUtilities.isLeftMouseButton(e)) {
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
