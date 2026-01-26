package com.dv.trunov.game.model;

import com.badlogic.gdx.graphics.Texture;

public abstract class WorldObject {

    private final Texture texture;

    public WorldObject(String texturePath) {
        this.texture = new Texture(texturePath);
        texture.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );
    }

    public Texture getTexture() {
        return texture;
    }
}
