package com.kirbydee.sound;

public class EmptySound extends Sound {

    @Override
    public void setVolume(float gain) {
        // do nothing
    }

    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public void setMute(boolean mute) {
        // do nothing
    }

    @Override
    public boolean getMute() {
        return false;
    }

    @Override
    public void play() {
        // do nothing
    }

    @Override
    public void playRandom() {
        // do nothing
    }

    @Override
    public void play(int index) {
        // do nothing
    }

    @Override
    public void stop() {
        // do nothing
    }

    @Override
    public void reset() {
        // do nothing
    }

    @Override
    public void setFramePosition(int frameNumber) {
        // do nothing
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
