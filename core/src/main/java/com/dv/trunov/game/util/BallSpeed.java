package com.dv.trunov.game.util;

public enum BallSpeed {

    VERY_SLOW(1000f),
    SLOW(1200f),
    NORMAL(1400f),
    FAST(1600f),
    VERY_FAST(1800f),
    EXTREME(2000f);

    final float value;

    BallSpeed(float value) {
        this.value = value;
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