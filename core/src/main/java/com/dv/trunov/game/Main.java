package com.dv.trunov.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.dv.trunov.game.controller.InputController;
import com.dv.trunov.game.controller.ObjectController;
import com.dv.trunov.game.controller.PhysicsController;
import com.dv.trunov.game.controller.UIController;
import com.dv.trunov.game.engine.PhysicsEngine;
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
    private PhysicsController physicsController;
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
        uiController.createUIObjects();
        inputController = InputController.getInstance();
        physicsController = PhysicsController.getInstance();
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
                    uiController.updateLocalization(gameParameters.getCurrentLanguage());
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
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getPressEnter());
                physicsEngine.updateAlpha(deltaTime);
                drawBackground();
                drawWorldObjects();
                uiController.updateCounters(gameParameters, isSingleplayer);
                drawUI(uiController.getPlayingScreen(isSingleplayer));
                drawUI(uiController.getPressEnter());
            }
            case PLAYING ->  {
                physicsEngine.resume();
                updatePhysics(deltaTime);
                inputController.processPlayingInputs(objectController.getPlatforms(), gameParameters);
                drawBackground();
                drawWorldObjects();
                if (isSingleplayer) {
                    uiController.updateCounters(gameParameters, true);
                }
                drawUI(uiController.getPlayingScreen(isSingleplayer));
            }
            case PAUSE -> {
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getPauseMenu());
                physicsEngine.updateAlpha(deltaTime);
                drawBackground();
                drawWorldObjects();
                drawUI(uiController.getPauseScreen(isSingleplayer));
                drawUI(uiController.getPauseMenu());
                physicsEngine.pause();
                if (GameState.MENU == gameParameters.getGameState()) {
                    worldObjectsCreated = objectController.destroyWorldObjects();
                }
            }
            case GOAL -> {
                physicsEngine.processCooldown(gameParameters, deltaTime);
                if (gameParameters.getCooldown() <= Constants.Physics.GOAL_COOLDOWN * 0.66f) {
                    uiController.updateCounters(gameParameters, false);
                }
                if (gameParameters.getCooldown() <= Constants.Physics.GOAL_COOLDOWN * 0.33f) {
                    objectController.resetBallPosition();
                }
                updatePhysics(deltaTime);
                inputController.processPlayingInputs(objectController.getPlatforms(), gameParameters);
                drawBackground();
                drawWorldObjects();
                drawUI(uiController.getPlayingScreen(false));
                gameParameters.checkWin();
            }
            case WIN -> {
                // TODO Fix bug when ball is not visible after Win
                inputController.processMenuInputs(gameParameters, physicsEngine, uiController.getEndGameMenu());
                physicsEngine.updateAlpha(deltaTime);
                objectController.resetBallPosition();
                updatePhysics(deltaTime);
                drawBackground();
                drawWorldObjects();
                int scoreOne = gameParameters.getScoreOne();
                int scoreTwo = gameParameters.getScoreTwo();
                if (scoreOne > scoreTwo) {
                    drawUI(uiController.getWinScreen(true));
                } else  {
                    drawUI(uiController.getWinScreen(false));
                }
                drawUI(uiController.getEndGameMenu());
            }
            case EXIT -> Gdx.app.exit();
        }
    }

    private void updatePhysics(float deltaTime) {
        physicsEngine.updatePhysics(
            physicsController,
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
        objectRenderer.drawWorldObjects(
            objectController.getPlatforms(),
            objectController.getBall(),
            gameParameters,
            spriteBatch
        );
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
