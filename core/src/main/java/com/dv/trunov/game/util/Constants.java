package com.dv.trunov.game.util;

import com.badlogic.gdx.graphics.Color;
import com.dv.trunov.game.ui.text.TextKey;

import java.util.List;

public final class Constants {

    public static final class World {

        public static final float WIDTH = 1920;
        public static final float HEIGHT = 1080;
        public static final float PLATFORM_WIDTH = 20f;
        public static final float PLATFORM_HEIGHT = 160f;
        public static final float BALL_RADIUS = 15f;
        public static final float BALL_START_X = Border.RIGHT / 2f;
        public static final float BALL_START_Y = Border.GAME_FIELD_TOP / 2f;
        public static final float BALL_PLAYER_ONE_SERVE = Border.LEFT_PLATFORM_BOUNDARY + PLATFORM_WIDTH + BALL_RADIUS;
        public static final float BALL_PLAYER_TWO_SERVE = Border.RIGHT_PLATFORM_BOUNDARY - BALL_RADIUS;
        public static final int BALL_TRAIL_NUMBER = 15;
        public static final int BALL_PARTICLES_NUMBER = 50;
    }

    public static final class Border {

        public static final float BOTTOM = 0;
        public static final float TOP = World.HEIGHT;
        public static final float GAME_FIELD_BOTTOM = BOTTOM + 2f;
        public static final float GAME_FIELD_TOP = World.HEIGHT * 0.9f;
        public static final float RIGHT = World.WIDTH;
        public static final float LEFT = 0;
        public static final float BOTTOM_BALL_BOUNDARY = GAME_FIELD_BOTTOM + World.BALL_RADIUS;
        public static final float TOP_BALL_BOUNDARY = GAME_FIELD_TOP - World.BALL_RADIUS;
        public static final float RIGHT_BALL_BOUNDARY = RIGHT - World.BALL_RADIUS;
        public static final float LEFT_BALL_BOUNDARY = LEFT + World.BALL_RADIUS;
        public static final float BOTTOM_PLATFORM_BOUNDARY = GAME_FIELD_BOTTOM;
        public static final float TOP_PLATFORM_BOUNDARY = GAME_FIELD_TOP - World.PLATFORM_HEIGHT;
        public static final float RIGHT_PLATFORM_BOUNDARY = RIGHT - World.PLATFORM_WIDTH - 30f;
        public static final float LEFT_PLATFORM_BOUNDARY = LEFT + 30f;
        public static final float LEFT_SETTINGS_BOUNDARY = RIGHT / 2f - 50f;
        public static final float RIGHT_SETTINGS_BOUNDARY = RIGHT / 2f + 50f;
    }

    public static final class Baseline {

        public static final float TITLE = Border.TOP * 0.8f;
        public static final float SUBTITLE = Border.TOP * 0.6f;
        public static final float FIRST_ROW = Border.TOP * 0.5f;
        public static final float SECOND_ROW = Border.TOP * 0.4f;
        public static final float THIRD_ROW = Border.TOP * 0.3f;
        public static final float FOURTH_ROW = Border.TOP * 0.2f;
        public static final float FIFTH_ROW = Border.TOP * 0.1f;
        public static final float MIDDLE_OF_COUNTER_FILED = (Border.TOP - Border.GAME_FIELD_TOP) / 2f + Constants.Border.GAME_FIELD_TOP;
        public static final float SETTINGS_FIRST_ROW = Border.TOP * 0.9f;
        public static final float SETTINGS_SECOND_ROW = Border.TOP * 0.8f;
        public static final float SETTINGS_THIRD_ROW = Border.TOP * 0.74f;
        public static final float SETTINGS_FOURTH_ROW = Border.TOP * 0.64f;
        public static final float SETTINGS_FIFTH_ROW = Border.TOP * 0.54f;
        public static final float SETTINGS_SIXTH_ROW = Border.TOP * 0.44f;
        public static final float SETTINGS_SEVENTH_ROW = Border.TOP * 0.34f;
        public static final float SETTINGS_EIGHTH_ROW = Border.TOP * 0.28f;
        public static final float SETTINGS_NINTH_ROW = Border.TOP * 0.22f;
        public static final float SETTINGS_TENTH_ROW = Border.TOP * 0.12f;
    }

    public static final class Physics {

        public static final float INTERPOLATION_COEFFICIENT = 0.1f;
        public static final float SPIN_FACTOR = 0.3f;
        public static final float SERVE_ANGLE_FACTOR = 0.5f;
        public static final float FIXED_TIMESTEP = 1f / 240f;
        public static final float BALL_SPEED = 1200f;
        public static final float BALL_SPEED_STEP = 20f;
        public static final float PLATFORM_SPEED_MODIFICATOR = 0.7f;
        public static final float SCORE_UP_COOLDOWN = 0.1f;
        public static final float GOAL_COOLDOWN = 0.5f;
        public static final float EXIT_COOLDOWN = 0.1f;
    }

    public static final class Colors {

        public static final Color TITLE_FONT_COLOR = new Color(0.60f, 0.19f, 0.48f, 1f);
        public static final Color TITLE_SHADOW_COLOR = new Color(0.80f, 0.10f, 0.58f, 0.25f);
        public static final Color REGULAR_FONT_COLOR = new Color(0.7f, 0.7f, 0.7f, 1f);
        public static final Color SELECTION_FONT_COLOR = new Color(0.85f, 0.30f, 0.60f, 1f);
        public static final Color PLAYER_ONE_WINNER_COLOR = new Color(0.28f, 0.51f, 0.88f, 1f);
        public static final Color PLAYER_TWO_WINNER_COLOR = new Color(0.87f, 0.48f, 0.28f, 1f);
        public static final Color SCORE_UP_COLOR = new Color(0.60f, 0.85f, 0.43f, 1f);
    }

    public static final class Asset {

        public static final String BALL_TEXTURE_PATH = "texture/ball.png";
        public static final String PLATFORM_RIGHT_TEXTURE_PATH = "texture/platformR.png";
        public static final String PLATFORM_LEFT_TEXTURE_PATH = "texture/platformL.png";
        public static final String MENU_MOVE_SOUND = "sound/menuMove.wav";
        public static final String MENU_SELECT_SOUND = "sound/menuSelect.wav";
        public static final String WALL_HIT_SOUND = "sound/wallHit.wav";
        public static final String ACTIVE_WALL_HIT_SOUND = "sound/activeWallHit.wav";
        public static final String PLATFORM_HIT_LEFT_SOUND = "sound/platformHitL.wav";
        public static final String PLATFORM_HIT_RIGHT_SOUND = "sound/platformHitR.wav";
        public static final String SERVE_SOUND = "sound/serve.wav";
        public static final String BALL_EXPLOSION_SOUND = "sound/ballExplosion.wav";
        public static final String SCORE_UP_SOUND = "sound/scoreUp.wav";
        public static final String WIN_SOUND = "sound/win.wav";
    }

    public static final class Font {

        public static final String TITLE = "font/RussoOne-Regular.ttf";
        public static final String REGULAR = "font/Montserrat-Bold.ttf";
        public static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
                + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
                + "0123456789"
                + " .,:;-_!?()[]<>";
    }

    public static final class Prefs {

        public static final String PREFS_NAME = "classic_pong";
        public static final List<TextKey> KEY_LIST = List.of(
            TextKey.BEST,
            TextKey.BALL_SPEED,
            TextKey.POINTS_TO_WIN,
            TextKey.SOUND,
            TextKey.STARTING_SERVE,
            TextKey.LANGUAGE
        );
    }

    private Constants() {
    }
}
