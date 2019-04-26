package terrain;

import camera.Camera;
import camera.Light;
import org.lwjgl.util.vector.Matrix4f;
import shader.ShaderProgram;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram {
  private static final String VERTEX_FILE = "src/main/java/shader/terrainVertexShader.txt";
  private static final String FRAGMENT_FILE = "src/main/java/shader/terrainFragmentShader.txt";

  private int locationTransformationMatrix;
  private int locationProjectionMatrix;
  private int locationViewMatrix;
  private int lightPosition;
  private int lightDirection;
  private int lightColor;
  private int lightBias;
  private int ambientLight;

  public TerrainShader() {
    super(VERTEX_FILE, FRAGMENT_FILE);
  }

  @Override
  protected void getAllUniformLocations() {
    locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
    locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
    locationViewMatrix = super.getUniformLocation("viewMatrix");

    lightPosition = super.getUniformLocation("lightPosition");
    lightDirection = super.getUniformLocation("lightDirection");
    lightColor = super.getUniformLocation("lightColor");
    lightBias = super.getUniformLocation("lightBias");
    ambientLight = super.getUniformLocation("ambientLight");
  }

  @Override
  protected void bindAttributes() {
    super.bindAttribute(0, "inPosition");
    super.bindAttribute(1, "inColor");
    super.bindAttribute(2, "inNormal");
  }

  public void loadLight(Light light) {
    super.loadVector3f(lightPosition, light.getPosition());
    super.loadVector3f(lightDirection, light.getDirection());
    super.loadVector3f(lightColor, light.getColor());
    super.loadVector2f(lightBias, light.getLightBias());
    super.loadVector3f(ambientLight, light.getAmbient());
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
