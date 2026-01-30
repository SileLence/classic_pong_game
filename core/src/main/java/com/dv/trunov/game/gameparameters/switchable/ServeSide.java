package com.dv.trunov.game.gameparameters.switchable;

import com.badlogic.gdx.math.MathUtils;
import com.dv.trunov.game.ui.text.TextKey;

public enum ServeSide implements Switchable<ServeSide> {

    CENTER(TextKey.CENTER_SERVE, 0),
    RANDOM_PLAYER(TextKey.RANDOM_PLAYER_SERVE, Float.NaN),
    PLAYER_ONE(TextKey.PLAYER_ONE_SERVE, 1),
    PLAYER_TWO(TextKey.PLAYER_TWO_SERVE, -1);

    private final TextKey key;
    private final float direction;

    ServeSide(TextKey key, float direction) {
        this.key = key;
        this.direction = direction;
    }

    public TextKey getKey() {
        return key;
    }

    public static ServeSide fromIndex(int index) {
        return values()[index];
    }

    public float getDirectionX() {
        if (ServeSide.RANDOM_PLAYER == this) {
            return MathUtils.randomBoolean() ? 1 : -1;
        } else {
            return direction;
        }
    }
}
