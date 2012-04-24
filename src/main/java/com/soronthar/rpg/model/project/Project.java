package com.soronthar.rpg.model.project;

import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.scenery.SceneryBag;

import java.io.Serializable;

//TODO: a project should not know about paths and file versions
public class Project implements Serializable {
    private String fileVersion = "0.1";
    private String name;
    private String path;
    private SceneryBag sceneries;

    public Project(String name) {
        this.name = name;
        this.sceneries = new SceneryBag();
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public SceneryBag getSceneries() {
        return sceneries;
    }

    public void addScenery(Scenery scenery) {
        this.sceneries.put(scenery);
    }

    public String toString() {
        return this.name;
    }

    public Scenery getScenery(String name) {
        return this.sceneries.get(name);
    }

    public String getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }
}
