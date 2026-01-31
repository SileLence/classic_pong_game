package com.dv.trunov.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dv.trunov.game.controller.InputController;
import com.dv.trunov.game.controller.ActionController;
import com.dv.trunov.game.controller.ObjectController;
import com.dv.trunov.game.controller.SoundController;
import com.dv.trunov.game.controller.TextController;
import com.dv.trunov.game.gameparameters.switchable.Language;
import com.dv.trunov.game.gameparameters.switchable.Toggle;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.physics.PhysicsEngine;
import com.dv.trunov.game.physics.PhysicsProcessor;
import com.dv.trunov.game.renderer.ObjectRenderer;
import com.dv.trunov.game.renderer.UIRenderer;
import com.dv.trunov.game.storage.StorageService;
import com.dv.trunov.game.ui.text.LocalizationService;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.ui.text.TextLabel;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.SoundToPlay;

public class MainLoop extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private ObjectController objectController;
    private TextController textController;
    private InputController inputController;
    private ActionController actionController;
    private PhysicsProcessor physicsProcessor;
    private PhysicsEngine physicsEngine;
    private ObjectRenderer objectRenderer;
    private UIRenderer uiRenderer;
    private LocalizationService localizationService;
    private GameParameters gameParameters;
    private boolean worldObjectsCreated;
    private OrthographicCamera camera;
    private Viewport viewport;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        objectController = ObjectController.getInstance();
        gameParameters = GameParameters.getInstance();
        gameParameters.init();
        localizationService = LocalizationService.getInstance();
        localizationService.setLanguage(gameParameters.getLanguage());
        textController = TextController.getInstance();
        textController.setLocalizationService(localizationService);
        textController.init(gameParameters);
        inputController = InputController.getInstance();
        actionController = ActionController.getInstance();
        actionController.init();
        SoundController.init();
        physicsProcessor = PhysicsProcessor.getInstance();
        physicsEngine = PhysicsEngine.getInstance();
        objectRenderer = ObjectRenderer.getInstance();
        uiRenderer = UIRenderer.getInstance();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.World.WIDTH, Constants.World.HEIGHT, camera);
        viewport.apply(true);
    }

    @Override
    public void render() {
        // TODO implement app icon
        // TODO Separate settings for 1 player and 2 players
        // TODO implement exit from settings and from pause by pressing Escape
        float deltaTime = Gdx.graphics.getDeltaTime();
        GameState gameState = gameParameters.getGameState();
        boolean isSingleplayer = GameMode.SINGLEPLAYER == gameParameters.getGameMode();
        clearScreen();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        switch (gameState) {
            case TITLE -> {
                inputController.processInput(gameParameters, physicsEngine, actionController.getTitleActions());
                if (GameState.MENU == gameParameters.getGameState()) {
                    localizationService.setLanguage(gameParameters.getLanguage());
                    textController.updateLocalization();
                }
                physicsEngine.updateAlpha(deltaTime);
                drawUI(textController.getTitleScreen());
                processSound();
            }
            case MENU -> {
                if (worldObjectsCreated) {
                    worldObjectsCreated = objectController.destroyWorldObjects();
                }
                inputController.processInput(gameParameters, physicsEngine, actionController.getMainMenuActions());
                physicsEngine.updateAlpha(deltaTime);
                drawUI(textController.getMainMenuScreen());
                processSound();
            }
            case SETTINGS -> {
                Language curretLanguage = gameParameters.getLanguage();
                inputController.processInput(gameParameters, physicsEngine, actionController.getSettingsActions());
                physicsEngine.updateAlpha(deltaTime);
                boolean isLanguageChanged = curretLanguage.equals(gameParameters.getLanguage());
                if (isLanguageChanged) {
                    localizationService.setLanguage(gameParameters.getLanguage());
                    textController.updateLocalization();
                }
                textController.updateSettingsValues(gameParameters);
                drawUI(textController.getSettingsScreen());
                drawUI(textController.getSettingsMenu());
                processSound();
                StorageService.persistAll(gameParameters);
            }
            case RESET -> {
                inputController.processInput(gameParameters, physicsEngine, actionController.getResetBestActions());
                physicsEngine.updateAlpha(deltaTime);
                drawUI(textController.getResetScreen());
                drawUI(textController.getResetMenu());
                processSound();
            }
            case IDLE -> {
                if (!worldObjectsCreated) {
                    worldObjectsCreated = objectController.createWorldObjects(gameParameters);
                }
                inputController.processInput(gameParameters, physicsEngine, actionController.getIdleActions());
                physicsEngine.updateAlpha(deltaTime);
                updatePhysics(deltaTime);
                textController.updateCounters(gameParameters, isSingleplayer);
                drawBackground();
                drawWorldObjects();
                drawUI(textController.getPlayingScreen(isSingleplayer));
                boolean isBestMoreThanOne = gameParameters.getBestScore() != 1;
                if (isSingleplayer && isBestMoreThanOne) {
                    drawUI(textController.getCounterBestScore());
                }
                drawUI(textController.getPressEnter());
                boolean isGameStateChanged = GameState.IDLE != gameParameters.getGameState();
                if (isGameStateChanged) {
                    gameParameters.setSoundToPlay(SoundToPlay.SERVE);
                }
                processSound();
            }
            case PLAYING ->  {
                inputController.processPlayingInput(objectController.getPlatforms(), gameParameters);
                updatePhysics(deltaTime);
                if (isSingleplayer) {
                    objectController.increaseSpeed(gameParameters);
                    textController.updateCounters(gameParameters, true);
                    gameParameters.updateCooldown();
                }
                boolean isGameStateChanged = GameState.PLAYING != gameParameters.getGameState();
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                if (!isGameStateChanged) {
                    objectRenderer.drawBall(objectController.getBall(), spriteBatch);
                }
                objectRenderer.drawBallTail(objectController.getBall(), spriteBatch);
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                drawUI(textController.getPlayingScreen(isSingleplayer));
                if (isGameStateChanged) {
                    boolean isGameOver = GameState.GAME_OVER == gameParameters.getGameState();
                    boolean isNewRecord = gameParameters.isNewRecord();
                    if (isGameOver && isNewRecord) {
                        gameParameters.setSoundToPlay(SoundToPlay.WIN);
                        StorageService.persistValue(TextKey.BEST, gameParameters.getBestScore());
                    }
                }
                processSound();
            }
            case PAUSE -> {
                physicsEngine.pause();
                inputController.processInput(gameParameters, physicsEngine, actionController.getPauseActions());
                boolean isGameStateChanged = GameState.PAUSE != gameParameters.getGameState();
                if (isGameStateChanged) {
                    physicsEngine.resume();
                    if (GameState.MENU == gameParameters.getGameState()) {
                        worldObjectsCreated = objectController.destroyWorldObjects();
                        processSound();
                        return;
                    }
                }
                physicsEngine.updateAlpha(deltaTime);
                drawBackground();
                spriteBatch.begin();
                Ball ball = objectController.getBall();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                objectRenderer.drawBall(ball, spriteBatch);
                objectRenderer.drawBallTail(ball, spriteBatch);
                objectRenderer.drawBallExplosion(ball, spriteBatch);
                spriteBatch.end();
                drawUI(textController.getPauseScreen(isSingleplayer));
                drawUI(textController.getPauseMenu());
                processSound();
            }
            case GOAL -> {
                inputController.processPlayingInput(objectController.getPlatforms(), gameParameters);
                boolean isGameStateChanged = GameState.GOAL != gameParameters.getGameState();
                if (!isGameStateChanged) {
                    updatePhysics(deltaTime);
                }
                physicsEngine.updateAlpha(deltaTime);
                textController.updateCounters(gameParameters, false);
                boolean isWin = gameParameters.checkWin();
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                if (!isWin) {
                    objectRenderer.drawBall(objectController.getBall(), spriteBatch);
                }
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                drawUI(textController.getPlayingScreen(false));
                if (!isGameStateChanged) {
                    drawUI(textController.getServeText(gameParameters.getServeState()));
                }
                processSound();
            }
            case WIN -> {
                inputController.processInput(gameParameters, physicsEngine, actionController.getEndGameActions());
                physicsEngine.updateAlpha(deltaTime);
                updatePhysics(deltaTime);
                textController.updateCounters(gameParameters, false);
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                int scoreOne = gameParameters.getScoreOne();
                int scoreTwo = gameParameters.getScoreTwo();
                drawUI(textController.getWinScreen(scoreOne > scoreTwo));
                drawUI(textController.getEndGameMenu());
                worldObjectsCreated = false;
                processSound();
            }
            case GAME_OVER -> {
                inputController.processInput(gameParameters, physicsEngine, actionController.getEndGameActions());
                physicsEngine.updateAlpha(deltaTime);
                updatePhysics(deltaTime);
                textController.updateCounters(gameParameters, true);
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                drawUI(textController.getEndGameScreen(gameParameters.isNewRecord()));
                drawUI(textController.getEndGameMenu());
                worldObjectsCreated = false;
                processSound();
            }
            case EXIT -> {
                updatePhysics(deltaTime);
                gameParameters.updateCooldown();
                if (gameParameters.getCooldown() == 0) {
                    Gdx.app.exit();
                }
            }
        }
    }

    private void updatePhysics(float deltaTime) {
        physicsEngine.updatePhysics(
            physicsProcessor,
            objectController.getPlatforms(),
            objectController.getBall(),
            gameParameters,
            deltaTime
        );
    }

    private void drawBackground() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        objectRenderer.drawBorder(shapeRenderer);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawWorldObjects() {
        spriteBatch.begin();
        objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
        objectRenderer.drawBall(objectController.getBall(), spriteBatch);
        objectRenderer.drawBallTail(objectController.getBall(), spriteBatch);
        objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
        spriteBatch.end();
    }

    private void drawUI(TextLabel... textLabels) {
        spriteBatch.begin();
        uiRenderer.drawUI(
            textLabels,
            gameParameters,
            physicsEngine.getAlpha(),
            spriteBatch
        );
        spriteBatch.end();
    }

    private void processSound() {
        SoundToPlay soundToPlay = gameParameters.getSoundToPlay();
        Toggle soundState = gameParameters.getSoundState();
        if (SoundToPlay.NONE != soundToPlay) {
            SoundController.playSound(soundToPlay, soundState);
            gameParameters.clearSoundToPlay();
        }
    }

    @Override
    public void pause() {
        gameParameters.setGameState(GameState.PAUSE);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        textController.dispose();
        SoundController.dispose();
        if (worldObjectsCreated) {
            objectController.dispose();
        }
    }

    private void clearScreen() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
