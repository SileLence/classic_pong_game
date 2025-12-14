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
            if (!ballIntersectsPlatform(platform, ball)) {
                continue;
            }
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
                if (platform.isPlayerOne()) {
                    ball.setX(platform.getX() - radius);
                } else {
                    ball.setX(platform.getX() + platform.getWidth() + radius);
                }

                float ballCenter = ball.getY();
                float platformCenter = platform.getY() + platform.getHeight() / 2f;
                float hitOffset = (ballCenter - platformCenter) / (platform.getHeight() / 2f);
                float spin = platform.getVelocityY() / Constants.Speed.PLATFORM_SPEED;
                // calc offset and adjust it with spin (platform moving)
                hitOffset = MathUtils.clamp(hitOffset + spin, -1f, 1f);

                newDirectionY = hitOffset;
                // calc horizontal hit depending on spin (platform moving)
                newDirectionX = 1f - Math.abs(spin) * Constants.Physics.SPIN_FACTOR;
                // normalize
                float vectorLength = (float) Math.sqrt(newDirectionX * newDirectionX + newDirectionY * newDirectionY);
                newDirectionX /= vectorLength;
                newDirectionY /= vectorLength;

                if (platform.isPlayerOne()) {
                    newDirectionX = -newDirectionX;
                }
            } else {
                float distanceSquared = distX * distX + distY * distY;
                if (distanceSquared == 0f) {
                    // if ball hit directly to corner
                    distX = platform.isPlayerOne() ? 1f : -1f;
                    distY = 0f;
                    distanceSquared = 1f;
                }
                float distance = (float) Math.sqrt(distanceSquared);
                float normalX = distX / distance;
                float normalY = distY / distance;
                float penetration = radius - distance;

                // correct ball position to prevent sticking
                ball.setPosition(ballX + normalX * penetration, ballY + normalY * penetration);

                // отражение направления по нормали
                float dirX = ball.getDirectionX();
                float dirY = ball.getDirectionY();

                float dot = dirX * normalX + dirY * normalY;

                newDirectionX = dirX - 2f * dot * normalX;
                newDirectionY = dirY - 2f * dot * normalY;

                // нормализация направления
                float vectorLength = (float) Math.sqrt(newDirectionX * newDirectionX + newDirectionY * newDirectionY);
                if (vectorLength != 0f) {
                    newDirectionX /= vectorLength;
                    newDirectionY /= vectorLength;
                }
            }
            ball.setDirection(newDirectionX, newDirectionY);
            break;
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
