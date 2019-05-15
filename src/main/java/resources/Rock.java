package resources;

import model.RawModel;
import org.lwjgl.util.vector.Vector3f;

public class Rock {

    private static final float SIZE = 1f;

    private static final float[] VERTICES = {
            //SIDE 1
            1.5f * SIZE, 1.5f * SIZE, 0, //I minus
            SIZE, 0, 2 * SIZE, //A minus
            2 * SIZE, 0, 2 * SIZE, //B minus

            SIZE, 0, 2 * SIZE, //A minus
            2 * SIZE, 0, 2 * SIZE, //B minus
            SIZE, 0, 6 * SIZE, //A plus

            2 * SIZE, 0, 2 * SIZE, //B minus
            SIZE, 0, 6 * SIZE, //A plus
            2 * SIZE, 0, 6 * SIZE, //B plus

            SIZE, 0, 6 * SIZE, //A plus
            2 * SIZE, 0, 6 * SIZE, //B plus
            1.5f * SIZE, 1.5f * SIZE, 8 * SIZE, //I plus

            //SIDE 2
            1.5f * SIZE, 1.5f * SIZE, 0, //I minus
            2 * SIZE, 0, 2 * SIZE, //B minus
            3 * SIZE, 1 * SIZE, 2 * SIZE, //C minus

            2 * SIZE, 0, 2 * SIZE, //B minus
            3 * SIZE, 1 * SIZE, 2 * SIZE, //C minus
            2 * SIZE, 0, 6 * SIZE, //B plus

            3 * SIZE, 1 * SIZE, 2 * SIZE, //C minus
            2 * SIZE, 0, 6 * SIZE, //B plus
            3 * SIZE, 1 * SIZE, 6 * SIZE, //C plus

            2 * SIZE, 0, 6 * SIZE, //B plus
            3 * SIZE, 1 * SIZE, 6 * SIZE, //C plus
            1.5f * SIZE, 1.5f * SIZE, 8 * SIZE, //I plus

            //SIDE 3
            1.5f * SIZE, 1.5f * SIZE, 0, //I minus
            3 * SIZE, 1 * SIZE, 2 * SIZE, //C minus
            3 * SIZE, 2 * SIZE, 2 * SIZE, //D minus

            3 * SIZE, 1 * SIZE, 2 * SIZE, //C minus
            3 * SIZE, 2 * SIZE, 2 * SIZE, //D minus
            3 * SIZE, 1 * SIZE, 6 * SIZE, //C plus

            3 * SIZE, 2 * SIZE, 2 * SIZE, //D minus
            3 * SIZE, 1 * SIZE, 6 * SIZE, //C plus
            3 * SIZE, 2 * SIZE, 6 * SIZE, //D plus

            3 * SIZE, 1 * SIZE, 6 * SIZE, //C plus
            3 * SIZE, 2 * SIZE, 6 * SIZE, //D plus
            1.5f * SIZE, 1.5f * SIZE, 8 * SIZE, //I plus

            //SIDE 4
            1.5f * SIZE, 1.5f * SIZE, 0, //I minus
            3 * SIZE, 2 * SIZE, 2 * SIZE, //D minus
            2 * SIZE, 3 * SIZE, 2 * SIZE, //E minus

            3 * SIZE, 2 * SIZE, 2 * SIZE, //D minus
            2 * SIZE, 3 * SIZE, 2 * SIZE, //E minus
            3 * SIZE, 2 * SIZE, 6 * SIZE, //D plus

            2 * SIZE, 3 * SIZE, 2 * SIZE, //E minus
            3 * SIZE, 2 * SIZE, 6 * SIZE, //D plus
            2 * SIZE, 3 * SIZE, 6 * SIZE, //E plus

            3 * SIZE, 2 * SIZE, 6 * SIZE, //D plus
            2 * SIZE, 3 * SIZE, 6 * SIZE, //E plus
            1.5f * SIZE, 1.5f * SIZE, 8 * SIZE, //I plus

            //SIDE 5
            1.5f * SIZE, 1.5f * SIZE, 0, //I minus
            2 * SIZE, 3 * SIZE, 2 * SIZE, //E minus
            1 * SIZE, 3 * SIZE, 2 * SIZE, //F minus

            2 * SIZE, 3 * SIZE, 2 * SIZE, //E minus
            1 * SIZE, 3 * SIZE, 2 * SIZE, //F plus
            2 * SIZE, 3 * SIZE, 6 * SIZE, //E plus

            1 * SIZE, 3 * SIZE, 2 * SIZE, //F minus
            2 * SIZE, 3 * SIZE, 6 * SIZE, //E plus
            1 * SIZE, 3 * SIZE, 6 * SIZE, //F plus

            2 * SIZE, 3 * SIZE, 6 * SIZE, //E plus
            1 * SIZE, 3 * SIZE, 6 * SIZE, //F plus
            1.5f * SIZE, 1.5f * SIZE, 8 * SIZE, //I plus

            //SIDE 6
            1.5f * SIZE, 1.5f * SIZE, 0, //I minus
            1 * SIZE, 3 * SIZE, 2 * SIZE, //F minus
            0 * SIZE, 2 * SIZE, 2 * SIZE, //G minus

            1 * SIZE, 3 * SIZE, 2 * SIZE, //F minus
            0 * SIZE, 2 * SIZE, 2 * SIZE, //G minus
            1 * SIZE, 3 * SIZE, 6 * SIZE, //F plus

            0 * SIZE, 2 * SIZE, 2 * SIZE, //G minus
            1 * SIZE, 3 * SIZE, 6 * SIZE, //F plus
            0 * SIZE, 2 * SIZE, 6 * SIZE, //G plus

            1 * SIZE, 3 * SIZE, 6 * SIZE, //F plus
            0 * SIZE, 2 * SIZE, 6 * SIZE, //G plus
            1.5f * SIZE, 1.5f * SIZE, 8 * SIZE, //I plus

            //SIDE 7
            1.5f * SIZE, 1.5f * SIZE, 0, //I minus
            0 * SIZE, 2 * SIZE, 2 * SIZE, //G minus
            0 * SIZE, 1 * SIZE, 2 * SIZE, //H minus

            0 * SIZE, 2 * SIZE, 2 * SIZE, //G minus
            0 * SIZE, 1 * SIZE, 2 * SIZE, //H minus
            0 * SIZE, 2 * SIZE, 6 * SIZE, //G plus

            0 * SIZE, 1 * SIZE, 2 * SIZE, //H minus
            0 * SIZE, 2 * SIZE, 6 * SIZE, //G plus
            0 * SIZE, 1 * SIZE, 6 * SIZE, //H plus

            0 * SIZE, 2 * SIZE, 6 * SIZE, //G plus
            0 * SIZE, 1 * SIZE, 6 * SIZE, //H plus
            1.5f * SIZE, 1.5f * SIZE, 8 * SIZE, //I plus

            //SIDE 8
            1.5f * SIZE, 1.5f * SIZE, 0, //I minus
            0 * SIZE, 1 * SIZE, 2 * SIZE, //H minus
            1 * SIZE, 0 * SIZE, 2 * SIZE, //A minus

            0 * SIZE, 1 * SIZE, 2 * SIZE, //H minus
            1 * SIZE, 0 * SIZE, 2 * SIZE, //A minus
            0 * SIZE, 1 * SIZE, 6 * SIZE, //H plus

            1 * SIZE, 0 * SIZE, 2 * SIZE, //A minus
            0 * SIZE, 1 * SIZE, 6 * SIZE, //H plus
            1 * SIZE, 0 * SIZE, 6 * SIZE, //A plus

            0 * SIZE, 1 * SIZE, 6 * SIZE, //H plus
            1 * SIZE, 0 * SIZE, 6 * SIZE, //A plus
            1.5f * SIZE, 1.5f * SIZE, 8 * SIZE, //I plus
    };

    private RawModel model;
    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    public Rock(Vector3f position, Vector3f rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public static float[] getVERTICES() {
        return VERTICES;
    }

    public RawModel getModel() {
        return model;
    }

    public void setModel(RawModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
