package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.dv.trunov.game.gameparameters.GameParameters;
import com.dv.trunov.game.ui.text.LocalizationService;
import com.dv.trunov.game.ui.text.TextKey;
import com.dv.trunov.game.ui.text.TextLabel;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.ServeState;

public class TextController {

    private static final TextController INSTANCE = new TextController();
    private static final int FONT_SIZE_160 = 160;
    private static final int FONT_SIZE_92 = 92;
    private static final int FONT_SIZE_72 = 72;
    private static final int FONT_SIZE_54 = 54;
    private static final int FONT_SIZE_42 = 42;
    private static final int FONT_SIZE_34 = 34;
    private static final int OFFSET_0 = 0;
    private static final int OFFSET_1 = 1;
    private static final int OFFSET_3 = 3;
    private static final int OFFSET_5 = 5;
    private LocalizationService localizationService;
    private FreeTypeFontGenerator titleFontGenerator;
    private FreeTypeFontGenerator regularFontGenerator;
    private BitmapFont titleFont160;
    private BitmapFont titleFont92;
    private BitmapFont titleFont72;
    private BitmapFont titleFont42;
    private BitmapFont titleFont54;
    private BitmapFont regularFont42;
    private BitmapFont regularFont34;
    private TextLabel title;
    private TextLabel pause;
    private TextLabel newRecord;
    private TextLabel playerOneWins;
    private TextLabel playerTwoWins;
    private TextLabel counterScore;
    private TextLabel counterBestScore;
    private TextLabel counterBestScoreIdle;
    private TextLabel counterGameResult;
    private TextLabel counterOne;
    private TextLabel counterTwo;
    private TextLabel scoreSeparator;
    private TextLabel english;
    private TextLabel russian;
    private TextLabel onePlayer;
    private TextLabel twoPlayers;
    private TextLabel settings;
    private TextLabel exit;
    private TextLabel continueGame;
    private TextLabel exitToMenu;
    private TextLabel pressEnter;
    private TextLabel playAgain;
    private TextLabel tabToServe;
    private TextLabel enterToServe;
    private TextLabel pointsToWin;
    private TextLabel ballSpeed;
    private TextLabel sound;
    private TextLabel resetBestScore;
    private TextLabel back;
    private TextLabel pointsToWinValue;
    private TextLabel ballSpeedValue;
    private TextLabel soundValue;
    private TextLabel resetBestScoreQuestion;
    private TextLabel yes;
    private TextLabel no;
    private TextLabel startingServe;
    private TextLabel startingServeValue;
    private TextLabel language;
    private TextLabel languageValue;
    private TextLabel onePLayerTitle;
    private TextLabel twoPlayersTitle;
    private TextLabel generalTitle;
    private TextLabel currentBestScore;

    private TextController() {
    }

    public static TextController getInstance() {
        return INSTANCE;
    }

    public void init(GameParameters gameParameters) {
        prepareFonts();
        updateLocalization();
        updateSettingsValues(gameParameters);
    }

