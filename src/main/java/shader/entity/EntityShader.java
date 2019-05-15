package shader.entity;

import camera.Camera;
import lights.DirectionalLight;
import lights.Light;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shader.ShaderProgram;
import toolbox.math.Maths;

import java.util.ArrayList;
import java.util.List;

import static manager.RenderManager.SKY_COLOR;

public class EntityShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/main/java/shader/entity/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shader/entity/fragmentShader.txt";

    private static final int DIRECTIONAL = 2;

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;

    private int skyColor;

    //Directional
    private int[] directionalLightDirection;
    private int[] directionalLightAmbient;
    private int[] directionalLightDiffuse;
    private int[] directionalLightColor;

    public EntityShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");

        skyColor = super.getUniformLocation("skyColor");

        directionalLightDirection = new int[DIRECTIONAL];
        directionalLightAmbient = new int[DIRECTIONAL];
        directionalLightDiffuse = new int[DIRECTIONAL];
        directionalLightColor = new int[DIRECTIONAL];

        for (int i = 0; i < DIRECTIONAL; i++) {
            directionalLightDirection[i] = super.getUniformLocation("directionalLightDirection[" + i + "]");
            directionalLightAmbient[i] = super.getUniformLocation("directionalLightAmbient[" + i + "]");
            directionalLightDiffuse[i] = super.getUniformLocation("directionalLightDiffuse[" + i + "]");
            directionalLightColor[i] = super.getUniformLocation("directionalLightColor[" + i + "]");
        }
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    public void loadDirectionalLight(List<Light> lights) {
        List<DirectionalLight> directionalLights = getDirectionalLights(lights);
        for (int i = 0; i < directionalLights.size(); i++) {
            super.loadVector3f(directionalLightDirection[i], directionalLights.get(i).getDirection());
            super.loadVector3f(directionalLightAmbient[i], directionalLights.get(i).getAmbient());
            super.loadVector3f(directionalLightDiffuse[i], directionalLights.get(i).getDiffuse());
            super.loadVector3f(directionalLightColor[i], directionalLights.get(i).getColor());
        }
    }

    private void loadSkyColor() {
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

    private List<DirectionalLight> getDirectionalLights(List<Light> lights) {
        List<DirectionalLight> directionalLights = new ArrayList<DirectionalLight>();
        for (Light light : lights) {
            if (light.getClass() == DirectionalLight.class) {
                directionalLights.add((DirectionalLight) light);
            }
        }
        return directionalLights;
    }
}
