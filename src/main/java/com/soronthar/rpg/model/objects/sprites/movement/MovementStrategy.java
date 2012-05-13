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
    };

    void init();

    void handleCollisionAt(Point tileLocation);

    void handleAtEdge(Rectangle bounds);
}
