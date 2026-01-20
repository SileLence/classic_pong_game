package com.dv.trunov.game.util;

public enum Toggle {

    OFF,
    ON;

    public int getIndex() {
        return ordinal();
    }

    public static Toggle fromIndex(int index) {
        return values()[index];
    }

    public static int size() {
        return values().length;
    }
}
