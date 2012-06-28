package com.soronthar.rpg.alchemist.model;

import com.badlogic.gdx.utils.JsonWriter;
import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.SceneryBag;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;
import org.soronthar.error.ExceptionHandler;

import java.io.IOException;
import java.io.Writer;

public class ProjectWriter {
    public void write(Project project,Writer writer) {
        JsonWriter jsonWriter=new JsonWriter(writer);

        try {
            jsonWriter.object();
            writeProjectInfo(project, jsonWriter);
            writeTilesetInfo(project.getTileSetBag(), jsonWriter);
            writeSceneriesInfo(project.getSceneries(), jsonWriter);
            jsonWriter.pop();

        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }

    }

    private void writeSceneriesInfo(SceneryBag sceneries, JsonWriter writer) throws IOException {
        writer.array("sceneries");
        for(Scenery scenery:sceneries) {
            writeSceneryData(scenery,writer);
        }
        writer.pop();
    }

    private void writeSceneryData(Scenery scenery, JsonWriter writer) throws IOException {
        writer.object();
        writeAttribute(writer,"id",Long.toString(scenery.getId()));
        writeAttribute(writer,"name",scenery.getName());
        writeAttribute(writer,"size",scenery.getWidth()+"x"+scenery.getHeight());
        writer.pop();
    }

    private void writeTilesetInfo(TileSetBag tileSetBag, JsonWriter writer) throws IOException {
        writer.object("tilesets");
        for(TileSet tileSet:tileSetBag) {
            writeAttribute(writer,tileSet.getName(),tileSet.getResourceName());
        }
        writer.pop();
    }

    private void writeProjectInfo(Project project, JsonWriter jsonWriter) throws IOException {
        writeAttribute(jsonWriter, "name",project.getName());
    }

    private void writeAttribute(JsonWriter writer, String name, String value) throws IOException {
        writer.name(name);
        writer.value(value);
    }
}

