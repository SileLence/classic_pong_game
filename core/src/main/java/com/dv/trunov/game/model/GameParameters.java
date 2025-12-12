package com.dv.trunov.game.model;

import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;

public class GameParameters {

    private static final GameParameters INSTANCE = new GameParameters();
    private GameState gameState;
    private GameMode gameMode;
    private int activeMenuItemId;

    public static GameParameters getInstance() {
        return INSTANCE;
    }

    private GameParameters() {
        this.gameState = GameState.TITLE;
        this.activeMenuItemId = 0;
    }

    public void setGameParametersByMenuItemId(int menuItemId) {
        switch (menuItemId) {
            case 1 -> {
                this.gameMode = GameMode.SINGLEPLAYER;
                this.gameState = GameState.PLAYING;
            }
            case 2 -> {
                this.gameMode = GameMode.MULTIPLAYER;
                this.gameState = GameState.PLAYING;
            }
            case 3 -> this.gameState = GameState.SETTINGS;
            case 4 -> this.gameState = GameState.EXIT;
            case 5 -> this.gameState = GameState.PLAYING;
            case 6 -> this.gameState = GameState.MENU;
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
