package lights;

import org.lwjgl.util.vector.Vector3f;
import toolbox.Color;
import toolbox.Timer;
import toolbox.math.Maths;

public class DirectionalLight extends Light {

    private Vector3f direction;

    public DirectionalLight(Vector3f ambient, Vector3f diffuse, Color color, Vector3f direction) {
        super(ambient, diffuse, color);
        this.direction = direction;
    }

    public void update() {
        float currentTime = Timer.getCurrentTime();
        if (currentTime < 5) {
            diffuse = Maths.changeVectorByValues(diffuse, 0.001f);
            diffuse = Maths.clampVectorToValue(diffuse, 0, 0.001f);
        } else if (currentTime >= 5 && currentTime < 8) {
            diffuse = Maths.changeVectorByValues(diffuse, 0.002f);
            diffuse = Maths.clampVectorToValue(diffuse, 0, 0.01f);
        } else if (currentTime >= 8 && currentTime < 21) {
            diffuse = Maths.changeVectorByValues(diffuse, 0.001f);
            diffuse = Maths.clampVectorToValue(diffuse, 0, 0.4f);
        } else {
            diffuse = Maths.changeVectorByValues(diffuse, -0.003f);
            diffuse = Maths.clampVectorToValue(diffuse, 0, 0.4f);
        }
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
}
