package lights;

import org.lwjgl.util.vector.Vector3f;
import toolbox.Color;

public abstract class Light {

    protected Vector3f ambient;
    protected Vector3f diffuse;
    protected Color color;

    public Light(Vector3f ambient, Vector3f diffuse, Color color) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.color = color;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient = ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse = diffuse;
    }

    public Vector3f getColor() {
        return new Vector3f(color.getR(), color.getG(), color.getB());
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
