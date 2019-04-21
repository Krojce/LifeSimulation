package input;

import camera.Camera;
import camera.Target;
import manager.Display;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private Mouse mouse;
    private Keyboard keyboard;

    private Camera camera;
    private Target target;

    public Input(Camera camera) {
        this.camera = camera;
        this.target = camera.getTarget();
        mouse = new Mouse();
        keyboard = new Keyboard();

        glfwSetScrollCallback(Display.getWindow(), (long win, double dx, double dy) -> {
            camera.calculateZoom((float) dy * 0.3f);
        });
    }

    public void handleInput() {
        if (keyboard.isKeyDown(GLFW_KEY_D)) {
            target.increaseSideSpeed();
        } else if (keyboard.isKeyDown(GLFW_KEY_A)) {
            target.decreaseSideSpeed();
        }else {
            target.zeroSideSpeed();
        }

        if (keyboard.isKeyDown(GLFW_KEY_W)) {
            target.increaseSpeed();
        } else if (keyboard.isKeyDown(GLFW_KEY_S)) {
            target.decreaseSpeed();
        }else {
            target.zeroSpeed();
        }

        if (keyboard.isKeyDown(GLFW_KEY_E)) {
            camera.decreaseRotation();
        } else if (keyboard.isKeyDown(GLFW_KEY_Q)) {
            camera.increaseRotation();
        }

        if(mouse.isMouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
            camera.increasePitch(mouse.getDY() * 0.1f);
        }


    }

    public  void checkInput() {
        keyboard.checkKeyPress();
        mouse.checkButtonPress();
    }
}
