package com.cryptix.cube_portal;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;

public class MainGLSurfaceView extends GLSurfaceView {

    private final GLRenderer glRenderer;
    private final GameLoop   gameLoop;

    public MainGLSurfaceView(Context context) {
        super(context);
        glRenderer = new GLRenderer();
        gameLoop = new GameLoop(glRenderer);

        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsES2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsES2) {
            setEGLContextClientVersion(2);

            setRenderer(glRenderer);
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

            gameLoop.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        gameLoop.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        gameLoop.onPause();
    }

    protected class GameLoop implements Runnable {

        private Thread           t;
        private final GLRenderer renderer;
        private boolean          isRunning;

        private Object           pauseLock;
        private boolean          isPaused;
        private long             pauseMillis     = 0;

        private long             startMillis     = SystemClock.elapsedRealtime();
        private long             nextGameTick    = startMillis;

        private final int        UPDATE_FPS      = 30;
        private final int        SKIP_TICKS      = 1000 / UPDATE_FPS;
        private final int        MAX_FRAMESKIP   = 5;

        private long             oldDrawMillis   = startMillis;
        private long             oldUpdateMillis = startMillis;

        public GameLoop(GLRenderer renderer) {
            this.renderer = renderer;
            pauseLock = new Object();
            isPaused = false;
            isRunning = true;
        }

        @Override
        public void run() {

            int loops;
            float interpolation;

            long updateMillis = startMillis;
            long drawMillis = startMillis;

            while (isRunning) {

                loops = 0;
                while ((updateMillis = SystemClock.elapsedRealtime()) > nextGameTick && loops < MAX_FRAMESKIP) {
                    nextGameTick += SKIP_TICKS;
                    renderer.update(new GameTime(new TimeSpan((long) (updateMillis - oldUpdateMillis)), new TimeSpan(
                            (long) (updateMillis - startMillis)), loops != 0));
                    loops++;
                    oldUpdateMillis = updateMillis;
                }

                drawMillis = SystemClock.elapsedRealtime();
                interpolation = (float) (drawMillis + SKIP_TICKS - nextGameTick) / (float) SKIP_TICKS;
                renderer.prepareDraw(new GameTime(new TimeSpan((long) (drawMillis - oldDrawMillis)), new TimeSpan(
                        (long) (drawMillis - startMillis)), loops != 0), interpolation);
                requestRender();
                oldDrawMillis = drawMillis;

                synchronized (pauseLock) {
                    while (isPaused) {
                        try {
                            pauseLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void start() {
            if (t == null) {
                t = new Thread(this, "GameLoop");
                t.start();
            }
        }

        public void stop() {
            isRunning = false;
        }

        public void onPause() {
            synchronized (pauseLock) {
                isPaused = true;
                pauseMillis = SystemClock.elapsedRealtime();
            }
        }

        public void onResume() {
            synchronized (pauseLock) {
                isPaused = false;

                if (pauseMillis != 0) {
                    long interval = SystemClock.elapsedRealtime() - pauseMillis;
                    startMillis += interval;
                    oldDrawMillis += interval;
                    oldUpdateMillis += interval;
                }
                pauseLock.notifyAll();
            }
        }
    }
}
