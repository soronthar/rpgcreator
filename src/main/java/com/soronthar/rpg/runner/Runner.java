package com.soronthar.rpg.runner;

import com.soronthar.rpg.model.project.ProjectPersister;
import com.soronthar.rpg.runner.manager.GameManager;


public class Runner {

    public static void main(String[] args) throws Exception {
        String project = "test.xml";
        if (args.length > 0) {
            project = args[0];
        }
        GameManager manager = new GameManager(new ProjectPersister().load(project));
        manager.executeMainLoop();
    }
}