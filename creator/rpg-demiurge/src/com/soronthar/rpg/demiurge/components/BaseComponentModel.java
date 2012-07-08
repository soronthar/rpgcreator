package com.soronthar.rpg.demiurge.components;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class BaseComponentModel {
    private PropertyChangeSupport pcs=new PropertyChangeSupport(this);

    public void addChangeListener(String name, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(name,listener);
    }
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(name, listener);
    }

    protected void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        pcs.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void firePropertyChange(String location, Object oldValue, Object newValue) {
        pcs.firePropertyChange(location, oldValue, newValue);
    }

}
