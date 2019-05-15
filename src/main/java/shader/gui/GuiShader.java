package shader.gui;

import org.lwjgl.util.vector.Matrix4f;
import shader.ShaderProgram;

public class GuiShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/java/shader/gui/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shader/gui/fragmentShader.txt";

    private int transformationMatrix;
    private int offset;

    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix) {
        super.loadMatrix(transformationMatrix, matrix);
    }

    public void loadOffset(float value) {
        super.loadFloat(offset, value);
    }

    @Override
    protected void getAllUniformLocations() {
        transformationMatrix = super.getUniformLocation("transformationMatrix");
        offset = super.getUniformLocation("offset");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}