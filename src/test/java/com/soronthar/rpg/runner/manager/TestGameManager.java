package com.soronthar.rpg.runner.manager;

import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.Queue;

public class TestGameManager extends TestCase {
    public void test() {
        QueueManager manager=new QueueManager();
        CheckTimesCalledAction calledThreeTimes = new CheckTimesCalledAction(3);
        CheckTimesCalledAction calledOneTime = new CheckTimesCalledAction(1);
        manager.enqueue(calledThreeTimes);
        manager.enqueue(calledOneTime);
        
        do {
            manager.doIt();
        } while(manager.hasActions());
        assertEquals(3,calledThreeTimes.timesExecuted());
        assertEquals(1,calledOneTime.timesExecuted());
    }

    private static interface Action {
        void execute();
        boolean isFinished();
        int timesExecuted();
    }
    
    private static class CheckTimesCalledAction implements Action {
        private int timesExecutedLimit=0;
        private int timesExecuted=0;

        private CheckTimesCalledAction(int timesExecutedLimit) {
            this.timesExecutedLimit = timesExecutedLimit;
        }

        @Override
        public void execute() {
            timesExecuted++;
        }

        @Override
        public boolean isFinished() {
            return timesExecuted>=timesExecutedLimit;
        }

        @Override
        public int timesExecuted() {
            return timesExecuted;
        }
    }
    
    
    

    
    private static class QueueManager {
        private Queue<Action> actionQueue=new LinkedList<Action>();
        private Action currentAction=null;

        public void enqueue(Action action) {
            actionQueue.add(action);
        }

        public void doIt() {
            if (actionQueue.isEmpty()) {
                this.currentAction=null;
            } else {
                if (this.currentAction == null || this.currentAction.isFinished()) {
                    this.currentAction = actionQueue.poll();
                }
                this.currentAction.execute();
            }
        }

        public boolean hasActions() {
            return !actionQueue.isEmpty();
        }
    }
}
