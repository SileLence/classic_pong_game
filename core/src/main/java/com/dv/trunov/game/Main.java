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
import com.dv.trunov.game.controller.ObjectController;
import com.dv.trunov.game.controller.SoundController;
import com.dv.trunov.game.controller.UIController;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.physics.PhysicsEngine;
import com.dv.trunov.game.physics.PhysicsProcessor;
import com.dv.trunov.game.renderer.ObjectRenderer;
import com.dv.trunov.game.renderer.UIRenderer;
import com.dv.trunov.game.ui.LocalizationService;
import com.dv.trunov.game.ui.TextLabel;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.SoundToPlay;

public class Main extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private ObjectController objectController;
    private UIController uiController;
    private InputController inputController;
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
        objectController.initGameParameters();
        gameParameters = objectController.getGameParameters();
        localizationService = LocalizationService.getInstance();
        localizationService.setLanguage(gameParameters.getCurrentLanguage());
        uiController = UIController.getInstance();
        uiController.setLocalizationService(localizationService);
        uiController.init(gameParameters);
        inputController = InputController.getInstance();
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
        // TODO add language selection
        // TODO Separate settings for 1 player and 2 players
        // TODO Don't show language selection if language was selected earlier
        // TODO проверить сохранение файла с нуля без изменения настроек
        float deltaTime = Gdx.graphics.getDeltaTime();
        GameState gameState = gameParameters.getGameState();
        boolean isSingleplayer = GameMode.SINGLEPLAYER == gameParameters.getGameMode();
        clearScreen();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        switch (gameState) {
            case TITLE -> {
                inputController.processMenuInput(gameParameters, physicsEngine, uiController.getTitleMenu());
                if (GameState.MENU == gameParameters.getGameState()) {
                    localizationService.setLanguage(gameParameters.getCurrentLanguage());
                    uiController.updateLocalization();
                }
                physicsEngine.updateAlpha(deltaTime);
                drawUI(uiController.getTitle());
                drawUI(uiController.getTitleMenu());
                processSound();
            }
            case MENU -> {
                if (worldObjectsCreated) {
                    worldObjectsCreated = objectController.destroyWorldObjects();
                }
                inputController.processMenuInput(gameParameters, physicsEngine, uiController.getMainMenu());
                physicsEngine.updateAlpha(deltaTime);
                drawUI(uiController.getTitle());
                drawUI(uiController.getMainMenu());
                processSound();
            }
            case SETTINGS -> {
                inputController.processSettingsInput(gameParameters, physicsEngine, uiController.getSettingsMenu());
                physicsEngine.updateAlpha(deltaTime);
                uiController.updateSettingsValues(gameParameters);
                drawUI(uiController.getSettingsScreen());
                drawUI(uiController.getSettingsMenu());
                processSound();
            }
            case RESET -> {
                inputController.processMenuInput(gameParameters, physicsEngine, uiController.getResetMenu());
                physicsEngine.updateAlpha(deltaTime);
                drawUI(uiController.getResetScreen());
                drawUI(uiController.getResetMenu());
                processSound();
            }
            case IDLE -> {
                if (!worldObjectsCreated) {
                    worldObjectsCreated = objectController.createWorldObjects(gameParameters);
                }
                inputController.processMenuInput(gameParameters, physicsEngine, uiController.getPressEnter());
                physicsEngine.updateAlpha(deltaTime);
                updatePhysics(deltaTime);
                uiController.updateCounters(gameParameters, isSingleplayer);
                drawBackground();
                drawWorldObjects();
                drawUI(uiController.getPlayingScreen(isSingleplayer));
                boolean isBestMoreThanOne = gameParameters.getBestLevel() != 1;
                if (isSingleplayer && isBestMoreThanOne) {
                    drawUI(uiController.getCounterBestLevel());
                }
                drawUI(uiController.getPressEnter());
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
                    objectController.increaseSpeed(gameParameters.getLevel());
                    uiController.updateCounters(gameParameters, true);
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
                drawUI(uiController.getPlayingScreen(isSingleplayer));
                if (isGameStateChanged) {
                    boolean isGameOver = GameState.GAME_OVER == gameParameters.getGameState();
                    boolean isNewRecord = gameParameters.isNewRecord();
                    if (isGameOver && isNewRecord) {
                        gameParameters.setSoundToPlay(SoundToPlay.WIN);
                    }
                }
                processSound();
            }
            case PAUSE -> {
                physicsEngine.pause();
                inputController.processMenuInput(gameParameters, physicsEngine, uiController.getPauseMenu());
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
                drawUI(uiController.getPauseScreen(isSingleplayer));
                drawUI(uiController.getPauseMenu());
                processSound();
            }
            case GOAL -> {
                inputController.processPlayingInput(objectController.getPlatforms(), gameParameters);
                boolean isGameStateChanged = GameState.GOAL != gameParameters.getGameState();
                if (!isGameStateChanged) {
                    updatePhysics(deltaTime);
                }
                physicsEngine.updateAlpha(deltaTime);
                uiController.updateCounters(gameParameters, false);
                boolean isWin = gameParameters.checkWin();
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                if (!isWin) {
                    objectRenderer.drawBall(objectController.getBall(), spriteBatch);
                }
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                drawUI(uiController.getPlayingScreen(false));
                if (!isGameStateChanged) {
                    drawUI(uiController.getServeText(gameParameters.getServeState()));
                }
                processSound();
            }
            case WIN -> {
                inputController.processMenuInput(gameParameters, physicsEngine, uiController.getEndGameMenu());
                physicsEngine.updateAlpha(deltaTime);
                updatePhysics(deltaTime);
                uiController.updateCounters(gameParameters, false);
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                int scoreOne = gameParameters.getScoreOne();
                int scoreTwo = gameParameters.getScoreTwo();
                drawUI(uiController.getWinScreen(scoreOne > scoreTwo));
                drawUI(uiController.getEndGameMenu());
                worldObjectsCreated = false;
                processSound();
            }
            case GAME_OVER -> {
                inputController.processMenuInput(gameParameters, physicsEngine, uiController.getEndGameMenu());
                physicsEngine.updateAlpha(deltaTime);
                updatePhysics(deltaTime);
                uiController.updateCounters(gameParameters, true);
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                drawUI(uiController.getEndGameScreen(gameParameters.isNewRecord()));
                drawUI(uiController.getEndGameMenu());
                worldObjectsCreated = false;
                processSound();
            }
            case EXIT -> Gdx.app.exit();
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
        float volume = gameParameters.getSoundState().getIndex();
        if (SoundToPlay.NONE != soundToPlay) {
            SoundController.playSound(soundToPlay, volume);
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
        uiController.dispose();
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
