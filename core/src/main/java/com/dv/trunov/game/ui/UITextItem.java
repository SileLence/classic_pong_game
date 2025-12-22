package com.dv.trunov.game.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dv.trunov.game.util.TextLabel;

public abstract class UITextItem {

    private final int id;
    private final TextLabel textLabel;

    public UITextItem(int id, TextLabel textLabel) {
        this.id = id;
        this.textLabel = textLabel;
    }

    public int getId() {
        return id;
    }

    public TextLabel getTextLabel() {
        return textLabel;
    }

    public  float getX() {
        return textLabel.x();
    }
    public float getY() {
        return textLabel.y();
    }

    public BitmapFont getFont() {
        return textLabel.font();
    }

    public String getText() {
        return textLabel.text();
    }
}
