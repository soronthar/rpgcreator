package com.soronthar.rpg.runner.manager.screens;

import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.TileSetBag;
import com.soronthar.rpg.model.tiles.TileSetBagPersister;
import com.soronthar.rpg.runner.manager.TilesetRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class MapScreenManager {
    public static final TileSetBag TILE_SETS = new TileSetBagPersister().loadTilesets();

    private BufferedImage[] layers;
    private Dimension imageSize;
    private Dimension dimension;


    public void setScenery(Scenery scenery) {
        this.layers = TilesetRenderer.createLayers(TILE_SETS, scenery);
        dimension = new org.soronthar.geom.Dimension(scenery.getWidth(), scenery.getHeight());
        imageSize = new Dimension(layers[0].getWidth(), layers[0].getHeight());
    }

    public Dimension getImageSize() {
        return imageSize;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Image getImage(int layer, Rectangle viewBounds) {
        return layers[layer].getSubimage(viewBounds.x,
                viewBounds.y,
                viewBounds.width,
                viewBounds.height);
    }

    public void paint(Graphics g, Rectangle viewPort, Hero player, List<Sprite> sprites) {
        for (int layer = 0; layer < 4; layer++) {
            g.drawImage(getImage(layer, viewPort), 0, 0, null);
        }

        drawSprite(player, g,viewPort);

        for (Sprite sprite : sprites) {
            if (viewPort.contains(sprite.getLocation()) && sprite.isVisible()) {
                drawSprite(sprite, g, viewPort);
            }
        }

        g.drawImage(getImage(4, viewPort), 0, 0, null);

    }

    private void drawSprite(Sprite sprite, Graphics g, Rectangle viewPort) {
        Point p = mapToViewport(sprite.getLocation(),viewPort);
        g.drawImage(sprite.getFrame(), p.x, p.y, null);
    }

    public Point mapToViewport(Point position, Rectangle viewPort) {
        return new Point(position.x - viewPort.x, position.y - viewPort.y);
    }
}
