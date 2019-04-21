package object;

import loader.Loader;
import model.RawModel;
import org.joml.Vector3f;

public class Object {
    private Vector3f position = new Vector3f(0, 10, 10);
    private float orientationY = 90;
    private float scale = 1;
    private RawModel model;

    public Object(Loader loader) {
        this.model = loader.loadToVAO(Cube.vertices, Cube.indices);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getOrientationY() {
        return orientationY;
    }

    public float getScale() {
        return scale;
    }

    public RawModel getModel() {
        return model;
    }

}
