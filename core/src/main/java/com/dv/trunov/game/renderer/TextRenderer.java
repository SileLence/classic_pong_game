package com.dv.trunov.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dv.trunov.game.ui.UITextItem;
import com.dv.trunov.game.util.Constants;

public class TextRenderer {

    private static final TextRenderer INSTANCE = new TextRenderer();

    private TextRenderer() {
    }

    public static TextRenderer getInstance() {
        return INSTANCE;
    }

    public void drawUI(UITextItem[] textItems, int activeTextItem, float alpha, SpriteBatch spriteBatch) {
        for (UITextItem textItem : textItems) {
            BitmapFont font = textItem.getFont();
            Color staticColor = new Color(font.getColor());
            if (textItem.getId() == activeTextItem) {
                Color blinkingColor = new Color().set(Constants.Colors.SELECTION_FONT_COLOR, alpha);
                font.getColor().set(blinkingColor);
            }
            font.draw(spriteBatch, textItem.getText(), textItem.getX(), textItem.getY());
            font.getColor().set(staticColor);
        }
    }
}
