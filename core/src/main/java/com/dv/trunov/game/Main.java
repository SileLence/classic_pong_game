package com.dv.trunov.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dv.trunov.game.controller.InputController;
import com.dv.trunov.game.controller.ObjectController;
import com.dv.trunov.game.physics.PhysicsProcessor;
import com.dv.trunov.game.controller.UIController;
import com.dv.trunov.game.physics.PhysicsEngine;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.renderer.ObjectRenderer;
import com.dv.trunov.game.renderer.UIRenderer;
import com.dv.trunov.game.ui.TextLabel;
import com.dv.trunov.game.util.Constants;
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
        float deltaTime = Gdx.graphics.getDeltaTime();
        GameState gameState = gameParameters.getGameState();
        boolean isSingleplayer = GameMode.SINGLEPLAYER == gameParameters.getGameMode();
        clearScreen();
        switch (gameState) {
            case TITLE -> {
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getTitleMenu());
                if (GameState.MENU == gameParameters.getGameState()) {
                    uiController.createLocalizedUI(gameParameters.getCurrentLanguage());
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
                // TODO Implement settings
                physicsEngine.updateAlpha(deltaTime);
                gameParameters.setGameState(GameState.MENU);
            }
            case IDLE -> {
                if (!worldObjectsCreated) {
                    worldObjectsCreated = objectController.createWorldObjects(gameParameters);
                }
                objectController.resetPlatformPosition();
                objectController.resetBallPosition();
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
                physicsEngine.resume();
                inputController.processPlayingInputs(objectController.getPlatforms(), gameParameters);
                updatePhysics(deltaTime);
                if (isSingleplayer) {
                    objectController.increaseSpeed(gameParameters.getLevel());
                    uiController.updateCounters(gameParameters, true);
                }
                drawBackground();
                drawWorldObjects();
                drawUI(uiController.getPlayingScreen(isSingleplayer));
            }
            case PAUSE -> {
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getPauseMenu());
                physicsEngine.updateAlpha(deltaTime);
                drawBackground();
                spriteBatch.begin();
                Ball ball = objectController.getBall();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                if (ball.getParticles().isEmpty()) {
                    objectRenderer.drawBall(ball, spriteBatch);
                    objectRenderer.drawBallTail(ball, spriteBatch);
                }
                objectRenderer.drawBallExplosion(ball, spriteBatch);
                spriteBatch.end();
                drawUI(uiController.getPauseScreen(isSingleplayer));
                drawUI(uiController.getPauseMenu());
                physicsEngine.pause();
                if (GameState.MENU == gameParameters.getGameState()) {
                    worldObjectsCreated = objectController.destroyWorldObjects();
                }
            }
            case GOAL -> {
                // TODO fix bug when you can add score by press Esc button at the goal time
                inputController.processPlayingInputs(objectController.getPlatforms(), gameParameters);
                physicsEngine.processCooldown(gameParameters, deltaTime);
                updatePhysics(deltaTime);
                if (gameParameters.getCooldown() <= Constants.Physics.GOAL_COOLDOWN * 0.66f) {
                    uiController.updateCounters(gameParameters, false);
                }
                drawBackground();
                spriteBatch.begin();
                objectRenderer.drawPlatforms(objectController.getPlatforms(), spriteBatch);
                if (gameParameters.getCooldown() <= Constants.Physics.GOAL_COOLDOWN * 0.33f) {
                    objectController.resetBallPosition();
                    objectRenderer.drawBall(objectController.getBall(), spriteBatch);
                }
                objectRenderer.drawBallExplosion(objectController.getBall(), spriteBatch);
                spriteBatch.end();
                drawUI(uiController.getPlayingScreen(false));
                gameParameters.checkWin();
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
                objectController.resetBallPosition();
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
                objectController.resetBallPosition();
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
            gameParameters.getSelectedItemIndex(),
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
