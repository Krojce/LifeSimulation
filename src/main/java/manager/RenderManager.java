package manager;

import camera.Camera;
import camera.Light;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;
import terrain.Terrain;
import terrain.TerrainRenderer;
import terrain.TerrainShader;

public class RenderManager {

  private static final float FOV = 70;
  private static final float NEAR_PLANE = 0.1f;
  private static final float FAR_PLANE = 10000;

  private Matrix4f projectionMatrix;

  private TerrainRenderer terrainRenderer;
  private TerrainShader terrainShader = new TerrainShader();
  private Terrain terrain;

  public RenderManager() {
    GL11.glEnable(GL11.GL_CULL_FACE);
    GL11.glCullFace(GL11.GL_BACK);
    createProjectionMatrix();
    terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
  }

  public void render(Camera camera, Light light) {
    prepare();
    terrainShader.start();
    terrainShader.loadViewMatrix(camera);
    terrainShader.loadLight(light);
    terrainRenderer.render(terrain);
    terrainShader.stop();
  }

  public void processTerrain(Terrain terrain) {
    this.terrain = terrain;
  }

  private void prepare() {
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    GL32.glProvokingVertex(GL32.GL_FIRST_VERTEX_CONVENTION);
    if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
      GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    } else {
      GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
    }
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
  }

  private void createProjectionMatrix() {
    float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
    float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
    float x_scale = y_scale / aspectRatio;
    float frustum_length = FAR_PLANE - NEAR_PLANE;

    projectionMatrix = new Matrix4f();
    projectionMatrix.m00 = x_scale;
    projectionMatrix.m11 = y_scale;
    projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
    projectionMatrix.m23 = -1;
    projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
    projectionMatrix.m33 = 0;
  }

  public void cleanUp() {
    terrainShader.cleanUp();
  }

}
