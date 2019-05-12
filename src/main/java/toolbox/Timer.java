package toolbox;

import manager.DisplayManager;

public class Timer {

    private static float currentTime;

    public static float getCurrentTime() {
        return currentTime;
    }

    public void update() {
        currentTime += DisplayManager.getFrameTimeSeconds();
        currentTime %= 24;
    }
}
