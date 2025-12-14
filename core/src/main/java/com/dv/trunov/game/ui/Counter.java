package com.dv.trunov.game.ui;

import com.dv.trunov.game.util.TextLabel;

public class Counter extends Title {

    private int value;

    public Counter(TextLabel textLabel) {
        super(textLabel);
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
