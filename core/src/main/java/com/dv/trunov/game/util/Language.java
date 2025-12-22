package com.dv.trunov.game.util;

public enum Language {

    RUSSIAN(Constants.ItemKey.RU_KEY),
    ENGLISH(Constants.ItemKey.EN_KEY);

    private final String key;

    Language(String key) {
        this.key = key;
    }

    public static Language getLanguageByKey(String key) {
        return key.equals(RUSSIAN.key) ? RUSSIAN : ENGLISH;
    }
}
