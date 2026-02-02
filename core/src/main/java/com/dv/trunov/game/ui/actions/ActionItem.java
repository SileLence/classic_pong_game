package com.dv.trunov.game.ui.actions;

import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.SoundToPlay;

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

    default void onReturn(GameParameters gameParameters) {
        switch (gameParameters.getGameState()) {
            case SETTINGS -> {
                gameParameters.setGameState(GameState.MENU);
                gameParameters.setSoundToPlay(SoundToPlay.MENU_SELECT);
            }
            case PAUSE -> {
                gameParameters.resumeGame();
                gameParameters.setSoundToPlay(SoundToPlay.MENU_SELECT);
            }
        }
    }

    default TextKey getSelectionKey(GameParameters gameParameters) {
        return getKey();
    }

    TextKey getKey();

    boolean isSwitchable();
}
