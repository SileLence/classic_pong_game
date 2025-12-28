package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.ui.TextLabel;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.Language;

public class UIController {

    private static final UIController INSTANCE = new UIController();
    FreeTypeFontGenerator titleFontGenerator;
    FreeTypeFontGenerator regularFontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter regularParams;
    private BitmapFont titleFont;
    private BitmapFont subtitleFont;
    private BitmapFont counterFont;
    private BitmapFont scoreFont;
    private BitmapFont winnerFont;
    private BitmapFont regularFont;
    private TextLabel title;
    private TextLabel pause;
    private TextLabel playerOneWins;
    private TextLabel playerTwoWins;
    private TextLabel currentScore;
    private TextLabel bestScore;
    private TextLabel counterCurrent;
    private TextLabel counterBest;
    private TextLabel counterOne;
    private TextLabel counterTwo;
    private TextLabel colon;
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

    private UIController() {
    }

    public static UIController getInstance() {
        return INSTANCE;
    }

    public void createLanguageSelectionUI() {
        titleFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TITLE_FONT));
        regularFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TEXT_FONT));
        regularParams = createRegularParameters();

        createMainTitleText(titleFontGenerator);

        russian = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.RU_KEY, Constants.Text.RUSSIAN, Constants.Baseline.SECOND_ROW);
        english = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.EN_KEY, Constants.Text.ENGLISH, Constants.Baseline.THIRD_ROW);

        titleFontGenerator.dispose();
        regularFontGenerator.dispose();
    }

    public void createLocalizedUI(Language language) {
        Constants.setLocalization(language);
        titleFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TITLE_FONT));
        regularFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TEXT_FONT));

        createSubtitleText(titleFontGenerator);
        createCounterText(titleFontGenerator);
        createScoreText(titleFontGenerator);
        createWinnerText(titleFontGenerator);

        onePlayer = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.ONE_PLAYER_KEY, Constants.Text.ONE_PLAYER, Constants.Baseline.FIRST_ROW);
        twoPlayers = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.TWO_PLAYERS_KEY, Constants.Text.TWO_PLAYERS, Constants.Baseline.SECOND_ROW);
        settings = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.SETTINGS_KEY, Constants.Text.SETTINGS, Constants.Baseline.THIRD_ROW);
        exit = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.EXIT_KEY, Constants.Text.EXIT, Constants.Baseline.FOURTH_ROW);
        continueGame = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.CONTINUE_KEY, Constants.Text.CONTINUE, Constants.Baseline.THIRD_ROW);
        exitToMenu = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.EXIT_TO_MENU_KEY, Constants.Text.EXIT_TO_MENU, Constants.Baseline.FOURTH_ROW);
        pressEnter = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.PRESS_ENTER_KEY, Constants.Text.PRESS_ENTER, Constants.Baseline.FOURTH_ROW);
        playAgain = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.PLAY_AGAIN_KEY, Constants.Text.PLAY_AGAIN, Constants.Baseline.THIRD_ROW);

        titleFontGenerator.dispose();
        regularFontGenerator.dispose();
    }

    public void updateCounters(GameParameters gameParameters, boolean isSingleplayer) {
        int scoreOne = gameParameters.getScoreOne();
        if (isSingleplayer) {
            int bestScore = gameParameters.getBestScore();
            counterCurrent = createSingleplayerCounter(String.valueOf(scoreOne), true);
            if (scoreOne == bestScore) {
                counterBest = createSingleplayerCounter(String.valueOf(bestScore), false);
            }
        } else {
            int scoreTwo = gameParameters.getScoreTwo();
            counterOne = createMultiplayerCounter(String.valueOf(scoreOne), true);
            counterTwo = createMultiplayerCounter(String.valueOf(scoreTwo), false);
        }
    }

    private void createMainTitleText(FreeTypeFontGenerator generator) {
        FreeTypeFontGenerator.FreeTypeFontParameter params = createBaseTitleFontParameters();
        params.size = 160;
        params.shadowOffsetX = 5;
        params.shadowOffsetY = -5;
        titleFont = generator.generateFont(params);
        GlyphLayout titleLayout = new GlyphLayout(titleFont, Constants.Text.TITLE);
        title = new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            (Constants.Border.RIGHT - titleLayout.width) / 2f,
            Constants.Baseline.TITLE,
            titleFont,
            Constants.Text.TITLE,
            titleLayout,
            false
        );
    }

    private void createSubtitleText(FreeTypeFontGenerator generator) {
        FreeTypeFontGenerator.FreeTypeFontParameter params = createBaseTitleFontParameters();
        params.size = 92;
        params.shadowOffsetX = 3;
        params.shadowOffsetY = -3;
        subtitleFont = generator.generateFont(params);
        GlyphLayout pauseLayout = new GlyphLayout(subtitleFont, Constants.Text.PAUSE);
        pause = new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            (Constants.Border.RIGHT - pauseLayout.width) / 2f,
            Constants.Baseline.TITLE,
            subtitleFont,
            Constants.Text.PAUSE,
            pauseLayout,
            false
        );
    }

    private void createCounterText(FreeTypeFontGenerator generator) {
        FreeTypeFontGenerator.FreeTypeFontParameter params = createBaseTitleFontParameters();
        params.size = 72;
        params.shadowOffsetX = 1;
        params.shadowOffsetY = -1;
        counterFont = generator.generateFont(params);
        GlyphLayout colonLayout = new GlyphLayout(counterFont, ":");
        colon = new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            Constants.Border.RIGHT / 2f - colonLayout.width / 2f,
            Constants.Baseline.MIDDLE_OF_COUNTER_FILED + colonLayout.height / 2f,
            counterFont,
            ":",
            colonLayout,
            false
        );
        counterOne = createMultiplayerCounter("0", true);
        counterTwo = createMultiplayerCounter("0", false);
    }

    private void createWinnerText(FreeTypeFontGenerator generator) {
        FreeTypeFontGenerator.FreeTypeFontParameter params = createBaseTitleFontParameters();
        params.size = 54;
        winnerFont = generator.generateFont(params);
        GlyphLayout winnerLayout = new GlyphLayout(winnerFont, Constants.Text.PLAYER_TWO_WINS);
        playerOneWins = new TextLabel(
            Constants.ItemKey.PLAYER_ONE_WINS_KEY,
            Constants.Border.RIGHT / 2f - winnerLayout.width / 2f,
            Constants.Baseline.TITLE,
            winnerFont,
            Constants.Text.PLAYER_ONE_WINS,
            winnerLayout,
            false
        );
        playerTwoWins = new TextLabel(
            Constants.ItemKey.PLAYER_TWO_WINS_KEY,
            Constants.Border.RIGHT / 2f - winnerLayout.width / 2f,
            Constants.Baseline.TITLE,
            winnerFont,
            Constants.Text.PLAYER_TWO_WINS,
            winnerLayout,
            false
        );
    }

    private void createScoreText(FreeTypeFontGenerator generator) {
        FreeTypeFontGenerator.FreeTypeFontParameter params = createBaseTitleFontParameters();
        params.size = 28;
        scoreFont = generator.generateFont(params);
        GlyphLayout bestScoreLayout = new GlyphLayout(scoreFont, Constants.Text.BEST_SCORE);
        GlyphLayout currentScoreLayout = new GlyphLayout(scoreFont, Constants.Text.CURRENT_SCORE);
        Constants.setBestScoreTextBaseline(bestScoreLayout.height);
        Constants.setCurrentScoreTextBaseline(currentScoreLayout.height);
        Constants.setSingleplayerCounterOffset(currentScoreLayout.width);
        bestScore = new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            Constants.Baseline.SINGLEPLAYER_SCORE_TEXT_OFFSET + currentScoreLayout.width - bestScoreLayout.width,
            Constants.Baseline.BEST_SCORE_TEXT,
            scoreFont,
            Constants.Text.BEST_SCORE,
            bestScoreLayout,
            false
        );
        currentScore = new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            Constants.Baseline.SINGLEPLAYER_SCORE_TEXT_OFFSET,
            Constants.Baseline.CURRENT_SCORE_TEXT,
            scoreFont,
            Constants.Text.CURRENT_SCORE,
            currentScoreLayout,
            false
        );
        counterBest = createSingleplayerCounter("0", false);
        counterCurrent = createSingleplayerCounter("0", true);
    }

    private TextLabel createRegularText(FreeTypeFontGenerator generator,
                                        FreeTypeFontGenerator.FreeTypeFontParameter params,
                                        String key,
                                        String text,
                                        float baseline) {
        regularFont = generator.generateFont(params);
        GlyphLayout layout = new GlyphLayout(regularFont, text);
        float placement = (Constants.Border.RIGHT - layout.width) / 2f;
        return new TextLabel(key, placement, baseline, regularFont, text, layout, true);
    }

    private TextLabel createSingleplayerCounter(String scoreValue, boolean isCurrentScore) {
        GlyphLayout counterLayout = new GlyphLayout(scoreFont, scoreValue);
        return new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            Constants.Baseline.SINGLEPLAYER_COUNTER_OFFSET,
            isCurrentScore ? Constants.Baseline.CURRENT_SCORE_TEXT : Constants.Baseline.BEST_SCORE_TEXT,
            scoreFont,
            scoreValue,
            counterLayout,
            false
        );
    }

    private TextLabel createMultiplayerCounter(String scoreValue, boolean isCounterOne) {
        GlyphLayout counterLayout = new GlyphLayout(counterFont, scoreValue);
        return new TextLabel(
            Constants.ItemKey.STATIC_TEXT_KEY,
            isCounterOne ? Constants.Border.RIGHT / 2f - counterLayout.width - 50f : Constants.Border.RIGHT / 2f + 50f,
            Constants.Baseline.MIDDLE_OF_COUNTER_FILED + counterLayout.height / 2f,
            counterFont,
            scoreValue,
            counterLayout,
            false
        );
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createBaseTitleFontParameters() {
        FreeTypeFontGenerator.FreeTypeFontParameter baseTitleParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        baseTitleParams.characters = Constants.Asset.CHARACTERS;
        baseTitleParams.color = Constants.Colors.TITLE_FONT_COLOR;
        baseTitleParams.shadowColor = Constants.Colors.TITLE_SHADOW_COLOR;
        baseTitleParams.gamma = 1.2f;
        baseTitleParams.minFilter = Texture.TextureFilter.Linear;
        baseTitleParams.magFilter = Texture.TextureFilter.Linear;
        return baseTitleParams;
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createRegularParameters() {
        FreeTypeFontGenerator.FreeTypeFontParameter regularParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        regularParams.size = 42;
        regularParams.characters = Constants.Asset.CHARACTERS;
        regularParams.color = Constants.Colors.REGULAR_FONT_COLOR;
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
            ? new TextLabel[]{bestScore, currentScore, counterCurrent, counterBest}
            : new TextLabel[]{counterOne, counterTwo, colon};
    }

    public TextLabel[] getPauseScreen(boolean isSingleplayer) {
        return isSingleplayer
            ? new TextLabel[]{bestScore, currentScore, counterCurrent, counterBest}
            : new TextLabel[]{pause, counterOne, counterTwo, colon};
    }

    public TextLabel[] getPauseMenu() {
        return new TextLabel[]{continueGame, exitToMenu};
    }

    public TextLabel[] getWinScreen(boolean isPlayerOneWins) {
        return new TextLabel[]{counterOne, counterTwo, colon, isPlayerOneWins ? playerOneWins : playerTwoWins};
    }

    public TextLabel[] getEndGameMenu() {
        return new TextLabel[]{playAgain, exitToMenu};
    }

    public TextLabel getPressEnter() {
        return pressEnter;
    }

    public void dispose() {
        titleFont.dispose();
        subtitleFont.dispose();
        counterFont.dispose();
        regularFont.dispose();
        scoreFont.dispose();
        winnerFont.dispose();
    }
}
