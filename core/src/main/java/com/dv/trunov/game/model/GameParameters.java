package com.dv.trunov.game.model;

import com.dv.trunov.game.storage.StorageService;
import com.dv.trunov.game.util.BallSpeed;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.Language;
import com.dv.trunov.game.util.PointsToWin;
import com.dv.trunov.game.util.ServeState;
import com.dv.trunov.game.util.ServeSide;
import com.dv.trunov.game.util.Toggle;

public class GameParameters {

    private static final GameParameters INSTANCE = new GameParameters();
    private GameState gameState;
    private ServeState serveState;
    private GameMode gameMode;
    private Language language;
    private PointsToWin pointsToWin;
    private BallSpeed multiplayerBallSpeed;
    private Toggle soundsState;
    private ServeSide serveSide;
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
        serveState = ServeState.NONE;
        selectedItemIndex = 0;
        cooldown = 0;
        level = 1;
        isNewRecord = false;
        bestLevel = StorageService.getValue(Constants.Prefs.BEST_LEVEL, 1);
        pointsToWin = PointsToWin.fromIndex(StorageService.getValue(Constants.Prefs.POINTS_TO_WIN, 1));
        multiplayerBallSpeed = BallSpeed.fromIndex(StorageService.getValue(Constants.Prefs.BALL_SPEED, 2));
        soundsState = Toggle.fromIndex(StorageService.getValue(Constants.Prefs.SOUNDS, 1));
        serveSide = ServeSide.fromIndex(StorageService.getValue(Constants.Prefs.SERVE, 0));
    }

    public void updateParametersBySelectedItemKey(String key) {
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
                 Constants.ItemKey.CONTINUE_KEY -> gameState = serveState == ServeState.NONE ? GameState.PLAYING : GameState.GOAL;

            case Constants.ItemKey.SETTINGS_KEY,
                 Constants.ItemKey.NO_KEY -> gameState = GameState.SETTINGS;

            case Constants.ItemKey.RESET_BEST_KEY -> gameState = GameState.RESET;

            case Constants.ItemKey.BACK_KEY,
                 Constants.ItemKey.EXIT_TO_MENU_KEY -> gameState = GameState.MENU;

            case Constants.ItemKey.PLAY_AGAIN_KEY -> {
                gameState = GameState.IDLE;
                setStartGame();
            }
            case Constants.ItemKey.YES_KEY -> {
                if (GameState.RESET.equals(gameState)) {
                    bestLevel = 1;
                    StorageService.storeValue(Constants.Prefs.BEST_LEVEL, bestLevel);
                }
                gameState = GameState.SETTINGS;
            }
            case Constants.ItemKey.EXIT_KEY -> gameState = GameState.EXIT;
        }
        selectedItemIndex = 0;
    }

    public void updateCooldown(float... cooldownValue) {
        if (cooldown == 0 && cooldownValue.length > 0) {
            cooldown = cooldownValue[0];
        }
        cooldown -= Constants.Physics.FIXED_TIMESTEP;
        if (cooldown < 0) {
            cooldown = 0;
        }
    }

    public void checkWin() {
        int winScore = Integer.parseInt(pointsToWin.getValue());
        if (scoreOne >= winScore || scoreTwo >= winScore) {
            gameState = GameState.WIN;
            cooldown = 0;
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
            StorageService.storeValue(Constants.Prefs.BEST_LEVEL, bestLevel);
        }
    }

    private void setStartGame() {
        scoreOne = 0;
        scoreTwo = 0;
        level = 1;
        isNewRecord = false;
        serveState = ServeState.NONE;
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

    public PointsToWin getPointsToWin() {
        return pointsToWin;
    }

    public void setPointsToWin(int index) {
        pointsToWin = PointsToWin.fromIndex(index);
        StorageService.storeValue(Constants.Prefs.POINTS_TO_WIN, pointsToWin.getIndex());
    }

    public BallSpeed getMultiplayerBallSpeed() {
        return multiplayerBallSpeed;
    }

    public void setMultiplayerBallSpeed(int index) {
        multiplayerBallSpeed = BallSpeed.fromIndex(index);
        StorageService.storeValue(Constants.Prefs.BALL_SPEED, multiplayerBallSpeed.getIndex());
    }

    public Toggle getSoundsState() {
        return soundsState;
    }

    public void setSoundsState(int index) {
        soundsState = Toggle.fromIndex(index);
        StorageService.storeValue(Constants.Prefs.SOUNDS, soundsState.getIndex());
    }

    public ServeSide getStartingServe() {
        return serveSide;
    }

    public void setStartingServe(int index) {
        serveSide = ServeSide.fromIndex(index);
        StorageService.storeValue(Constants.Prefs.SERVE, serveSide.getIndex());
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

    public ServeState getServeState() {
        return serveState;
    }

    public void setServeState(boolean isPlayerOneScoredGoal) {
        serveState = isPlayerOneScoredGoal ? ServeState.PLAYER_TWO : ServeState.PLAYER_ONE;
    }

    public void setServeState(ServeState serveState) {
        this.serveState = serveState;
    }
}
