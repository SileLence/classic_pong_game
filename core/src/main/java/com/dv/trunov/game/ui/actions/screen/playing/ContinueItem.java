package com.dv.trunov.game.ui.actions.screen.playing;

import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.actions.ActionItem;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.ServeState;
import com.dv.trunov.game.util.SoundToPlay;

public class ContinueItem implements ActionItem {

    private final TextKey key = TextKey.CONTINUE;

    @Override
    public void onSelect(GameParameters gameParameters) {
        gameParameters.resetSelectionIndex();
        ServeState serveState = gameParameters.getServeState();
        GameState gameState = serveState == ServeState.NONE ? GameState.PLAYING : GameState.GOAL;
        gameParameters.setGameState(gameState);
        gameParameters.setSoundToPlay(SoundToPlay.MENU_SELECT);
    }

    @Override
    public TextKey getKey() {
        return key;
    }

    @Override
    public boolean isSwitchable() {
        return false;
    }
}
