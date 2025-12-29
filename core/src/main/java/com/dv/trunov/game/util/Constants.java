package com.dv.trunov.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public final class Constants {

    public static final class Object {

        public static final float PLATFORM_WIDTH = 20f;
        public static final float PLATFORM_HEIGHT = 160f;
        public static final float BALL_RADIUS = 15f;
        public static final float BALL_START_X = Border.RIGHT / 2f;
        public static final float BALL_START_Y = Border.GAME_FIELD_TOP / 2f;
        public static final int BALL_TRAIL_NUMBER = 15;
        public static final int BALL_PARTICLES_NUMBER = 30;
    }

    public static final class Border {

        public static final float BOTTOM = 0f;
        public static final float TOP = Gdx.graphics.getHeight();
        public static final float GAME_FIELD_TOP = Gdx.graphics.getHeight() * 0.9f;
        public static final float RIGHT = Gdx.graphics.getWidth();
        public static final float LEFT = 0f;
        public static final float BOTTOM_BALL_BOUNDARY = BOTTOM + Object.BALL_RADIUS;
        public static final float TOP_BALL_BOUNDARY = GAME_FIELD_TOP - Object.BALL_RADIUS;
        public static final float RIGHT_BALL_BOUNDARY = RIGHT - Object.BALL_RADIUS;
        public static final float LEFT_BALL_BOUNDARY = LEFT + Object.BALL_RADIUS;
        public static final float TOP_PLATFORM_BOUNDARY = GAME_FIELD_TOP - Object.PLATFORM_HEIGHT;
        public static final float RIGHT_PLATFORM_BOUNDARY = RIGHT - Object.PLATFORM_WIDTH - 30f;
        public static final float LEFT_PLATFORM_BOUNDARY = LEFT + 30f;
    }

    public static final class Baseline {

        public static final float TITLE = Border.TOP * 0.8f;
        public static final float SUBTITLE = Border.TOP * 0.6f;
        public static final float FIRST_ROW = Border.TOP * 0.5f;
        public static final float SECOND_ROW = Border.TOP * 0.4f;
        public static final float THIRD_ROW = Border.TOP * 0.3f;
        public static final float FOURTH_ROW = Border.TOP * 0.2f;
        public static final float LEVEL_TEXT_OFFSET = Border.LEFT + 30f;
        public static final float MIDDLE_OF_COUNTER_FILED = (Border.TOP - Border.GAME_FIELD_TOP) / 2f + Constants.Border.GAME_FIELD_TOP;
        public static final float QUARTER_OF_GAME_FIELD = (Border.TOP - Border.GAME_FIELD_TOP) / 2f * 0.5f;
        public static float LEVEL_TEXT;
        public static float BEST_LEVEL_TEXT;
        public static float LEVEL_COUNTER_OFFSET;
        public static float BEST_LEVEL_COUNTER_OFFSET;
    }

    public static final class Speed {

        public static final float BALL_SPEED = 900f;
        public static final float BALL_SPEED_STEP = 100f;
        public static final float PLATFORM_SPEED = BALL_SPEED * 0.9f;
    }

    public static final class Physics {

        public static final float INTERPOLATION_COEFFICIENT = 0.1f;
        public static final float SPIN_FACTOR = 0.3f;
        public static final float FIXED_TIMESTEP = 1f / 240f;
        public static final float GOAL_COOLDOWN = 1.5f;
    }

    public static final class Colors {

        public static final Color TITLE_FONT_COLOR = new Color(0.60f, 0.19f, 0.48f, 1f);
        public static final Color TITLE_SHADOW_COLOR = new Color(0.80f, 0.10f, 0.58f, 0.25f);
        public static final Color REGULAR_FONT_COLOR = new Color(0.7f, 0.7f, 0.7f, 1f);
        public static final Color SELECTION_FONT_COLOR = new Color(0.90f, 0.35f, 0.65f, 1f);
        public static final Color PLAYER_ONE_COLOR = new Color(0.322f, 0.486f, 0.843f, 1f);
        public static final Color PLAYER_TWO_COLOR = new Color(0.843f, 0.475f, 0.322f, 1f);
    }

    public static final class Score {

        public static final int WIN_SCORE = 3;
    }

    public static final class Asset {

        public static final String BALL_TEXTURE_PATH = "texture/ball.png";
        public static final String PLATFORM_RIGHT_TEXTURE_PATH = "texture/platformR.png";
        public static final String PLATFORM_LEFT_TEXTURE_PATH = "texture/platformL.png";
        public static final String TITLE_FONT = "font/title/RussoOne-Regular.ttf";
        public static final String TEXT_FONT = "font/text/Montserrat-Bold.ttf";
        public static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
                + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
                + "0123456789"
                + " .,:;-_!?";
    }

    public static final class ItemKey {

        public static final String STATIC_TEXT_KEY = "staticText";
        public static final String RU_KEY = "ru";
        public static final String EN_KEY = "en";
        public static final String PLAYER_ONE_WINS_KEY = "playerOneWins";
        public static final String PLAYER_TWO_WINS_KEY = "playerTwoWins";
        public static final String ONE_PLAYER_KEY = "onePlayer";
        public static final String TWO_PLAYERS_KEY = "twoPlayers";
        public static final String SETTINGS_KEY = "settings";
        public static final String EXIT_KEY = "exit";
        public static final String PRESS_ENTER_KEY = "pressEnter";
        public static final String CONTINUE_KEY = "continue";
        public static final String PLAY_AGAIN_KEY = "playAgain";
        public static final String EXIT_TO_MENU_KEY = "exitToMenu";
    }

    public static final class Text {

        public static final String TITLE = "CLASSIC PONG";
        public static final String RUSSIAN = "Русский";
        public static final String ENGLISH = "English";
        public static String ONE_PLAYER ;
        public static String TWO_PLAYERS;
        public static String SETTINGS;
        public static String PAUSE;
        public static String CONTINUE;
        public static String PRESS_ENTER;
        public static String BEST_LEVEL;
        public static String LEVEL;
        public static String NEW_RECORD;
        public static String PLAYER_ONE_WINS;
        public static String PLAYER_TWO_WINS;
        public static String PLAY_AGAIN;
        public static String EXIT_TO_MENU;
        public static String EXIT;

        private static final class Russian {

            public static final String ONE_PLAYER = "1 игрок";
            public static final String TWO_PLAYERS = "2 игрока";
            public static final String SETTINGS = "Настройки";
            public static final String PAUSE = "ПАУЗА";
            public static final String CONTINUE = "Продолжить";
            public static final String PRESS_ENTER = "Нажмите Enter";
            public static final String BEST_LEVEL = "Лучший: ";
            public static final String LEVEL = "Уровень: ";
            public static final String NEW_RECORD = "Новый Рекорд!";
            public static final String PLAYER_ONE_WINS = "Победил 1 Игрок!";
            public static final String PLAYER_TWO_WINS = "Победил 2 Игрок!";
            public static final String PLAY_AGAIN = "Сыграть ещё раз";
            public static final String EXIT_TO_MENU = "Выйти в меню";
            public static final String EXIT = "Выход";
        }

        private static final class English {

            public static final String ONE_PLAYER = "1 Player";
            public static final String TWO_PLAYERS = "2 Players";
            public static final String SETTINGS = "Settings";
            public static final String PAUSE = "PAUSE";
            public static final String CONTINUE = "Continue";
            public static final String PRESS_ENTER = "Press Enter";
            public static final String BEST_LEVEL = "Best: ";
            public static final String LEVEL = "Level: ";
            public static final String NEW_RECORD = "New Record!";
            public static final String PLAYER_ONE_WINS = "Player 1 Wins!";
            public static final String PLAYER_TWO_WINS = "Player 2 Wins!";
            public static final String PLAY_AGAIN = "Play Again";
            public static final String EXIT_TO_MENU = "Exit to Menu";
            public static final String EXIT = "Exit";
        }
    }

    public static void setLocalization(Language language) {
        switch (language) {
            case RUSSIAN -> {
                Text.ONE_PLAYER = Text.Russian.ONE_PLAYER;
                Text.TWO_PLAYERS = Text.Russian.TWO_PLAYERS;
                Text.SETTINGS = Text.Russian.SETTINGS;
                Text.PAUSE = Text.Russian.PAUSE;
                Text.CONTINUE = Text.Russian.CONTINUE;
                Text.PRESS_ENTER = Text.Russian.PRESS_ENTER;
                Text.BEST_LEVEL = Text.Russian.BEST_LEVEL;
                Text.LEVEL = Text.Russian.LEVEL;
                Text.NEW_RECORD = Text.Russian.NEW_RECORD;
                Text.PLAYER_ONE_WINS = Text.Russian.PLAYER_ONE_WINS;
                Text.PLAYER_TWO_WINS = Text.Russian.PLAYER_TWO_WINS;
                Text.PLAY_AGAIN = Text.Russian.PLAY_AGAIN;
                Text.EXIT_TO_MENU = Text.Russian.EXIT_TO_MENU;
                Text.EXIT = Text.Russian.EXIT;
            }
            case ENGLISH -> {
                Text.ONE_PLAYER = Text.English.ONE_PLAYER;
                Text.TWO_PLAYERS = Text.English.TWO_PLAYERS;
                Text.SETTINGS = Text.English.SETTINGS;
                Text.PAUSE = Text.English.PAUSE;
                Text.CONTINUE = Text.English.CONTINUE;
                Text.PRESS_ENTER = Text.English.PRESS_ENTER;
                Text.BEST_LEVEL = Text.English.BEST_LEVEL;
                Text.LEVEL = Text.English.LEVEL;
                Text.NEW_RECORD = Text.English.NEW_RECORD;
                Text.PLAYER_ONE_WINS = Text.English.PLAYER_ONE_WINS;
                Text.PLAYER_TWO_WINS = Text.English.PLAYER_TWO_WINS;
                Text.PLAY_AGAIN = Text.English.PLAY_AGAIN;
                Text.EXIT_TO_MENU = Text.English.EXIT_TO_MENU;
                Text.EXIT = Text.English.EXIT;
            }
        }
    }

    public static void setLevelCounterOffset(float textLayoutWidth) {
        Baseline.LEVEL_COUNTER_OFFSET = Border.RIGHT / 2f - textLayoutWidth + 10f;
    }

    public static void setBestLevelCounterOffset(float textLayoutWidth) {
        Baseline.BEST_LEVEL_COUNTER_OFFSET = Border.RIGHT / 2f - textLayoutWidth;
    }

    public static void setLevelTextBaseline(float textLayoutHeight) {
        Baseline.LEVEL_TEXT = (Baseline.MIDDLE_OF_COUNTER_FILED + textLayoutHeight / 2f) - Baseline.QUARTER_OF_GAME_FIELD;
    }

    public static void setBestLevelTextBaseline(float textLayoutHeight) {
       Baseline.BEST_LEVEL_TEXT = (Baseline.MIDDLE_OF_COUNTER_FILED + textLayoutHeight / 2f) + Baseline.QUARTER_OF_GAME_FIELD;
    }

    private Constants() {
    }
}
