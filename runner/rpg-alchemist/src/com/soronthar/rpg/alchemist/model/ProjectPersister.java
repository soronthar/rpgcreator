package com.soronthar.rpg.alchemist.model;

import org.soronthar.error.ExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProjectPersister {
    
    public void createNewProject(String name) {
        try {
            File projectDir=new File("project/"+name);
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
}
