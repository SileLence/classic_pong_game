package com.dv.trunov.game.ui.actions.screen.mainmenu;

import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.actions.ActionItem;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.SoundToPlay;

public class SettingsItem implements ActionItem {

    private final TextKey key = TextKey.SETTINGS;

    @Override
    public void onSelect(GameParameters gameParameters) {
        gameParameters.resetSelectionIndex();
        gameParameters.setGameState(GameState.SETTINGS);
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
