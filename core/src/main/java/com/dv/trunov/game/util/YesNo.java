package com.dv.trunov.game.util;

public enum YesNo {

    NO,
    YES;

    public int getIndex() {
        return ordinal();
    }

    public YesNo fromIndex(int index) {
        return values()[index];
    }
}
