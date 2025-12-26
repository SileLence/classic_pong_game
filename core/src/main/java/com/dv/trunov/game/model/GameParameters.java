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
    private int bestScore;

    public static GameParameters getInstance() {
        return INSTANCE;
    }

    private GameParameters() {
        gameState = GameState.TITLE;
        selectedItemIndex = 0;
        cooldown = 0f;
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

    public void checkWin() {
        if (scoreOne == Constants.Score.WIN_SCORE || scoreTwo == Constants.Score.WIN_SCORE) {
            gameState = GameState.WIN;
        }
    }

    public void processGoal(boolean isPlayerOneScoredGoal) {
        gameState = GameState.GOAL;
        if (isPlayerOneScoredGoal) {
            scoreOne++;
        } else {
            scoreTwo++;
        }
    }

    private void setStartGame() {
        scoreOne = 0;
        scoreTwo = 0;
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

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public int getScoreOne() {
        return scoreOne;
    }

    public void updateLevel() {
        scoreOne++;
        if (scoreOne > bestScore) {
            bestScore = scoreOne;
        }
    }

    public int getScoreTwo() {
        return scoreTwo;
    }

    public int getBestScore() {
        return bestScore;
    }
}
