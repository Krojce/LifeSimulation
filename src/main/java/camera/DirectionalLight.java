package camera;

import org.lwjgl.util.vector.Vector3f;
import toolbox.Color;
import toolbox.Timer;
import toolbox.math.Maths;

public class DirectionalLight {

    private Vector3f direction;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private Color color;

    public DirectionalLight(Vector3f direction, Vector3f ambient, Vector3f diffuse, Vector3f specular, Color color) {
        this.direction = direction;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.color = color;
    }

    public void update() {
        float currentTime = Timer.getCurrentTime();
        if (currentTime < 5) {
            ambient = Maths.changeVectorByValues(ambient, 0.001f);
            ambient = Maths.clampVectorToValue(ambient, 0, 0.01f);
        } else if (currentTime >= 5 && currentTime < 8) {
            ambient = Maths.changeVectorByValues(ambient, 0.002f);
            ambient = Maths.clampVectorToValue(ambient, 0, 0.1f);
        } else if (currentTime >= 8 && currentTime < 21) {
            ambient = Maths.changeVectorByValues(ambient, 0.001f);
            ambient = Maths.clampVectorToValue(ambient, 0, 0.4f);
        } else {
            ambient = Maths.changeVectorByValues(ambient, -0.003f);
            ambient = Maths.clampVectorToValue(ambient, 0, 0.4f);
        }
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public Vector3f getColor() {
        return new Vector3f(color.getR(), color.getG(), color.getB());
    }
}
