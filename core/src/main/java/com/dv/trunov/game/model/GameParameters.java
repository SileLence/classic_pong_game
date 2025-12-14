package com.dv.trunov.game.model;

import com.dv.trunov.game.ui.Counter;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;

public class GameParameters {

    private static final GameParameters INSTANCE = new GameParameters();
    private GameState gameState;
    private GameMode gameMode;
    private int activeMenuItemId;
    private Counter counterOne;
    private Counter counterTwo;

    public static GameParameters getInstance() {
        return INSTANCE;
    }

    private GameParameters() {
        gameState = GameState.TITLE;
        activeMenuItemId = 0;
    }

    public void setGameParametersByMenuItemId(int menuItemId) {
        switch (menuItemId) {
            case 1 -> {
                gameMode = GameMode.SINGLEPLAYER;
                gameState = GameState.PLAYING;
            }
            case 2 -> {
                gameMode = GameMode.MULTIPLAYER;
                gameState = GameState.PLAYING;
            }
            case 3 -> gameState = GameState.SETTINGS;
            case 4 -> gameState = GameState.EXIT;
            case 5 -> gameState = GameState.PLAYING;
            case 6 -> {
                gameState = GameState.MENU;
                activeMenuItemId = 1;
            }
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public int getActiveMenuItemId() {
        return activeMenuItemId;
    }

    public void setActiveMenuItemId(int activeMenuItemId) {
        this.activeMenuItemId = activeMenuItemId;
    }


}
