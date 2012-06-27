package com.soronthar.rpg.adventure.project;

import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.SceneryBag;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;

import java.io.Serializable;

//TODO: a project should not know about paths and file versions
public class Project implements Serializable {
    private String fileVersion = "0.1";
    private String name;
    private SceneryBag sceneries;
    private TileSetBag tileSetBag;

    public Project(String name) {
        this.name = name;
        this.sceneries = new SceneryBag();
        this.tileSetBag=new TileSetBag();
    }

    public Project() {
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

    public void setName(String name) {
        this.name=name;
    }

    public TileSetBag getTileSetBag() {
        return tileSetBag;
    }

    public void addTileset(String name, String resourceName) {
        this.tileSetBag.put(new TileSet(name,resourceName,null));
    }
}
