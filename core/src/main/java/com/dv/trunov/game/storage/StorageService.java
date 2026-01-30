package com.dv.trunov.game.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.util.Constants;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;

public class StorageService {

    private static final Preferences PREFERENCES = Gdx.app.getPreferences(Constants.Prefs.PREFS_NAME);
    private static final String SALT = "pong_salt1";
    private static final String HASH = "HASH";

    public static void persistAll(GameParameters gameParameters) {
        for (TextKey key : Constants.Prefs.KEY_LIST) {
            int value;
            if (TextKey.BEST.equals(key)) {
                value = gameParameters.getBestLevel();
            } else {
                value = gameParameters.getSetting(key).getIndex();
            }
            persistValue(key, value);
        }
    }

    public static void persistValue(TextKey key, Object value) {
        PREFERENCES.putString(key.name(), String.valueOf(value));
        updateHash();
        PREFERENCES.flush();
    }

    public static int getValue(TextKey key, int defaultValue) {
        String value = getValue(key, String.valueOf(defaultValue));
        return Integer.parseInt(value);
    }

    public static String getValue(TextKey key, String defaultValue) {
        if (isFileChanged()) {
            System.out.printf("Value of [%s] wasn't found or changed manually. Return default value: %s\n",  key, defaultValue);
            return defaultValue;
        }
        String value = PREFERENCES.getString(key.name(), null);
        return value == null ? defaultValue : value;
    }

    private static boolean isFileChanged() {
        String storedHash = PREFERENCES.getString(HASH, "");
        String recalculatedHash = calcHash();
        return !Objects.equals(storedHash, recalculatedHash);
    }

    private static void updateHash() {
        String hash = calcHash();
        PREFERENCES.putString(HASH, hash);
        PREFERENCES.flush();
    }

    private static String calcHash() {
        List<TextKey> keys = Constants.Prefs.KEY_LIST;
        StringBuilder builder = new StringBuilder();
        for (TextKey key : keys) {
            builder.append(PREFERENCES.getString(key.name()));
        }
        return getHash(builder + SALT);
    }

    private static String getHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
