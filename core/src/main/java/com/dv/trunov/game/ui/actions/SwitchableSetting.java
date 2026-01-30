package com.dv.trunov.game.ui.actions;

import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.util.SoundToPlay;

public abstract class SwitchableSetting implements ActionItem {

    protected abstract TextKey key();

    @Override
    public void onLeft(GameParameters gameParameters) {
        gameParameters.getSetting(key()).previous();
        gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
    }

    @Override
    public void onRight(GameParameters gameParameters) {
        gameParameters.getSetting(key()).next();
        gameParameters.setSoundToPlay(SoundToPlay.MENU_MOVE);
    }

    @Override
    public TextKey getKey() {
        return key();
    }

    @Override
    public TextKey getValueKey(GameParameters gameParameters) {
        return gameParameters.getSetting(key()).getValueKey();
    }

    @Override
    public boolean isSwitchable() {
        return true;
    }
}
