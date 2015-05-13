package com.cryptix.cube_portal;

public class GameTime {

    private TimeSpan elapsedGameTime;
    private TimeSpan totalGameTime;
    private boolean  isRunningSlowly;

    public GameTime(TimeSpan elapsedGameTime, TimeSpan totalGameTime, boolean isRunningSlowly) {
        this.elapsedGameTime = elapsedGameTime;
        this.totalGameTime = totalGameTime;
        this.isRunningSlowly = isRunningSlowly;
    }

    public TimeSpan ElapsedGameTime() {
        return elapsedGameTime;
    }

    public boolean IsRunningSlowly() {
        return isRunningSlowly;
    }

    public TimeSpan TotalGameTime() {
        return totalGameTime;

    }
}
