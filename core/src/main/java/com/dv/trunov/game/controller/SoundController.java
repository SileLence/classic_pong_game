package com.dv.trunov.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundController {

    private static final SoundController INSTANCE = new SoundController();
    private float volume;
    private Sound menuMove;
    private Sound menuSelect;
    private Sound wallHit;
    private Sound activeWallHit;
    private Sound platformHit;
    private Sound ballExplosion;
    private Sound levelUp;

    private SoundController() {
    }

    public static SoundController getInstance() {
        return INSTANCE;
    }

    public void init() {
        volume = 1f;
        menuMove = Gdx.audio.newSound(Gdx.files.internal("sound/menuMove.wav"));
        menuSelect = Gdx.audio.newSound(Gdx.files.internal("sound/menuSelect.wav"));
        wallHit = Gdx.audio.newSound(Gdx.files.internal("sound/wallHit.wav"));
        activeWallHit = Gdx.audio.newSound(Gdx.files.internal("sound/activeWallHit.wav"));
        platformHit = Gdx.audio.newSound(Gdx.files.internal("sound/platformHit.wav"));
        ballExplosion = Gdx.audio.newSound(Gdx.files.internal("sound/ballExplosion2.wav"));
        levelUp = Gdx.audio.newSound(Gdx.files.internal("sound/levelUp.wav"));
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void playMenuMove() {
        menuMove.play(volume);
    }

    public void playMenuSelect() {
        menuSelect.play(volume);
    }

    public void playWallHit() {
        wallHit.play(volume);
    }

    public void playActiveWallHit() {
        activeWallHit.play(volume);
    }

    public void playPlatformHit() {
        platformHit.play(volume);
    }

    public void playBallExplosion() {
        ballExplosion.play(volume);
    }

    public void playLevelUp() {
        levelUp.play(volume);
    }

    public void dispose() {
        menuMove.dispose();
        menuSelect.dispose();
        wallHit.dispose();
        activeWallHit.dispose();
        platformHit.dispose();
        ballExplosion.dispose();
        levelUp.dispose();
    }
}
