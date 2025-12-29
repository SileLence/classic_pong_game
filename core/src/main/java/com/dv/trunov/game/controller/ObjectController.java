package com.dv.trunov.game.controller;

import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.util.Constants;

public class ObjectController {

    private static final ObjectController INSTANCE = new ObjectController();
    private GameParameters gameParameters;
    private Platform[] platforms;
    private Ball ball;

    private ObjectController() {
    }

    public static ObjectController getInstance() {
        return INSTANCE;
    }

    public void initGameParameters() {
        gameParameters = GameParameters.getInstance();
    }

    public boolean createWorldObjects(GameParameters gameParameters) {
        if (gameParameters == null) {
            throw new IllegalStateException("GameParameters are not initialized.");
        }
        ball = new Ball(Constants.Asset.BALL_TEXTURE_PATH);
        platforms = new Platform[gameParameters.getGameMode().getValue()];
        platforms[0] = new Platform(
            Constants.Border.LEFT_PLATFORM_BOUNDARY,
            Constants.Border.TOP_PLATFORM_BOUNDARY / 2f,
            true,
            Constants.Asset.PLATFORM_LEFT_TEXTURE_PATH
        );
        if (platforms.length > 1) {
            platforms[1] = new Platform(
                Constants.Border.RIGHT_PLATFORM_BOUNDARY,
                Constants.Border.TOP_PLATFORM_BOUNDARY / 2f,
                false,
                Constants.Asset.PLATFORM_RIGHT_TEXTURE_PATH
            );
        }
        return true;
    }

    public void resetBallPosition() {
        ball.setStartPosition();
    }

    public void resetPlatformPosition() {
        for (Platform platform : platforms) {
            platform.setStartPosition();
        }
    }

    public void increaseLevel(int level) {
        float ballSpeed = Constants.Speed.BALL_SPEED + (level - 1) * Constants.Speed.BALL_SPEED_STEP;
        ball.setSpeed(ballSpeed);
        platforms[0].setSpeed(ballSpeed * 0.9f);
    }

    public Ball getBall() {
        return ball;
    }

    public Platform[] getPlatforms() {
        return platforms;
    }

    public GameParameters getGameParameters() {
        return gameParameters;
    }

    public boolean destroyWorldObjects() {
        ball = null;
        platforms = null;
        return false;
    }

    public void dispose() {
        ball.getTexture().dispose();
        for (Platform platform : platforms) {
            platform.getTexture().dispose();
        }
    }
}
