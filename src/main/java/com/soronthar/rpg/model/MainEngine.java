package com.soronthar.rpg.model;

import com.soronthar.rpg.ImageLoader;

public class MainEngine {
    private static MainEngine self = new MainEngine();
    private ImageLoader imageLoader;

    public static MainEngine getInstance() {
        return self;
    }

    private MainEngine() {
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public ImageLoader imageLoader() {
        return this.imageLoader;
    }


}
