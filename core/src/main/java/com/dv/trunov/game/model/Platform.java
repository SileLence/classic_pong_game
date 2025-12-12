package com.dv.trunov.game.model;

import com.badlogic.gdx.math.Rectangle;
import com.dv.trunov.game.util.Constants;

public class Platform extends WorldObject {

    private final Rectangle bounds;
    private float velocityY;
    private float direction;
    private final boolean playerOne;

    public Platform(float x, float y, boolean playerOne, String texturePath) {
        super(texturePath);
        this.bounds = new Rectangle(x, y, Constants.Object.PLATFORM_WIDTH, Constants.Object.PLATFORM_HEIGHT);
        this.playerOne = playerOne;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getX() {
        return bounds.x;
    }

    public void setX(float x) {
        this.bounds.x = x;
    }

    public float getY() {
        return bounds.y;
    }

    public void setY(float y) {
        this.bounds.y = y;
    }

    public float getWidth() {
        return bounds.width;
    }

    public float getHeight() {
        return bounds.height;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public boolean isPlayerOne() {
        return playerOne;
    }
}
