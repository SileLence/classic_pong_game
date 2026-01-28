package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
    private static Sound levelUp;
    private static Sound win;

    private SoundController() {
    }

    public static void init() {
        menuMove = Gdx.audio.newSound(Gdx.files.internal("sound/menuMove.wav"));
        menuSelect = Gdx.audio.newSound(Gdx.files.internal("sound/menuSelect.wav"));
        wallHit = Gdx.audio.newSound(Gdx.files.internal("sound/wallHit.wav"));
        activeWallHit = Gdx.audio.newSound(Gdx.files.internal("sound/activeWallHit.wav"));
        platformHitL = Gdx.audio.newSound(Gdx.files.internal("sound/platformHitL.wav"));
        platformHitR = Gdx.audio.newSound(Gdx.files.internal("sound/platformHitR.wav"));
        serve = Gdx.audio.newSound(Gdx.files.internal("sound/serve.wav"));
        ballExplosion = Gdx.audio.newSound(Gdx.files.internal("sound/ballExplosion.wav"));
        levelUp = Gdx.audio.newSound(Gdx.files.internal("sound/levelUp.wav"));
        win = Gdx.audio.newSound(Gdx.files.internal("sound/win.wav"));
    }

    public static void playSound(SoundToPlay soundToPlay, float volume) {
        switch (soundToPlay) {
            case MENU_MOVE -> menuMove.play(volume);
            case MENU_SELECT -> menuSelect.play(volume);
            case WALL_HIT -> wallHit.play(volume);
            case ACTIVE_WALL_HIT -> activeWallHit.play(volume);
            case PLATFORM_HIT_LEFT -> platformHitL.play(volume);
            case PLATFORM_HIT_RIGHT -> platformHitR.play(volume);
            case SERVE -> serve.play(volume);
            case LEVEL_UP -> levelUp.play(volume);
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
        levelUp.dispose();
    }
}
