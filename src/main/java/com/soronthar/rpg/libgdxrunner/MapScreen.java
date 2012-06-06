package com.soronthar.rpg.libgdxrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.soronthar.rpg.libgdxrunner.actors.HeroActor;
import com.soronthar.rpg.libgdxrunner.actors.Mob;
import com.soronthar.rpg.libgdxrunner.actors.ObstacleActor;
import com.soronthar.rpg.model.objects.actors.Hero;
import com.soronthar.rpg.model.objects.actors.Sprite;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.project.ProjectPersister;
import com.soronthar.rpg.model.scenery.DrawnTile;
import com.soronthar.rpg.model.scenery.Layer;
import com.soronthar.rpg.model.scenery.LayersArray;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import org.soronthar.error.ApplicationException;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

import static com.soronthar.rpg.Utils.normalizePointToTile;

public class MapScreen implements Screen {
    private Texture textureL;
    private Texture textureH;
    private Stage stage;
    private HeroActor actor;
    private final FPSLogger log = new FPSLogger();


    @Override
    public void render(float delta) {
        GL10 gl = Gdx.graphics.getGL10();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        SpriteBatch spriteBatch = stage.getSpriteBatch();
        spriteBatch.begin();
        spriteBatch.draw(textureL, 0, 0, 0, 0, 1024, 512);
        spriteBatch.end();
        stage.act(delta);
        stage.getCamera().position.set(actor.x, actor.y, 0);
        stage.draw();

        spriteBatch.begin();
        spriteBatch.draw(textureH, 0, 0, 0, 0, 1024, 512);
        spriteBatch.end();
        log.log();
    }


    @Override
    public void resize(int width, int height) {
        stage.setViewport(width, height, false);
    }



    @Override
    public void show() {
        Project firstProject = new ProjectPersister().load("FirstProject");
        Scenery scenery = firstProject.getSceneries().iterator().next();

        createTextureForScenery(scenery);
        Point heroPos = scenery.getHeroStartingPoint();

        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        stage = new Stage(width, height, true);
        stage.getCamera().position.set(heroPos.x, heroPos.y, 0);
        actor = new HeroActor(new Hero(heroPos));

        Collection<Point> obstacles = scenery.getObstacles();
        Group obstaclesGroup = new Group("obstacles");
        Iterator<Point> iterator = obstacles.iterator();
        for (; iterator.hasNext(); ) {
            Point loc = iterator.next();
            loc.setLocation(loc.x, scenery.getHeight() - loc.y);
            obstaclesGroup.addActor(new ObstacleActor(loc));
        }

        Group mobsGroup=new Group("mobs");
        Collection<Sprite> sprites = scenery.getSprites();
        for (Sprite sprite : sprites) {
            mobsGroup.addActor(new Mob(sprite.getId(), sprite));
        }

        stage.addActor(mobsGroup);
        stage.addActor(actor);
        stage.addActor(obstaclesGroup);
    }

    private void createTextureForScenery(Scenery scenery) {
        TileSetBag tileSets = new TileSetBagPersister().loadTilesets();
        Pixmap layerPixmapL = new Pixmap(1024, 512, Pixmap.Format.RGBA8888);
        Pixmap layerPixmapH = new Pixmap(1024, 512, Pixmap.Format.RGBA8888);
        LayersArray layers = scenery.getLayers();

        for (Layer layer : layers) {
            if (layer.getIndex() < 3) {
                drawScenery(tileSets, layerPixmapL, layer);
            } else {
                drawScenery(tileSets, layerPixmapH, layer);
            }
        }
        textureH = new Texture(layerPixmapH);
        textureL = new Texture(layerPixmapL);
        layerPixmapH.dispose();
        layerPixmapL.dispose();
    }

    private void drawScenery(TileSetBag tileSets, Pixmap layerPixmap, Layer sceneryLayer) {
        for (DrawnTile drawnTile : sceneryLayer) {
            Tile info = drawnTile.getTile();
            if (info != null) {
                org.soronthar.geom.Dimension dimension = info.getDimension();
                TileSet tileSet = tileSets.get(info.getTilesetName());
                if (tileSet == null) {
                    throw new ApplicationException("Tileset " + info.getTilesetName() + " is not loaded");
                }
                Pixmap image = tileSet.image();
                Point tilesetPoint = info.getPoint();

                Point p = normalizePointToTile(drawnTile.getPoint());
                layerPixmap.drawPixmap(image, p.x, p.y, tilesetPoint.x, tilesetPoint.y, dimension.width, dimension.height);
            }
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}