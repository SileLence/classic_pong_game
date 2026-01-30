package com.dv.trunov.game.gameparameters;

import com.dv.trunov.game.ui.text.TextKey;

public interface SettingOption {

    void next();

    void previous();

    int getIndex();

    TextKey getValueKey();
}
