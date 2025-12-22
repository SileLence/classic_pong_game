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
import com.dv.trunov.game.ui.UITextItem;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.Language;

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
        objectController.initGameParameters(Language.ENGLISH);
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
        if (GameState.TITLE == gameState || GameState.PAUSE == gameState || GameState.MENU == gameState) {
            physicsEngine.updateAlpha(deltaTime);
        }
        clearScreen();
        if (GameState.TITLE == gameState) {
            inputController.processTitleInputs(gameParameters);
            drawUI(uiController.getTitleScreen());
        }
        if (GameState.MENU == gameState) {
            inputController.processMenuInputs(gameParameters, physicsEngine);
            drawUI(uiController.getMenuScreen());
        }
        if (GameState.SETTINGS == gameState) {
            // TODO Implement settings
            gameParameters.setGameState(GameState.MENU);
        }
        if (GameState.PLAYING == gameState && !worldObjectsCreated) {
            worldObjectsCreated = objectController.createWorldObjects(gameParameters);
        }
        if (GameState.PLAYING == gameState) {
            physicsEngine.resume();
            updatePhysics(deltaTime);
            inputController.processPlayingInputs(objectController.getPlatforms(), gameParameters);
            drawBackground();
            drawWorldObjects();
            drawUI(uiController.getPlayingScreen());
        }
        if (GameState.GOAL == gameState) {
            physicsEngine.goalCooldown(gameParameters, deltaTime);
            if (gameParameters.getCooldown() <= 1f) {
                uiController.updateCounters(gameParameters);
            }
            if (gameParameters.getCooldown() <= 0.5f) {
                objectController.resetBallPosition();
            }
            updatePhysics(deltaTime);
            inputController.processPlayingInputs(objectController.getPlatforms(), gameParameters);
            drawBackground();
            drawWorldObjects();
            drawUI(uiController.getPlayingScreen());
        }
        if (GameState.PAUSE == gameState) {
            inputController.processPauseInputs(gameParameters, physicsEngine);
            drawBackground();
            drawWorldObjects();
            drawUI(uiController.getPauseScreen());
            physicsEngine.pause();
            if (gameParameters.getGameState() == GameState.MENU) {
                worldObjectsCreated = objectController.destroyWorldObjects();
            }
        }
        if (GameState.EXIT == gameState) {
            Gdx.app.exit();
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

    private void drawUI(UITextItem[] uiTextItems) {
        spriteBatch.begin();
        uiRenderer.drawUI(
            uiTextItems,
            gameParameters.getActiveMenuItemId(),
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
