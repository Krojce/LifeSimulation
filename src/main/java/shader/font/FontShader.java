package shader.font;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import shader.ShaderProgram;

public class FontShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/java/shader/font/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shader/font/fragmentShader.txt";

    private int color;
    private int translation;

    public FontShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        color = super.getUniformLocation("color");
        translation = super.getUniformLocation("translation");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    public void loadColor(Vector3f value) {
        super.loadVector3f(color, value);
    }

    public void loadTranslation(Vector2f value) {
        super.loadVector2f(translation, value);
    }


}
