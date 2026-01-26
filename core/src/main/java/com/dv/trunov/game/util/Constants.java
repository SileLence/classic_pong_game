package com.dv.trunov.game.util;

import com.badlogic.gdx.graphics.Color;

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
        public static final float MIDDLE_OF_COUNTER_FILED = (Border.TOP - Border.GAME_FIELD_TOP) / 2f + Constants.Border.GAME_FIELD_TOP;
        public static final float SETTINGS_FIRST_ROW = Border.TOP * 0.8f;
        public static final float SETTINGS_SECOND_ROW = Border.TOP * 0.7f;
        public static final float SETTINGS_THIRD_ROW = Border.TOP * 0.6f;
        public static final float SETTINGS_FOURTH_ROW = Border.TOP * 0.5f;
    }

    public static final class Physics {

        public static final float INTERPOLATION_COEFFICIENT = 0.1f;
        public static final float SPIN_FACTOR = 0.3f;
        public static final float SERVE_ANGLE_FACTOR = 0.5f;
        public static final float FIXED_TIMESTEP = 1f / 240f;
        public static final float LEVEL_UP_COOLDOWN = 0.3f;
        public static final float BALL_SPEED = 1000f;
        public static final float BALL_SPEED_STEP = 75f;
        public static final float PLATFORM_SPEED_MODIFICATOR = 0.7f;
    }

    public static final class Colors {

        public static final Color TITLE_FONT_COLOR = new Color(0.60f, 0.19f, 0.48f, 1f);
        public static final Color TITLE_SHADOW_COLOR = new Color(0.80f, 0.10f, 0.58f, 0.25f);
        public static final Color REGULAR_FONT_COLOR = new Color(0.7f, 0.7f, 0.7f, 1f);
        public static final Color SELECTION_FONT_COLOR = new Color(0.85f, 0.30f, 0.60f, 1f);
        public static final Color PLAYER_ONE_WINNER_COLOR = new Color(0.28f, 0.51f, 0.88f, 1f);
        public static final Color PLAYER_TWO_WINNER_COLOR = new Color(0.87f, 0.48f, 0.28f, 1f);
        public static final Color LEVEL_UP_COLOR = new Color(0.60f, 0.85f, 0.43f, 1f);
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
                + " .,:;-_!?()[]<>";
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
        public static final String LEVEL_COUNTER_KEY = "levelCounter";
        public static final String TAB_TO_SERVE_KEY = "tabToServe";
        public static final String ENTER_TO_SERVE_KEY = "enterToServe";
        public static final String POINTS_TO_WIN_KEY = "pointsToWin";
        public static final String BALL_SPEED_KEY = "ballSpeed";
        public static final String SOUNDS_KEY = "sounds";
        public static final String RESET_BEST_KEY = "resetBest";
        public static final String BACK_KEY = "back";
        public static final String POINTS_TO_WIN_VALUE_KEY = "pointsToWinValue";
        public static final String BALL_SPEED_VALUE_KEY = "ballSpeedValue";
        public static final String SOUNDS_VALUE_KEY = "soundValue";
        public static final String RESET_BEST_QUESTION_KEY = "resetBestQuestion";
        public static final String YES_KEY = "yes";
        public static final String NO_KEY = "no";
        public static final String STARTING_SERVE_KEY = "startingServe";
        public static final String STARTING_SERVE_VALUE_KEY = "startingServeValue";
    }

    public static final class Prefs {

        public static final String PREFS_NAME = "classic_pong";
        public static final String HASH = "hash";
        public static final String BEST_LEVEL = "best";
        public static final String BALL_SPEED = "speed";
        public static final String POINTS_TO_WIN = "points";
        public static final String SOUNDS = "sounds";
        public static final String SERVE = "serve";
        public static final List<String> PREFS_KEY_LIST = List.of(BEST_LEVEL, BALL_SPEED, POINTS_TO_WIN, SOUNDS, SERVE);
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
        public static String TAB_TO_SERVE;
        public static String ENTER_TO_SERVE;
        public static String POINTS_TO_WIN;
        public static String BALL_SPEED;
        public static String SOUNDS;
        public static String RESET_BEST;
        public static String BACK;
        public static String[] BALL_SPEED_VALUES;
        public static String RESET_BEST_QUESTION;
        public static String ON;
        public static String OFF;
        public static String YES;
        public static String NO;
        public static String STARTING_SERVE;
        public static String[] STARTING_SERVE_VALUES;

        private static final class Russian {

            public static final String ONE_PLAYER = "1 игрок";
            public static final String TWO_PLAYERS = "2 игрока";
            public static final String SETTINGS = "Настройки";
            public static final String PAUSE = "ПАУЗА";
            public static final String CONTINUE = "Продолжить";
            public static final String PRESS_ENTER = "Нажмите Enter";
            public static final String BEST_LEVEL = "Рекорд: ";
            public static final String LEVEL = "Уровень: ";
            public static final String NEW_RECORD = "Новый Рекорд!";
            public static final String PLAYER_ONE_WINS = "Победил 1 Игрок!";
            public static final String PLAYER_TWO_WINS = "Победил 2 Игрок!";
            public static final String PLAY_AGAIN = "Сыграть ещё раз";
            public static final String EXIT_TO_MENU = "Выйти в меню";
            public static final String EXIT = "Выход";
            public static final String TAB_TO_SERVE = "Tab - подача";
            public static final String ENTER_TO_SERVE = "Enter - подача";
            public static final String POINTS_TO_WIN = "Очки до победы (2 игрока):";
            public static final String BALL_SPEED = "Скорость мяча (2 игрока):";
            public static final String SOUNDS = "Звуки:";
            public static final String RESET_BEST = "Сбросить лучший результат";
            public static final String BACK = "Назад";
            public static final String[] BALL_SPEED_VALUES = new String[]{"Очень медленно", "Медленно", "Нормально", "Быстро", "Очень быстро", "Экстремально"};
            public static final String RESET_BEST_QUESTION = "Сбросить лучший результат?";
            public static final String ON = "Включены";
            public static final String OFF = "Выключены";
            public static final String YES = "Да";
            public static final String NO = "Нет";
            public static final String STARTING_SERVE = "Первая подача:";
            public static final String[] STARTING_SERVE_VALUES = new String[]{"Центр", "Случайный игрок", "Игрок 1", "Игрок 2"};
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
            public static final String TAB_TO_SERVE = "Tab to Serve";
            public static final String ENTER_TO_SERVE = "Enter to Serve";
            public static final String POINTS_TO_WIN = "Points to win (2 players):";
            public static final String BALL_SPEED = "Ball speed (2 players):";
            public static final String SOUNDS = "Sounds:";
            public static final String RESET_BEST = "Reset best result";
            public static final String BACK = "Back";
            public static final String[] BALL_SPEED_VALUES = new String[]{"Very Slow", "Slow", "Normal", "Fast", "Very Fast", "Extreme"};
            public static final String RESET_BEST_QUESTION = "Reset best result?";
            public static final String ON = "On";
            public static final String OFF = "Off";
            public static final String YES = "Yes";
            public static final String NO = "No";
            public static final String STARTING_SERVE = "Starting Serve:";
            public static final String[] STARTING_SERVE_VALUES = new String[]{"Center", "Random Player", "Player 1", "Player 2"};
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
                Text.TAB_TO_SERVE = Text.Russian.TAB_TO_SERVE;
                Text.ENTER_TO_SERVE = Text.Russian.ENTER_TO_SERVE;
                Text.POINTS_TO_WIN = Text.Russian.POINTS_TO_WIN;
                Text.BALL_SPEED = Text.Russian.BALL_SPEED;
                Text.SOUNDS = Text.Russian.SOUNDS;
                Text.RESET_BEST = Text.Russian.RESET_BEST;
                Text.BACK = Text.Russian.BACK;
                Text.BALL_SPEED_VALUES = Text.Russian.BALL_SPEED_VALUES;
                Text.RESET_BEST_QUESTION = Text.Russian.RESET_BEST_QUESTION;
                Text.ON = Text.Russian.ON;
                Text.OFF = Text.Russian.OFF;
                Text.YES = Text.Russian.YES;
                Text.NO = Text.Russian.NO;
                Text.STARTING_SERVE = Text.Russian.STARTING_SERVE;
                Text.STARTING_SERVE_VALUES = Text.Russian.STARTING_SERVE_VALUES;
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
                Text.TAB_TO_SERVE = Text.English.TAB_TO_SERVE;
                Text.ENTER_TO_SERVE = Text.English.ENTER_TO_SERVE;
                Text.POINTS_TO_WIN = Text.English.POINTS_TO_WIN;
                Text.BALL_SPEED = Text.English.BALL_SPEED;
                Text.SOUNDS = Text.English.SOUNDS;
                Text.RESET_BEST = Text.English.RESET_BEST;
                Text.BACK = Text.English.BACK;
                Text.BALL_SPEED_VALUES = Text.English.BALL_SPEED_VALUES;
                Text.RESET_BEST_QUESTION = Text.English.RESET_BEST_QUESTION;
                Text.ON = Text.English.ON;
                Text.OFF = Text.English.OFF;
                Text.YES = Text.English.YES;
                Text.NO = Text.English.NO;
                Text.STARTING_SERVE = Text.English.STARTING_SERVE;
                Text.STARTING_SERVE_VALUES = Text.English.STARTING_SERVE_VALUES;
            }
        }
    }

    private Constants() {
    }
}
