package com.dv.trunov.game.model;

import com.badlogic.gdx.graphics.Texture;

public class WorldObject {

    private final Texture texture;

    public WorldObject(String texturePath) {
        this.texture = new Texture(texturePath);
    }

    public Texture getTexture() {
        return texture;
    }
}
