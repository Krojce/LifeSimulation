package shader.rock;

import camera.Camera;
import org.lwjgl.util.vector.Matrix4f;
import shader.ShaderProgram;
import toolbox.math.Maths;

public class RockShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/java/shader/rock/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shader/rock/fragmentShader.txt";

    private int projectionMatrix;
    private int viewMatrix;
    private int transformationMatrix;

    public RockShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f matrix = Maths.createViewMatrix(camera);
        super.loadMatrix(viewMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        projectionMatrix = super.getUniformLocation("projectionMatrix");
        viewMatrix = super.getUniformLocation("viewMatrix");
        transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(transformationMatrix, matrix);
    }

}