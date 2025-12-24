package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.ui.TextLabel;
import com.dv.trunov.game.util.Constants;

public class UIController {

    private static final UIController INSTANCE = new UIController();
    FreeTypeFontGenerator titleFontGenerator;
    FreeTypeFontGenerator regularFontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter regularParams;
    private BitmapFont titleFont;
    private BitmapFont pauseFont;
    private BitmapFont counterFont;
    private BitmapFont regularFont;
    private TextLabel title;
    private TextLabel pause;
    private TextLabel english;
    private TextLabel russian;
    private TextLabel onePlayer;
    private TextLabel twoPlayers;
    private TextLabel settings;
    private TextLabel exit;
    private TextLabel counterOne;
    private TextLabel counterTwo;
    private TextLabel colon;
    private TextLabel continueGame;
    private TextLabel exitToMenu;

    private UIController() {
    }

    public static UIController getInstance() {
        return INSTANCE;
    }

    public void createUIObjects() {
        titleFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TITLE_FONT));
        regularFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TEXT_FONT));
        regularParams = createRegularParameters();

        createMainTitleText(titleFontGenerator);

        russian = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.RU_KEY, Constants.Text.RUSSIAN, Constants.Baseline.SECOND_ROW);
        english = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.EN_KEY, Constants.Text.ENGLISH, Constants.Baseline.THIRD_ROW);

        titleFontGenerator.dispose();
        regularFontGenerator.dispose();
    }

    public void updateLocalization(GameParameters gameParameters) {
        Constants.setLocalization(gameParameters.getCurrentLanguage());
        titleFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TITLE_FONT));
        regularFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TEXT_FONT));

        createSubtitleText(titleFontGenerator);
        createCounterText(titleFontGenerator);

        onePlayer = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.ONE_PLAYER_KEY, Constants.Text.ONE_PLAYER, Constants.Baseline.FIRST_ROW);
        twoPlayers = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.TWO_PLAYERS_KEY, Constants.Text.TWO_PLAYERS, Constants.Baseline.SECOND_ROW);
        settings = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.SETTINGS_KEY, Constants.Text.SETTINGS, Constants.Baseline.THIRD_ROW);
        exit = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.EXIT_KEY, Constants.Text.EXIT, Constants.Baseline.FOURTH_ROW);
        continueGame = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.CONTINUE_KEY, Constants.Text.CONTINUE, Constants.Baseline.THIRD_ROW);
        exitToMenu = createRegularText(regularFontGenerator, regularParams, Constants.ItemKey.EXIT_TO_MENU_KEY, Constants.Text.EXIT_TO_MENU, Constants.Baseline.FOURTH_ROW);

        titleFontGenerator.dispose();
        regularFontGenerator.dispose();
    }

    public void updateCounters(GameParameters gameParameters) {
        String scoreOne = String.valueOf(gameParameters.getScoreOne());
        String scoreTwo = String.valueOf(gameParameters.getScoreTwo());
        counterOne = createCounter(Constants.ItemKey.COUNTER_ONE_KEY, scoreOne, true);
        counterTwo = createCounter(Constants.ItemKey.COUNTER_TWO_KEY, scoreTwo, false);
    }

    private void createMainTitleText(FreeTypeFontGenerator generator) {
        FreeTypeFontGenerator.FreeTypeFontParameter params = createBaseTitleFontParameters();
        params.size = 160;
        params.shadowOffsetX = 5;
        params.shadowOffsetY = -5;
        titleFont = generator.generateFont(params);
        GlyphLayout titleLayout = new GlyphLayout(titleFont, Constants.Text.TITLE);
        title = new TextLabel(
            Constants.ItemKey.TITLE_KEY,
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
        pauseFont = generator.generateFont(params);
        GlyphLayout pauseLayout = new GlyphLayout(pauseFont, Constants.Text.PAUSE);
        pause = new TextLabel(
            Constants.ItemKey.PAUSE_KEY,
            (Constants.Border.RIGHT - pauseLayout.width) / 2f,
            Constants.Baseline.TITLE,
            pauseFont,
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
            Constants.ItemKey.COLON_KEY,
            Constants.Border.RIGHT / 2f - colonLayout.width / 2f,
            Constants.Baseline.MIDDLE_OF_COUNTER_FILED + colonLayout.height / 2f,
            counterFont,
            ":",
            colonLayout,
            false
        );
        counterOne = createCounter(Constants.ItemKey.COUNTER_ONE_KEY, "0", true);
        counterTwo = createCounter(Constants.ItemKey.COUNTER_TWO_KEY, "0", false);
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

    private TextLabel createCounter(String key,String scoreValue, boolean isCounterOne) {
        GlyphLayout counterLayout = new GlyphLayout(counterFont, scoreValue);
        return new TextLabel(
            key,
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

    public TextLabel[] getPauseScreen() {
        return new TextLabel[]{pause, counterOne, counterTwo, colon};
    }

    public TextLabel[] getPauseMenu() {
        return new TextLabel[]{continueGame, exitToMenu};
    }

    public TextLabel[] getPlayingScreen() {
        return new TextLabel[]{counterOne, counterTwo, colon};
    }

    public void dispose() {
        titleFont.dispose();
        pauseFont.dispose();
        counterFont.dispose();
        regularFont.dispose();
    }
}
