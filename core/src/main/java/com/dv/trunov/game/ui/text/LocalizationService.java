package com.dv.trunov.game.ui.text;

import com.dv.trunov.game.gameparameters.switchable.Language;

public class LocalizationService {

    private static final LocalizationService INSTANCE = new LocalizationService();
    private Language currentLanguage;

    public static LocalizationService getInstance() {
        return INSTANCE;
    }

    private LocalizationService() {
    }

    public void setLanguage(Language language) {
        this.currentLanguage = language;
    }

    public String getText(TextKey key) {
        return key.getText(currentLanguage);
    }
}
