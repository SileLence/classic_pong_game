package com.dv.trunov.game.ui.actions.screen.settings;

import com.dv.trunov.game.ui.actions.SwitchableSetting;
import com.dv.trunov.game.ui.text.TextKey;

public class PointsToWinItem extends SwitchableSetting {

    @Override
    protected TextKey key() {
        return TextKey.POINTS_TO_WIN;
    }
}
