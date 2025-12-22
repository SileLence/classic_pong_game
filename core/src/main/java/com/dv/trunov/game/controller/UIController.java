package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.ui.Colon;
import com.dv.trunov.game.ui.Continue;
import com.dv.trunov.game.ui.Counter;
import com.dv.trunov.game.ui.Exit;
import com.dv.trunov.game.ui.ExitToMenu;
import com.dv.trunov.game.ui.OnePlayer;
import com.dv.trunov.game.ui.Pause;
import com.dv.trunov.game.ui.PressEnter;
import com.dv.trunov.game.ui.Settings;
import com.dv.trunov.game.ui.Title;
import com.dv.trunov.game.ui.TwoPlayers;
import com.dv.trunov.game.ui.UITextItem;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.TextLabel;

public class UIController {

    private static final UIController INSTANCE = new UIController();
    private Title title;
    private Pause pause;
    private PressEnter pressEnter;
    private OnePlayer onePlayer;
    private TwoPlayers twoPlayers;
    private Settings settings;
    private Exit exit;
    private Counter counterOne;
    private Counter counterTwo;
    private Colon colon;
    private Continue continueGame;
    private ExitToMenu exitToMenu;
    private BitmapFont titleFont;
    private BitmapFont pauseFont;
    private BitmapFont counterFont;
    private BitmapFont regularFont;

    private UIController() {
    }

    public static UIController getInstance() {
        return INSTANCE;
    }

    public void createUIObjects() {
        createTitleTexts();
        createRegularTexts();
    }

    public void updateCounters(GameParameters gameParameters) {
        String scoreOne = String.valueOf(gameParameters.getScoreOne());
        String scoreTwo = String.valueOf(gameParameters.getScoreTwo());
        counterOne = createCounter(scoreOne, true);
        counterTwo = createCounter(scoreTwo, false);
    }

