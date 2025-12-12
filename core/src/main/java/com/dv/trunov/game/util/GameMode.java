package com.dv.trunov.game.util;

public enum GameMode {

    SINGLEPLAYER(1),
    MULTIPLAYER(2);

    final int value;

    GameMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
