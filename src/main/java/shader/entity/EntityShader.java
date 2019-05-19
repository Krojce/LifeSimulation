package shader.entity;

import camera.Camera;
import lights.DirectionalLight;
import org.lwjgl.util.vector.Matrix4f;
import shader.ShaderProgram;
import toolbox.math.Maths;

public class EntityShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/java/shader/entity/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shader/entity/fragmentShader.txt";

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;

    //Directional
    private int directionalLightDirection;
    private int directionalLightAmbient;
    private int directionalLightDiffuse;
    private int directionalLightColor;

    public EntityShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");

        getDirectionalLightsUniformLocations();
    }

    private void getDirectionalLightsUniformLocations() {
        directionalLightDirection = super.getUniformLocation("directionalLightDirection");
        directionalLightAmbient = super.getUniformLocation("directionalLightAmbient");
        directionalLightDiffuse = super.getUniformLocation("directionalLightDiffuse");
        directionalLightColor = super.getUniformLocation("directionalLightColor");
    }

    public void loadDirectionalLight(DirectionalLight sun) {
        super.loadVector3f(directionalLightDirection, sun.getDirection());
        super.loadVector3f(directionalLightAmbient, sun.getAmbient());
        super.loadVector3f(directionalLightDiffuse, sun.getDiffuse());
        super.loadVector3f(directionalLightColor, sun.getColor());
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
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
