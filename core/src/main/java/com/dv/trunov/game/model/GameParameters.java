package com.dv.trunov.game.model;

import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;

public class GameParameters {

    private static final GameParameters INSTANCE = new GameParameters();
    private GameState gameState;
    private GameMode gameMode;
    private String selectedItemKey;
    private float cooldown;
    private int scoreOne;
    private int scoreTwo;

    public static GameParameters getInstance() {
        return INSTANCE;
    }

    private GameParameters() {
        gameState = GameState.TITLE;
        cooldown = 0f;
    }

    public void setGameParametersBySelectedItemKey() {
        switch (this.selectedItemKey) {
            case Constants.ItemKey.ONE_PLAYER_KEY -> {
                gameMode = GameMode.SINGLEPLAYER;
                gameState = GameState.PLAYING;
            }
            case Constants.ItemKey.TWO_PLAYERS_KEY -> {
                gameMode = GameMode.MULTIPLAYER;
                gameState = GameState.PLAYING;
            }
            case Constants.ItemKey.SETTINGS_KEY -> gameState = GameState.SETTINGS;
            case Constants.ItemKey.EXIT_KEY -> gameState = GameState.EXIT;
            case Constants.ItemKey.CONTINUE_KEY -> gameState = GameState.PLAYING;
            case Constants.ItemKey.EXIT_TO_MENU_KEY -> {
                gameState = GameState.MENU;
                this.selectedItemKey = Constants.ItemKey.ONE_PLAYER_KEY;
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

    public String getSelectedItemKey() {
        return selectedItemKey;
    }

    public void setSelectedItemKey(String selectedItemKey) {
        this.selectedItemKey = selectedItemKey;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public int getScoreOne() {
        return scoreOne;
    }

    public void addScoreOne() {
        scoreOne++;
    }

    public int getScoreTwo() {
        return scoreTwo;
    }

    public void addScoreTwo() {
        scoreTwo++;
    }
}
