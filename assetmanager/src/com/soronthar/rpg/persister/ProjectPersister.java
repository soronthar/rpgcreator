package com.soronthar.rpg.persister;

import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.SceneryBag;
import org.soronthar.error.ExceptionHandler;

import java.io.*;

public class ProjectPersister {
    
    public void createNewProject(String name) {
        try {
            File projectDir=new File("projects/"+name);
            projectDir.mkdirs();

            File projectFile=new File(projectDir,name+".json");
            FileWriter writer=new FileWriter(projectFile);
            writer.write(EMPTY_PROJECT);
            writer.close();

            new File(projectDir,"sceneries").mkdir();
            new File(projectDir,"sprites").mkdir();
            new File(projectDir,"tilesets").mkdir();
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }
    }
    
    private static final String EMPTY_PROJECT="{\"name\":\"name\",\"tilesets\":{},\"sceneries\":[]}";

    public Project load(String projectName) {
        File projectDir=new File("resources/projects/"+projectName);
        File projectFile=new File(projectDir,projectName+".json");

        ProjectReader projectReader=new ProjectReader();
        Project project=null;
        try {
            FileReader reader = new FileReader(projectFile);
            project = projectReader.read(reader);
            SceneryBag sceneries = project.getSceneries();
            SceneryReader sceneryReader = new SceneryReader();
            for(Scenery scenery:sceneries) {
                File sceneryDir=new File(projectDir,"scenery/s"+scenery.getId());
                File sceneryFile=new File(sceneryDir,"scenery.json");
                FileReader sceneryFileReader = new FileReader(sceneryFile);
                sceneryReader.read(scenery, sceneryFileReader);
                sceneryFileReader.close();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            ExceptionHandler.handleException(e);
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }

        return project;
    }

    public void save(Project project) {
        String projectName=project.getName();
        File projectDir=new File("resources/projects/"+projectName);
        File projectFile=new File(projectDir,projectName+".json");

        if (!projectDir.exists()) {
            projectDir.mkdirs();
        }

        ProjectWriter projectWriter=new ProjectWriter();
        try {
            FileWriter projectFileWriter = new FileWriter(projectFile);
            projectWriter.write(project,projectFileWriter);

            SceneryBag sceneries = project.getSceneries();
            SceneryWriter sceneryWriter = new SceneryWriter();
            for(Scenery scenery:sceneries) {
                File sceneryDir=new File(projectDir,"scenery/s"+scenery.getId());
                if (!sceneryDir.exists()) {
                    sceneryDir.mkdirs();
                }

                File sceneryFile=new File(sceneryDir,"scenery.json");
                FileWriter sceneryFileWriter = new FileWriter(sceneryFile);
                sceneryWriter.write(scenery, sceneryFileWriter);
                sceneryFileWriter.close();
            }
            projectFileWriter.close();
        } catch (FileNotFoundException e) {
            ExceptionHandler.handleException(e);
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }
    }
}
