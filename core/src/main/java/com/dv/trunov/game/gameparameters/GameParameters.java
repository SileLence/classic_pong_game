package com.dv.trunov.game.gameparameters;

import com.dv.trunov.game.gameparameters.switchable.BallSpeed;
import com.dv.trunov.game.gameparameters.switchable.Language;
import com.dv.trunov.game.gameparameters.switchable.PointsToWin;
import com.dv.trunov.game.gameparameters.switchable.ServeSide;
import com.dv.trunov.game.gameparameters.switchable.Toggle;
import com.dv.trunov.game.storage.StorageService;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.ServeState;
import com.dv.trunov.game.util.SoundToPlay;

import java.util.HashMap;
import java.util.Map;

public class GameParameters {

    private static final GameParameters INSTANCE = new GameParameters();
    private final Map<TextKey, SettingOption> settings = new HashMap<>();
    private GameState gameState;
    private ServeState serveState;
    private GameMode gameMode;
    private Language language;
    private PointsToWin pointsToWin;
    private BallSpeed multiplayerBallSpeed;
    private Toggle soundState;
    private ServeSide serveSide;
    private SoundToPlay soundToPlay;
    private TextKey selectedKey;
    private float cooldown;
    private int selectedItemIndex;
    private int scoreOne;
    private int scoreTwo;
    private int bestScore;
    private boolean isNewRecord;
    private boolean appPaused;
    private boolean gamePaused;

    public static GameParameters getInstance() {
        return INSTANCE;
    }

    private GameParameters() {
    }

    public void init() {
        soundToPlay = SoundToPlay.NONE;
        selectedItemIndex = 0;
        cooldown = 0;
        bestScore = StorageService.getValue(TextKey.BEST, 0);
        pointsToWin = PointsToWin.fromIndex(StorageService.getValue(TextKey.POINTS_TO_WIN, 1));
        multiplayerBallSpeed = BallSpeed.fromIndex(StorageService.getValue(TextKey.BALL_SPEED, 2));
        soundState = Toggle.fromIndex(StorageService.getValue(TextKey.SOUND, 0));
        serveSide = ServeSide.fromIndex(StorageService.getValue(TextKey.STARTING_SERVE, 0));
        String languageIndex = StorageService.getValue(TextKey.LANGUAGE, "");
        if ("".equals(languageIndex)) {
            language = Language.ENGLISH;
            gameState = GameState.TITLE;
        } else {
            language = Language.fromIndex(Integer.parseInt(languageIndex));
            gameState = GameState.MENU;
        }
        setSettings();
        StorageService.persistAll(this);
        setStartGame();
    }

    public void resetSelectionIndex() {
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
        if (scoreOne > bestScore) {
            bestScore = scoreOne;
            isNewRecord = true;
        }
    }

    public void setStartGame() {
        scoreOne = 0;
        scoreTwo = 0;
        isNewRecord = false;
        serveState = ServeState.NONE;
    }

    public void resetBestScore() {
        if (GameState.RESET.equals(gameState)) {
            bestScore = 0;
        }
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

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public PointsToWin getPointsToWin() {
        return pointsToWin;
    }

    public BallSpeed getMultiplayerBallSpeed() {
        return multiplayerBallSpeed;
    }

    public Toggle getSoundState() {
        return soundState;
    }

    public ServeSide getServeSide() {
        return serveSide;
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

    public int getBestScore() {
        return bestScore;
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

    public void setSelectedKey(TextKey selectedKey) {
        this.selectedKey = selectedKey;
    }

    public TextKey getSelectedKey() {
        return selectedKey;
    }

    public void pauseGame() {
        gameState = GameState.PAUSE;
        gamePaused = true;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public void resumeGame() {
        if (gameState == GameState.PAUSE) {
            gameState = serveState == ServeState.NONE ? GameState.PLAYING : GameState.GOAL;
        }
        gamePaused = false;
    }

    public void pauseApplication() {
        appPaused = true;
    }

    public boolean isApplicationPaused() {
        return appPaused;
    }

    public void resumeApplication() {
        appPaused = false;
    }

    public SettingOption getSetting(TextKey key) {
        return settings.get(key);
    }

    private void setSettings() {
        settings.put(TextKey.POINTS_TO_WIN, new SettingOption() {
            @Override
            public void next() {
                pointsToWin = pointsToWin.next();
            }

            @Override
            public void previous() {
                pointsToWin = pointsToWin.previous();
            }

            @Override
            public int getIndex() {
                return pointsToWin.getIndex();
            }

            @Override
            public TextKey getValueKey() {
                return pointsToWin.getKey();
            }
        });

        settings.put(TextKey.BALL_SPEED, new SettingOption() {
            @Override
            public void next() {
                multiplayerBallSpeed = multiplayerBallSpeed.next();
            }

            @Override
            public void previous() {
                multiplayerBallSpeed = multiplayerBallSpeed.previous();
            }

            @Override
            public int getIndex() {
                return multiplayerBallSpeed.getIndex();
            }

            @Override
            public TextKey getValueKey() {
                return multiplayerBallSpeed.getKey();
            }
        });

        settings.put(TextKey.STARTING_SERVE, new SettingOption() {
            @Override
            public void next() {
                serveSide = serveSide.next();
            }

            @Override
            public void previous() {
                serveSide = serveSide.previous();
            }

            @Override
            public int getIndex() {
                return serveSide.getIndex();
            }

            @Override
            public TextKey getValueKey() {
                return serveSide.getKey();
            }
        });

        settings.put(TextKey.SOUND, new SettingOption() {
            @Override
            public void next() {
                soundState = soundState.next();
            }

            @Override
            public void previous() {
                soundState = soundState.previous();
            }

            @Override
            public int getIndex() {
                return soundState.getIndex();
            }

            @Override
            public TextKey getValueKey() {
                return soundState.getKey();
            }
        });

        settings.put(TextKey.LANGUAGE, new SettingOption() {
            @Override
            public void next() {
                language = language.next();
            }

            @Override
            public void previous() {
                language = language.previous();
            }

            @Override
            public int getIndex() {
                return language.getIndex();
            }

            @Override
            public TextKey getValueKey() {
                return language.getKey();
            }
        });
    }
}
