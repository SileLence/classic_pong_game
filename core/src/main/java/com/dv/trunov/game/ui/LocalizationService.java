package com.dv.trunov.game.ui;

import com.dv.trunov.game.util.Language;

import java.util.HashMap;
import java.util.Map;

public class LocalizationService {

    private static Language currentLanguage;
    private static final Map<String, String> strings = new HashMap<>();

    private LocalizationService() {
    }

    public static void setLanguage(Language language) {
        // TODO implement changing localization on-flight
        LocalizationService.currentLanguage = language;
        load();
    }

    public static String get(String key) {
        return strings.get(key);
    }

    private static void load() {
        strings.clear();
        switch (currentLanguage) {
            case ENGLISH: {
//                strings.put(Constants.ItemKey.ONE_PLAYER_KEY, Constants.Text.English.ONE_PLAYER);
//                strings.put(Constants.ItemKey.TWO_PLAYERS_KEY, Constants.Text.English.TWO_PLAYERS);
//                strings.put(Constants.ItemKey.SETTINGS_KEY, Constants.Text.English.SETTINGS);
//                strings.put(Constants.ItemKey.EXIT_KEY, Constants.Text.English.EXIT);
//                strings.put(Constants.ItemKey.PRESS_ENTER_KEY, Constants.Text.English.PRESS_ENTER);
//                strings.put(Constants.ItemKey.CONTINUE_KEY, Constants.Text.English.CONTINUE);
//                strings.put(Constants.ItemKey.PLAY_AGAIN_KEY, Constants.Text.English.PLAY_AGAIN);
//                strings.put(Constants.ItemKey.EXIT_TO_MENU_KEY, Constants.Text.English.EXIT_TO_MENU);
//                strings.put(Constants.ItemKey.TAB_TO_SERVE_KEY, Constants.Text.English.TAB_TO_SERVE);
//                strings.put(Constants.ItemKey.ENTER_TO_SERVE_KEY, Constants.Text.English.ENTER_TO_SERVE);
//                strings.put(Constants.ItemKey.PLAYER_ONE_WINS_KEY, Constants.Text.English.PLAYER_ONE_WINS);
//                strings.put(Constants.ItemKey.PLAYER_TWO_WINS_KEY, Constants.Text.English.PLAYER_TWO_WINS);
            }
            case RUSSIAN: {
//                strings.put(Constants.ItemKey.ONE_PLAYER_KEY, Constants.Text.Russian.ONE_PLAYER);
//                strings.put(Constants.ItemKey.TWO_PLAYERS_KEY, Constants.Text.Russian.TWO_PLAYERS);
//                strings.put(Constants.ItemKey.SETTINGS_KEY, Constants.Text.Russian.SETTINGS);
//                strings.put(Constants.ItemKey.EXIT_KEY, Constants.Text.Russian.EXIT);
//                strings.put(Constants.ItemKey.PRESS_ENTER_KEY, Constants.Text.Russian.PRESS_ENTER);
//                strings.put(Constants.ItemKey.CONTINUE_KEY, Constants.Text.Russian.CONTINUE);
//                strings.put(Constants.ItemKey.PLAY_AGAIN_KEY, Constants.Text.Russian.PLAY_AGAIN);
//                strings.put(Constants.ItemKey.EXIT_TO_MENU_KEY, Constants.Text.Russian.EXIT_TO_MENU);
//                strings.put(Constants.ItemKey.TAB_TO_SERVE_KEY, Constants.Text.Russian.TAB_TO_SERVE);
//                strings.put(Constants.ItemKey.ENTER_TO_SERVE_KEY, Constants.Text.Russian.ENTER_TO_SERVE);
//                strings.put(Constants.ItemKey.PLAYER_ONE_WINS_KEY, Constants.Text.Russian.PLAYER_ONE_WINS);
//                strings.put(Constants.ItemKey.PLAYER_TWO_WINS_KEY, Constants.Text.Russian.PLAYER_TWO_WINS);
            }
        }
    }
}
