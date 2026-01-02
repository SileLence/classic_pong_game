package com.dv.trunov.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public record TextLabel(

    String key,
    float x,
    float y,
    BitmapFont font,
    String text,
    GlyphLayout layout,
    Color color,
    boolean isSelectable) {
}
