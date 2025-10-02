package com.mario.faceengine.util;

public class Timer {
    private long startTime;

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public long end() {
        return System.currentTimeMillis() - this.startTime;
    }
}

