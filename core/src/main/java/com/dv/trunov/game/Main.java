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
import com.dv.trunov.game.engine.PhysicsEngine;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.renderer.ObjectRenderer;
import com.dv.trunov.game.renderer.TextRenderer;
import com.dv.trunov.game.util.GameState;

public class Main extends ApplicationAdapter {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private ObjectController objectController;
    private InputController inputController;
    private PhysicsController physicsController;
    private PhysicsEngine physicsEngine;
    private ObjectRenderer objectRenderer;
    private TextRenderer textRenderer;
    private GameParameters gameParameters;
    private boolean worldObjectsCreated;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        objectController = ObjectController.getInstance();
        objectController.init();
        gameParameters = objectController.getGameParameters();
        inputController = InputController.getInstance();
        physicsController = PhysicsController.getInstance();
        physicsEngine = PhysicsEngine.getInstance();
        objectRenderer = ObjectRenderer.getInstance();
        textRenderer = TextRenderer.getInstance();
    }

    @Override
    public void render() {

        // TODO Добавить отскок мяча от торцов платформы

        float deltaTime = Gdx.graphics.getDeltaTime();
        GameState gameState = gameParameters.getGameState();
        if (gameState == GameState.TITLE || gameState == GameState.PAUSE || gameState == GameState.MENU) {
            physicsEngine.updateAlpha(deltaTime);
        }
        clearScreen();
        if (gameState == GameState.TITLE) {
            gameParameters = inputController.processTitleInputs(gameParameters);

            spriteBatch.begin();
            textRenderer.drawUI(
                objectController.getTitleScreen(),
                gameParameters.getActiveMenuItemId(),
                physicsEngine.getAlpha(),
                spriteBatch
            );
            spriteBatch.end();
        }
        if (gameState == GameState.MENU) {
            if (gameParameters.getActiveMenuItemId() > 4) {
                gameParameters.setActiveMenuItemId(1);
            }
            gameParameters = inputController.processMenuInputs(gameParameters, physicsEngine);

            spriteBatch.begin();
            textRenderer.drawUI(
                objectController.getMenuScreen(),
                gameParameters.getActiveMenuItemId(),
                physicsEngine.getAlpha(),
                spriteBatch
            );
            spriteBatch.end();
        }
        if (gameState == GameState.PLAYING && !worldObjectsCreated) {
            worldObjectsCreated = objectController.createWorldObjects(gameParameters);
        }
        if (gameState == GameState.PLAYING) {
            physicsEngine.resume();
            physicsEngine.updatePhysics(
                physicsController,
                objectController.getPlatforms(),
                objectController.getBall(),
                gameParameters.getGameMode(),
                deltaTime);
            gameParameters = inputController.processPlayingInputs(objectController.getPlatforms(), gameParameters);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            objectRenderer.drawBorder(shapeRenderer);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            spriteBatch.begin();
            objectRenderer.drawWorldObjects(objectController.getPlatforms(), objectController.getBall(), spriteBatch);
            spriteBatch.end();
        }
        if (gameState == GameState.PAUSE) {
            gameParameters = inputController.processPauseInputs(gameParameters, physicsEngine);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            objectRenderer.drawBorder(shapeRenderer);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            spriteBatch.begin();
            objectRenderer.drawWorldObjects(objectController.getPlatforms(), objectController.getBall(), spriteBatch);
            textRenderer.drawUI(
                objectController.getPauseScreen(),
                gameParameters.getActiveMenuItemId(),
                physicsEngine.getAlpha(),
                spriteBatch
            );
            spriteBatch.end();

            physicsEngine.pause();
            if (gameParameters.getGameState() == GameState.MENU) {
                worldObjectsCreated = objectController.destroyWorldObjects();
            }
        }
        if (gameState == GameState.EXIT) {
            Gdx.app.exit();
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
        if (worldObjectsCreated) {
            objectController.dispose();
        }
    }

    private void clearScreen() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
    }
}
