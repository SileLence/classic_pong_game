package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.ui.TextLabel;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.GameState;
import com.dv.trunov.game.util.Language;
import com.dv.trunov.game.util.ServeState;

public class UIController {

    private static final UIController INSTANCE = new UIController();
    private FreeTypeFontGenerator titleFontGenerator;
    private FreeTypeFontGenerator regularFontGenerator;
    private BitmapFont titleFont;
    private BitmapFont subtitleFont;
    private BitmapFont scoreCounterFont;
    private BitmapFont levelCounterFont;
    private BitmapFont winnerFont;
    private BitmapFont regularFont;
    private TextLabel title;
    private TextLabel pause;
    private TextLabel newRecord;
    private TextLabel playerOneWins;
    private TextLabel playerTwoWins;
    private TextLabel counterLevel;
    private TextLabel counterBestLevel;
    private TextLabel counterResult;
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

    private UIController() {
    }

    public static UIController getInstance() {
        return INSTANCE;
    }

    public void createLanguageSelectionUI() {
        titleFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TITLE_FONT));
        regularFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TEXT_FONT));

        FreeTypeFontGenerator.FreeTypeFontParameter regularParams = createRegularParameters();
        regularFont = regularFontGenerator.generateFont(regularParams);

        FreeTypeFontGenerator.FreeTypeFontParameter subtitleParams = createSubtitleFontParameters();
        subtitleFont = titleFontGenerator.generateFont(subtitleParams);

        FreeTypeFontGenerator.FreeTypeFontParameter scoreCounterParams = createScoreCounterFontParameters();
        scoreCounterFont = titleFontGenerator.generateFont(scoreCounterParams);

        FreeTypeFontGenerator.FreeTypeFontParameter levelCounterParams = createLevelCounterParams();
        levelCounterFont = titleFontGenerator.generateFont(levelCounterParams);

        createMainTitleText(titleFontGenerator);
        createScoreSeparator();

        russian = createRegularText(Constants.ItemKey.RU_KEY, Constants.Text.RUSSIAN, Constants.Baseline.SECOND_ROW);
        english = createRegularText(Constants.ItemKey.EN_KEY, Constants.Text.ENGLISH, Constants.Baseline.THIRD_ROW);
    }

    public void createLocalizedUI(Language language) {
        Constants.setLocalization(language);

        pause = createSubtitleText(Constants.Text.PAUSE, Constants.Baseline.TITLE);
        newRecord = createSubtitleText(Constants.Text.NEW_RECORD, Constants.Baseline.TITLE);
        createWinnerText(titleFontGenerator);

        onePlayer = createRegularText(Constants.ItemKey.ONE_PLAYER_KEY, Constants.Text.ONE_PLAYER, Constants.Baseline.FIRST_ROW);
        twoPlayers = createRegularText(Constants.ItemKey.TWO_PLAYERS_KEY, Constants.Text.TWO_PLAYERS, Constants.Baseline.SECOND_ROW);
        settings = createRegularText(Constants.ItemKey.SETTINGS_KEY, Constants.Text.SETTINGS, Constants.Baseline.THIRD_ROW);
        exit = createRegularText(Constants.ItemKey.EXIT_KEY, Constants.Text.EXIT, Constants.Baseline.FOURTH_ROW);
        continueGame = createRegularText(Constants.ItemKey.CONTINUE_KEY, Constants.Text.CONTINUE, Constants.Baseline.THIRD_ROW);
        exitToMenu = createRegularText(Constants.ItemKey.EXIT_TO_MENU_KEY, Constants.Text.EXIT_TO_MENU, Constants.Baseline.FOURTH_ROW);
        pressEnter = createRegularText(Constants.ItemKey.PRESS_ENTER_KEY, Constants.Text.PRESS_ENTER, Constants.Baseline.FOURTH_ROW);
        playAgain = createRegularText(Constants.ItemKey.PLAY_AGAIN_KEY, Constants.Text.PLAY_AGAIN, Constants.Baseline.THIRD_ROW);
        tabToServe = createRegularText(Constants.ItemKey.TAB_TO_SERVE_KEY, Constants.Text.TAB_TO_SERVE, Constants.Baseline.FOURTH_ROW);
        enterToServe = createRegularText(Constants.ItemKey.ENTER_TO_SERVE_KEY, Constants.Text.ENTER_TO_SERVE, Constants.Baseline.FOURTH_ROW);

        titleFontGenerator.dispose();
        regularFontGenerator.dispose();
    }

    public void updateCounters(GameParameters gameParameters, boolean isSingleplayer) {
        if (isSingleplayer) {
            int level = gameParameters.getLevel();
            int bestLevel = gameParameters.getBestLevel();
            counterLevel = createLevelCounter(String.valueOf(level), false);
            counterBestLevel = createLevelCounter(String.valueOf(bestLevel), true);
            if (GameState.GAME_OVER == gameParameters.getGameState()) {
                counterResult = createSubtitleText(String.valueOf(level), Constants.Baseline.SUBTITLE);
            }
        } else {
            int scoreOne = gameParameters.getScoreOne();
            int scoreTwo = gameParameters.getScoreTwo();
            counterOne = createScoreCounter(String.valueOf(scoreOne), true);
            counterTwo = createScoreCounter(String.valueOf(scoreTwo), false);
        }
    }

    private void createMainTitleText(FreeTypeFontGenerator generator) {
        FreeTypeFontGenerator.FreeTypeFontParameter params = createBaseTitleFontParameters();
        params.size = 160;
        params.shadowOffsetX = 5;
        params.shadowOffsetY = -5;
        params.shadowColor = Constants.Colors.TITLE_SHADOW_COLOR;
        titleFont = generator.generateFont(params);
        GlyphLayout titleLayout = new GlyphLayout(titleFont, Constants.Text.TITLE);
        title = new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            (Constants.Border.RIGHT - titleLayout.width) / 2f,
            Constants.Baseline.TITLE,
            titleFont,
            Constants.Text.TITLE,
            titleLayout,
            Constants.Colors.TITLE_FONT_COLOR,
            false
        );
    }

    private void createScoreSeparator() {
        GlyphLayout layout = new GlyphLayout(scoreCounterFont, ":");
        scoreSeparator = new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            (Constants.Border.RIGHT - layout.width) / 2f,
            Constants.Baseline.MIDDLE_OF_COUNTER_FILED + layout.height / 2f,
            scoreCounterFont,
            ":",
            layout,
            Constants.Colors.TITLE_FONT_COLOR,
            false
        );
    }

    private TextLabel createSubtitleText(String text, float baseline) {
        GlyphLayout layout = new GlyphLayout(subtitleFont, text);
        return new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            (Constants.Border.RIGHT - layout.width) / 2f,
            baseline,
            subtitleFont,
            text,
            layout,
            Constants.Colors.TITLE_FONT_COLOR,
            false
        );
    }

    private void createWinnerText(FreeTypeFontGenerator generator) {
        FreeTypeFontGenerator.FreeTypeFontParameter params = createBaseTitleFontParameters();
        params.size = 54;
        winnerFont = generator.generateFont(params);
        GlyphLayout winnerLayout = new GlyphLayout(winnerFont, Constants.Text.PLAYER_TWO_WINS);
        playerOneWins = new TextLabel(
            Constants.ItemKey.PLAYER_ONE_WINS_KEY,
            (Constants.Border.RIGHT - winnerLayout.width) / 2f,
            Constants.Baseline.TITLE,
            winnerFont,
            Constants.Text.PLAYER_ONE_WINS,
            winnerLayout,
            Constants.Colors.PLAYER_ONE_WINNER_COLOR,
            false
        );
        playerTwoWins = new TextLabel(
            Constants.ItemKey.PLAYER_TWO_WINS_KEY,
            (Constants.Border.RIGHT - winnerLayout.width) / 2f,
            Constants.Baseline.TITLE,
            winnerFont,
            Constants.Text.PLAYER_TWO_WINS,
            winnerLayout,
            Constants.Colors.PLAYER_TWO_WINNER_COLOR,
            false
        );
    }

    private TextLabel createRegularText(String key, String text, float baseline) {
        GlyphLayout layout = new GlyphLayout(regularFont, text);
        float x = (Constants.Border.RIGHT - layout.width) / 2f;
        return new TextLabel(key, x, baseline, regularFont, text, layout, Constants.Colors.REGULAR_FONT_COLOR, true);
    }

    private TextLabel createLevelCounter(String counterValue, boolean isBest) {
        String text = isBest ? Constants.Text.BEST_LEVEL + counterValue : Constants.Text.LEVEL + counterValue;
        GlyphLayout layout = new GlyphLayout(levelCounterFont, text);
        float x = isBest ? (Constants.Border.RIGHT - layout.width) / 2f : Constants.Border.LEFT + 30f;
        float y = isBest ? Constants.Baseline.SUBTITLE : Constants.Baseline.MIDDLE_OF_COUNTER_FILED + layout.height / 2f;
        return new TextLabel(
            isBest ? Constants.ItemKey.STATIC_TEXT_KEY : Constants.ItemKey.LEVEL_COUNTER_KEY,
            x,
            y,
            levelCounterFont,
            text,
            layout,
            Constants.Colors.TITLE_FONT_COLOR,
            false
        );
    }

    private TextLabel createScoreCounter(String scoreValue, boolean isCounterOne) {
        GlyphLayout counterLayout = new GlyphLayout(scoreCounterFont, scoreValue);
        return new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            isCounterOne ? Constants.Border.RIGHT / 2f - counterLayout.width - 50f : Constants.Border.RIGHT / 2f + 50f,
            Constants.Baseline.MIDDLE_OF_COUNTER_FILED + counterLayout.height / 2f,
            scoreCounterFont,
            scoreValue,
            counterLayout,
            Constants.Colors.TITLE_FONT_COLOR,
            false
        );
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createBaseTitleFontParameters() {
        FreeTypeFontGenerator.FreeTypeFontParameter baseTitleParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        baseTitleParams.characters = Constants.Asset.CHARACTERS;
        baseTitleParams.color = Color.WHITE;
        baseTitleParams.shadowColor = Color.WHITE;
        baseTitleParams.gamma = 1.2f;
        baseTitleParams.minFilter = Texture.TextureFilter.Linear;
        baseTitleParams.magFilter = Texture.TextureFilter.Linear;
        return baseTitleParams;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createSubtitleFontParameters() {
        FreeTypeFontGenerator.FreeTypeFontParameter subtitleParams = createBaseTitleFontParameters();
        subtitleParams.size = 92;
        subtitleParams.shadowOffsetX = 3;
        subtitleParams.shadowOffsetY = -3;
        subtitleParams.shadowColor = Constants.Colors.TITLE_SHADOW_COLOR;
        return subtitleParams;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createLevelCounterParams() {
        FreeTypeFontGenerator.FreeTypeFontParameter levelCounterParams = createBaseTitleFontParameters();
        levelCounterParams.size = 42;
        return levelCounterParams;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createScoreCounterFontParameters() {
        FreeTypeFontGenerator.FreeTypeFontParameter scoreCounterParams = createBaseTitleFontParameters();
        scoreCounterParams.size = 72;
        scoreCounterParams.shadowOffsetX = 1;
        scoreCounterParams.shadowOffsetY = -1;
        scoreCounterParams.shadowColor = Constants.Colors.TITLE_SHADOW_COLOR;
        return scoreCounterParams;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createRegularParameters() {
        FreeTypeFontGenerator.FreeTypeFontParameter regularParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        regularParams.size = 42;
        regularParams.characters = Constants.Asset.CHARACTERS;
        regularParams.color = Color.WHITE;
        regularParams.minFilter = Texture.TextureFilter.Linear;
        regularParams.magFilter = Texture.TextureFilter.Linear;
        return regularParams;
    }

    public TextLabel getTitle() {
        return title;
    }

    public TextLabel[] getTitleMenu() {
        return new TextLabel[]{russian, english};
    }

    public TextLabel[] getMainMenu() {
        return new TextLabel[]{onePlayer, twoPlayers, settings, exit};
    }

    public TextLabel[] getPlayingScreen(boolean isSingleplayer) {
        return isSingleplayer
            ? new TextLabel[]{counterLevel}
            : new TextLabel[]{counterOne, counterTwo, scoreSeparator};
    }

    public TextLabel[] getPauseScreen(boolean isSingleplayer) {
        return isSingleplayer
            ? new TextLabel[]{pause, counterLevel, counterBestLevel}
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
            ? new TextLabel[]{newRecord, counterResult}
            : new TextLabel[]{counterResult};
    }

    public TextLabel[] getEndGameMenu() {
        return new TextLabel[]{playAgain, exitToMenu};
    }

    public TextLabel getPressEnter() {
        return pressEnter;
    }

    public void dispose() {
        titleFont.dispose();
        regularFont.dispose();
        if (subtitleFont != null) {
            subtitleFont.dispose();
        }
        if (scoreCounterFont != null) {
            scoreCounterFont.dispose();
        }
        if (levelCounterFont != null) {
            levelCounterFont.dispose();
        }
        if (winnerFont != null) {
            winnerFont.dispose();
        }
    }
}
