package com.dv.trunov.game.ui.actions;

import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.text.TextKey;

public interface ActionItem {

    default void onLeft(GameParameters gameParameters) {
        // no action
    }

    default void onRight(GameParameters gameParameters) {
        // no action
    }

    default void onSelect(GameParameters gameParameters) {
        // no action
    }

    default TextKey getValueKey(GameParameters gameParameters) {
        return null;
    }

    TextKey getKey();

    boolean isSwitchable();
}
