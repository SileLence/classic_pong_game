package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.physics.PhysicsEngine;
import com.dv.trunov.game.ui.actions.ActionItem;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.ServeState;
import com.dv.trunov.game.util.SoundToPlay;

public class InputController {

    private static final InputController INSTANCE = new InputController();

    private InputController() {
    }

    public static InputController getInstance() {
        return INSTANCE;
    }

    public void processInput(GameParameters gameParameters, PhysicsEngine physicsEngine, ActionItem... actionItems) {
        int oldSelectedItemIndex = gameParameters.getSelectedItemIndex();
        int newSelectedItemIndex = gameParameters.getSelectedItemIndex();
        ActionItem selectedItem = actionItems[oldSelectedItemIndex];
        boolean isMenuMove = false;
        if (selectedItem.isSwitchable()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                selectedItem.onRight(gameParameters);
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) {
                selectedItem.onLeft(gameParameters);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            newSelectedItemIndex++;
            if (newSelectedItemIndex > actionItems.length - 1) {
                newSelectedItemIndex = 0;
            }
            isMenuMove = true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            newSelectedItemIndex--;
            if (newSelectedItemIndex < 0) {
                newSelectedItemIndex = actionItems.length - 1;
            }
            isMenuMove = true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            selectedItem.onSelect(gameParameters);
            return;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            selectedItem.onReturn(gameParameters);
        }
        if (oldSelectedItemIndex != newSelectedItemIndex) {
            physicsEngine.resetAlpha();
        }
        if (isMenuMove) {
            gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
        }
        gameParameters.setSelectedKey(selectedItem.getSelectionKey(gameParameters));
        gameParameters.setSelectedItemIndex(newSelectedItemIndex);
    }

    public void processPlayingInput(Platform[] platforms, GameParameters gameParameters) {
        ServeState serveState = gameParameters.getServeState();
        if (serveState != ServeState.NONE) {
            if (serveState == ServeState.PLAYER_ONE && Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
                gameParameters.setSoundToPlay(SoundToPlay.SERVE);
                gameParameters.setGameState(GameState.PLAYING);
                gameParameters.setServeState(ServeState.NONE);
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                gameParameters.setGameState(GameState.PLAYING);
                gameParameters.setServeState(ServeState.NONE);
                gameParameters.setSoundToPlay(SoundToPlay.SERVE);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameParameters.pauseGame();
            gameParameters.setSoundToPlay(SoundToPlay.MENU_SELECT);
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
