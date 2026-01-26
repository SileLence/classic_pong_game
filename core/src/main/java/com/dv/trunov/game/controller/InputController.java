package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dv.trunov.game.physics.PhysicsEngine;
import com.dv.trunov.game.ui.TextLabel;
import com.dv.trunov.game.util.BallSpeed;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.util.PointsToWin;
import com.dv.trunov.game.util.ServeState;
import com.dv.trunov.game.util.ServeSide;
import com.dv.trunov.game.util.Toggle;

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
            gameParameters.updateParametersBySelectedItemKey(selectedItemKey);
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

    public void processSettingsInputs(GameParameters gameParameters, PhysicsEngine physicsEngine, TextLabel... menuItems) {
        int oldSelectedItemIndex = gameParameters.getSelectedItemIndex();
        int newSelectedItemIndex = gameParameters.getSelectedItemIndex();
        int pointsIndex = gameParameters.getPointsToWin().getIndex();
        int speedIndex = gameParameters.getMultiplayerBallSpeed().getIndex();
        int soundsIndex = gameParameters.getSoundsState().getIndex();
        int serveIndex = gameParameters.getStartingServe().getIndex();
        String selectedItemKey = menuItems[oldSelectedItemIndex].key();
        switch (selectedItemKey) {
            case Constants.ItemKey.BACK_KEY,
                 Constants.ItemKey.RESET_BEST_KEY -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    gameParameters.updateParametersBySelectedItemKey(selectedItemKey);
                    return;
                }
            }
            case Constants.ItemKey.POINTS_TO_WIN_VALUE_KEY -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    pointsIndex++;
                    if (pointsIndex > PointsToWin.size() - 1) {
                        pointsIndex = PointsToWin.size() - 1;
                    }
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    pointsIndex--;
                    if (pointsIndex < 0) {
                        pointsIndex = 0;
                    }
                }
                gameParameters.setPointsToWin(pointsIndex);
            }
            case Constants.ItemKey.BALL_SPEED_VALUE_KEY -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    speedIndex++;
                    if (speedIndex > BallSpeed.size() - 1) {
                        speedIndex = BallSpeed.size() - 1;
                    }
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    speedIndex--;
                    if (speedIndex < 0) {
                        speedIndex = 0;
                    }
                }
                gameParameters.setMultiplayerBallSpeed(speedIndex);
            }
            case Constants.ItemKey.SOUNDS_VALUE_KEY -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    soundsIndex++;
                    if (soundsIndex > Toggle.size() - 1) {
                        soundsIndex = 0;
                    }
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    soundsIndex--;
                    if (soundsIndex < 0) {
                        soundsIndex = Toggle.size() - 1;
                    }
                }
                gameParameters.setSoundsState(soundsIndex);
            }
            case Constants.ItemKey.STARTING_SERVE_VALUE_KEY -> {
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    serveIndex++;
                    if (serveIndex > ServeSide.size() - 1) {
                        serveIndex = ServeSide.size() - 1;
                    }
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    serveIndex--;
                    if (serveIndex < 0) {
                        serveIndex = 0;
                    }
                }
                gameParameters.setStartingServe(serveIndex);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameParameters.updateParametersBySelectedItemKey(Constants.ItemKey.BACK_KEY);
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
        ServeState serveState = gameParameters.getServeState();
        if ((serveState == ServeState.PLAYER_ONE && Gdx.input.isKeyJustPressed(Input.Keys.TAB))
                || (serveState == ServeState.PLAYER_TWO && Gdx.input.isKeyJustPressed(Input.Keys.ENTER))) {
            gameParameters.setGameState(GameState.PLAYING);
            gameParameters.setServeState(ServeState.NONE);
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
