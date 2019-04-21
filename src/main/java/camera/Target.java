package camera;

import org.joml.Vector3f;

public class Target {

    private static final float SPEED = 1f;

    private Vector3f position;
    private float rotY;
    private float speed = 0;
    private float sideSpeed = 0;

    public Target() {
        this.position = new Vector3f(400, 50, 400);
        this.rotY = 0;
    }

    public void update() {
        float distance = speed;
        float sideDistance = sideSpeed;
        float dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));
        float sideDx = (float) (sideDistance * Math.sin(Math.toRadians(getRotY() + 90)));
        float sideDz = (float) (sideDistance * Math.cos(Math.toRadians(getRotY() + 90)));
        increasePosition(dx + sideDx, 0, dz + sideDz);
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

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotY() {
        return rotY;
    }

    public void increaseSideSpeed() {
        sideSpeed = +SPEED;
    }

    public void decreaseSideSpeed() {
        sideSpeed = -SPEED;
    }

    public void zeroSideSpeed() {
        sideSpeed = 0;
    }

    public void increaseSpeed() {
        speed = SPEED;
    }

    public void decreaseSpeed() {
        speed = -SPEED;
    }

    public void zeroSpeed() {
        speed = 0;
    }
}