package com.soronthar.rpg.libgdxrunner;

import com.badlogic.gdx.backends.jogl.JoglApplication;

/**
 * Created by IntelliJ IDEA.
 * User: Rafael Alvarez
 * Date: 5/30/12
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class LibGdxRunnerDesktop {

    public static void main(String[] args) {
        String project = "test.xml";
        if (args.length > 0) {
            project = args[0];
        }

//        new LwjglApplication(new LibGdxRunner(),"Runner",482,320,false);
        new JoglApplication(new LibGdxRunner(),"Runner",1024,768,false);
//        new JoglApplication(new LibGdxRunner(),"Runner",480,320,false);
    }
}
