package com.dv.trunov.game.ui.actions;

import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.ServeState;
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
        GameState gameState = gameParameters.getGameState();
        if (GameState.SETTINGS.equals(gameState)) {
            gameParameters.setGameState(GameState.MENU);
            gameParameters.setSoundToPlay(SoundToPlay.MENU_SELECT);
        } else if (GameState.PAUSE.equals(gameState)) {
            ServeState serveState = gameParameters.getServeState();
            GameState nextGameState = serveState == ServeState.NONE ? GameState.PLAYING : GameState.GOAL;
            gameParameters.setGameState(nextGameState);
            gameParameters.setSoundToPlay(SoundToPlay.MENU_SELECT);
        }
    }

    default TextKey getValueKey(GameParameters gameParameters) {
        return null;
    }

    TextKey getKey();

    boolean isSwitchable();
}
