package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dv.trunov.game.engine.PhysicsEngine;
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

    public void processTitleInputs(GameParameters gameParameters, TextLabel[] titleMenu) {
        String selectedMenuItemKey = gameParameters.getSelectedItemKey();
        int selectedIndex = 0;
        // TODO resolve problem with menu items selection
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameParameters.setGameState(GameState.MENU);
            gameParameters.setGameParametersBySelectedItemKey();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedIndex = (++selectedIndex + titleMenu.length) % titleMenu.length;
            selectedMenuItemKey = titleMenu[selectedIndex].key();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedIndex = (--selectedIndex + titleMenu.length) % titleMenu.length;
            selectedMenuItemKey = titleMenu[selectedIndex].key();
        }
        gameParameters.setSelectedItemKey(selectedMenuItemKey);
        gameParameters.setGameParametersBySelectedItemKey();
    }

    public void processMenuInputs(GameParameters gameParameters, PhysicsEngine physicsEngine) {
//        int activeMenuItemId = gameParameters.getSelectedItemKey();
//        int newActiveMenuItemId = gameParameters.getSelectedItemKey();
//        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
//            newActiveMenuItemId++;
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
//            newActiveMenuItemId--;
//        }
//        if (newActiveMenuItemId < 1) {
//            newActiveMenuItemId = 4;
//        } else if (newActiveMenuItemId > 4) {
//            newActiveMenuItemId = 1;
//        }
//        gameParameters.setSelectedItemKey(newActiveMenuItemId);
//        if (activeMenuItemId != newActiveMenuItemId) {
//            physicsEngine.resetAlpha();
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
//            gameParameters.setGameParametersByMenuItemId(activeMenuItemId);
//        }
    }

    public void processPauseInputs(GameParameters gameParameters, PhysicsEngine physicsEngine) {
//        int activeMenuItemId = gameParameters.getSelectedItemKey();
//        int newActiveMenuItemId = gameParameters.getSelectedItemKey();
//        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)  || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
//            newActiveMenuItemId++;
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
//            newActiveMenuItemId--;
//        }
//        if (newActiveMenuItemId < 5) {
//            newActiveMenuItemId = 5;
//        } else if (newActiveMenuItemId > 6) {
//            newActiveMenuItemId = 6;
//        }
//        if (activeMenuItemId != newActiveMenuItemId) {
//            physicsEngine.resetAlpha();
//        }
//        gameParameters.setSelectedItemKey(newActiveMenuItemId);
//        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
//            gameParameters.setGameParametersByMenuItemId(activeMenuItemId);
//        }
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
