package com.dv.trunov.game.util;

public enum ServeState {

    NONE(-1),
    PLAYER_ONE(0),
    PLAYER_TWO(1);

    private final int value;

    ServeState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
