package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.runner.manager.screens.DialogManager;
import com.soronthar.rpg.runner.manager.screens.MapScreenManager;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

public class ScreenManager extends Canvas {

    private Rectangle viewPortBounds;
    private Rectangle viewPort;
    private Point relativeCenter;
    private DialogManager dialog;
    private MapScreenManager mapScreenManager=new MapScreenManager();


    public ScreenManager() {
        //Ignore repaints and focus so we are in complete control over the rendering
        this.setIgnoreRepaint(true);
        this.setFocusable(false);

    }

    //TODO: make the runner to draw the sceneries on a fixed size, filling up with "black" squares
    public void setScenery(Scenery scenery) {
        mapScreenManager.setScenery(scenery);

        Dimension imageSize = mapScreenManager.getImageSize();
        Dimension dimension=mapScreenManager.getDimension();

        Dimension viewSize = new Dimension(dimension.width, dimension.height);
        this.setPreferredSize(viewSize);
        this.viewPort = new Rectangle(viewSize);
        this.viewPortBounds = new Rectangle(0, 0, imageSize.width - viewSize.width, imageSize.height - viewSize.height);

        int stepBits = (int) (Math.log(Tile.TILE_SIZE) / Math.log(2));
        this.relativeCenter = calculateViewPortRelativeCenter(viewSize, stepBits);
    }



    public void paint(Hero player, List<Sprite> sprites) {
        centerAround(player.getLocation());

        BufferStrategy bufferStrategy = getBufferStrategy();
        Graphics g = bufferStrategy.getDrawGraphics();
        mapScreenManager.paint(g,viewPort,player,sprites);


        if (isShowingDialog()) {
            showTextDialog(g);
        }

        g.dispose();
        bufferStrategy.show();
    }

    private void showTextDialog(Graphics g) {
        this.dialog.paint(g);
    }

    /**
     * StepBits are used to work on "tiles" instead of pixels. The calculations are more precise that way
     *
     * @param viewSize
     * @param stepBits
     * @return
     */
    private Point calculateViewPortRelativeCenter(Dimension viewSize, int stepBits) {
        int xTiles = viewSize.width >> stepBits;
        int yTiles = viewSize.height >> stepBits;
        return new Point((xTiles / 2) << stepBits, (yTiles / 2) << stepBits);
    }

    public void centerAround(Point position) {
        Point newPosition = new Point(position.x - relativeCenter.x, position.y - relativeCenter.y);
        normalizePointToBounds(newPosition, viewPortBounds);
        viewPort.setLocation(newPosition);
    }

    private Point normalizePointToBounds(Point newPosition, Rectangle bounds) {
        if (newPosition.x < bounds.x) newPosition.x = bounds.x;
        if (newPosition.y < bounds.y) newPosition.y = bounds.y;
        if (newPosition.x > bounds.width) newPosition.x = bounds.width;
        if (newPosition.y > bounds.height) newPosition.y = bounds.height;
        return newPosition;
    }

    public boolean isShowingDialog() {
        return this.dialog!=null && !this.dialog.isFinished();
    }

    public void advanceDialog() {
        if (dialog.isFinished()) {
            this.dialog = null;
        } else {
            dialog.advance();
        }
    }

    public void showDialog(String text) {
        this.dialog = new DialogManager(text, this.viewPort, this.getGraphics().getFontMetrics());
    }
}
