package com.rolandVoxes.internetAPIBenchmark.model;

import java.util.ArrayList;

public class MockModel {
    String url;
    ArrayList<Float>avgDurations;

    public MockModel(String url) {
        this.url = url;
        avgDurations = new ArrayList<>();
    }

    public void addAvgDuration(float avgDuration) {
        avgDurations.add(avgDuration);
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<Float> getAvgDurations() { return avgDurations; }
}
