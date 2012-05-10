package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.project.ProjectPersister;
import junit.framework.TestCase;

import java.io.File;
import java.net.URISyntaxException;

public abstract class BaseMapManagerTest extends TestCase {
    protected MapManager initManager() {
        MapManager manager = createMapManager();
        assertNull(manager.getActiveScenery());
        manager.init();
        return manager;
    }

    protected MapManager createMapManager() {
        try {
            ProjectPersister persister = new ProjectPersister();
            File file = new File(this.getClass().getResource("/MapManagerTest.xml").toURI());
            Project project = persister.load(file.getAbsolutePath());
            return new MapManager(project.getSceneries());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
