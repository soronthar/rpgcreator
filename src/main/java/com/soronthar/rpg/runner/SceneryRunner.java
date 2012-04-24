package com.soronthar.rpg.runner;

import com.soronthar.rpg.model.project.NewProjectPersister;
import com.soronthar.rpg.runner.manager.GameManager;


public class SceneryRunner {

    public static void main(String[] args) throws Exception {
//        GameManager manager = new GameManager(new ProjectPersister().load("TestSerialization.xml"));
        GameManager manager = new GameManager(new NewProjectPersister().load("test.xml"));
        manager.executeMainLoop();
    }
}