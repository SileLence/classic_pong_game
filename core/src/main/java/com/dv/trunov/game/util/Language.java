package com.dv.trunov.game.util;

import com.dv.trunov.game.ui.TextKey;

public enum Language {

    RUSSIAN(TextKey.RU),
    ENGLISH(TextKey.EN);

    private final TextKey key;

    Language(TextKey key) {
        this.key = key;
    }

    public TextKey getKey() {
        return key;
    }

    public int getIndex() {
        return ordinal();
    }

    public static Language fromIndex(int index) {
        return values()[index];
    }

    public int size() {
        return values().length;
    }
}
