package shader.terrain;

import camera.Camera;
import camera.DirectionalLight;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shader.ShaderProgram;
import toolbox.math.Maths;

import static manager.RenderManager.SKY_COLOR;

public class TerrainShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/main/java/shader/terrain/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shader/terrain/fragmentShader.txt";

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;

    private int skyColor;

    private int lightDirection;
    private int lightAmbient;
    private int lightDiffuse;
    private int lightSpecular;
    private int lightColor;

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");

        skyColor = super.getUniformLocation("skyColor");

        lightDirection = super.getUniformLocation("lightDirection");
        lightAmbient = super.getUniformLocation("lightAmbient");
        lightDiffuse = super.getUniformLocation("lightDiffuse");
        lightSpecular = super.getUniformLocation("lightSpecular");
        lightColor = super.getUniformLocation("lightColor");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "inPosition");
        super.bindAttribute(1, "inColor");
        super.bindAttribute(2, "inNormal");
    }

    public void loadLight(DirectionalLight light) {
        super.loadVector3f(lightDirection, light.getDirection());
        super.loadVector3f(lightAmbient, light.getAmbient());
        super.loadVector3f(lightDiffuse, light.getDiffuse());
        super.loadVector3f(lightSpecular, light.getSpecular());
        super.loadVector3f(lightColor, light.getColor());
        loadSkyColor();
    }

    public void loadSkyColor() {
        super.loadVector3f(skyColor, new Vector3f(SKY_COLOR.x, SKY_COLOR.y, SKY_COLOR.z));
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(locationViewMatrix, Maths.createViewMatrix(camera));
    }
}
