package com.dv.trunov.game.util;

public enum BallSpeed {

    SLOW,
    NORMAL,
    FAST,
    VERY_FAST;

    public int getIndex() {
        return ordinal();
    }

    public static BallSpeed fromIndex(int index) {
        return BallSpeed.values()[index];
    }

    public int getSize() {
        return values().length;
    }
}