package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.project.ProjectPersister;
import junit.framework.TestCase;

public abstract class BaseMapManagerTest extends TestCase {
    protected MapManager initManager() {
        MapManager manager = createMapManager();
        assertNull(manager.getActiveScenery());
        manager.init();
        return manager;
    }

    protected MapManager createMapManager() {
            ProjectPersister persister = new ProjectPersister();
            Project project = persister.load("MapManagerTest");
            return new MapManager(project.getSceneries());
    }
}
