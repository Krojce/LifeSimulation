package camera;

import org.joml.Vector3f;

public class Camera extends Thread {

    private static final float TURN_SPEED = 0.5f;
    private static final float MAX_ZOOM = 400;
    private static final float MIN_ZOOM = 15;
    private static final float MAX_PITCH = 90;
    private static final float MIN_PITCH = 10;

    private float distanceFromPlayer = 50;

    private Vector3f position = new Vector3f(0, 10, 0);
    private float pitch = 30;
    private float yaw;
    private Target target;

    public Camera(Target target) {
        this.target = target;
    }

    public void update() {
        target.update();
        calculatePitch();
        calculateCameraPosition(calculatedHorizontalDistance(), calculatedVerticalDistance());
        this.yaw = 180 - target.getRotY();
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = target.getRotY();
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = target.getPosition().x - offsetX;
        position.z = target.getPosition().z - offsetZ;
        position.y = target.getPosition().y + verticalDistance;
    }

    private float calculatedHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculatedVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    public void calculateZoom(float change) {
        distanceFromPlayer -= change;
        if (distanceFromPlayer >= MAX_ZOOM) {
            distanceFromPlayer = MAX_ZOOM;
        } else if (distanceFromPlayer <= MIN_ZOOM) {
            distanceFromPlayer = MIN_ZOOM;
        }
    }

    private void calculatePitch() {
        if (pitch >= MAX_PITCH) {
            pitch = MAX_PITCH;
        } else if (pitch <= MIN_PITCH) {
            pitch = MIN_PITCH;
        }
    }

    public Target getTarget() {
        return target;
    }

    public void increaseRotation() {
        target.increaseRotation(-TURN_SPEED);
    }

    public void decreaseRotation() {
        target.increaseRotation(TURN_SPEED);
    }

    public void increasePitch(float change) {
        pitch += change;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
}