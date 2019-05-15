package toolbox.collision;

import org.lwjgl.util.vector.Vector3f;

public class Collider {
    private final int radiusFactor = 5;
    private float radius;
    private Vector3f center;

    public Collider(float radius, Vector3f center) {
        this.radius = radius;
        this.center = center;
    }

    public void update(Vector3f entityPosition, float scale) {
        center = entityPosition;
        radius = scale * radiusFactor;
    }

    public float getRadius() {
        return radius;
    }

    public Vector3f getCenter() {
        return center;
    }
}
