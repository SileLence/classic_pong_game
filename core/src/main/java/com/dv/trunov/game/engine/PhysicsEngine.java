package com.dv.trunov.game.engine;

import com.dv.trunov.game.controller.PhysicsController;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;

public class PhysicsEngine {

    private static final PhysicsEngine INSTANCE = new PhysicsEngine();
    private static final float TIMESTEP = Constants.Physics.FIXED_TIMESTEP;
    private float accumulator;
    private float alpha;
    private boolean paused;
    private boolean isGrow;

    private PhysicsEngine() {
        accumulator = 0f;
        alpha = 1f;
        paused = false;
        isGrow = false;
    }

    public static PhysicsEngine getInstance() {
        return INSTANCE;
    }

    public void updatePhysics(PhysicsController physicsController,
                              Platform[] platforms,
                              Ball ball,
                              GameMode gameMode,
                              float deltaTime) {

        if (paused) {
            return;
        }
        accumulator += deltaTime;
        while (accumulator >= TIMESTEP) {
            physicsController.update(platforms, ball, gameMode, TIMESTEP);
            accumulator -= TIMESTEP;
        }
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha() {
        this.alpha = 1f;
        isGrow = false;
    }

    public void updateAlpha(float deltaTime) {
        if (isGrow) {
            alpha += deltaTime;
        } else {
            alpha -= deltaTime;
        }
        if (alpha > 1f) {
            alpha = 1f;
            isGrow = false;
        } else if (alpha < 0.5f) {
            alpha = 0.5f;
            isGrow = true;
        }
    }
}
