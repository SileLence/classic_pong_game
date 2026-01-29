package com.dv.trunov.game.util;

import com.dv.trunov.game.ui.TextKey;

public enum BallSpeed {

    VERY_SLOW(TextKey.VERY_SLOW, 1000f),
    SLOW(TextKey.VERY_SLOW, 1200f),
    NORMAL(TextKey.VERY_SLOW, 1400f),
    FAST(TextKey.VERY_SLOW, 1600f),
    VERY_FAST(TextKey.VERY_SLOW, 1800f),
    EXTREME(TextKey.VERY_SLOW, 2000f);

    private final TextKey key;
    private final float value;

    BallSpeed(TextKey key, float value) {
        this.key = key;
        this.value = value;
    }

    public TextKey getKey() {
        return key;
    }

    public int getIndex() {
        return ordinal();
    }

    public float getValue() {
        return value;
    }

    public static BallSpeed fromIndex(int index) {
        return BallSpeed.values()[index];
    }

    public static int size() {
        return values().length;
    }
}