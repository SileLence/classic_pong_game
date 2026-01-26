package com.dv.trunov.game.util;

public enum BallSpeed {

    VERY_SLOW(800f),
    SLOW(1000f),
    NORMAL(1200f),
    FAST(1400f),
    VERY_FAST(1600f),
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