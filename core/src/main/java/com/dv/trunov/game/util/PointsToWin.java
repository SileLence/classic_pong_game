package com.dv.trunov.game.util;

public enum PointsToWin {

    THREE("3"),
    FIVE("5"),
    SEVEN("7"),
    TEN("10"),
    FIFTEEN("15"),
    TWENTY_ONE("21");

    final String value;

    PointsToWin(String value) {
        this.value = value;
    }

    public int getIndex() {
        return ordinal();
    }

    public String getValue() {
        return value;
    }

    public static PointsToWin fromIndex(int index) {
        return PointsToWin.values()[index];
    }

    public static int size() {
        return values().length;
    }
}
