package shader.skybox;

import camera.Camera;
import manager.DisplayManager;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shader.ShaderProgram;
import toolbox.math.Maths;

public class SkyboxShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/java/shader/skybox/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shader/skybox/fragmentShader.txt";

    private static final float ROTATE_SPEED = 1f;

    private int projectionMatrix;
    private int viewMatrix;
    private int cubeMap1;
    private int cubeMap2;
    private int blendFactor;
    private float rotation = 0;

    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
        matrix.rotate((float) Math.toRadians(rotation), new Vector3f(0f, 1f, 0f));
        super.loadMatrix(viewMatrix, matrix);
    }

    public void connectTextureUnits() {
        super.loadInt(cubeMap1, 0);
        super.loadInt(cubeMap2, 1);
    }

    public void loadBlendFactor(float value) {
        super.loadFloat(blendFactor, value);
    }

    @Override
    protected void getAllUniformLocations() {
        projectionMatrix = super.getUniformLocation("projectionMatrix");
        viewMatrix = super.getUniformLocation("viewMatrix");
        cubeMap1 = super.getUniformLocation("cubeMap1");
        cubeMap2 = super.getUniformLocation("cubeMap2");
        blendFactor = super.getUniformLocation("blendFactor");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}