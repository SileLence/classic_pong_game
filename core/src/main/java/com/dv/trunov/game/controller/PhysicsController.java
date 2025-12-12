package com.dv.trunov.game.controller;

import com.badlogic.gdx.math.MathUtils;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;

public class PhysicsController {

    private static final PhysicsController INSTANCE = new PhysicsController();

    private PhysicsController() {
    }

    public static PhysicsController getInstance() {
        return INSTANCE;
    }

    public void update(Platform[] platforms, Ball ball, GameMode gameMode, float timeStep) {
        calcPlatformPhysics(platforms, timeStep);
        calcBallPhysics(ball, gameMode, timeStep);
        calcPlatformCollision(platforms, ball);
    }

    private void calcPlatformPhysics(Platform[] platforms, float timeStep) {
        for (Platform platform : platforms) {
            float targetVelocity = 0f;
            if (platform.getDirection() < 0) {
                targetVelocity = -Constants.Speed.PLATFORM_SPEED;
            } else if (platform.getDirection() > 0) {
                targetVelocity = Constants.Speed.PLATFORM_SPEED;
            }

            // interpolate platform movement
            float interpolatedVelocity = MathUtils.lerp(platform.getVelocityY(), targetVelocity, Constants.Physics.INTERPOLATION_COEFFICIENT);

            platform.setVelocityY(interpolatedVelocity);
            platform.setY(platform.getY() + interpolatedVelocity * timeStep);

            if (platform.getY() < 0) {
                platform.setY(0);
            }
            if (platform.getY() > Constants.Border.TOP_PLATFORM_BOUNDARY) {
                platform.setY(Constants.Border.TOP_PLATFORM_BOUNDARY);
            }
        }
    }

    private void calcBallPhysics(Ball ball, GameMode gameMode, float timeStep) {
        float directionX = ball.getDirectionX();
        float directionY = ball.getDirectionY();

        ball.setPosition(
            ball.getX() + directionX * Constants.Speed.BALL_SPEED * timeStep,
            ball.getY() + directionY * Constants.Speed.BALL_SPEED * timeStep
        );
        ball.addTrailPoint();

        if (ball.getY() < Constants.Border.BOTTOM_BALL_BOUNDARY) {
            ball.setY(Constants.Border.BOTTOM_BALL_BOUNDARY);
            directionY = -directionY;
        }
        if (ball.getY() > Constants.Border.TOP_BALL_BOUNDARY) {
            ball.setY(Constants.Border.TOP_BALL_BOUNDARY);
            directionY = -directionY;
        }

        if (gameMode == GameMode.SINGLEPLAYER) {
            if (ball.getX() < Constants.Border.LEFT_BALL_BOUNDARY) {
                ball.setX(Constants.Border.LEFT_BALL_BOUNDARY);
                directionX = -directionX;
            }
            ball.setDirection(directionX, directionY);

            if (ball.getX() > Constants.Border.RIGHT_BALL_BOUNDARY) {
                ball.spawnExplosion();
                ball.setStartPosition();
            }
        } else {
            ball.setDirection(directionX, directionY);

            if (ball.getX() < Constants.Border.LEFT_BALL_BOUNDARY || ball.getX() > Constants.Border.RIGHT_BALL_BOUNDARY) {
                ball.spawnExplosion();
                ball.setStartPosition();
            }
        }
        // update particles position if case of explosion
        ball.updateParticles(timeStep);
    }

    private void calcPlatformCollision(Platform[] platforms, Ball ball) {
        for (Platform platform : platforms) {
            if (ballIntersectsPlatform(platform, ball)) {

                // correct ball position to prevent sticking
                if (platform.isPlayerOne()) {
                    ball.setX(platform.getX() - ball.getRadius());
                } else {
                    ball.setX(platform.getX() + platform.getWidth() + ball.getRadius());
                }

                float ballCenter = ball.getY();
                float platformCenter = platform.getY() + platform.getHeight() / 2f;
                float hitOffset = (ballCenter - platformCenter) / (platform.getHeight() / 2f);
                float spin = platform.getVelocityY() / Constants.Speed.PLATFORM_SPEED;
                // calc offset and adjust it with spin (platform moving)
                hitOffset = MathUtils.clamp(hitOffset + spin, -1f, 1f);

                float newDirectionY = hitOffset;
                // calc horizontal hit depending on spin (platform moving)
                float newDirectionX = 1f - Math.abs(spin) * Constants.Physics.SPIN_FACTOR;
                // normalize
                float vectorLength = (float) Math.sqrt(newDirectionX * newDirectionX + newDirectionY * newDirectionY);
                newDirectionX /= vectorLength;
                newDirectionY /= vectorLength;

                if (platform.isPlayerOne()) {
                    newDirectionX = -newDirectionX;
                }

                ball.setDirection(newDirectionX, newDirectionY);
                break;
            }
        }
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
