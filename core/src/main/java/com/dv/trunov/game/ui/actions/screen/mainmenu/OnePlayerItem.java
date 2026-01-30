package com.dv.trunov.game.ui.actions.screen.mainmenu;

import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.actions.ActionItem;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.util.GameMode;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.SoundToPlay;

public class OnePlayerItem implements ActionItem {

    private final TextKey key = TextKey.ONE_PLAYER;

    @Override
    public void onSelect(GameParameters gameParameters) {
        gameParameters.resetSelectionIndex();
        gameParameters.setGameMode(GameMode.SINGLEPLAYER);
        gameParameters.setGameState(GameState.IDLE);
        gameParameters.setStartGame();
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
