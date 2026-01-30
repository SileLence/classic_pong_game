package com.dv.trunov.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.ui.text.TextLabel;
import com.dv.trunov.game.util.Constants;

public class UIRenderer {

    private static final UIRenderer INSTANCE = new UIRenderer();

    private UIRenderer() {
    }

    public static UIRenderer getInstance() {
        return INSTANCE;
    }

    public void drawUI(TextLabel[] textLabels, GameParameters gameParameters, float alpha, SpriteBatch spriteBatch) {
        TextKey selectedKey = gameParameters.getSelectedKey();
        float cooldown = gameParameters.getCooldown();
        for (TextLabel textLabel : textLabels) {
            BitmapFont font = textLabel.font();
            Color originColor = new Color(font.getColor());
            if (textLabel.isSelectable() && textLabel.key().equals(selectedKey)) {
                Color blinkingColor = new Color().set(Constants.Colors.SELECTION_FONT_COLOR, alpha);
                font.getColor().set(blinkingColor);
            } else if (TextKey.LEVEL.equals(textLabel.key())) {
                if (cooldown > 0) {
                    font.getColor().set(Constants.Colors.LEVEL_UP_COLOR);
                } else {
                    font.getColor().set(textLabel.color());
                }
            } else {
                font.getColor().set(textLabel.color());
            }
            font.draw(spriteBatch, textLabel.text(), textLabel.x(), textLabel.y());
            font.getColor().set(originColor);
        }
    }
}
