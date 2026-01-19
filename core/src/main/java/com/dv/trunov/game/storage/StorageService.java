package com.dv.trunov.game.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.dv.trunov.game.util.Constants;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class StorageService {

    private static final Preferences PREFERENCES = Gdx.app.getPreferences(Constants.Prefs.PREFS_NAME);
    private static final String SALT = "pong_salt1";

    public static void storeValue(String key, Object value) {
        if (value instanceof Integer) {
            PREFERENCES.putInteger(key, (Integer) value);
        } else if (value instanceof String) {
            PREFERENCES.putString(key, (String) value);
        }
        updateHash();
        PREFERENCES.flush();
    }

    public static int getValue(String key, int defaultValue) {
        String value = getValue(key, String.valueOf(defaultValue));
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public static String getValue(String key, String defaultValue) {
        String storedHash = PREFERENCES.getString(Constants.Prefs.HASH, "");
        String freshHash = calcHash();
        String value = PREFERENCES.getString(key, null);
        if (value != null && freshHash.equals(storedHash)) {
            return value;
        } else {
            System.out.printf("The value [%s] was changed manually! Return default value: %s\n",  key, defaultValue);
            return defaultValue;
        }
    }

    private static void updateHash() {
        String hash = calcHash();
        PREFERENCES.putString(Constants.Prefs.HASH, hash);
        PREFERENCES.flush();
    }

    private static String calcHash() {
        List<String> keys = Constants.Prefs.KEYS;
        StringBuilder builder = new StringBuilder();
        for (String key : keys) {
            builder.append(PREFERENCES.getString(key));
        }
        return hash(builder + SALT);
    }

    private static String hash(String input) {
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
