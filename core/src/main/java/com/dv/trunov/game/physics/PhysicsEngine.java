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
        accumulator = 0f;
        alpha = 1f;
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
                case GOAL -> physicsProcessor.processGoalPhysics(platforms, ball, TIMESTEP);
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

    public void processCooldown(GameParameters gameParameters, float deltaTime) {
        GameState gameState = gameParameters.getGameState();
        float cooldown = gameParameters.getCooldown();
        if (gameState == GameState.GOAL) {
            if (cooldown == 0f) {
                cooldown = Constants.Physics.GOAL_COOLDOWN;
            }
            cooldown -= deltaTime;
            if (cooldown < 0f) {
                cooldown = 0f;
                gameParameters.setGameState(GameState.PLAYING);
            }
        }
        gameParameters.setCooldown(cooldown);
    }
}
