package com.dv.trunov.game.physics;

import com.badlogic.gdx.math.MathUtils;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.gameparameters.switchable.ServeSide;
import com.dv.trunov.game.util.ServeState;
import com.dv.trunov.game.util.SoundToPlay;

public class PhysicsProcessor {

    private static final PhysicsProcessor INSTANCE = new PhysicsProcessor();
    private static final float MIN_X_VALUE = 0.2f;

    private PhysicsProcessor() {
    }

    public static PhysicsProcessor getInstance() {
        return INSTANCE;
    }

    void processGoalPhysics(Platform[] platforms, Ball ball, GameParameters gameParameters, float timeStep) {
        calcPlatformPhysics(platforms, timeStep);
        ServeState serveState = gameParameters.getServeState();
        if (ServeState.NONE != serveState) {
            Platform platform = platforms[serveState.getValue()];
            float platformSpeedRatio = platform.getVelocityY() / platform.getSpeed();
            float directionY = platformSpeedRatio * Constants.Physics.SERVE_ANGLE_FACTOR;
            float directionX = ball.getDirectionX();
            float vector = (float) Math.sqrt(directionX * directionX + directionY * directionY);
            directionX /= vector;
            directionY /= vector;
            ball.setDirection(directionX, directionY);
            ball.setY(platform.getY() + Constants.World.PLATFORM_HEIGHT / 2f);
        }
        ball.updateParticles(timeStep);
    }

    void processPhysics(Platform[] platforms, Ball ball, GameParameters gameParameters, float timeStep) {
        calcPlatformPhysics(platforms, timeStep);
        calcBallPhysics(ball, gameParameters, timeStep);
        calcPlatformCollision(platforms, ball, gameParameters);
    }

    private void calcPlatformPhysics(Platform[] platforms, float timeStep) {
        for (Platform platform : platforms) {
            float targetVelocity = 0;
            if (platform.getDirection() < 0) {
                targetVelocity = -platform.getSpeed();
            } else if (platform.getDirection() > 0) {
                targetVelocity = platform.getSpeed();
            }

            // interpolate platform movement
            float interpolatedVelocity = MathUtils.lerp(
                platform.getVelocityY(),
                targetVelocity,
                Constants.Physics.INTERPOLATION_COEFFICIENT
            );

            platform.setVelocityY(interpolatedVelocity);
            platform.setY(platform.getY() + interpolatedVelocity * timeStep);

            if (platform.getY() < Constants.Border.BOTTOM_PLATFORM_BOUNDARY) {
                platform.setY(Constants.Border.BOTTOM_PLATFORM_BOUNDARY);
            }
            if (platform.getY() > Constants.Border.TOP_PLATFORM_BOUNDARY) {
                platform.setY(Constants.Border.TOP_PLATFORM_BOUNDARY);
            }
        }
    }

    private void calcBallPhysics(Ball ball, GameParameters gameParameters, float timeStep) {
        float ballX = ball.getX();
        float ballY = ball.getY();
        float directionX = ball.getDirectionX();
        float directionY = ball.getDirectionY();

        ball.setPosition(
            ballX + directionX * ball.getSpeed() * timeStep,
            ballY + directionY * ball.getSpeed() * timeStep
        );
        ball.addTrailPoint();

        if (ballY < Constants.Border.BOTTOM_BALL_BOUNDARY) {
            ball.setY(Constants.Border.BOTTOM_BALL_BOUNDARY);
            directionY = -directionY;
            gameParameters.setSoundToPlay(SoundToPlay.WALL_HIT);
        }
        if (ballY > Constants.Border.TOP_BALL_BOUNDARY) {
            ball.setY(Constants.Border.TOP_BALL_BOUNDARY);
            directionY = -directionY;
            gameParameters.setSoundToPlay(SoundToPlay.WALL_HIT);
        }

        if (GameMode.SINGLEPLAYER == gameParameters.getGameMode()) {
            if (ballX > Constants.Border.RIGHT_BALL_BOUNDARY) {
                ball.setX(Constants.Border.RIGHT_BALL_BOUNDARY);
                directionX = -directionX;
                gameParameters.addSingleplayerPoint();
                gameParameters.setSoundToPlay(SoundToPlay.ACTIVE_WALL_HIT);
            } else if (ballX < Constants.Border.LEFT_BALL_BOUNDARY) {
                ball.spawnExplosion();
                ball.setStartPositionAndDirection(ServeSide.PLAYER_TWO);
                gameParameters.setGameState(GameState.GAME_OVER);
                gameParameters.setSoundToPlay(SoundToPlay.BALL_EXPLOSION);
            }
        } else {
            if (ballX < Constants.Border.LEFT_BALL_BOUNDARY || ballX > Constants.Border.RIGHT_BALL_BOUNDARY) {
                ball.spawnExplosion();
                gameParameters.setGameState(GameState.GOAL);
                boolean isPlayerOneScoredGoal = ballX > Constants.Border.RIGHT_BALL_BOUNDARY;
                gameParameters.addMultiplayerPoint(isPlayerOneScoredGoal);
                gameParameters.setServeState(isPlayerOneScoredGoal);
                ball.setStartPositionAndDirection(isPlayerOneScoredGoal ? ServeSide.PLAYER_TWO : ServeSide.PLAYER_ONE);
                boolean isWin = gameParameters.checkWin();
                if (isWin) {
                    gameParameters.setSoundToPlay(SoundToPlay.WIN);
                } else {
                    gameParameters.updateCooldown(Constants.Physics.GOAL_COOLDOWN);
                    gameParameters.setSoundToPlay(SoundToPlay.BALL_EXPLOSION);
                }
            }
        }
        if (GameState.PLAYING == gameParameters.getGameState()) {
            ball.setDirection(directionX, directionY);
        }
        ball.updateParticles(timeStep);
        ball.updateHitCooldown(timeStep);
    }

