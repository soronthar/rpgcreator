package com.soronthar.rpg.demiurge.legacy.gui.builder.components.scenery;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.SceneryBag;
import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgueController;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SceneryTree extends JTree {
    public SceneryTree(final DemiurgueController controller) {

        super(new SceneryTreeModel());

        this.setMinimumSize(Utils.getScaledTileDimension(8, 2).addPadding(23, 49).toAWT());
        this.setMaximumSize(Utils.getScaledTileDimension(8, 2).addPadding(23, 49).toAWT());

        controller.setSceneryTree(this);
        this.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getNewLeadSelectionPath();
                if (path != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (!node.isRoot()) {
                        controller.selectScenery(((Scenery) node.getUserObject()).getId());
                    }
                }
            }
        });
        this.setEditable(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                final SceneryTree sceneryTree = SceneryTree.this;
                int closestRowForLocation = sceneryTree.getClosestRowForLocation(e.getX(), e.getY());
                sceneryTree.setSelectionRow(closestRowForLocation);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                final SceneryTree sceneryTree = SceneryTree.this;

                if (e.isPopupTrigger() && sceneryTree.getSelectionPath().getPathCount() > 1) {
                    JPopupMenu menu = new JPopupMenu();
                    menu.add(new AbstractAction("Resize Scenery") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DefaultMutableTreeNode node = (DefaultMutableTreeNode) sceneryTree.getSelectionPath().getLastPathComponent();
                            Scenery scenery = (Scenery) node.getUserObject();

                            final JDialog dialog = new ScenerySizeDialog(sceneryTree, scenery, controller);
                            dialog.setVisible(true);
                        }
                    });
                    menu.show(sceneryTree, e.getX(), e.getY());
                }
            }
        });
    }


    public void updateSceneriesForProject(Project project) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(project.getName());

        SceneryBag sceneryBag = project.getSceneries();
        for (Scenery scenery : sceneryBag) {
            root.add(new DefaultMutableTreeNode(scenery));
        }

        getModel().setRoot(root);
        if (project.getSceneries().size() > 0) {
            this.setSelectionRow(1);
        }
        this.revalidate();
    }

    public void clearSceneryTree(Project project) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(project);
        getModel().setRoot(root);
        this.revalidate();
    }

    public void addSceneryToProjectTree(Scenery scenery) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(scenery);
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
        root.add(node);
        getModel().setRoot(root);
        this.revalidate();
    }

    public DefaultTreeModel getModel() {
        return (DefaultTreeModel) super.getModel();
    }

    private static class SceneryTreeModel extends DefaultTreeModel {
        private SceneryTreeModel() {
            super(new DefaultMutableTreeNode("<< No Project Loaded >>"), false);
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            Scenery scenery = (Scenery) node.getUserObject();
            scenery.setName((String) newValue);
            nodeChanged(node);
        }
    }
}
