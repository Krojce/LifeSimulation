package manager;

import camera.Camera;
import object.Object;
import object.ObjectRenderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import shader.ObjectShader;
import shader.TerrainShader;
import terrain.Terrain;
import terrain.TerrainRenderer;

public class RenderManager {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();
    private Terrain terrain;

    private ObjectRenderer objectRenderer;
    private ObjectShader objectShader = new ObjectShader();
    private Object object;

    public RenderManager() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        objectRenderer = new ObjectRenderer(objectShader, projectionMatrix);
    }

    public void render(Camera camera) {
        prepare();
        terrainShader.start();
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrain);
        terrainShader.stop();

        objectShader.start();
        objectShader.loadViewMatrix(camera);
        objectRenderer.render(object);
        objectShader.stop();
    }

    public void processTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public void processObject(Object object){
        this.object = object;
    }

    private void prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL32.glProvokingVertex(GL32.GL_FIRST_VERTEX_CONVENTION);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWIDTH() / (float) Display.getHEIGHT();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        projectionMatrix._m00(x_scale);
        projectionMatrix._m11(y_scale);
        projectionMatrix._m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix._m23(-1);
        projectionMatrix._m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix._m33(0);
    }

    public void cleanUp() {
        terrainShader.cleanUp();
    }

}
