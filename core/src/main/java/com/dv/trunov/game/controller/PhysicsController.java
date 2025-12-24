package com.dv.trunov.game.controller;

import com.badlogic.gdx.math.MathUtils;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;

public class PhysicsController {

    private static final PhysicsController INSTANCE = new PhysicsController();

    private PhysicsController() {
    }

    public static PhysicsController getInstance() {
        return INSTANCE;
    }

    public void processPhysics(Platform[] platforms, Ball ball, GameParameters gameParameters, float timeStep) {
        if (GameState.GOAL == gameParameters.getGameState()) {
            calcPlatformPhysics(platforms, timeStep);
            ball.updateParticles(timeStep);
            ball.addTrailPoint();
            return;
        }
        calcPlatformPhysics(platforms, timeStep);
        calcBallPhysics(ball, gameParameters, timeStep);
        calcPlatformCollision(platforms, ball);
    }

    private void calcPlatformPhysics(Platform[] platforms, float timeStep) {
        for (Platform platform : platforms) {
            float targetVelocity = 0f;
            if (platform.getDirection() < 0f) {
                targetVelocity = -Constants.Speed.PLATFORM_SPEED;
            } else if (platform.getDirection() > 0f) {
                targetVelocity = Constants.Speed.PLATFORM_SPEED;
            }

            // interpolate platform movement
            float interpolatedVelocity = MathUtils.lerp(
                platform.getVelocityY(),
                targetVelocity,
                Constants.Physics.INTERPOLATION_COEFFICIENT
            );

            platform.setVelocityY(interpolatedVelocity);
            platform.setY(platform.getY() + interpolatedVelocity * timeStep);

            if (platform.getY() < 0f) {
                platform.setY(0f);
            }
            if (platform.getY() > Constants.Border.TOP_PLATFORM_BOUNDARY) {
                platform.setY(Constants.Border.TOP_PLATFORM_BOUNDARY);
            }
        }
    }

    private void calcBallPhysics(Ball ball, GameParameters gameParameters, float timeStep) {
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

        if (GameMode.SINGLEPLAYER == gameParameters.getGameMode()) {
            if (ball.getX() > Constants.Border.RIGHT_BALL_BOUNDARY) {
                ball.setX(Constants.Border.RIGHT_BALL_BOUNDARY);
                directionX = -directionX;
            }
            ball.setDirection(directionX, directionY);

            if (ball.getX() < Constants.Border.LEFT_BALL_BOUNDARY) {
                ball.spawnExplosion();
                gameParameters.setGameState(GameState.GOAL);
            }
        } else {
            ball.setDirection(directionX, directionY);

            if (ball.getX() < Constants.Border.LEFT_BALL_BOUNDARY) {
                processGoal(ball, gameParameters, false);
            }
            if (ball.getX() > Constants.Border.RIGHT_BALL_BOUNDARY) {
                processGoal(ball, gameParameters, true);
            }
        }
        ball.updateHitCooldown(timeStep);
    }

    private void calcPlatformCollision(Platform[] platforms, Ball ball) {
        for (Platform platform : platforms) {
            if (!calculationNeeded(platform, ball)) {
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
                    ball.setX(platform.getX() + platform.getWidth() + radius);
                } else {
                    ball.setX(platform.getX() - radius);
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

                if (!platform.isPlayerOne()) {
                    newDirectionX = -newDirectionX;
                }
            } else {
                float distanceSquared = distX * distX + distY * distY;
                if (distanceSquared == 0f) {
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
                if (vectorLength != 0f) {
                    newDirectionX /= vectorLength;
                    newDirectionY /= vectorLength;
                }
            }
            ball.setDirection(newDirectionX, newDirectionY);
            ball.setHitCooldown();
            break;
        }
    }

    private boolean calculationNeeded(Platform platform, Ball ball) {
        if (ball.getHitCooldown() > 0f) {
            return false;
        }
        if (platform.isPlayerOne()) {
            if (ball.getX() < platform.getX() && ball.getDirectionX() < 0f) {
                return false;
            }
        } else {
            if (ball.getX() > platform.getX() + platform.getWidth() && ball.getDirectionX() > 0f) {
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

    private void processGoal(Ball ball, GameParameters gameParameters, boolean isPlayerOneScoredGoal) {
        ball.spawnExplosion();
        gameParameters.setGameState(GameState.GOAL);
        if (isPlayerOneScoredGoal) {
            gameParameters.addScoreOne();
        } else {
            gameParameters.addScoreTwo();
        }
    }
}
