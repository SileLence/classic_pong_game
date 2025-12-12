package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.dv.trunov.game.ui.Continue;
import com.dv.trunov.game.ui.Exit;
import com.dv.trunov.game.ui.ExitToMenu;
import com.dv.trunov.game.ui.Pause;
import com.dv.trunov.game.ui.Title;
import com.dv.trunov.game.ui.UITextItem;
import com.dv.trunov.game.ui.OnePlayer;
import com.dv.trunov.game.ui.PressEnter;
import com.dv.trunov.game.ui.Settings;
import com.dv.trunov.game.ui.TwoPlayers;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.model.Ball;
import com.dv.trunov.game.model.Platform;
import com.dv.trunov.game.model.GameParameters;
import com.dv.trunov.game.util.TextLabel;

public class ObjectController {

    private static final ObjectController INSTANCE = new ObjectController();
    private GameParameters gameParameters;
    private Platform[] platforms;
    private Ball ball;
    private Title title;
    private Pause pause;
    private UITextItem[] titleScreen;
    private UITextItem[] menuScreen;
    private UITextItem[] pauseScreen;
    private BitmapFont titleFont;
    private BitmapFont pauseFont;
    private BitmapFont regularFont;

    private ObjectController() {
    }

    public static ObjectController getInstance() {
        return INSTANCE;
    }

    public void init() {
        gameParameters = GameParameters.getInstance();
        generateTitleTexts();
        createRegularTexts();
    }

    public boolean createWorldObjects(GameParameters gameParameters) {
        ball = new Ball(Constants.Asset.BALL_TEXTURE_PATH);
        platforms = new Platform[gameParameters.getGameMode().getValue()];
        platforms[0] = new Platform(
            Constants.Border.RIGHT_PLATFORM_BOUNDARY,
            Constants.Border.TOP_PLATFORM_BOUNDARY / 2f,
            true,
            Constants.Asset.PLATFORM_RIGHT_TEXTURE_PATH
        );
        if (platforms.length > 1) {
            platforms[1] = new Platform(
                Constants.Border.LEFT_PLATFORM_BOUNDARY,
                Constants.Border.TOP_PLATFORM_BOUNDARY / 2f,
                false,
                Constants.Asset.PLATFORM_LEFT_TEXTURE_PATH
            );
        }
        return true;
    }

    private void generateTitleTexts() {
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
            Constants.Baseline.THIRD_MENU_ITEM,
            regularFont,
            Constants.Text.PRESS_ENTER,
            pressEnterLayout);
        titleScreen  = new UITextItem[]{title, new PressEnter(pressEnterLabel)};
    }

    private void createMenuSelection(BitmapFont menuItemFont) {
        GlyphLayout onePlayerLayout = new GlyphLayout(menuItemFont, Constants.Text.ONE_PLAYER);
        GlyphLayout twoPlayersLayout = new GlyphLayout(menuItemFont, Constants.Text.TWO_PLAYERS);
        GlyphLayout settingsLayout = new GlyphLayout(menuItemFont, Constants.Text.SETTINGS);
        GlyphLayout exitLayout = new GlyphLayout(menuItemFont, Constants.Text.EXIT);
        TextLabel onePlayerLabel = new TextLabel(
            (Constants.Border.RIGHT - settingsLayout.width) / 2f,
            Constants.Baseline.FIRST_MENU_ITEM,
            menuItemFont,
            Constants.Text.ONE_PLAYER,
            onePlayerLayout
        );
        TextLabel twoPlayersLabel = new TextLabel(
            (Constants.Border.RIGHT - settingsLayout.width) / 2f,
            Constants.Baseline.SECOND_MENU_ITEM,
            menuItemFont,
            Constants.Text.TWO_PLAYERS,
            twoPlayersLayout
        );
        TextLabel settingsLabel = new TextLabel(
            (Constants.Border.RIGHT - settingsLayout.width) / 2f,
            Constants.Baseline.THIRD_MENU_ITEM,
            menuItemFont,
            Constants.Text.SETTINGS,
            settingsLayout
        );
        TextLabel exitLabel = new TextLabel(
            (Constants.Border.RIGHT - settingsLayout.width) / 2f,
            Constants.Baseline.FOURTH_MENU_ITEM,
            menuItemFont,
            Constants.Text.EXIT,
            exitLayout
        );
        menuScreen = new UITextItem[]{
            title,
            new OnePlayer(onePlayerLabel),
            new TwoPlayers(twoPlayersLabel),
            new Settings(settingsLabel),
            new Exit(exitLabel)
        };
    }

    private void createPauseSelection(BitmapFont regularFont) {
        GlyphLayout continueLayout = new GlyphLayout(regularFont, Constants.Text.CONTINUE);
        GlyphLayout exitToMenu = new GlyphLayout(regularFont, Constants.Text.EXIT_TO_MENU);
        TextLabel continueLabel = new TextLabel(
            (Constants.Border.RIGHT - exitToMenu.width) /2f,
            Constants.Baseline.THIRD_MENU_ITEM,
            regularFont,
            Constants.Text.CONTINUE,
            continueLayout
        );
        TextLabel exitToMenuLabel = new TextLabel(
            (Constants.Border.RIGHT - exitToMenu.width) / 2f,
            Constants.Baseline.FOURTH_MENU_ITEM,
            regularFont,
            Constants.Text.EXIT_TO_MENU,
            exitToMenu
        );
        pauseScreen = new UITextItem[]{
            pause,
            new Continue(continueLabel),
            new ExitToMenu(exitToMenuLabel)
        };
    }

    public Ball getBall() {
        return ball;
    }

    public Platform[] getPlatforms() {
        return platforms;
    }

    public UITextItem[] getTitleScreen() {
        return titleScreen;
    }

    public UITextItem[] getMenuScreen() {
        return menuScreen;
    }

    public UITextItem[] getPauseScreen() {
        return pauseScreen;
    }

    public GameParameters getGameParameters() {
        return gameParameters;
    }

    public boolean destroyWorldObjects() {
        ball = null;
        platforms = null;
        return false;
    }

    public void dispose() {
        ball.getTexture().dispose();
        titleFont.dispose();
        pauseFont.dispose();
        regularFont.dispose();
    }
}
