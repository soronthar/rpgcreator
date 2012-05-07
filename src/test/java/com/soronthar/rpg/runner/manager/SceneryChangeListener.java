package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.scenery.Scenery;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
* Utility class to test MapManager
*/
class SceneryChangeListener implements PropertyChangeListener {
    int timesCalled;
    Scenery lastNewValue;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        timesCalled++;
        lastNewValue = (Scenery) evt.getNewValue();
    }

    public int getTimesCalled() {
        return timesCalled;
    }

    public Scenery getLastNewValue() {
        return lastNewValue;
    }
}