    public void updateLocalization() {
        Color titleColor = Constants.Colors.TITLE_FONT_COLOR;
        Color playerOneColor = Constants.Colors.PLAYER_ONE_WINNER_COLOR;
        Color playerTwoColor = Constants.Colors.PLAYER_TWO_WINNER_COLOR;

        title = createTitleText(TextKey.TITLE, titleFont160, titleColor);
        pause = createTitleText(TextKey.PAUSE, titleFont92, titleColor);
        newRecord = createTitleText(TextKey.NEW_RECORD, titleFont92, titleColor);
        playerOneWins = createTitleText(TextKey.PLAYER_ONE_WINS, titleFont54, playerOneColor);
        playerTwoWins = createTitleText(TextKey.PLAYER_TWO_WINS, titleFont54, playerTwoColor);

        scoreSeparator = createTitleCounterText(TextKey.COLON, "", titleFont72, true);

        russian = createRegularText(TextKey.RU, regularFont42, Constants.Baseline.SECOND_ROW);
        english = createRegularText(TextKey.EN, regularFont42, Constants.Baseline.THIRD_ROW);
        onePlayer = createRegularText(TextKey.ONE_PLAYER, regularFont42, Constants.Baseline.FIRST_ROW);
        twoPlayers = createRegularText(TextKey.TWO_PLAYERS, regularFont42, Constants.Baseline.SECOND_ROW);
        settings = createRegularText(TextKey.SETTINGS, regularFont42, Constants.Baseline.THIRD_ROW);
        exit = createRegularText(TextKey.EXIT, regularFont42, Constants.Baseline.FOURTH_ROW);
        continueGame = createRegularText(TextKey.CONTINUE, regularFont42, Constants.Baseline.THIRD_ROW);
        exitToMenu = createRegularText(TextKey.EXIT_TO_MENU, regularFont42, Constants.Baseline.FOURTH_ROW);
        pressEnter = createRegularText(TextKey.PRESS_ENTER, regularFont42, Constants.Baseline.FOURTH_ROW);
        playAgain = createRegularText(TextKey.PLAY_AGAIN, regularFont42, Constants.Baseline.THIRD_ROW);
        tabToServe = createRegularText(TextKey.TAB_TO_SERVE, regularFont42, Constants.Baseline.FIFTH_ROW);
        enterToServe = createRegularText(TextKey.ENTER_TO_SERVE, regularFont42, Constants.Baseline.FIFTH_ROW);
        resetBestScoreQuestion = createRegularText(TextKey.RESET_BEST_QUESTION, regularFont34, Constants.Baseline.SETTINGS_SIXTH_ROW);
        yes = createRegularText(TextKey.YES, regularFont34, Constants.Baseline.SETTINGS_SEVENTH_ROW);
        no = createRegularText(TextKey.NO, regularFont34, Constants.Baseline.SETTINGS_EIGHTH_ROW);

        // settings layout
        generalTitle = createSettingsTitle(TextKey.GENERAL_TITLE, Constants.Baseline.SETTINGS_FIRST_ROW);
        language = createSettingsText(TextKey.LANGUAGE, Constants.Baseline.SETTINGS_SECOND_ROW, true);
        sound = createSettingsText(TextKey.SOUND, Constants.Baseline.SETTINGS_THIRD_ROW, true);
        onePLayerTitle = createSettingsTitle(TextKey.ONE_PLAYER_TITLE, Constants.Baseline.SETTINGS_FOURTH_ROW);
        resetBestScore = createRegularText(TextKey.RESET_BEST, regularFont34, Constants.Baseline.SETTINGS_FIFTH_ROW);
        twoPlayersTitle = createSettingsTitle(TextKey.TWO_PLAYERS_TITLE, Constants.Baseline.SETTINGS_SIXTH_ROW);
        pointsToWin = createSettingsText(TextKey.POINTS_TO_WIN, Constants.Baseline.SETTINGS_SEVENTH_ROW, true);
        ballSpeed = createSettingsText(TextKey.BALL_SPEED, Constants.Baseline.SETTINGS_EIGHTH_ROW, true);
        startingServe = createSettingsText(TextKey.STARTING_SERVE, Constants.Baseline.SETTINGS_NINTH_ROW, true);
        back = createRegularText(TextKey.BACK, regularFont34, Constants.Baseline.SETTINGS_TENTH_ROW);
    }

    public void updateSettingsValues(GameParameters gameParameters) {
        TextKey pointsToWinKey = gameParameters.getPointsToWin().getKey();
        TextKey ballSpeedKey = gameParameters.getMultiplayerBallSpeed().getKey();
        TextKey startingServeKey = gameParameters.getServeSide().getKey();
        TextKey soundStateKey = gameParameters.getSoundState().getKey();
        TextKey languageKey = gameParameters.getLanguage().getKey();
        String currentBestValue = String.valueOf(gameParameters.getBestScore());

        languageValue = createSettingsText(languageKey, Constants.Baseline.SETTINGS_SECOND_ROW, false);
        soundValue = createSettingsText(soundStateKey, Constants.Baseline.SETTINGS_THIRD_ROW, false);
        pointsToWinValue = createSettingsText(pointsToWinKey, Constants.Baseline.SETTINGS_SEVENTH_ROW, false);
        ballSpeedValue = createSettingsText(ballSpeedKey, Constants.Baseline.SETTINGS_EIGHTH_ROW, false);
        startingServeValue = createSettingsText(startingServeKey, Constants.Baseline.SETTINGS_NINTH_ROW, false);
        currentBestScore = createTitleCounterText(TextKey.CURRENT_BEST, currentBestValue, titleFont42, false);
    }

    public void updateCounters(GameParameters gameParameters, boolean isSingleplayer) {
        if (isSingleplayer) {
            String scoreOne = String.valueOf(gameParameters.getScoreOne());
            String bestScore = String.valueOf(gameParameters.getBestScore());
            counterScore = createSingleplayerCounterText(TextKey.SCORE, scoreOne);
            counterBestScore = createSingleplayerCounterText(TextKey.BEST, bestScore);
            GameState gameState = gameParameters.getGameState();
            switch (gameState) {
                case GAME_OVER -> counterGameResult = createTitleCounterText(TextKey.COUNTER, scoreOne, titleFont92, false);
                case IDLE -> counterBestScoreIdle = createTitleCounterText(TextKey.BEST, bestScore, titleFont72, false);
            }
        } else {
            String scoreOne = String.valueOf(gameParameters.getScoreOne());
            String scoreTwo = String.valueOf(gameParameters.getScoreTwo());
            counterOne = createMultiplayerCounterText(scoreOne, true);
            counterTwo = createMultiplayerCounterText(scoreTwo, false);
        }
    }

