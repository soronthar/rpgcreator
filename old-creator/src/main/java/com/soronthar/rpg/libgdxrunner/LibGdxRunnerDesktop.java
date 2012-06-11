package com.soronthar.rpg.libgdxrunner;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class LibGdxRunnerDesktop {

    public static void main(String[] args) {
        String project = "test.xml";
        if (args.length > 0) {
            project = args[0];
        }

//        new LwjglApplication(new LibGdxRunner(),"Runner",1024,768,false);
        new LwjglApplication(new LibGdxRunner(),"Runner",480,320,false);
//        new JoglApplication(new LibGdxRunner(),"Runner",1024,768,false);
//        new JoglApplication(new LibGdxRunner(),"Runner",480,320,false);
    }
}
