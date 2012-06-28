package com.soronthar.rpg.model.project;

import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.alchemist.model.ProjectWriter;

import java.io.IOException;
import java.io.StringWriter;

public class ConvertProject {

    public static void main(String[] args) throws IOException {
//        ProjectPersister projectPersister = new ProjectPersister();
//        Project firstProject = projectPersister.load("FirstProject");
//        String projectPath= ProjectPersister.buildProjectPath("FirstProject");
//
//        System.out.println("ConvertProject.main:13 - projectName = " + projectPath);
//        SceneryBag sceneries = firstProject.getSceneries();
//        String sceneriesDirPath = projectPath + "/sceneries/";
//
//        File sceneriesDir=new File(sceneriesDirPath);
//        System.out.println("ConvertProject.main:23 - sceneriesDir = " + sceneriesDir);
//        System.out.println("ConvertProject.main:24 - sceneriesDir.exists() = " + sceneriesDir.exists());
//        SceneryWriter sceneryWriter=new SceneryWriter();
//        for(Scenery scenery:sceneries) {
//            File sceneryFile=new File(sceneriesDir,"s"+scenery.getId()+"/scenery.json");
//            FileWriter writer=new FileWriter(sceneryFile);
//            sceneryWriter.write(scenery,writer);
//            writer.close();
//        }
        StringWriter w=new StringWriter();
        Project project=new Project("name");
        new ProjectWriter().write(project,w);
        System.out.println(w);


    }
}
