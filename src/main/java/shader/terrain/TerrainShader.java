package shader.terrain;

import camera.Camera;
import lights.DirectionalLight;
import lights.Light;
import lights.PointLight;
import org.lwjgl.util.vector.Matrix4f;
import shader.ShaderProgram;
import toolbox.math.Maths;

import java.util.ArrayList;
import java.util.List;

public class TerrainShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/main/java/shader/terrain/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/shader/terrain/fragmentShader.txt";

    private static final int DIRECTIONAL = 2;
    private static final int POINT = 10;

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;

    //Directional
    private int[] directionalLightDirection;
    private int[] directionalLightAmbient;
    private int[] directionalLightDiffuse;
    private int[] directionalLightColor;

    //Directional
    private int[] pointLightPosition;
    private int[] pointLightAmbient;
    private int[] pointLightDiffuse;
    private int[] pointLightColor;
    private int[] pointLightAttenuation;

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");

        getDirectionalLightsUniformLocations();
        getPointLightsUniformLocations();
    }

    private void getDirectionalLightsUniformLocations() {
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

    private void getPointLightsUniformLocations() {
        pointLightPosition = new int[POINT];
        pointLightAmbient = new int[POINT];
        pointLightDiffuse = new int[POINT];
        pointLightColor = new int[POINT];
        pointLightAttenuation = new int[POINT];

        for (int i = 0; i < DIRECTIONAL; i++) {
            pointLightPosition[i] = super.getUniformLocation("pointLightPosition[" + i + "]");
            pointLightAmbient[i] = super.getUniformLocation("pointLightAmbient[" + i + "]");
            pointLightDiffuse[i] = super.getUniformLocation("pointLightDiffuse[" + i + "]");
            pointLightColor[i] = super.getUniformLocation("pointLightColor[" + i + "]");
            pointLightAttenuation[i] = super.getUniformLocation("pointLightAttenuation[" + i + "]");
        }
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "inPosition");
        super.bindAttribute(1, "inColor");
        super.bindAttribute(2, "inNormal");
    }

    public void loadDirectionalLights(List<Light> lights) {
        List<DirectionalLight> directionalLights = getDirectionalLights(lights);
        for (int i = 0; i < directionalLights.size(); i++) {
            super.loadVector3f(directionalLightDirection[i], directionalLights.get(i).getDirection());
            super.loadVector3f(directionalLightAmbient[i], directionalLights.get(i).getAmbient());
            super.loadVector3f(directionalLightDiffuse[i], directionalLights.get(i).getDiffuse());
            super.loadVector3f(directionalLightColor[i], directionalLights.get(i).getColor());
        }
    }

    public void loadPointLights(List<Light> lights) {
        List<PointLight> pointLights = getPointLights(lights);
        for (int i = 0; i < pointLights.size(); i++) {
            super.loadVector3f(pointLightPosition[i], pointLights.get(i).getPosition());
            super.loadVector3f(pointLightAmbient[i], pointLights.get(i).getAmbient());
            super.loadVector3f(pointLightDiffuse[i], pointLights.get(i).getDiffuse());
            super.loadVector3f(pointLightColor[i], pointLights.get(i).getColor());
            super.loadVector3f(pointLightAttenuation[i], pointLights.get(i).getAttenuation());
        }
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

    private List<PointLight> getPointLights(List<Light> lights) {
        List<PointLight> pointLights = new ArrayList<PointLight>();
        for (Light light : lights) {
            if (light.getClass() == PointLight.class) {
                pointLights.add((PointLight) light);
            }
        }
        return pointLights;
    }
}
