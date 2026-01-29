package com.dv.trunov.game.model;

import com.dv.trunov.game.storage.StorageService;
import com.dv.trunov.game.ui.TextKey;
import com.dv.trunov.game.util.BallSpeed;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.Language;
import com.dv.trunov.game.util.PointsToWin;
import com.dv.trunov.game.util.ServeSide;
import com.dv.trunov.game.util.ServeState;
import com.dv.trunov.game.util.SoundToPlay;
import com.dv.trunov.game.util.Toggle;

public class GameParameters {

    private static final GameParameters INSTANCE = new GameParameters();
    private GameState gameState;
    private ServeState serveState;
    private GameMode gameMode;
    private Language language;
    private PointsToWin pointsToWin;
    private BallSpeed multiplayerBallSpeed;
    private Toggle soundState;
    private ServeSide startingServe;
    private SoundToPlay soundToPlay;
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
        serveState = ServeState.NONE;
        soundToPlay = SoundToPlay.NONE;
        selectedItemIndex = 0;
        cooldown = 0;
        level = 1;
        isNewRecord = false;
        bestLevel = StorageService.getValue(TextKey.BEST_LEVEL, 1);
        pointsToWin = PointsToWin.fromIndex(StorageService.getValue(TextKey.POINTS_TO_WIN, 1));
        multiplayerBallSpeed = BallSpeed.fromIndex(StorageService.getValue(TextKey.BALL_SPEED, 2));
        soundState = Toggle.fromIndex(StorageService.getValue(TextKey.SOUNDS, 1));
        startingServe = ServeSide.fromIndex(StorageService.getValue(TextKey.STARTING_SERVE, 0));
        String languageIndex = StorageService.getValue(TextKey.LANGUAGE, "");
        if ("".equals(languageIndex)) {
            language = Language.ENGLISH;
            gameState = GameState.TITLE;
        } else {
            language = Language.fromIndex(Integer.parseInt(languageIndex));
            gameState = GameState.MENU;
        }
    }

    public void updateParametersBySelectedItemKey(TextKey key) {
        switch (key) {
            case RU -> {
                language = Language.RUSSIAN;
                gameState = GameState.MENU;
            }
            case EN -> {
                language = Language.ENGLISH;
                gameState = GameState.MENU;
            }
            case ONE_PLAYER -> {
                gameMode = GameMode.SINGLEPLAYER;
                gameState = GameState.IDLE;
                setStartGame();
            }
            case TWO_PLAYERS -> {
                gameMode = GameMode.MULTIPLAYER;
                gameState = GameState.IDLE;
                setStartGame();
            }
            case PRESS_ENTER,
                 CONTINUE -> gameState = serveState == ServeState.NONE ? GameState.PLAYING : GameState.GOAL;

            case SETTINGS,
                 NO -> gameState = GameState.SETTINGS;

            case RESET_BEST -> gameState = GameState.RESET;

            case BACK,
                 EXIT_TO_MENU -> gameState = GameState.MENU;

            case PLAY_AGAIN -> {
                gameState = GameState.IDLE;
                setStartGame();
            }
            case YES -> {
                if (GameState.RESET.equals(gameState)) {
                    bestLevel = 1;
                    StorageService.storeValue(TextKey.BEST_LEVEL, bestLevel);
                }
                gameState = GameState.SETTINGS;
            }
            case EXIT -> gameState = GameState.EXIT;
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

    public boolean checkWin() {
        int winScore = Integer.parseInt(pointsToWin.getValue());
        if (scoreOne >= winScore || scoreTwo >= winScore) {
            gameState = GameState.WIN;
            cooldown = 0;
            return true;
        }
        return false;
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
            StorageService.storeValue(TextKey.BEST_LEVEL, bestLevel);
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
        StorageService.storeValue(TextKey.POINTS_TO_WIN, pointsToWin.getIndex());
    }

    public BallSpeed getMultiplayerBallSpeed() {
        return multiplayerBallSpeed;
    }

    public void setMultiplayerBallSpeed(int index) {
        multiplayerBallSpeed = BallSpeed.fromIndex(index);
        StorageService.storeValue(TextKey.BALL_SPEED, multiplayerBallSpeed.getIndex());
    }

    public Toggle getSoundState() {
        return soundState;
    }

    public void setSoundState(int index) {
        soundState = Toggle.fromIndex(index);
        StorageService.storeValue(TextKey.SOUNDS, soundState.getIndex());
    }

    public ServeSide getStartingServe() {
        return startingServe;
    }

    public void setSoundToPlay(SoundToPlay soundToPlay) {
        this.soundToPlay = soundToPlay;
    }

    public SoundToPlay getSoundToPlay() {
        return soundToPlay;
    }

    public void clearSoundToPlay() {
        soundToPlay = SoundToPlay.NONE;
    }

    public void setStartingServe(int index) {
        startingServe = ServeSide.fromIndex(index);
        StorageService.storeValue(TextKey.STARTING_SERVE, startingServe.getIndex());
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
