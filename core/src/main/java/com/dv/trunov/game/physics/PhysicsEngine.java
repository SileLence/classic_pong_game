package com.dv.trunov.game.physics;

import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameState;

public class PhysicsEngine {

    private static final PhysicsEngine INSTANCE = new PhysicsEngine();
    private static final float TIMESTEP = Constants.Physics.FIXED_TIMESTEP;
    private float accumulator;
    private float alpha;
    private boolean paused;
    private boolean isGrow;

    private PhysicsEngine() {
        accumulator = 0;
        alpha = 1;
        paused = false;
        isGrow = false;
    }

    public static PhysicsEngine getInstance() {
        return INSTANCE;
    }

    public void updatePhysics(PhysicsProcessor physicsProcessor,
                              Platform[] platforms,
                              Ball ball,
                              GameParameters gameParameters,
                              float deltaTime) {
        if (paused) {
            return;
        }
        accumulator += deltaTime;
        while (accumulator >= TIMESTEP) {
            GameState gameState = gameParameters.getGameState();
            switch (gameState) {
                case IDLE, WIN, GAME_OVER -> ball.updateParticles(TIMESTEP);
                case GOAL -> physicsProcessor.processGoalPhysics(platforms, ball, gameParameters, TIMESTEP);
                default -> physicsProcessor.processPhysics(platforms, ball, gameParameters, TIMESTEP);
            }
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

    public void resetAlpha() {
        this.alpha = 1;
        isGrow = false;
    }

    public void updateAlpha(float deltaTime) {
        if (isGrow) {
            alpha += deltaTime;
        } else {
            alpha -= deltaTime;
        }
        if (alpha > 1) {
            alpha = 1;
            isGrow = false;
        } else if (alpha < 0.5f) {
            alpha = 0.5f;
            isGrow = true;
        }
    }
}
