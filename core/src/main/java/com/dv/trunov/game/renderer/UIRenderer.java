package com.dv.trunov.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dv.trunov.game.ui.TextLabel;
import com.dv.trunov.game.util.Constants;

public class UIRenderer {

    private static final UIRenderer INSTANCE = new UIRenderer();

    private UIRenderer() {
    }

    public static UIRenderer getInstance() {
        return INSTANCE;
    }

    public void drawUI(TextLabel[] textLabels, int selectedItemIndex, float alpha, SpriteBatch spriteBatch) {
        for (int index = 0; index < textLabels.length; index++) {
            TextLabel textLabel = textLabels[index];
            BitmapFont font = textLabel.font();
            Color originColor = new Color(font.getColor());
            String key = textLabel.key();
            if (textLabel.isSelectable() && index == selectedItemIndex) {
                Color blinkingColor = new Color().set(Constants.Colors.SELECTION_FONT_COLOR, alpha);
                font.getColor().set(blinkingColor);
            } else if (Constants.ItemKey.PLAYER_ONE_WINS_KEY.equals(key)) {
                Color playerOneColor = Constants.Colors.PLAYER_ONE_COLOR;
                font.getColor().set(playerOneColor);
            } else if (Constants.ItemKey.PLAYER_TWO_WINS_KEY.equals(key)) {
                Color playerTwoColor = Constants.Colors.PLAYER_TWO_COLOR;
                font.getColor().set(playerTwoColor);
            }
            font.draw(spriteBatch, textLabel.text(), textLabel.x(), textLabel.y());
            font.getColor().set(originColor);
        }
    }
}
