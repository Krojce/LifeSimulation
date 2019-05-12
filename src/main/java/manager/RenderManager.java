package manager;

import camera.Camera;
import camera.DirectionalLight;
import entity.template.BaseEntity;
import loader.Loader;
import model.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import render.EntityRenderer;
import render.SkyboxRenderer;
import render.TerrainRenderer;
import shader.entity.EntityShader;
import shader.terrain.TerrainShader;
import terrain.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderManager {

    public static final Vector4f SKY_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 0f);
    private static final float FOV = 100;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 10000f;
    private Matrix4f projectionMatrix;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();
    private Terrain terrain;

    private EntityShader entityShader = new EntityShader();
    private EntityRenderer entityRenderer;

    private SkyboxRenderer skyboxRenderer;

    private Map<TexturedModel, List<BaseEntity>> entities = new HashMap<TexturedModel, List<BaseEntity>>();

    public RenderManager(Loader loader) {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
        skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
    }

    public void render(Camera camera, DirectionalLight light) {
        prepare();
        renderTerrain(camera, light);
        renderEntities(camera, light);
        renderSkybox(camera);
    }

    private void renderSkybox(Camera camera) {
        skyboxRenderer.render(camera);
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

    public void processEntity(BaseEntity entity) {
        TexturedModel entityModel = entity.getTexturedModel();
        List<BaseEntity> batch = entities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<BaseEntity> newBatch = new ArrayList<BaseEntity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
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

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
