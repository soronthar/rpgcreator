package com.soronthar.rpg.model.objects.sprites.movement;

import java.awt.*;

public interface MovementStrategy {
    MovementStrategy NO_MOVE = new MovementStrategy() {
        public void init() {
        }

        public void handleCollisionAt(Point tileLocation) {
        }

        public void handleAtEdge(Rectangle bounds) {
        }

        public void setSpeed(int dx, int dy) {
        }

        public int getDx() {
            return 0;
        }

        @Override
        public int getDy() {
            return 0;
        }

        @Override
        public boolean isMoving() {
            return false;
        }
    };

    void init();

    void setSpeed(int dx, int dy);

    void handleCollisionAt(Point tileLocation);

    void handleAtEdge(Rectangle bounds);

    int getDy();

    int getDx();

    boolean isMoving();
}
