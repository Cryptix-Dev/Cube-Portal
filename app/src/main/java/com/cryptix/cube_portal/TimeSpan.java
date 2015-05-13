package com.cryptix.cube_portal;

public class TimeSpan {
    private long millis;

    public TimeSpan(long millis) {
        this.millis = millis;
    }

    public long getMilliseconds() {
        return millis;
    }

    public double getSeconds() {
        return (double) millis / 1000;
    }

    public double getMinutes() {
        return (double) getSeconds() / 60;
    }
}
