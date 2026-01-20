package com.dv.trunov.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dv.trunov.game.controller.InputController;
import com.dv.trunov.game.controller.ObjectController;
import com.dv.trunov.game.controller.UIController;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.physics.PhysicsEngine;
import com.dv.trunov.game.physics.PhysicsProcessor;
import com.dv.trunov.game.renderer.ObjectRenderer;
import com.dv.trunov.game.renderer.UIRenderer;
import com.dv.trunov.game.ui.TextLabel;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;

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
    private GameParameters gameParameters;
    private boolean worldObjectsCreated;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        objectController = ObjectController.getInstance();
        objectController.initGameParameters();
        gameParameters = objectController.getGameParameters();
        uiController = UIController.getInstance();
        uiController.createLanguageSelectionUI();
        inputController = InputController.getInstance();
        physicsProcessor = PhysicsProcessor.getInstance();
        physicsEngine = PhysicsEngine.getInstance();
        objectRenderer = ObjectRenderer.getInstance();
        uiRenderer = UIRenderer.getInstance();
    }

    @Override
    public void render() {
        // TODO implement resize and fullscreen
        // TODO add sounds
        // TODO implement app icon
        float deltaTime = Gdx.graphics.getDeltaTime();
        GameState gameState = gameParameters.getGameState();
        boolean isSingleplayer = GameMode.SINGLEPLAYER == gameParameters.getGameMode();
        clearScreen();
        switch (gameState) {
            case TITLE -> {
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getTitleMenu());
                if (GameState.MENU == gameParameters.getGameState()) {
                    uiController.createLocalizedUI(gameParameters);
                }
                physicsEngine.updateAlpha(deltaTime);
                drawUI(uiController.getTitle());
                drawUI(uiController.getTitleMenu());
            }
            case MENU -> {
                if (worldObjectsCreated) {
                    worldObjectsCreated = objectController.destroyWorldObjects();
                }
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getMainMenu());
                physicsEngine.updateAlpha(deltaTime);
                drawUI(uiController.getTitle());
                drawUI(uiController.getMainMenu());
            }
            case SETTINGS -> {
                inputController.processSettingsInputs(gameParameters, physicsEngine, uiController.getSettingsMenu());
                physicsEngine.updateAlpha(deltaTime);
                uiController.updateSettingsValues(gameParameters);
                drawUI(uiController.getSettingsScreen());
                drawUI(uiController.getSettingsMenu());
            }
            case RESET -> {
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getResetMenu());
                physicsEngine.updateAlpha(deltaTime);
                drawUI(uiController.getResetScreen());
                drawUI(uiController.getResetMenu());
            }
            case IDLE -> {
                if (!worldObjectsCreated) {
                    worldObjectsCreated = objectController.createWorldObjects(gameParameters);
                }
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getPressEnter());
                physicsEngine.updateAlpha(deltaTime);
                updatePhysics(deltaTime);
                uiController.updateCounters(gameParameters, isSingleplayer);
                drawBackground();
                drawWorldObjects();
                drawUI(uiController.getPlayingScreen(isSingleplayer));
                drawUI(uiController.getPressEnter());
            }
            case PLAYING ->  {
                inputController.processPlayingInputs(objectController.getPlatforms(), gameParameters);
                updatePhysics(deltaTime);
                if (isSingleplayer) {
                    objectController.increaseSpeed(gameParameters.getLevel());
                    uiController.updateCounters(gameParameters, true);
                    gameParameters.updateCooldown();
                }
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                if (GameState.PLAYING == gameParameters.getGameState()) {
                    // since game state could change don't need to draw ball in that case
                    objectRenderer.drawBall(objectController.getBall(), spriteBatch);
                }
                objectRenderer.drawBallTail(objectController.getBall(), spriteBatch);
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                drawUI(uiController.getPlayingScreen(isSingleplayer));
            }
            case PAUSE -> {
                physicsEngine.pause();
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getPauseMenu());
                gameState = gameParameters.getGameState();
                if (GameState.PAUSE != gameState) {
                    if (GameState.MENU == gameState) {
                        worldObjectsCreated = objectController.destroyWorldObjects();
                    }
                    physicsEngine.resume();
                    return;
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
            }
            case GOAL -> {
                inputController.processPlayingInputs(objectController.getPlatforms(), gameParameters);
                updatePhysics(deltaTime);
                physicsEngine.updateAlpha(deltaTime);
                uiController.updateCounters(gameParameters, false);
                gameParameters.checkWin();
                boolean isGameStateChanged = GameState.GOAL != gameParameters.getGameState();
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                if (!isGameStateChanged) {
                    objectRenderer.drawBall(objectController.getBall(), spriteBatch);
                }
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                drawUI(uiController.getPlayingScreen(false));
                if (!isGameStateChanged) {
                    drawUI(uiController.getServeText(gameParameters.getServeState()));
                }
            }
            case WIN -> {
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getEndGameMenu());
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
            }
            case GAME_OVER -> {
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getEndGameMenu());
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

    @Override
    public void pause() {
        gameParameters.setGameState(GameState.PAUSE);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        uiController.dispose();
        if (worldObjectsCreated) {
            objectController.dispose();
        }
    }

    private void clearScreen() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
    }
}
