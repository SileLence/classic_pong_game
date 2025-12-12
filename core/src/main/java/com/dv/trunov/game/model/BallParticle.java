package com.dv.trunov.game.model;

import com.badlogic.gdx.math.Vector2;

public class BallParticle {

    private final Vector2 position;
    private final Vector2 velocity;
    private final float lifeTime;
    private final float startSize;
    private final float endSize;
    private float age;

    public BallParticle(float x,
                        float y,
                        float velocityX,
                        float velocityY,
                        float lifeTime,
                        float startSize,
                        float endSize) {
        position = new Vector2(x, y);
        velocity = new Vector2(velocityX, velocityY);
        this.lifeTime = lifeTime;
        this.startSize = startSize;
        this.endSize = endSize;
        this.age = 0f;
    }

    public void update(float deltaTime) {
        age += deltaTime;
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
        // gravity
        velocity.y -= 300f * deltaTime;
    }

    public boolean isFinished() {
        return age >= lifeTime;
    }

    public float getNormalizedAge() {
        return Math.min(1f, age / lifeTime);
    }

    public float getSize() {
        return startSize + (endSize - startSize) * getNormalizedAge();
    }

    public Vector2 getPosition() {
        return position;
    }
}
