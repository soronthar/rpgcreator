package com.soronthar.rpg.alchemist.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.project.ProjectPersister;
import com.soronthar.rpg.adventure.scenery.DrawnTile;
import com.soronthar.rpg.adventure.scenery.Layer;
import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.Hero;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.alchemist.actors.HeroActor;
import com.soronthar.rpg.alchemist.actors.LayerActor;
import com.soronthar.rpg.alchemist.actors.Mob;
import com.soronthar.rpg.alchemist.actors.ObstacleActor;
import com.soronthar.rpg.alchemist.tileset.TileSet;
import com.soronthar.rpg.alchemist.tileset.TileSetBag;
import com.soronthar.rpg.alchemist.tileset.TileSetBagPersister;
import com.soronthar.rpg.utils.Utils;
import org.soronthar.error.ApplicationException;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

public class MapScreen implements Screen {
    private Texture textureL;
    private Texture textureH;
    private Stage stage;
    private final FPSLogger log = new FPSLogger();
    private Scenery scenery;
    private HeroActor heroActor;
    private final Project project = new ProjectPersister().load(Gdx.files.internal("projects/FirstProject/FirstProject.xml").reader());

    @Override
    public void render(float delta) {
        GL10 gl = Gdx.graphics.getGL10();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        Collection<JumpPoint> jumpPoints = scenery.getJumpPoints();
        for (JumpPoint jumpPoint : jumpPoints) {
            Vector2 vector=new Vector2((float) jumpPoint.getLocation().x, this.scenery.getHeight() - (float) jumpPoint.getLocation().y);
            heroActor.toLocalCoordinates(vector);
            if (heroActor.hit(vector.x,  vector.y)!=null) {
                long targetId = jumpPoint.getTargetId();
                setScenery(project.getScenery(targetId));
            }
        }


        stage.act(delta);
        stage.getCamera().position.set(heroActor.x, heroActor.y, 0);
        stage.draw();

        log.log();
    }


    @Override
    public void resize(int width, int height) {
        stage.setViewport(width, height, false);
    }



    @Override
    public void show() {
        Iterator<Scenery> iterator = project.getSceneries().iterator();
        iterator.next();
        scenery = iterator.next();
        setScenery(scenery);

    }

    private void setScenery(Scenery scenery) {
        this.scenery=scenery;
        createTextureForScenery(scenery);
        Point heroPos = scenery.getHeroStartingPoint();
        heroPos.y=this.scenery.getHeight()-heroPos.y;

        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        if (stage!=null) stage.clear();
        stage = new Stage(width, height, true);
        stage.getCamera().position.set(heroPos.x, heroPos.y, 0);

        heroActor = new HeroActor(new Hero(heroPos));

        Collection<Point> obstacles = scenery.getObstacles();
        Group obstaclesGroup = new Group("obstacles");
        Iterator<Point> iterator = obstacles.iterator();
        for (; iterator.hasNext(); ) {
            Point loc = new Point(iterator.next());
            loc.setLocation(loc.x, scenery.getHeight() - loc.y);
            obstaclesGroup.addActor(new ObstacleActor(loc));
        }
        obstaclesGroup.addActor(new ObstacleActor(new Point(0,0)));


        Group mobsGroup=new Group("mobs");
        Collection<Sprite> sprites = scenery.getSprites();
        for (Sprite sprite : sprites) {
            mobsGroup.addActor(new Mob(sprite.getId(), sprite));
        }

        stage.addActor(new LayerActor(textureL));
        stage.addActor(mobsGroup);
        stage.addActor(heroActor);
        stage.addActor(obstaclesGroup);
        stage.addActor(new LayerActor(textureH));
    }

    private void createTextureForScenery(Scenery scenery) {
        TileSetBag tileSets = new TileSetBagPersister().loadTilesets();
        int w= MathUtils.nextPowerOfTwo(scenery.getWidth());
        int h= MathUtils.nextPowerOfTwo(scenery.getHeight());
        Pixmap layerPixmapL = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        Pixmap layerPixmapH = new Pixmap(w, h, Pixmap.Format.RGBA8888);
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

                Point p = Utils.normalizePointToTile(drawnTile.getPoint());
                layerPixmap.drawPixmap(image, p.x, p.y, tilesetPoint.x, tilesetPoint.y, dimension.width, dimension.height);
            }
        }
    }

    @Override
    public void hide() {
        //TODO: test hide, show, pause and resume on android
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        //TODO: dispose all resources
    }
}
