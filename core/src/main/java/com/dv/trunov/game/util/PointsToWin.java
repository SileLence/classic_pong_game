package com.dv.trunov.game.util;

import com.dv.trunov.game.ui.TextKey;

public enum PointsToWin {

    THREE(TextKey.THREE, "3"),
    FIVE(TextKey.FIVE,  "5"),
    SEVEN(TextKey.SEVEN, "7"),
    TEN(TextKey.TEN, "10"),
    FIFTEEN(TextKey.FIFTEEN, "15"),
    TWENTY_ONE(TextKey.TWENTY_ONE, "21");

    private final TextKey key;
    private final String value;

    PointsToWin(TextKey key, String value) {
        this.key = key;
        this.value = value;
    }

    public TextKey getKey() {
        return key;
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
