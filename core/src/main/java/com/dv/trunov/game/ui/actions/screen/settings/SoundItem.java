package com.dv.trunov.game.ui.actions.screen.settings;

import com.dv.trunov.game.ui.actions.SwitchableSetting;
import com.dv.trunov.game.ui.text.TextKey;

public class SoundItem extends SwitchableSetting {

    @Override
    protected TextKey key() {
        return TextKey.SOUND;
    }
}
