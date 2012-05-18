package com.soronthar.rpg.model.objects.sprites;

import java.util.ArrayList;

public class SpriteActions extends ArrayList<SpriteActions.SpriteAction> {

    /** Marker interface */
    public static interface SpriteAction {}

    public static class ShowText implements SpriteAction{
        private String text;

        public ShowText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
