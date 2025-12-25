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
        public static final int BALL_PARTICLES_NUMBER = 25;
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
        public static final float FIRST_ROW = Border.TOP * 0.5f;
        public static final float SECOND_ROW = Border.TOP * 0.4f;
        public static final float THIRD_ROW = Border.TOP * 0.3f;
        public static final float FOURTH_ROW = Border.TOP * 0.2f;
        public static final float SINGLEPLAYER_SCORE_TEXT_OFFSET = Border.LEFT + 30f;
        public static final float MIDDLE_OF_COUNTER_FILED = (Border.TOP - Border.GAME_FIELD_TOP) / 2f + Constants.Border.GAME_FIELD_TOP;
        public static final float QUARTER_OF_GAME_FIELD = (Border.TOP - Border.GAME_FIELD_TOP) / 2f * 0.5f;
        public static float CURRENT_SCORE_TEXT;
        public static float BEST_SCORE_TEXT;
        public static float SINGLEPLAYER_COUNTER_OFFSET;
    }

    public static final class Speed {

        public static final float BALL_SPEED = 1200f;
        public static final float PLATFORM_SPEED = BALL_SPEED * 0.9f;
    }

    public static final class Physics {

        public static final float INTERPOLATION_COEFFICIENT = 0.1f;
        public static final float SPIN_FACTOR = 0.3f;
        public static final float FIXED_TIMESTEP = 1f / 240f;
    }

    public static final class Colors {

        public static final Color TITLE_FONT_COLOR = new Color(0.60f, 0.19f, 0.48f, 1f);
        public static final Color TITLE_SHADOW_COLOR = new Color(0.80f, 0.10f, 0.58f, 0.25f);
        public static final Color REGULAR_FONT_COLOR = new Color(0.7f, 0.7f, 0.7f, 1f);
        public static final Color SELECTION_FONT_COLOR = new Color(0.90f, 0.35f, 0.65f, 1f);
    }

    public static final class Score {

        public static final int WIN_SCORE = 10;
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

        public static final String TITLE_KEY = "title";
        public static final String PAUSE_KEY = "pause";
        public static final String COUNTER_ONE_KEY = "counterOne";
        public static final String COUNTER_TWO_KEY = "counterTwo";
        public static final String COUNTER_CURRENT_KEY = "counterCurrent";
        public static final String COUNTER_BEST_KEY = "counterBest";
        public static final String COLON_KEY = "colon";
        public static final String RU_KEY = "ru";
        public static final String EN_KEY = "en";
        public static final String ONE_PLAYER_KEY = "onePlayer";
        public static final String TWO_PLAYERS_KEY = "twoPlayers";
        public static final String SETTINGS_KEY = "settings";
        public static final String CURRENT_SCORE_KEY = "currentScore";
        public static final String BEST_SCORE_KEY = "bestScore";
        public static final String WIN_KEY = "win";
        public static final String PLAYER_ONE_WINS_KEY = "playerOneWins";
        public static final String PLAYER_TWO_WINS_KEY = "playerTwoWins";
        public static final String EXIT_KEY = "exit";
        public static final String CONTINUE_KEY = "continue";
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
        public static String PRESS_ENTER_TO_START;
        public static String BEST_SCORE;
        public static String CURRENT_SCORE;
        public static String WIN;
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
            public static final String BEST_SCORE = "Лучший счёт: ";
            public static final String CURRENT_SCORE = "Текущий счёт: ";
            public static final String WIN = "ПОБЕДА!";
            public static final String PLAYER_ONE_WINS = "Победил игрок 1";
            public static final String PLAYER_TWO_WINS = "Победил игрок 2";
            public static final String PLAY_AGAIN = "Сыграть ещё раз";
            public static final String EXIT_TO_MENU = "Выйти в меню";
            public static final String EXIT = "Выход";
        }

        private static final class English {

            public static final String ONE_PLAYER = "1 player";
            public static final String TWO_PLAYERS = "2 players";
            public static final String SETTINGS = "Settings";
            public static final String PAUSE = "PAUSE";
            public static final String CONTINUE = "Continue";
            public static final String BEST_SCORE = "Best score: ";
            public static final String CURRENT_SCORE = "Current score: ";
            public static final String WIN = "WIN!";
            public static final String PLAYER_ONE_WINS = "Player 1 wins";
            public static final String PLAYER_TWO_WINS = "Player 2 wins";
            public static final String PLAY_AGAIN = "Play again";
            public static final String EXIT_TO_MENU = "Exit to menu";
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
                Text.BEST_SCORE = Text.Russian.BEST_SCORE;
                Text.CURRENT_SCORE = Text.Russian.CURRENT_SCORE;
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
                Text.BEST_SCORE = Text.English.BEST_SCORE;
                Text.CURRENT_SCORE = Text.English.CURRENT_SCORE;
                Text.PLAY_AGAIN = Text.English.PLAY_AGAIN;
                Text.EXIT_TO_MENU = Text.English.EXIT_TO_MENU;
                Text.EXIT = Text.English.EXIT;
            }
        }
    }

    public static void setSingleplayerCounterOffset(float widestTextLayoutWidth) {
        Baseline.SINGLEPLAYER_COUNTER_OFFSET = widestTextLayoutWidth + Baseline.SINGLEPLAYER_SCORE_TEXT_OFFSET + 10f;
    }

    public static void setCurrentScoreTextBaseline(float textLayoutHeight) {
        Baseline.CURRENT_SCORE_TEXT = (Baseline.MIDDLE_OF_COUNTER_FILED + textLayoutHeight / 2f) - Baseline.QUARTER_OF_GAME_FIELD;
    }

    public static void setBestScoreTextBaseline(float textLayoutHeight) {
       Baseline.BEST_SCORE_TEXT = (Baseline.MIDDLE_OF_COUNTER_FILED + textLayoutHeight / 2f) + Baseline.QUARTER_OF_GAME_FIELD;
    }

    private Constants() {
    }
}
