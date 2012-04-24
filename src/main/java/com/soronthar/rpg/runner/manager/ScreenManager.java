package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.Hero;
import com.soronthar.rpg.model.objects.Sprite;
import com.soronthar.rpg.model.tiles.Tile;

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


    public ScreenManager(Dimension dimension, BufferedImage[] layers) {
        this.layers = layers;
        this.setPreferredSize(new Dimension(dimension.width, dimension.height));
        Dimension viewSize = new Dimension(dimension.width, dimension.height);
        imageSize = new Dimension(layers[0].getWidth(), layers[0].getHeight());

        this.viewPort = new Rectangle(viewSize);
        this.viewPortBounds = new Rectangle(0, 0, imageSize.width - viewSize.width, imageSize.height - viewSize.height);

        int stepBits = (int) (Math.log(Tile.TILE_SIZE) / Math.log(2));
        this.relativeCenter = calculateViewPortRelativeCenter(viewSize, stepBits);


        //Ignore repaints so we are in complete control over the rendering
        this.setIgnoreRepaint(true);

        //Ignore repaints so we are in complete control over the rendering 
        this.setFocusable(false);

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

        for (int layer = 0; layer < 3; layer++) {
            g.drawImage(getImage(layer), 0, 0, null);
        }

        drawSprite(player, g);

        for (Sprite sprite : sprites) {
            if (viewPort.contains(sprite.getLocation())) {
                drawSprite(sprite, g);
            }
        }

        g.drawImage(getImage(4), 0, 0, null);

        g.dispose();
        bufferStrategy.show();
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

}
