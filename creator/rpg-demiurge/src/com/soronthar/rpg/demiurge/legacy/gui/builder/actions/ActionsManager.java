package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;

import javax.swing.*;

public class ActionsManager {
    private NewProjectAction newProjectAction;
    private LoadProjectAction loadProjectAction;
    private SaveProjectAction saveProjectAction;
    private NewSceneryAction newSceneryAction;
    private RunProjectAction runProjectAction;

    private ExitAction exitAction;

    private AddObstacleAction addObstacleAction;
    private AddHeroStartAction addHeroStartAction;
    private AddJumpAction addJumpAction;
    private AddSpriteAction addSpriteAction;
    private ManageTilesetAction manageTilesetsAction;

    public ActionsManager(JFrame parent, DemiurgeController controller) {
        this.newProjectAction = new NewProjectAction(parent, controller);
        this.loadProjectAction = new LoadProjectAction(parent, controller);
        this.saveProjectAction = new SaveProjectAction(controller);
        this.newSceneryAction = new NewSceneryAction(controller);
        this.exitAction = new ExitAction(controller);
        this.runProjectAction = new RunProjectAction(controller);
        this.addObstacleAction = new AddObstacleAction(controller);
        this.addHeroStartAction = new AddHeroStartAction(controller);
        this.addJumpAction = new AddJumpAction(controller);
        this.addSpriteAction = new AddSpriteAction(controller);
        this.manageTilesetsAction = new ManageTilesetAction(parent,controller);
        setInitialState();
    }

    public void setInitialState() {
        this.saveProjectAction().setEnabled(false);
        this.newSceneryAction().setEnabled(false);
        this.runProjectAction().setEnabled(false);
        this.addObstacleAction().setEnabled(false);
        this.addHeroStartAction().setEnabled(false);
        this.addJumpAction().setEnabled(false);
        this.addSpriteAction().setEnabled(false);
        this.manageTilesetsAction().setEnabled(false);
    }

    public void setAllEnabled() {
        this.saveProjectAction().setEnabled(true);
        this.newSceneryAction().setEnabled(true);
        this.runProjectAction().setEnabled(true);
        this.addObstacleAction().setEnabled(true);
        this.addHeroStartAction().setEnabled(true);
        this.addJumpAction().setEnabled(true);
        this.addSpriteAction().setEnabled(true);
        this.manageTilesetsAction().setEnabled(true);

    }

    public Action newProjectAction() {
        return newProjectAction;
    }

    public Action loadProjectAction() {
        return loadProjectAction;
    }

    public Action saveProjectAction() {
        return saveProjectAction;
    }

    public Action newSceneryAction() {
        return newSceneryAction;
    }

    public Action exitAction() {
        return exitAction;
    }

    public Action runProjectAction() {
        return runProjectAction;
    }

    public Action addObstacleAction() {
        return addObstacleAction;
    }

    public Action addHeroStartAction() {
        return addHeroStartAction;
    }

    public Action addJumpAction() {
        return addJumpAction;
    }

    public Action addSpriteAction() {
        return addSpriteAction;
    }

    public Action manageTilesetsAction() {
        return manageTilesetsAction;
    }
}
