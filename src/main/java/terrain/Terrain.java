package terrain;

import generator.ColorGenerator;
import generator.HeightGenerator;
import loader.Loader;
import model.RawModel;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Color;

public class Terrain {
  private static final float SIZE = 4000;
  private final int VERTEX_COUNT = 256;
  private RawModel model;

  public Terrain(Loader loader) {
    this.model = generateTerrain(loader);
  }

  public static float getSIZE() {
    return SIZE;
  }

  private Vector3f calculateNormal(int x, int z) {
    float heightL = getHeight(x - 1, z);
    float heightR = getHeight(x + 1, z);
    float heightD = getHeight(x, z - 1);
    float heightU = getHeight(x, z + 1);
    Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
    normal.normalise();
    return normal;
  }

  private float getHeight(int x, int z) {
    return HeightGenerator.generateHeight(x, z);
  }

  public RawModel getModel() {
    return model;
  }

  private RawModel generateTerrain(Loader loader) {

    int count = VERTEX_COUNT * VERTEX_COUNT;
    float[][] heights = new float[VERTEX_COUNT][VERTEX_COUNT];
    float[] vertices = new float[count * 3];
    float[] normals = new float[count * 3];
    float[] colors = new float[count * 3];
    int[] indexes = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
    int vertexPointer = 0;
    for (int i = 0; i < VERTEX_COUNT; i++) {
      for (int j = 0; j < VERTEX_COUNT; j++) {
        vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
        float height = getHeight(j,i);
        vertices[vertexPointer * 3 + 1] = height;
        heights[j][i] = height;
        vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
        Vector3f normal = calculateNormal(j, i);
        normals[vertexPointer * 3] = normal.x;
        normals[vertexPointer * 3 + 1] = normal.y;
        normals[vertexPointer * 3 + 2] = normal.z;
        vertexPointer++;
      }
    }
    ColorGenerator colorGenerator = new ColorGenerator(0.45f);
    Color[][] generatedColors =
        colorGenerator.generateColors(heights, HeightGenerator.getAMPLITUDE());

    int colorPointer = 0;
    for (int i = 0; i < VERTEX_COUNT; i++) {
      for (int j = 0; j < VERTEX_COUNT; j++) {
        colors[colorPointer * 3] = generatedColors[j][i].getR();
        colors[colorPointer * 3 + 1] = generatedColors[j][i].getG();
        colors[colorPointer * 3 + 2] = generatedColors[j][i].getB();
        colorPointer++;
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
    return loader.loadTerrainToVAO(vertices, indexes, colors, normals);
  }
}
