package com.dv.trunov.game.ui.text;

import com.dv.trunov.game.gameparameters.switchable.Language;

public enum TextKey {

    TITLE("CLASSIC PONG", "CLASSIC PONG"),
    ONE_PLAYER("1 игрок", "1 Player"),
    TWO_PLAYERS("2 игрока", "2 Players"),
    SETTINGS("Настройки", "Settings"),
    EXIT("Выход", "Exit"),
    POINTS_TO_WIN("Очки до победы (2 игрока):", "Points to Win (2 Players):"),
    THREE("3", "3"),
    FIVE("5", "5"),
    SEVEN("7", "7"),
    TEN("10", "10"),
    FIFTEEN("15", "15"),
    TWENTY_ONE("21", "21"),
    BALL_SPEED("Скорость мяча (2 игрока):", "Ball Speed (2 Players):"),
    VERY_SLOW("Очень медленно", "Very Slow"),
    SLOW("Медленно", "Slow"),
    NORMAL("Нормально", "Normal"),
    FAST("Быстро", "Fast"),
    VERY_FAST("Очень быстро", "Very Fast"),
    EXTREME("Экстремально", "Extreme"),
    STARTING_SERVE("Первая подача (2 игрока):", "Starting Serve (2 Players):"),
    CENTER_SERVE("Центр", "Center"),
    RANDOM_PLAYER_SERVE("Случайный игрок", "Random Player"),
    PLAYER_ONE_SERVE("Игрок 1", "Player One"),
    PLAYER_TWO_SERVE("Игрок 2", "Player Two"),
    SOUND("Звук:", "Sound:"),
    ON("Включён", "On"),
    OFF("Выключен", "Off"),
    LANGUAGE("Язык:", "Language:"),
    RU("Русский", "Русский"),
    EN("English", "English"),
    RESET_BEST("Сбросить рекорд (1 игрок)", "Reset Best Score (1 Player)"),
    RESET_BEST_QUESTION("Вы уверены, что хотите сбросить значение рекорда?", "Are you sure you want to reset the best score?"),
    YES("Да", "Yes"),
    NO("Нет", "No"),
    BACK("Назад", "Back"),
    PRESS_ENTER("Нажмите Enter", "Press Enter"),
    CONTINUE("Продолжить", "Continue"),
    PLAY_AGAIN("Сыграть ещё раз", "Play Again"),
    EXIT_TO_MENU("Выйти в меню", "Exit to Menu"),
    TAB_TO_SERVE("Tab - подача", "Tab to Serve"),
    ENTER_TO_SERVE("Enter - подача", "Enter to Serve"),
    PLAYER_ONE_WINS("Игрок 1 победил!", "Player 1 Wins!"),
    PLAYER_TWO_WINS("Игрок 2 победил!", "Player 2 Wins!"),
    PAUSE("ПАУЗА", "PAUSE"),
    SCORE("Счёт: ", "Score: "),
    BEST("Рекорд: ", "Best: "),
    NEW_RECORD("Новый Рекорд!", "New Record!"),
    COLON(":", ":"),
    COUNTER("", "");

    private final String russian;
    private final String english;

    TextKey(String russian, String english) {
        this.russian = russian;
        this.english = english;
    }

    String getText(Language language) {
        return switch (language) {
            case RUSSIAN ->  russian;
            case ENGLISH -> english;
        };
    }
}
