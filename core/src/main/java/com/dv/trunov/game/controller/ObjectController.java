package com.dv.trunov.game.controller;

import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.SoundToPlay;

public class ObjectController {

    private static final ObjectController INSTANCE = new ObjectController();
    private Platform[] platforms;
    private Ball ball;

    private ObjectController() {
    }

    public static ObjectController getInstance() {
        return INSTANCE;
    }

    public boolean createWorldObjects(GameParameters gameParameters) {
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
            ball.setSpeed(gameParameters.getMultiplayerBallSpeed().getValue());
            ball.setStartPositionAndDirection(gameParameters.getServeSide());
        }
        for (Platform platform : platforms) {
            platform.setSpeed(ball.getSpeed() * Constants.Physics.PLATFORM_SPEED_MODIFICATOR);
        }
        return true;
    }

    public void increaseSpeed(GameParameters gameParameters) {
        int scoreOne = gameParameters.getScoreOne();
        float ballSpeed = ball.getSpeed();
        float newBallSpeed = Constants.Physics.BALL_SPEED + (scoreOne - 1) * Constants.Physics.BALL_SPEED_STEP;
        ball.setSpeed(newBallSpeed);
        platforms[0].setSpeed(newBallSpeed * Constants.Physics.PLATFORM_SPEED_MODIFICATOR);
        if (newBallSpeed > ballSpeed) {
            gameParameters.updateCooldown(Constants.Physics.SCORE_UP_COOLDOWN);
            gameParameters.setSoundToPlay(SoundToPlay.SCORE_UP);
        }
    }

    public Ball getBall() {
        return ball;
    }

    public Platform[] getPlatforms() {
        return platforms;
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
