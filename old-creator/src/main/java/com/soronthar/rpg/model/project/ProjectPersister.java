package com.soronthar.rpg.model.project;

import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.DrawnTile;
import com.soronthar.rpg.model.project.xtream.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.soronthar.error.TechnicalException;
import org.soronthar.geom.Point;

import java.io.*;

public class ProjectPersister {

    private XStream createXStream() {
        XStream xstream = new XStream(new DomDriver());
        xstream.registerConverter(new TileConverter());
//        xstream.registerConverter(new PointConverter());
        xstream.registerConverter(new SceneryConverter());
        xstream.registerConverter(new SpriteConverter());
        xstream.registerConverter(new DrawnTileConverter());
        xstream.registerConverter(new SceneryBagConverter());
        xstream.registerConverter(new LayerConverter());
        xstream.registerConverter(new LayerArrayConverter());

        xstream.alias("drawntile", DrawnTile.class);

        xstream.alias("point", Point.class);

        xstream.useAttributeFor("name", String.class);
        xstream.alias("project", Project.class);
        xstream.omitField(Project.class, "path");
        return xstream;
    }


    public void save(Project project) {
        try {
            File projectPath=new File(buildProjectPath(project.getName()));
            if (!projectPath.exists()) {
                projectPath.mkdirs();
            }
            
            FileWriter writer = new FileWriter(buildProjectFilePath(project.getName()));
            generateProjectXML(project, writer);
            writer.close();
        } catch (IOException e) {
            throw new TechnicalException(e);
        }
    }

    public void generateProjectXML(Project project, Writer out) {
        XStream xstream = createXStream();
        xstream.toXML(project, out);
    }

    public Project load(Reader in) {
        XStream xstream = createXStream();
        return (Project) xstream.fromXML(in);
    }

    public Project load(String projectName) {
        try {
            return load(new FileReader(buildProjectFilePath(projectName)));
        } catch (FileNotFoundException e) {
            throw new TechnicalException(e);
        }
    }

    public static String buildProjectFilePath(String projectName) {
        return buildProjectPath(projectName) + "/" + projectName + ".xml";
    }

    private static String buildProjectPath(String projectName) {
        return "projects/" + projectName;
    }
}