    private TextLabel createTitleCounterText(TextKey key, String value, BitmapFont font, boolean isColon) {
        String text = localizationService.getText(key) + value;
        GlyphLayout layout = new GlyphLayout(font, text);
        float x = (Constants.Border.RIGHT - layout.width) / 2f;
        float y = isColon ? Constants.Baseline.MIDDLE_OF_COUNTER_FILED + layout.height / 2f : Constants.Baseline.SUBTITLE;
        return new TextLabel(key, x, y, font, text, layout, Constants.Colors.TITLE_FONT_COLOR, false);
    }

    private TextLabel createTitleText(TextKey key, BitmapFont font, Color fontColor) {
        String text = localizationService.getText(key);
        GlyphLayout layout = new GlyphLayout(font, text);
        float x = (Constants.Border.RIGHT - layout.width) / 2f;
        return new TextLabel(key, x, Constants.Baseline.TITLE, font, text, layout, fontColor, false);
    }

    private TextLabel createRegularText(TextKey key, BitmapFont font, float baseline) {
        String text = localizationService.getText(key);
        GlyphLayout layout = new GlyphLayout(font, text);
        float x = (Constants.Border.RIGHT - layout.width) / 2f;
        boolean isSelectable = !TextKey.RESET_BEST_QUESTION.equals(key);
        return new TextLabel(key, x, baseline, font, text, layout, Constants.Colors.REGULAR_FONT_COLOR, isSelectable);
    }

    private TextLabel createSettingsTitle(TextKey key, float baseline) {
        String text = localizationService.getText(key);
        GlyphLayout layout = new GlyphLayout(titleFont42, text);
        float x = (Constants.Border.RIGHT - layout.width) / 2f;
        return new TextLabel(key, x, baseline, titleFont42, text, layout, Constants.Colors.TITLE_FONT_COLOR, false);
    }

    private TextLabel createSettingsText(TextKey key, float baseline, boolean isSettingName) {
        String text = localizationService.getText(key);
        GlyphLayout layout = new GlyphLayout(regularFont34, text);
        float x = isSettingName
            ? Constants.Border.LEFT_SETTINGS_BOUNDARY - layout.width
            : Constants.Border.RIGHT_SETTINGS_BOUNDARY;
        boolean isSelectable = !isSettingName;
        return new TextLabel(key, x, baseline, regularFont34, text, layout, Constants.Colors.REGULAR_FONT_COLOR, isSelectable);
    }

    private TextLabel createSingleplayerCounterText(TextKey key, String counterValue) {
        boolean isBest = TextKey.BEST.equals(key);
        String text = localizationService.getText(key) + counterValue;
        GlyphLayout layout = new GlyphLayout(titleFont42, text);
        float x = isBest ? (Constants.Border.RIGHT - layout.width) / 2f : Constants.Border.LEFT + 30f;
        float y = isBest ? Constants.Baseline.SUBTITLE : Constants.Baseline.MIDDLE_OF_COUNTER_FILED + layout.height / 2f;
        return new TextLabel(key, x, y, titleFont42, text, layout, Constants.Colors.TITLE_FONT_COLOR, false);
    }

    private TextLabel createMultiplayerCounterText(String score, boolean isCounterOne) {
        GlyphLayout layout = new GlyphLayout(titleFont72, score);
        float x = isCounterOne ? Constants.Border.RIGHT / 2f - layout.width - 50f : Constants.Border.RIGHT / 2f + 50f;
        float y = Constants.Baseline.MIDDLE_OF_COUNTER_FILED + layout.height / 2f;
        return new TextLabel(TextKey.COUNTER, x, y, titleFont72, score, layout, Constants.Colors.TITLE_FONT_COLOR, false);
    }

