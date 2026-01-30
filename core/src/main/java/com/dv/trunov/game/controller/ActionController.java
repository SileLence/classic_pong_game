package com.dv.trunov.game.controller;

import com.dv.trunov.game.ui.actions.ActionItem;
import com.dv.trunov.game.ui.actions.screen.mainmenu.ExitItem;
import com.dv.trunov.game.ui.actions.screen.mainmenu.OnePlayerItem;
import com.dv.trunov.game.ui.actions.screen.mainmenu.SettingsItem;
import com.dv.trunov.game.ui.actions.screen.mainmenu.TwoPlayersItem;
import com.dv.trunov.game.ui.actions.screen.playing.ContinueItem;
import com.dv.trunov.game.ui.actions.screen.playing.ExitToMenuItem;
import com.dv.trunov.game.ui.actions.screen.playing.PlayAgainItem;
import com.dv.trunov.game.ui.actions.screen.playing.PressEnterItem;
import com.dv.trunov.game.ui.actions.screen.settings.BackItem;
import com.dv.trunov.game.ui.actions.screen.settings.BallSpeedItem;
import com.dv.trunov.game.ui.actions.screen.settings.LanguageItem;
import com.dv.trunov.game.ui.actions.screen.settings.NoItem;
import com.dv.trunov.game.ui.actions.screen.settings.PointsToWinItem;
import com.dv.trunov.game.ui.actions.screen.settings.ResetBestItem;
import com.dv.trunov.game.ui.actions.screen.settings.ServeSideItem;
import com.dv.trunov.game.ui.actions.screen.settings.SoundItem;
import com.dv.trunov.game.ui.actions.screen.settings.YesItem;
import com.dv.trunov.game.ui.actions.screen.title.EnglishItem;
import com.dv.trunov.game.ui.actions.screen.title.RussianItem;

public class ActionController {

    private static final ActionController INSTANCE = new ActionController();
    private ActionItem ru;
    private ActionItem en;
    private ActionItem onePlayer;
    private ActionItem twoPlayers;
    private ActionItem settings;
    private ActionItem exit;
    private ActionItem pointsToWin;
    private ActionItem ballSpeed;
    private ActionItem serveSide;
    private ActionItem sound;
    private ActionItem language;
    private ActionItem resetBest;
    private ActionItem yes;
    private ActionItem no;
    private ActionItem back;
    private ActionItem pressEnter;
    private ActionItem continueAction;
    private ActionItem playAgain;
    private ActionItem exitToMenu;

    private ActionController() {
    }

    public static ActionController getInstance() {
        return INSTANCE;
    }

    public void init() {
        ru = new RussianItem();
        en = new EnglishItem();
        onePlayer = new OnePlayerItem();
        twoPlayers = new TwoPlayersItem();
        settings = new SettingsItem();
        exit = new ExitItem();
        pointsToWin = new PointsToWinItem();
        ballSpeed = new BallSpeedItem();
        serveSide = new ServeSideItem();
        sound = new SoundItem();
        language = new LanguageItem();
        resetBest = new ResetBestItem();
        yes = new YesItem();
        no = new NoItem();
        back = new BackItem();
        pressEnter = new PressEnterItem();
        continueAction = new ContinueItem();
        playAgain = new PlayAgainItem();
        exitToMenu = new ExitToMenuItem();
    }

    public ActionItem[] getTitleActions() {
        return new ActionItem[]{ru, en};
    }

    public ActionItem[] getMainMenuActions() {
        return new ActionItem[]{onePlayer, twoPlayers, settings, exit};
    }

    public ActionItem[] getSettingsActions() {
        return new ActionItem[]{pointsToWin, ballSpeed, serveSide, sound, language, resetBest, back};
    }

    public ActionItem[] getResetBestActions() {
        return new ActionItem[]{yes, no};
    }

    public ActionItem[] getIdleActions() {
        return new ActionItem[]{pressEnter};
    }

    public ActionItem[] getPauseActions() {
        return new ActionItem[]{continueAction, exitToMenu};
    }

    public ActionItem[] getEndGameActions() {
        return new ActionItem[]{playAgain, exitToMenu};
    }
}
