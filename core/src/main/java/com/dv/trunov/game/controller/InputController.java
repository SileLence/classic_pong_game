package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dv.trunov.game.engine.PhysicsEngine;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.model.GameParameters;

public class InputController {

    private static final InputController INSTANCE = new InputController();

    private InputController() {
    }

    public static InputController getInstance() {
        return INSTANCE;
    }

    public GameParameters processTitleInputs(GameParameters gameParameters) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameParameters.setGameState(GameState.MENU);
            gameParameters.setActiveMenuItemId(1);
        }
        return gameParameters;
    }

    public GameParameters processMenuInputs(GameParameters gameParameters, PhysicsEngine physicsEngine) {
        int activeMenuItemId = gameParameters.getActiveMenuItemId();
        int newActiveMenuItemId = gameParameters.getActiveMenuItemId();
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            newActiveMenuItemId++;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            newActiveMenuItemId--;
        }
        if (newActiveMenuItemId < 1) {
            newActiveMenuItemId = 4;
        } else if (newActiveMenuItemId > 4) {
            newActiveMenuItemId = 1;
        }
        gameParameters.setActiveMenuItemId(newActiveMenuItemId);
        if (activeMenuItemId != newActiveMenuItemId) {
            physicsEngine.resetAlpha();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameParameters.setGameParametersByMenuItemId(activeMenuItemId);
        }
        return gameParameters;
    }

    public GameParameters processPauseInputs(GameParameters gameParameters, PhysicsEngine physicsEngine) {
        int activeMenuItemId = gameParameters.getActiveMenuItemId();
        int newActiveMenuItemId = gameParameters.getActiveMenuItemId();
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            newActiveMenuItemId++;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            newActiveMenuItemId--;
        }
        if (newActiveMenuItemId < 5) {
            newActiveMenuItemId = 5;
        } else if (newActiveMenuItemId > 6) {
            newActiveMenuItemId = 6;
        }
        if (activeMenuItemId != newActiveMenuItemId) {
            physicsEngine.resetAlpha();
        }
        gameParameters.setActiveMenuItemId(newActiveMenuItemId);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameParameters.setGameParametersByMenuItemId(activeMenuItemId);
        }
        return gameParameters;
    }

    public GameParameters processPlayingInputs(Platform[] platforms, GameParameters gameParameters) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameParameters.setGameState(GameState.PAUSE);
        } else {
            for (Platform platform : platforms) {
                if (platform.isPlayerOne()) {
                    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                        platform.setDirection(1);
                    } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                        platform.setDirection(-1);
                    } else {
                        platform.setDirection(0);
                    }
                } else {
                    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                        platform.setDirection(1);
                    } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                        platform.setDirection(-1);
                    } else {
                        platform.setDirection(0);
                    }
                }
            }
        }
        return gameParameters;
    }
}
