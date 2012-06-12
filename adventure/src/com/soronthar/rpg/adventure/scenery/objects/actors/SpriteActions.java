package com.soronthar.rpg.adventure.scenery.objects.actors;


import java.util.ArrayList;

public class SpriteActions extends ArrayList<SpriteActions.SpriteAction> {

    /**
     * Marker interface
     */
    public static interface SpriteAction {
        void execute();

        boolean isFinished();
    }

    public static class ShowText implements SpriteAction {
        private String text;

        public ShowText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }


        @Override
        public boolean isFinished() {
          return false;
        }

        @Override
        public void execute() {
        }
    }
}
