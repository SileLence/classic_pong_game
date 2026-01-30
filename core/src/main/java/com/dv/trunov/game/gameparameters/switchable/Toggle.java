package com.dv.trunov.game.gameparameters.switchable;

import com.dv.trunov.game.ui.text.TextKey;

public enum Toggle implements Switchable<Toggle> {

    ON(TextKey.ON),
    OFF(TextKey.OFF);

    private final TextKey key;

    Toggle(TextKey key) {
        this.key = key;
    }

    public TextKey getKey() {
        return key;
    }

    public static Toggle fromIndex(int index) {
        return values()[index];
    }
}
