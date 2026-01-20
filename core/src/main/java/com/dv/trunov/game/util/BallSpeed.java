package com.dv.trunov.game.util;

public enum BallSpeed {

    SLOW(800f),
    NORMAL(1000f),
    FAST(1200f),
    VERY_FAST(1400f),
    EXTREME(1600f);

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