    private void prepareFonts() {
        titleFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Font.TITLE));
        regularFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Font.REGULAR));

        FreeTypeFontGenerator.FreeTypeFontParameter params160 = createTitleFontParameters(FONT_SIZE_160, OFFSET_5);
        titleFont160 = titleFontGenerator.generateFont(params160);

        FreeTypeFontGenerator.FreeTypeFontParameter params92 = createTitleFontParameters(FONT_SIZE_92, OFFSET_3);
        titleFont92 = titleFontGenerator.generateFont(params92);

        FreeTypeFontGenerator.FreeTypeFontParameter params72 = createTitleFontParameters(FONT_SIZE_72, OFFSET_1);
        titleFont72 = titleFontGenerator.generateFont(params72);

        FreeTypeFontGenerator.FreeTypeFontParameter params54 = createTitleFontParameters(FONT_SIZE_54, OFFSET_0);
        titleFont54 = titleFontGenerator.generateFont(params54);

        FreeTypeFontGenerator.FreeTypeFontParameter params42 = createTitleFontParameters(FONT_SIZE_42, OFFSET_0);
        titleFont42 = titleFontGenerator.generateFont(params42);

        FreeTypeFontGenerator.FreeTypeFontParameter regularParams42 = createRegularFontParameters(FONT_SIZE_42);
        regularFont42 = regularFontGenerator.generateFont(regularParams42);

        FreeTypeFontGenerator.FreeTypeFontParameter regularParams34 = createRegularFontParameters(FONT_SIZE_34);
        regularFont34 = regularFontGenerator.generateFont(regularParams34);
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createRegularFontParameters(int size) {
        FreeTypeFontGenerator.FreeTypeFontParameter regularParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        regularParams.size = size;
        regularParams.characters = Constants.Font.CHARACTERS;
        regularParams.color = Color.WHITE;
        regularParams.minFilter = Texture.TextureFilter.Linear;
        regularParams.magFilter = Texture.TextureFilter.Linear;
        return regularParams;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createTitleFontParameters(int size, int shadowOffset) {
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.characters = Constants.Font.CHARACTERS;
        params.color = Color.WHITE;
        params.shadowColor = Color.WHITE;
        params.gamma = 1.2f;
        params.minFilter = Texture.TextureFilter.Linear;
        params.magFilter = Texture.TextureFilter.Linear;
        params.size = size;
        params.shadowOffsetX = shadowOffset;
        params.shadowOffsetY = -shadowOffset;
        params.shadowColor = Constants.Colors.TITLE_SHADOW_COLOR;
        return params;
    }

    public TextLabel[] getTitleScreen() {
        return new TextLabel[]{title, russian, english};
    }

    public TextLabel[] getMainMenuScreen() {
        return new TextLabel[]{title, onePlayer, twoPlayers, settings, exit};
    }

    public TextLabel[] getSettingsScreen() {
        return new TextLabel[]{
            generalTitle,
            language,
            sound,
            onePLayerTitle,
            twoPlayersTitle,
            pointsToWin,
            ballSpeed,
            startingServe
        };
    }

    public TextLabel[] getSettingsMenu() {
        return new TextLabel[]{
            languageValue,
            soundValue,
            pointsToWinValue,
            ballSpeedValue,
            startingServeValue,
            resetBestScore,
            back
        };
    }

    public TextLabel[] getResetScreen() {
        return new TextLabel[]{currentBestScore, resetBestScoreQuestion};
    }

    public TextLabel[] getResetMenu() {
        return new TextLabel[]{yes, no};
    }

    public TextLabel[] getPlayingScreen(boolean isSingleplayer) {
        return isSingleplayer
            ? new TextLabel[]{counterScore}
            : new TextLabel[]{counterOne, counterTwo, scoreSeparator};
    }

    public TextLabel[] getPauseScreen(boolean isSingleplayer) {
        return isSingleplayer
            ? new TextLabel[]{pause, counterScore, counterBestScore}
            : new TextLabel[]{pause, counterOne, counterTwo, scoreSeparator};
    }

    public TextLabel[] getPauseMenu() {
        return new TextLabel[]{continueGame, exitToMenu};
    }

    public TextLabel getServeText(ServeState serveState) {
        return ServeState.PLAYER_ONE ==  serveState ? tabToServe : enterToServe;
    }

    public TextLabel[] getWinScreen(boolean isPlayerOneWins) {
        return new TextLabel[]{counterOne, counterTwo, scoreSeparator, isPlayerOneWins ? playerOneWins : playerTwoWins};
    }

    public TextLabel[] getEndGameScreen(boolean isNewRecord) {
        return isNewRecord
            ? new TextLabel[]{newRecord, counterGameResult}
            : new TextLabel[]{counterGameResult};
    }

    public TextLabel[] getEndGameMenu() {
        return new TextLabel[]{playAgain, exitToMenu};
    }

    public TextLabel getPressEnter() {
        return pressEnter;
    }

    public TextLabel getCounterBestScore() {
        return counterBestScoreIdle;
    }

    public void setLocalizationService(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    public void dispose() {
        titleFontGenerator.dispose();
        regularFontGenerator.dispose();
        titleFont160.dispose();
        titleFont92.dispose();
        titleFont72.dispose();
        titleFont54.dispose();
        titleFont42.dispose();
        regularFont42.dispose();
        regularFont34.dispose();
    }
}
