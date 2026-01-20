package com.dv.trunov.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dv.trunov.game.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Ball extends WorldObject {

    private static final float MIN_X_VALUE = 0.5f;
    private static final float HIT_COOLDOWN = 0.03f;
    private final Circle circle;
    private final List<Vector2> trailPoints = new ArrayList<>(Constants.Object.BALL_TRAIL_NUMBER);
    private final List<BallParticle> particles = new ArrayList<>(Constants.Object.BALL_PARTICLES_NUMBER);
    private float directionX;
    private float directionY;
    private float hitCooldown;
    private float speed;

    public Ball(String texturePath) {
        super(texturePath);
        circle = new Circle();
        circle.radius = Constants.Object.BALL_RADIUS;
        speed = Constants.Physics.BALL_SPEED;
        setStartPositionAndDirection(0f);
    }

    public void setStartPositionAndDirection(float serveX) {
        trailPoints.clear();
        float x;
        if (serveX == 0) {
            directionX = MathUtils.random(-1f, 1f);
            if (Math.abs(directionX) < MIN_X_VALUE) {
                directionX = Math.copySign(MIN_X_VALUE, directionX);
            }
            x = Constants.Object.BALL_START_X;
        } else {
            x = serveX > 0 ? Constants.Object.BALL_PLAYER_ONE_SERVE : Constants.Object.BALL_PLAYER_TWO_SERVE;
            directionX = serveX;
        }
        circle.setPosition(x, Constants.Object.BALL_START_Y);
        directionY = MathUtils.random(-1f, 1f);
        // normalize vector
        float vectorLength = (float) Math.sqrt(directionX * directionX + directionY * directionY);
        directionX /= vectorLength;
        directionY /= vectorLength;
    }

    public void addTrailPoint() {
        trailPoints.add(new Vector2(circle.x, circle.y));
        if (trailPoints.size() == Constants.Object.BALL_TRAIL_NUMBER) {
            trailPoints.remove(0);
        }
    }

    public void spawnExplosion() {
        for (int index = 0; index < Constants.Object.BALL_PARTICLES_NUMBER; index++) {
            float angle = MathUtils.random(0f, MathUtils.PI2);
            float speed = MathUtils.random(100f, 500f);
            particles.add(new BallParticle(
                circle.x,
                circle.y,
                (float) (Math.cos(angle) * speed),
                (float) (Math.sin(angle) * speed),
                MathUtils.random(0.3f, 1.2f),
                6f,
                0)
            );
        }
    }

    public void updateParticles(float timeStep) {
        for (int index = particles.size() - 1; index >= 0; index--) {
            BallParticle particle = particles.get(index);
            particle.update(timeStep);
            if (particle.isFinished()) {
                particles.remove(index);
            }
        }
    }

    public void updateHitCooldown(float timeStep) {
        if (hitCooldown > 0) {
            hitCooldown -= timeStep;
        }
        if (hitCooldown < 0) {
            hitCooldown = 0;
        }
    }

    public float getX() {
        return circle.x;
    }

    public void setX(float x) {
        this.circle.x = x;
    }

    public float getY() {
        return circle.y;
    }

    public void setY(float y) {
        this.circle.y = y;
    }

    public void setPosition(float x, float y) {
        this.circle.x = x;
        this.circle.y = y;
    }

    public void setDirection(float directionX, float directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public float getRadius() {
        return circle.radius;
    }

    public float getDirectionX() {
        return directionX;
    }

    public float getDirectionY() {
        return directionY;
    }

    public List<Vector2> getTrailPoints() {
        return trailPoints;
    }

    public List<BallParticle> getParticles() {
        return particles;
    }

    public float getHitCooldown() {
        return hitCooldown;
    }

    public void setHitCooldown() {
        hitCooldown = HIT_COOLDOWN;
    }

    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