    private void calcPlatformCollision(Platform[] platforms, Ball ball, GameParameters gameParameters) {
        for (Platform platform : platforms) {
            if (!calculationNeeded(platform, ball)) {
                continue;
            }
            boolean isPlayerOne = platform.isPlayerOne();
            float ballX = ball.getX();
            float ballY = ball.getY();
            float radius = ball.getRadius();
            float closestX = MathUtils.clamp(ballX, platform.getX(), platform.getX() + platform.getWidth());
            float closestY = MathUtils.clamp(ballY, platform.getY(), platform.getY() + platform.getHeight());
            float distX = ballX - closestX;
            float distY = ballY - closestY;
            boolean isSideHit = Math.abs(distX) > Math.abs(distY);
            float newDirectionX;
            float newDirectionY;


            if (isSideHit) {
                // correct ball position to prevent sticking
                if (isPlayerOne) {
                    ball.setX(platform.getX() + platform.getWidth() + radius);
                } else {
                    ball.setX(platform.getX() - radius);
                }

                float ballCenter = ball.getY();
                float platformCenter = platform.getY() + platform.getHeight() / 2f;
                float hitOffset = (ballCenter - platformCenter) / (platform.getHeight() / 2f);
                float spin = platform.getVelocityY() / platform.getSpeed();
                // calc offset and adjust it with spin (platform moving)
                hitOffset = MathUtils.clamp(hitOffset + spin, -1f, 1f);

                newDirectionY = hitOffset;
                // calc horizontal hit depending on spin (platform moving)
                newDirectionX = 1f - Math.abs(spin) * Constants.Physics.SPIN_FACTOR;
                // normalize
                float vectorLength = (float) Math.sqrt(newDirectionX * newDirectionX + newDirectionY * newDirectionY);
                newDirectionX /= vectorLength;
                newDirectionY /= vectorLength;

                if (!isPlayerOne) {
                    newDirectionX = -newDirectionX;
                }
            } else {
                float distanceSquared = distX * distX + distY * distY;
                if (distanceSquared == 0) {
                    // Degenerate case: ball center exactly matches closest point on platform.
                    // In this situation collision normal cannot be determined (zero-length vector).
                    // Use inverse ball direction as a fallback normal to keep physics stable.
                    float dirX = ball.getDirectionX();
                    float dirY = ball.getDirectionY();
                    distX = -dirX;
                    distY = -dirY;
                    distanceSquared = distX * distX + distY * distY;
                }
                float distance = (float) Math.sqrt(distanceSquared);
                float normalX = distX / distance;
                float normalY = distY / distance;
                float penetration = radius - distance;
                // reduce penetration value
                penetration = Math.min(penetration, radius * 0.4f);

                // return ball to platform surface by normal direction
                ball.setPosition(ballX + normalX * penetration, ballY + normalY * penetration);

                // calc ball reflection by formula: R = D - 2 * (D · N) * N
                // where
                // R - reflected vector,
                // D - current ball direction vector,
                // N - normal is vector equals to 1 and perpendicular to surface, for example, [0, 1] for bottom
                // · (dotProduct) is a scalar (dot) multiplication (product) of direction and normal vectors.
                // Since both vectors are normalized (length = 1),
                // dotProduct is equal to cos(angle) between them.
                // Therefore, explicit cos(angle) calculation is not required.
                float directionX = ball.getDirectionX();
                float directionY = ball.getDirectionY();

                float dotProduct = directionX * normalX + directionY * normalY;
                newDirectionX = directionX - 2f * dotProduct * normalX;
                newDirectionY = directionY - 2f * dotProduct * normalY;
                float vectorLength = (float) Math.sqrt(newDirectionX * newDirectionX + newDirectionY * newDirectionY);
                if (vectorLength != 0) {
                    newDirectionX /= vectorLength;
                    newDirectionY /= vectorLength;
                }
            }
            // to prevent endless top/bottom bounces
            if (Math.abs(newDirectionX) < MIN_X_VALUE) {
                newDirectionX = Math.copySign(MIN_X_VALUE, newDirectionX);
                float length = (float) Math.sqrt(newDirectionX * newDirectionX + newDirectionY * newDirectionY);
                newDirectionX /= length;
                newDirectionY /= length;
            }
            ball.setDirection(newDirectionX, newDirectionY);
            ball.setHitCooldown();
            if (isPlayerOne) {
                gameParameters.setSoundToPlay(SoundToPlay.PLATFORM_HIT_LEFT);
            } else {
                gameParameters.setSoundToPlay(SoundToPlay.PLATFORM_HIT_RIGHT);
            }
            break;
        }
    }

    private boolean calculationNeeded(Platform platform, Ball ball) {
        if (ball.getHitCooldown() > 0) {
            return false;
        }
        if (platform.isPlayerOne()) {
            if (ball.getX() < platform.getX() && ball.getDirectionX() < 0) {
                return false;
            }
        } else {
            if (ball.getX() > platform.getX() + platform.getWidth() && ball.getDirectionX() > 0) {
                return false;
            }
        }
        return ballIntersectsPlatform(platform, ball);
    }

    private boolean ballIntersectsPlatform(Platform platform, Ball ball) {
        float ballX = ball.getX();
        float ballY = ball.getY();
        float radius = ball.getRadius();

        float closestX = MathUtils.clamp(ballX, platform.getX(), platform.getX() + platform.getWidth());
        float closestY = MathUtils.clamp(ballY, platform.getY(), platform.getY() + platform.getHeight());

        float distX = ballX - closestX;
        float distY = ballY - closestY;

        return distX * distX + distY * distY < radius * radius;
    }
}
