package com.soronthar.rpg.model.objects.sprites;


import com.soronthar.rpg.runner.manager.GameManager;
import com.soronthar.rpg.runner.manager.ScreenManager;
import com.soronthar.rpg.runner.manager.SpriteActionQueue;

import java.util.ArrayList;

public class SpriteActions extends ArrayList<SpriteActions.SpriteAction> {

    /**
     * Marker interface
     */
    public static interface SpriteAction {
        void execute(GameManager gameManager, SpriteActionQueue actionQueue);

        boolean isFinished();
    }

    public static class ShowText implements SpriteAction {
        private String text;
        private ScreenManager screenManager;

        public ShowText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }


        @Override
        public boolean isFinished() {
            return screenManager!=null && !screenManager.isShowingDialog();
        }

        @Override
        public void execute(GameManager gameManager, SpriteActionQueue actionQueue) {
            this.screenManager = gameManager.getScreenManager();
//            gameManager.giveInputControlTo(screenManager);
            if (screenManager.isShowingDialog()) {
                screenManager.advanceDialog();
            } else {
                screenManager.showDialog(this.getText());
            }
            
            if (isFinished()) {
                actionQueue.executeAction(gameManager);
            }
        }
    }
}
