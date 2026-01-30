package com.dv.trunov.game.gameparameters.switchable;

import com.dv.trunov.game.ui.text.TextKey;

public enum Language implements Switchable<Language> {

    RUSSIAN(TextKey.RU),
    ENGLISH(TextKey.EN);

    private final TextKey key;

    Language(TextKey key) {
        this.key = key;
    }

    public TextKey getKey() {
        return key;
    }

    public static Language fromIndex(int index) {
        return values()[index];
    }
}
