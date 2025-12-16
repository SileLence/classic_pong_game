package com.dv.trunov.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public final class Constants {

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

    public static final class Object {

        public static final float PLATFORM_WIDTH = 20f;
        public static final float PLATFORM_HEIGHT = 160f;
        public static final float BALL_RADIUS = 15f;
        public static final float BALL_START_X = Border.RIGHT / 2f;
        public static final float BALL_START_Y = Border.GAME_FIELD_TOP / 2f;
        public static final int BALL_TRAIL_NUMBER = 15;
        public static final int BALL_PARTICLES_NUMBER = 25;
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

    public static final class Text {

        public static final String TITLE = "CLASSIC PONG";
        public static final String PRESS_ENTER = "Нажмите Enter";
        public static final String ONE_PLAYER = "1 игрок";
        public static final String TWO_PLAYERS = "2 игрока";
        public static final String SETTINGS = "Настройки";
        public static final String PAUSE = "ПАУЗА";
        public static final String CONTINUE = "Продолжить";
        public static final String EXIT_TO_MENU = "Выйти в меню";
        public static final String EXIT = "Выход";
    }

    public static final class Baseline {

        public static final float TITLE = Border.TOP * 0.8f;
        public static final float FIRST_ROW = Border.TOP * 0.5f;
        public static final float SECOND_ROW = Border.TOP * 0.4f;
        public static final float THIRD_ROW = Border.TOP * 0.3f;
        public static final float FOURTH_ROW = Border.TOP * 0.2f;
        public static final float MIDDLE_OF_COUNTER_FILED = (Border.TOP - Border.GAME_FIELD_TOP) / 2f
            + Constants.Border.GAME_FIELD_TOP;
    }

    public static final class Colors {

        public static final Color TITLE_FONT_COLOR = new Color(0.60f, 0.19f, 0.48f, 1f);
        public static final Color TITLE_SHADOW_COLOR = new Color(0.80f, 0.10f, 0.58f, 0.25f);
        public static final Color REGULAR_FONT_COLOR = new Color(0.8f, 0.8f, 0.8f, 1f);
        public static final Color SELECTION_FONT_COLOR = new Color(0.90f, 0.35f, 0.65f, 1f);
    }

    private Constants() {
    }
}
