package com.soronthar.rpg.demiurge;

import org.soronthar.error.ExceptionHandler;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    public static final Configuration MY_CONF=new Configuration();
    Properties prop=new Properties();

    private Configuration() {
        try {
            prop.load(new FileReader("demiurge-config.properties"));
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }
    }

    public static String getProperty(String key) {
        return MY_CONF.prop.getProperty(key);
    }
}
