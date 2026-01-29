package com.dv.trunov.game.util;

import com.dv.trunov.game.ui.TextKey;

public enum Toggle {

    OFF(TextKey.OFF),
    ON(TextKey.ON);

    private final TextKey key;

    Toggle(TextKey key) {
        this.key = key;
    }

    public TextKey getKey() {
        return key;
    }

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
