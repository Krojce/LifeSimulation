package camera;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Color;

public class Light {

    private Vector3f position;
    private Vector3f direction;
    private Vector3f ambient = new Vector3f(0.1f, 0.1f, 0.1f);
    private Color color;
    private Vector2f lightBias;

    public Light(Vector3f position, Vector3f direction, Color color, Vector2f lightBias) {
        this.position = position;
        this.position.normalise();
        this.direction = direction;
        this.direction.normalise();
        this.color = color;
        this.lightBias = lightBias;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getColor() {
        return new Vector3f(color.getR(), color.getG(), color.getB());
    }

    public Vector2f getLightBias() {
        return lightBias;
    }

    public Vector3f getAmbient() {
        return ambient;
    }
}
