package com.dv.trunov.game.model;

import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.Language;

public class GameParameters {

    private static final GameParameters INSTANCE = new GameParameters();
    private GameState gameState;
    private GameMode gameMode;
    private Language language;
    private float cooldown;
    private int selectedItemIndex;
    private int scoreOne;
    private int scoreTwo;
    private int level;
    private int bestLevel;
    private boolean isNewRecord;

    public static GameParameters getInstance() {
        return INSTANCE;
    }

    private GameParameters() {
        gameState = GameState.TITLE;
        selectedItemIndex = 0;
        cooldown = 0f;
        level = 1;
        bestLevel = 1;
        isNewRecord = false;
    }

    public void setGameParametersBySelectedItemKey(String key) {
        switch (key) {
            case Constants.ItemKey.RU_KEY -> {
                language = Language.RUSSIAN;
                gameState = GameState.MENU;
            }
            case Constants.ItemKey.EN_KEY -> {
                language = Language.ENGLISH;
                gameState = GameState.MENU;
            }
            case Constants.ItemKey.ONE_PLAYER_KEY -> {
                gameMode = GameMode.SINGLEPLAYER;
                gameState = GameState.IDLE;
                setStartGame();
            }
            case Constants.ItemKey.TWO_PLAYERS_KEY -> {
                gameMode = GameMode.MULTIPLAYER;
                gameState = GameState.IDLE;
                setStartGame();
            }
            case Constants.ItemKey.PRESS_ENTER_KEY,
                 Constants.ItemKey.CONTINUE_KEY -> gameState = GameState.PLAYING;
            case Constants.ItemKey.SETTINGS_KEY -> gameState = GameState.SETTINGS;
            case Constants.ItemKey.PLAY_AGAIN_KEY -> {
                gameState = GameState.IDLE;
                setStartGame();
            }
            case Constants.ItemKey.EXIT_TO_MENU_KEY -> gameState = GameState.MENU;
            case Constants.ItemKey.EXIT_KEY -> gameState = GameState.EXIT;
        }
        selectedItemIndex = 0;
    }

    public void updateCooldown(float... cooldownValue) {
        if (cooldown == 0f && cooldownValue.length > 0) {
            cooldown = cooldownValue[0];
        }
        cooldown -= Constants.Physics.FIXED_TIMESTEP;
        if (cooldown < 0f) {
            cooldown = 0f;
        }
    }

    public void checkWin() {
        if (scoreOne >= Constants.Score.WIN_SCORE || scoreTwo >= Constants.Score.WIN_SCORE) {
            gameState = GameState.WIN;
            cooldown = 0f;
        }
    }

    public void addMultiplayerPoint(boolean isPlayerOneScoredGoal) {
        if (isPlayerOneScoredGoal) {
            scoreOne++;
        } else {
            scoreTwo++;
        }
    }

    public void addSingleplayerPoint() {
        scoreOne++;
        level = scoreOne / 5 + 1;
        if (level > bestLevel) {
            bestLevel = level;
            isNewRecord = true;
        }
    }

    private void setStartGame() {
        scoreOne = 0;
        scoreTwo = 0;
        level = 1;
        isNewRecord = false;
    }

    public boolean isNewRecord() {
        return isNewRecord;
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

    public Language getCurrentLanguage() {
        return language;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public float getCooldown() {
        return cooldown;
    }

    public int getScoreOne() {
        return scoreOne;
    }

    public int getScoreTwo() {
        return scoreTwo;
    }

    public int getLevel() {
        return level;
    }

    public int getBestLevel() {
        return bestLevel;
    }
}
