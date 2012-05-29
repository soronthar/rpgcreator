package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.sprites.SpriteActions;

import java.util.LinkedList;
import java.util.Queue;

public class SpriteActionQueue {
    Queue<SpriteActions.SpriteAction> queue=new LinkedList<SpriteActions.SpriteAction>();
    SpriteActions.SpriteAction currentAction=null;


    public void setActions(SpriteActions actions) {
        currentAction=null;
        queue.addAll(actions);
    }

    public void executeAction(GameEngine gameManager) {
        if (this.currentAction == null || this.currentAction.isFinished()) {
            this.currentAction = queue.poll();
        }

        if (this.currentAction != null) {
            this.currentAction.execute(gameManager, this);
        }

        if (this.currentAction!=null && this.currentAction.isFinished()) {
            this.currentAction=null;
        }
    }

    public boolean isActive() {
        return (currentAction!=null && !currentAction.isFinished()) || !queue.isEmpty();
    }
}
