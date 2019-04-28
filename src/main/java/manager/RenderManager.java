package manager;

import camera.Camera;
import camera.DirectionalLight;
import componentArchitecture.EntityManager;
import model.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import render.EntityRenderer;
import render.TerrainRenderer;
import shader.entity.EntityShader;
import shader.terrain.TerrainShader;
import terrain.Terrain;

import java.util.*;

public class RenderManager {

  private static final float FOV = 70;
  private static final float NEAR_PLANE = 0.1f;
  public static final Vector4f SKY_COLOR = new Vector4f(0.4f, 0.6f, 0.9f, 0f);
  private static final float FAR_PLANE = 3000f;

  private Matrix4f projectionMatrix;

  private TerrainRenderer terrainRenderer;
  private TerrainShader terrainShader = new TerrainShader();
  private Terrain terrain;

  private EntityManager entityManager;
    private EntityShader entityShader = new EntityShader();
  private EntityRenderer entityRenderer;
  private Map<TexturedModel, List<UUID>> entities = new HashMap<TexturedModel, List<UUID>>();

  public RenderManager() {
    GL11.glEnable(GL11.GL_CULL_FACE);
    GL11.glCullFace(GL11.GL_BACK);
    createProjectionMatrix();
    terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    entityRenderer = new EntityRenderer(entityShader, projectionMatrix, entityManager);
  }

  public void render(Camera camera, DirectionalLight light) {
    prepare();
    renderTerrain(camera, light);
  }

  private void renderTerrain(Camera camera, DirectionalLight light) {
    terrainShader.start();
    terrainShader.loadViewMatrix(camera);
    terrainShader.loadLight(light);
    terrainRenderer.render(terrain);
    terrainShader.stop();
  }

  private void renderEntities(Camera camera, DirectionalLight light) {
    entityShader.start();
    entityShader.loadLight(light);
    entityShader.loadViewMatrix(camera);
    entityRenderer.render(entities);
    entityShader.stop();
  }

  public void processTerrain(Terrain terrain) {
    this.terrain = terrain;
  }

  public void proccessEntity(UUID entity, TexturedModel texturedModel) {
    List<UUID> batch = entities.get(texturedModel);
    if (batch != null) {
      batch.add(entity);
    } else {
      List<UUID> newBatch = new ArrayList<UUID>();
      newBatch.add(entity);
      entities.put(texturedModel, newBatch);
    }
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
    GL11.glClearColor(SKY_COLOR.x, SKY_COLOR.y, SKY_COLOR.z, SKY_COLOR.w);
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
    entityShader.cleanUp();
    terrainShader.cleanUp();
  }

}
