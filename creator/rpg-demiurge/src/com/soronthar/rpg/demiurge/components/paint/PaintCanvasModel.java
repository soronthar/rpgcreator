package com.soronthar.rpg.demiurge.components.paint;

import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.soronthar.rpg.demiurge.components.BaseComponentModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class PaintCanvasModel extends BaseComponentModel {
    public static final String LAYER = "Layer";
    private BufferedImage drawingPen;
    private Point pointerLocation;
    ArrayList<SpecialEditEventListener> listeners=new ArrayList<SpecialEditEventListener>();

    private boolean[] layerVisibility = new boolean[LayersArray.LAYER_COUNT + 1];


    public static final String LOCATION = "location";
    private int activeLayer;
    private boolean specialMode;

    public PaintCanvasModel() {
        for (int i = 0; i < layerVisibility.length; i++) {
            layerVisibility[i] = true;
        }
    }

    public void setDrawingPen(BufferedImage drawingPen) {
        this.drawingPen = drawingPen;
    }

    public BufferedImage getDrawingPen() {
        return drawingPen;
    }

    //TODO: this "may" be used to undo stuff
    public void registerAction(Action action, Point point) {
        firePropertyChange(action.name(),null,point);
    }

    public void registerAction(Action action) {
        firePropertyChange(action.name(),false,true);
    }


    public void setPointerLocation(Point location) {
        Point oldValue = this.pointerLocation;
        this.pointerLocation = location;
        firePropertyChange(LOCATION, oldValue, this.pointerLocation);
    }


    public void addChangeListener(Action action, PropertyChangeListener listener) {
        addChangeListener(action.name(), listener);
    }

    public boolean isLayerVisible(int layer) {
        return layerVisibility[layer];
    }

    public void toggleLayerVisibility(int layer) {
        this.layerVisibility[layer] = !this.layerVisibility[layer];
        firePropertyChange(LAYER,false,true);  //TODO: this is not nice.
    }

    public void setActiveLayer(int activeLayer) {
        this.activeLayer = activeLayer;
    }

    public void setSpecialMode(boolean specialMode) {
        this.specialMode = specialMode;
    }

    public int getActiveLayer() {
        return (specialMode?LayersArray.LAYER_COUNT:activeLayer);
    }

    public enum Action {
        HIDE_POINTER, DRAW, ERASE, CLEAR
    }

    public void editSpecialAt(Point point) {
        notifySpecialEditRequest(point);
    }

    public void addSpecialEditRequestListener(SpecialEditEventListener listener) {
        listeners.add(listener);
    }

    private void notifySpecialEditRequest(Point point) {
        for (SpecialEditEventListener listener : listeners) {
            listener.onSpecialEditRequestAt(point);
        }
    }

}
