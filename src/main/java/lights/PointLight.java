package lights;

import org.lwjgl.util.vector.Vector3f;
import toolbox.Color;

public class PointLight extends Light {

    private Vector3f position;
    private Vector3f attenuation;

    public PointLight(Vector3f ambient, Vector3f diffuse, Color color, Vector3f position, Vector3f attenuation) {
        super(ambient, diffuse, color);
        this.position = position;
        this.attenuation = attenuation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Vector3f attenuation) {
        this.attenuation = attenuation;
    }
}
