package com.dv.trunov.game.util;

import com.badlogic.gdx.math.MathUtils;

public enum ServeSide {

    CENTER(0),
    RANDOM_PLAYER(Float.NaN),
    PLAYER_ONE(1),
    PLAYER_TWO(-1);

    final float direction;

    ServeSide(float direction) {
        this.direction = direction;
    }

    public int getIndex() {
        return ordinal();
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

    public static int size() {
        return values().length;
    }
}
