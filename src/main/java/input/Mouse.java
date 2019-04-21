package input;

import manager.Display;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private boolean[] mouseButtons = new boolean[GLFW_MOUSE_BUTTON_LAST];

    private double lastX = 0;
    private double lastY = 0;

    public Mouse() {
        init();
    }

    private void init() {
        lastX = getMouseX();
        lastY = getMouseY();
    }

    public void checkButtonPress() {
        for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++) {
            mouseButtons[i] = isMouseButtonDown(i);
        }
    }

    public boolean isMouseButtonDown(int mouseButton) {
        return GLFW.glfwGetMouseButton(Display.getWindow(), mouseButton) == 1;
    }

    public boolean isMouseButtonPressed(int mouseButton) {
        return isMouseButtonDown(mouseButton) && !mouseButtons[mouseButton];
    }

    public boolean isMouseButtonReleased(int mouseButton) {
        return !isMouseButtonDown(mouseButton) && mouseButtons[mouseButton];
    }

    public double getMouseX() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(Display.getWindow(), buffer, null);
        return buffer.get(0);
    }

    public double getMouseY() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(Display.getWindow(), null, buffer);
        return buffer.get(0);
    }

    public float getDY() {
        //System.out.println("last: " + lastY + " get current:" + getMouseY());
        double DY = getMouseY() - lastY;
        lastY = getMouseY();
        return (float) DY;
    }

    public float getDX() {
        //System.out.println("last: " + lastY + " get current:" + getMouseY());
        double DX = getMouseX() - lastX;
        lastX = getMouseX();
        return (float) DX;
    }
}
