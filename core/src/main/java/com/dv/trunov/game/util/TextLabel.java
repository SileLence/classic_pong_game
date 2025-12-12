package com.dv.trunov.game.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public record TextLabel(

    float x,
    float y,
    BitmapFont font,
    String text,
    GlyphLayout layout) {
}
