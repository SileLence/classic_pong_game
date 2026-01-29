package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.physics.PhysicsEngine;
import com.dv.trunov.game.ui.TextKey;
import com.dv.trunov.game.ui.TextLabel;
import com.dv.trunov.game.util.BallSpeed;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.PointsToWin;
import com.dv.trunov.game.util.ServeSide;
import com.dv.trunov.game.util.ServeState;
import com.dv.trunov.game.util.SoundToPlay;
import com.dv.trunov.game.util.Toggle;

public class InputController {

    private static final InputController INSTANCE = new InputController();

    private InputController() {
    }

    public static InputController getInstance() {
        return INSTANCE;
    }

    public void processMenuInput(GameParameters gameParameters, PhysicsEngine physicsEngine, TextLabel... menuItems) {
        int oldSelectedItemIndex = gameParameters.getSelectedItemIndex();
        int newSelectedItemIndex = gameParameters.getSelectedItemIndex();
        TextKey selectedItemKey = menuItems[oldSelectedItemIndex].key();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameParameters.updateParametersBySelectedItemKey(selectedItemKey);
            gameParameters.setSoundToPlay(SoundToPlay.MENU_SELECT);
            return;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            newSelectedItemIndex++;
            if (newSelectedItemIndex > menuItems.length - 1) {
                newSelectedItemIndex = 0;
            }
            gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            newSelectedItemIndex--;
            if (newSelectedItemIndex < 0) {
                newSelectedItemIndex = menuItems.length - 1;
            }
            gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
        }
        if (oldSelectedItemIndex != newSelectedItemIndex) {
            physicsEngine.resetAlpha();
        }
        gameParameters.setSelectedItemIndex(newSelectedItemIndex);
    }

    public void processSettingsInput(GameParameters gameParameters, PhysicsEngine physicsEngine, TextLabel... menuItems) {
        int oldSelectedItemIndex = gameParameters.getSelectedItemIndex();
        int newSelectedItemIndex = gameParameters.getSelectedItemIndex();
        int pointsIndex = gameParameters.getPointsToWin().getIndex();
        int speedIndex = gameParameters.getMultiplayerBallSpeed().getIndex();
        int soundIndex = gameParameters.getSoundState().getIndex();
        int serveIndex = gameParameters.getStartingServe().getIndex();
        TextKey selectedItemKey = menuItems[oldSelectedItemIndex].key();
        switch (selectedItemKey) {
            case BACK,
                 RESET_BEST -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    gameParameters.updateParametersBySelectedItemKey(selectedItemKey);
                    gameParameters.setSoundToPlay(SoundToPlay.MENU_SELECT);
                    return;
                }
            }
            case POINTS_TO_WIN -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    pointsIndex++;
                    if (pointsIndex > PointsToWin.size() - 1) {
                        pointsIndex = PointsToWin.size() - 1;
                    }
                    gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    pointsIndex--;
                    if (pointsIndex < 0) {
                        pointsIndex = 0;
                    }
                    gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
                }
                gameParameters.setPointsToWin(pointsIndex);
            }
            case BALL_SPEED -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    speedIndex++;
                    if (speedIndex > BallSpeed.size() - 1) {
                        speedIndex = BallSpeed.size() - 1;
                    }
                    gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    speedIndex--;
                    if (speedIndex < 0) {
                        speedIndex = 0;
                    }
                    gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
                }
                gameParameters.setMultiplayerBallSpeed(speedIndex);
            }
            case SOUNDS -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    soundIndex++;
                    if (soundIndex > Toggle.size() - 1) {
                        soundIndex = 0;
                    }
                    gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    soundIndex--;
                    if (soundIndex < 0) {
                        soundIndex = Toggle.size() - 1;
                    }
                    gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
                }
                gameParameters.setSoundState(soundIndex);
            }
            case STARTING_SERVE -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    serveIndex++;
                    if (serveIndex > ServeSide.size() - 1) {
                        serveIndex = ServeSide.size() - 1;
                    }
                    gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    serveIndex--;
                    if (serveIndex < 0) {
                        serveIndex = 0;
                    }
                    gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
                }
                gameParameters.setStartingServe(serveIndex);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameParameters.updateParametersBySelectedItemKey(TextKey.BACK);
            gameParameters.setSoundToPlay(SoundToPlay.MENU_SELECT);
            return;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            newSelectedItemIndex++;
            if (newSelectedItemIndex > menuItems.length - 1) {
                newSelectedItemIndex = 0;
            }
            gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            newSelectedItemIndex--;
            if (newSelectedItemIndex < 0) {
                newSelectedItemIndex = menuItems.length - 1;
            }
            gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
        }
        if (oldSelectedItemIndex != newSelectedItemIndex) {
            physicsEngine.resetAlpha();
        }
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