    private void createTitleTexts() {
        FreeTypeFontGenerator titleTextGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TITLE_FONT));
        FreeTypeFontGenerator.FreeTypeFontParameter titleParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParams.size = 160;
        titleParams.characters = Constants.Asset.CHARACTERS;
        titleParams.color = Constants.Colors.TITLE_FONT_COLOR;
        titleParams.shadowColor = Constants.Colors.TITLE_SHADOW_COLOR;
        titleParams.shadowOffsetX = 5;
        titleParams.shadowOffsetY = -5;
        titleParams.gamma = 1.2f;
        titleParams.minFilter = Texture.TextureFilter.Linear;
        titleParams.magFilter = Texture.TextureFilter.Linear;
        titleFont = titleTextGenerator.generateFont(titleParams);
        GlyphLayout titleLayout = new GlyphLayout(titleFont, Constants.Text.TITLE);
        TextLabel titleLabel = new TextLabel(
            (Constants.Border.RIGHT - titleLayout.width) / 2f,
            Constants.Baseline.TITLE,
            titleFont,
            Constants.Text.TITLE,
            titleLayout);
        title = new Title(titleLabel);

        titleParams.size = 92;
        titleParams.shadowOffsetX = 3;
        titleParams.shadowOffsetY = -3;
        pauseFont = titleTextGenerator.generateFont(titleParams);
        GlyphLayout pauseLayout = new GlyphLayout(pauseFont, Constants.Text.PAUSE);
        TextLabel pauseLabel = new TextLabel(
            (Constants.Border.RIGHT - pauseLayout.width) / 2f,
            Constants.Baseline.TITLE,
            pauseFont,
            Constants.Text.PAUSE,
            pauseLayout);
        pause = new Pause (pauseLabel);

        titleParams.size = 72;
        titleParams.shadowOffsetX = 1;
        titleParams.shadowOffsetY = -1;
        counterFont = titleTextGenerator.generateFont(titleParams);
        GlyphLayout colonLayout = new GlyphLayout(counterFont, ":");
        TextLabel colonLabel = new TextLabel(
            Constants.Border.RIGHT / 2f - colonLayout.width / 2f,
            Constants.Baseline.MIDDLE_OF_COUNTER_FILED + colonLayout.height / 2f,
            counterFont,
            ":",
            colonLayout
        );
        colon = new Colon(colonLabel);
        counterOne = createCounter("0", true);
        counterTwo = createCounter("0", false);

        titleTextGenerator.dispose();
    }

    private void createRegularTexts() {
        FreeTypeFontGenerator regularTextGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.Asset.TEXT_FONT));
        FreeTypeFontGenerator.FreeTypeFontParameter textParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        textParams.size = 42;
        textParams.characters = Constants.Asset.CHARACTERS;
        textParams.color = Constants.Colors.REGULAR_FONT_COLOR;
        textParams.minFilter = Texture.TextureFilter.Linear;
        textParams.magFilter = Texture.TextureFilter.Linear;
        regularFont = regularTextGenerator.generateFont(textParams);

        createPressEnter(regularFont);
        createMenuSelection(regularFont);
        createPauseSelection(regularFont);

        regularTextGenerator.dispose();
    }

    private void createPressEnter(BitmapFont regularFont) {
        GlyphLayout pressEnterLayout = new GlyphLayout(regularFont, Constants.Text.PRESS_ENTER);
        TextLabel pressEnterLabel = new TextLabel(
            (Constants.Border.RIGHT - pressEnterLayout.width) / 2f,
            Constants.Baseline.THIRD_ROW,
            regularFont,
            Constants.Text.PRESS_ENTER,
            pressEnterLayout);
        pressEnter = new PressEnter(pressEnterLabel);
    }

    private void createMenuSelection(BitmapFont menuItemFont) {
        GlyphLayout onePlayerLayout = new GlyphLayout(menuItemFont, Constants.Text.ONE_PLAYER);
        GlyphLayout twoPlayersLayout = new GlyphLayout(menuItemFont, Constants.Text.TWO_PLAYERS);
        GlyphLayout settingsLayout = new GlyphLayout(menuItemFont, Constants.Text.SETTINGS);
        GlyphLayout exitLayout = new GlyphLayout(menuItemFont, Constants.Text.EXIT);
        TextLabel onePlayerLabel = new TextLabel(
            (Constants.Border.RIGHT - settingsLayout.width) / 2f,
            Constants.Baseline.FIRST_ROW,
            menuItemFont,
            Constants.Text.ONE_PLAYER,
            onePlayerLayout
        );
        TextLabel twoPlayersLabel = new TextLabel(
            (Constants.Border.RIGHT - settingsLayout.width) / 2f,
            Constants.Baseline.SECOND_ROW,
            menuItemFont,
            Constants.Text.TWO_PLAYERS,
            twoPlayersLayout
        );
        TextLabel settingsLabel = new TextLabel(
            (Constants.Border.RIGHT - settingsLayout.width) / 2f,
            Constants.Baseline.THIRD_ROW,
            menuItemFont,
            Constants.Text.SETTINGS,
            settingsLayout
        );
        TextLabel exitLabel = new TextLabel(
            (Constants.Border.RIGHT - settingsLayout.width) / 2f,
            Constants.Baseline.FOURTH_ROW,
            menuItemFont,
            Constants.Text.EXIT,
            exitLayout
        );
        onePlayer = new OnePlayer(onePlayerLabel);
        twoPlayers = new TwoPlayers(twoPlayersLabel);
        settings = new Settings(settingsLabel);
        exit = new Exit(exitLabel);
    }

    private void createPauseSelection(BitmapFont regularFont) {
        GlyphLayout continueLayout = new GlyphLayout(regularFont, Constants.Text.CONTINUE);
        GlyphLayout exitToMenuLayout = new GlyphLayout(regularFont, Constants.Text.EXIT_TO_MENU);
        TextLabel continueLabel = new TextLabel(
            (Constants.Border.RIGHT - exitToMenuLayout.width) /2f,
            Constants.Baseline.THIRD_ROW,
            regularFont,
            Constants.Text.CONTINUE,
            continueLayout
        );
        TextLabel exitToMenuLabel = new TextLabel(
            (Constants.Border.RIGHT - exitToMenuLayout.width) / 2f,
            Constants.Baseline.FOURTH_ROW,
            regularFont,
            Constants.Text.EXIT_TO_MENU,
            exitToMenuLayout
        );
        continueGame = new Continue(continueLabel);
        exitToMenu = new ExitToMenu(exitToMenuLabel);
    }

    private Counter createCounter(String scoreValue, boolean isCounterOne) {
        GlyphLayout counterLayout = new GlyphLayout(counterFont, scoreValue);
        TextLabel counterLabel = new TextLabel(
            isCounterOne ? Constants.Border.RIGHT / 2f + 50f : Constants.Border.RIGHT / 2f - counterLayout.width - 50f,
            Constants.Baseline.MIDDLE_OF_COUNTER_FILED + counterLayout.height / 2f,
            counterFont,
            scoreValue,
            counterLayout
        );
        return new Counter(counterLabel);
    }

    public UITextItem[] getTitleScreen() {
        return new UITextItem[]{title, pressEnter};
    }

    public UITextItem[] getMenuScreen() {
        return new UITextItem[]{title, onePlayer, twoPlayers, settings, exit};
    }

    public UITextItem[] getPauseScreen() {
        return new UITextItem[]{pause, counterOne, counterTwo, colon, continueGame, exitToMenu};
    }

    public UITextItem[] getPlayingScreen() {
        return new UITextItem[]{counterOne, counterTwo, colon};
    }

    public void dispose() {
        titleFont.dispose();
        pauseFont.dispose();
        counterFont.dispose();
        regularFont.dispose();
    }
}
