package com.dv.trunov.game.gameparameters.switchable;

import com.dv.trunov.game.ui.text.TextKey;

public enum BallSpeed implements Switchable<BallSpeed> {

    VERY_SLOW(TextKey.VERY_SLOW, 1000f),
    SLOW(TextKey.SLOW, 1200f),
    NORMAL(TextKey.NORMAL, 1400f),
    FAST(TextKey.FAST, 1600f),
    VERY_FAST(TextKey.VERY_FAST, 1800f),
    EXTREME(TextKey.EXTREME, 2000f);

    private final TextKey key;
    private final float value;

    BallSpeed(TextKey key, float value) {
        this.key = key;
        this.value = value;
    }

    public TextKey getKey() {
        return key;
    }

    public float getValue() {
        return value;
    }

    public static BallSpeed fromIndex(int index) {
        return BallSpeed.values()[index];
    }
}