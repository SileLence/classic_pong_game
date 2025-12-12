package com.dv.trunov.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dv.trunov.game.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Ball extends WorldObject {

    private static final float MIN_X_VALUE = 0.3f;
    private final Circle circle;
    private final List<Vector2> trailPoints = new ArrayList<>(Constants.Object.BALL_TRAIL_NUMBER);
    private final List<BallParticle> particles = new ArrayList<>(Constants.Object.BALL_PARTICLES_NUMBER);
    private float directionX;
    private float directionY;
    public float vectorLength;

    public Ball(String texturePath) {
        super(texturePath);
        circle = new Circle();
        circle.radius = Constants.Object.BALL_RADIUS;
        setStartPosition();
    }

    public void setStartPosition() {
        circle.setPosition(Constants.Object.BALL_START_X, Constants.Object.BALL_START_Y);
        directionX = MathUtils.random(-1f, 1f);
        if (Math.abs(directionX) < MIN_X_VALUE) {
            directionX = Math.copySign(MIN_X_VALUE, directionX);
        }
        directionY = MathUtils.random(-1f, 1f);
        // normalize vector
        vectorLength = (float) Math.sqrt(directionX * directionX + directionY * directionY);
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
            float speed = MathUtils.random(200f, 600f);
            particles.add(new BallParticle(
                circle.x,
                circle.y,
                (float) (Math.cos(angle) * speed),
                (float) (Math.sin(angle) * speed),
                MathUtils.random(0.3f, 1f),
                6f,
                0)
            );
        }
    }

    public void updateParticles(float deltaTime) {
        for (int index = particles.size() - 1; index >= 0; index--) {
            BallParticle particle = particles.get(index);
            particle.update(deltaTime);
            if (particle.isFinished()) {
                particles.remove(index);
            }
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
}
