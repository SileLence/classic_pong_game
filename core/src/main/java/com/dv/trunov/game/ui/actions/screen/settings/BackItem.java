package com.dv.trunov.game.ui.actions.screen.settings;

import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.actions.ActionItem;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.SoundToPlay;

public class BackItem implements ActionItem {

    private final TextKey key = TextKey.BACK;

    @Override
    public void onSelect(GameParameters gameParameters) {
        gameParameters.resetSelectionIndex();
        gameParameters.setGameState(GameState.MENU);
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
