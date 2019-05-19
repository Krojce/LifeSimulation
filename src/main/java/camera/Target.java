package camera;

import manager.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Target {

    private static final float SPEED = 80;

    private Vector3f position;
    private float rotY;
    private boolean isMovable = true;
    private boolean isSpotlightOn = true;

    private Vector3f CAMERA_ONE = new Vector3f(400, 50, 400);
    private Vector3f CAMERA_TWO = new Vector3f(200, 50, 200);

    public Target(Vector3f initialPosition) {
        this.position = initialPosition;
        this.rotY = 0;
    }

    public void moveTarget() {

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_S) {
                isMovable = !isMovable;
            }
            if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_L) {
                isSpotlightOn = !isSpotlightOn;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            position = CAMERA_ONE;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
            position = CAMERA_TWO;
        }

        if (isMovable) {
            float speed = 0;
            float sideSpeed = 0;
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                sideSpeed = -SPEED;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                sideSpeed = SPEED;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                speed = SPEED;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                speed = -SPEED;
            }

            Vector2f change = calculatePositionChange();

            if (change.x != 0 || change.y != 0) {
                speed = change.y * SPEED;
                sideSpeed = change.x * SPEED;
            }

            float distance = speed * DisplayManager.getFrameTimeSeconds();
            float sideDistance = sideSpeed * DisplayManager.getFrameTimeSeconds();


            float dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
            float dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));

            float sideDx = (float) (sideDistance * Math.sin(Math.toRadians(getRotY() + 90)));
            float sideDz = (float) (sideDistance * Math.cos(Math.toRadians(getRotY() + 90)));
            increasePosition(dx + sideDx, 0, dz + sideDz);
        }
    }

    public float calculateRotationChange() {
        float rotationChange = 0;
        if (Mouse.isButtonDown(1)) {
            rotationChange -= Mouse.getDX() * 0.1f;
        }
        return rotationChange;
    }

    private Vector2f calculatePositionChange() {
        Vector2f positionChange = new Vector2f(0, 0);
        if (Mouse.isButtonDown(0)) {
            positionChange.x += Mouse.getDX() * 0.1f;
            positionChange.y -= Mouse.getDY() * 0.1f;
        }
        return positionChange;
    }

    private void increasePosition(float x, float y, float z) {
        this.position.x += x;
        this.position.y += y;
        this.position.z += z;
    }

    public void increaseRotation(float ry) {
        this.rotY += ry;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotY() {
        return rotY;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }

    public boolean isSpotlightOn() {
        return isSpotlightOn;
    }

    public void setSpotlightOn(boolean spotlightOn) {
        isSpotlightOn = spotlightOn;
    }
}
