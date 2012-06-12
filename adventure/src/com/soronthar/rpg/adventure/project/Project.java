package com.soronthar.rpg.adventure.project;

import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.SceneryBag;

import java.io.Serializable;

//TODO: a project should not know about paths and file versions
public class Project implements Serializable {
    private String fileVersion = "0.1";
    private String name;
    private SceneryBag sceneries;

    public Project(String name) {
        this.name = name;
        this.sceneries = new SceneryBag();
    }

    public String getName() {
        return name;
    }

    public SceneryBag getSceneries() {
        return sceneries;
    }

    public void addScenery(Scenery scenery) {
        this.sceneries.put(scenery);
    }

    public Scenery getScenery(long id) {
        return this.sceneries.get(id);
    }

    public String getFileVersion() {
        return fileVersion;
    }
}
