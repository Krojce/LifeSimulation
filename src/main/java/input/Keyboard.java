package input;

import manager.Display;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard {
    private boolean[] keys = new boolean[GLFW_KEY_LAST];

    public void checkKeyPress() {
        for (int i = 0; i < GLFW_KEY_LAST; i++) {
            keys[i] = isKeyDown(i);
        }
    }

    public boolean isKeyDown(int keyCode) {
        return GLFW.glfwGetKey(Display.getWindow(), keyCode) == 1;
    }

    public boolean isKeyPressed(int keyCode) {
        return isKeyDown(keyCode) && !keys[keyCode];
    }

    public boolean isKeyReleased(int keyCode) {
        return !isKeyDown(keyCode) && keys[keyCode];
    }
}
