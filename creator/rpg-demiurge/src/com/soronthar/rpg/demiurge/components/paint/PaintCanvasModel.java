package com.soronthar.rpg.demiurge.components.paint;

import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.demiurge.components.BaseComponentModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class PaintCanvasModel extends BaseComponentModel {
    public static final String LAYER = "Layer";
    public static final String LOCATION = "location";
    public static final String DIMENSION= "dimension";
    public enum Action {
        HIDE_POINTER, DRAW, ERASE, CLEAR
    }

    private BufferedImage drawingPen=null;
    private Point pointerLocation=new Point(0,0);
    private Dimension canvasSize=new Dimension(Scenery.DEFAULT_WIDTH - 1, Scenery.DEFAULT_HEIGHT - 1);
    private int activeLayer=0;
    private boolean specialMode=false;

    private boolean[] layerVisibility = new boolean[LayersArray.LAYER_COUNT + 1];

    ArrayList<SpecialEditEventListener> listeners=new ArrayList<SpecialEditEventListener>();

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
    public void fireAction(Action action, Point point) {
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

    public Dimension getCanvasSize() {
        return canvasSize;
    }

    public void setCanvasSize(Dimension canvasSize) {
        this.canvasSize = canvasSize;
        firePropertyChange(DIMENSION,null,canvasSize);
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
