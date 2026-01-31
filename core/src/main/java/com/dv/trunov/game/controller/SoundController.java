package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.dv.trunov.game.gameparameters.switchable.Toggle;
import com.dv.trunov.game.util.Constants;
import com.dv.trunov.game.util.SoundToPlay;

public class SoundController {

    private static Sound menuMove;
    private static Sound menuSelect;
    private static Sound wallHit;
    private static Sound activeWallHit;
    private static Sound platformHitL;
    private static Sound platformHitR;
    private static Sound serve;
    private static Sound ballExplosion;
    private static Sound scoreUp;
    private static Sound win;

    private SoundController() {
    }

    public static void init() {
        menuMove = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.MENU_MOVE_SOUND));
        menuSelect = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.MENU_SELECT_SOUND));
        wallHit = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.WALL_HIT_SOUND));
        activeWallHit = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.ACTIVE_WALL_HIT_SOUND));
        platformHitL = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.PLATFORM_HIT_LEFT_SOUND));
        platformHitR = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.PLATFORM_HIT_RIGHT_SOUND));
        serve = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.SERVE_SOUND));
        ballExplosion = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.BALL_EXPLOSION_SOUND));
        scoreUp = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.SCORE_UP_SOUND));
        win = Gdx.audio.newSound(Gdx.files.internal(Constants.Asset.WIN_SOUND));
    }

    public static void playSound(SoundToPlay soundToPlay, Toggle soundState) {
        float volume = soundState == Toggle.ON ? 1.0f : 0.0f;
        switch (soundToPlay) {
            case MENU_MOVE -> menuMove.play(volume);
            case MENU_SELECT -> menuSelect.play(volume);
            case WALL_HIT -> wallHit.play(volume);
            case ACTIVE_WALL_HIT -> activeWallHit.play(volume);
            case PLATFORM_HIT_LEFT -> platformHitL.play(volume);
            case PLATFORM_HIT_RIGHT -> platformHitR.play(volume);
            case SERVE -> serve.play(volume);
            case
                SCORE_UP -> scoreUp.play(volume);
            case BALL_EXPLOSION -> ballExplosion.play(volume);
            case WIN  -> win.play(volume);
        }
    }

    public static void dispose() {
        menuMove.dispose();
        menuSelect.dispose();
        wallHit.dispose();
        activeWallHit.dispose();
        platformHitL.dispose();
        platformHitR.dispose();
        ballExplosion.dispose();
        scoreUp.dispose();
    }
}
