package com.rolandVoxes.internetAPIBenchmark.controller;

public class TimerController {
    private long startTime;
    private long stopTime;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        stopTime = System.nanoTime();
    }

    public float getDuration() {
        return (stopTime - startTime) / 1000000000.f;
    }
}
