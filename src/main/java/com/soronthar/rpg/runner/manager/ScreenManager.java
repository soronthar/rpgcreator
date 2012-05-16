package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.objects.sprites.StandingNpc;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.model.tiles.TileSetBag;
import com.soronthar.rpg.model.tiles.TileSetBagPersister;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;

public class ScreenManager extends Canvas {
    private BufferedImage[] layers;

    private Rectangle viewPortBounds;
    private Rectangle viewPort;
    private Point relativeCenter;
    private Dimension imageSize;
    public static final TileSetBag TILE_SETS = new TileSetBagPersister().loadTilesets();
    private boolean showDialog = false;
    private DialogManager dialog;


    public ScreenManager() {
        //Ignore repaints and focus so we are in complete control over the rendering
        this.setIgnoreRepaint(true);
        this.setFocusable(false);
    }

    public void setScenery(Scenery scenery) {
        this.layers = TilesetRenderer.createLayers(TILE_SETS, scenery);
        Dimension dimension = new org.soronthar.geom.Dimension(scenery.getWidth(), scenery.getHeight());
        this.setPreferredSize(new Dimension(dimension.width, dimension.height));
        Dimension viewSize = new Dimension(dimension.width, dimension.height);
        imageSize = new Dimension(layers[0].getWidth(), layers[0].getHeight());

        this.viewPort = new Rectangle(viewSize);
        this.viewPortBounds = new Rectangle(0, 0, imageSize.width - viewSize.width, imageSize.height - viewSize.height);

        int stepBits = (int) (Math.log(Tile.TILE_SIZE) / Math.log(2));
        this.relativeCenter = calculateViewPortRelativeCenter(viewSize, stepBits);
    }

    public Image getImage(int layer) {
        Rectangle viewBounds = this.viewPort;
        return layers[layer].getSubimage(viewBounds.x,
                viewBounds.y,
                viewBounds.width,
                viewBounds.height);
    }

    public Rectangle getScreenBounds() {
        return new Rectangle(imageSize.width - Tile.TILE_SIZE, imageSize.height - Tile.TILE_SIZE);
    }

    public void paint(Hero player, List<Sprite> sprites) {
        centerAround(player.getLocation());

        BufferStrategy bufferStrategy = getBufferStrategy();
        Graphics g = bufferStrategy.getDrawGraphics();

        for (int layer = 0; layer < 4; layer++) {
            g.drawImage(getImage(layer), 0, 0, null);
        }

        drawSprite(player, g);

        for (Sprite sprite : sprites) {
            if (viewPort.contains(sprite.getLocation()) && sprite.isVisible()) {
                drawSprite(sprite, g);
            }
        }

        g.drawImage(getImage(4), 0, 0, null);

        if (showDialog) {
            showTextDialog(g);
        }


        g.dispose();
        bufferStrategy.show();
    }

    private void showTextDialog(Graphics g) {
        this.dialog.paint(g);
    }

    private void drawSprite(Sprite sprite, Graphics g) {
        Point p = mapToViewport(sprite.getLocation());
        g.drawImage(sprite.getFrame(), p.x, p.y, null);
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

    public Point mapToViewport(Point position) {
        return new Point(position.x - viewPort.x, position.y - viewPort.y);
    }

    private Point normalizePointToBounds(Point newPosition, Rectangle bounds) {
        if (newPosition.x < bounds.x) newPosition.x = bounds.x;
        if (newPosition.y < bounds.y) newPosition.y = bounds.y;
        if (newPosition.x > bounds.width) newPosition.x = bounds.width;
        if (newPosition.y > bounds.height) newPosition.y = bounds.height;
        return newPosition;
    }

    public boolean isShowingDialog() {
        return showDialog;
    }

    public void advanceDialog() {
        if (dialog.isFinished()) {
            this.showDialog = false;
            this.dialog = null;
        } else {
            dialog.advance();
        }
    }

    public void showDialogFor(Project project, Scenery scenery, StandingNpc npc) {
        this.dialog = new DialogManager("projects/"+project.getName()+"/scenery/"+scenery.getId() + "/" + npc.getId(), this.viewPort, this.getGraphics().getFontMetrics());
        this.showDialog = true;
    }
}
