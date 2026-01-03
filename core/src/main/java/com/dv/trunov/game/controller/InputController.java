package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dv.trunov.game.physics.PhysicsEngine;
import com.dv.trunov.game.ui.TextLabel;
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

    public void processMenuInputs(GameParameters gameParameters, PhysicsEngine physicsEngine, TextLabel... menuItems) {
        int oldSelectedItemIndex = gameParameters.getSelectedItemIndex();
        int newSelectedItemIndex = gameParameters.getSelectedItemIndex();
        String selectedItemKey = menuItems[oldSelectedItemIndex].key();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameParameters.setGameParametersBySelectedItemKey(selectedItemKey);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            newSelectedItemIndex++;
            if (newSelectedItemIndex > menuItems.length - 1) {
                newSelectedItemIndex = 0;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            newSelectedItemIndex--;
            if (newSelectedItemIndex < 0) {
                newSelectedItemIndex = menuItems.length - 1;
            }
        }
        if (oldSelectedItemIndex != newSelectedItemIndex) {
            physicsEngine.resetAlpha();
        }
        gameParameters.setSelectedItemIndex(newSelectedItemIndex);
    }

    public void processPlayingInputs(Platform[] platforms, GameParameters gameParameters) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameParameters.setGameState(GameState.PAUSE);
        } else {
            for (Platform platform : platforms) {
                if (platform.isPlayerOne()) {
                    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                        platform.setDirection(1);
                    } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                        platform.setDirection(-1);
                    } else {
                        platform.setDirection(0);
                    }
                } else {
                    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                        platform.setDirection(1);
                    } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                        platform.setDirection(-1);
                    } else {
                        platform.setDirection(0);
                    }
                }
            }
        }
    }
}
