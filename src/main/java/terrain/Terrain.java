package terrain;

import loader.Loader;
import model.RawModel;

public class Terrain {
    private static final float SIZE = 800;
    private final int VERTEX_COUNT = 128;
    private float x;
    private float z;
    private RawModel model;

    public Terrain(Loader loader) {
        this.x = 0;
        this.z = 0;
        this.model = generateTerrain(loader);
    }

    public static float getSIZE() {
        return SIZE;
    }

    private RawModel generateTerrain(Loader loader) {

        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] colors = new float[count * 3];
        int[] indexes = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
                float height = 0;
                vertices[vertexPointer * 3 + 1] = height;
                vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
                vertexPointer++;
            }
        }

        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indexes[pointer++] = topLeft;
                indexes[pointer++] = bottomLeft;
                indexes[pointer++] = topRight;
                indexes[pointer++] = topRight;
                indexes[pointer++] = bottomLeft;
                indexes[pointer++] = bottomRight;
            }
        }
        return loader.loadTerrainToVAO(vertices, indexes, colors);
    }

    public RawModel getModel() {
        return model;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }
}