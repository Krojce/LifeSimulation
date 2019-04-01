package terrain;

import camera.Camera;
import org.lwjgl.util.vector.Matrix4f;
import shader.ShaderProgram;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram {
  private static final String VERTEX_FILE = "src/main/java/shader/terrainVertexShader.txt";
  private static final String FRAGMENT_FILE = "src/main/java/shader/terrainFragmentShader.txt";

  private int locationTransformationMatrix;
  private int locationProjectionMatrix;
  private int locationViewMatrix;

  public TerrainShader() {
    super(VERTEX_FILE, FRAGMENT_FILE);
  }

  @Override
  protected void getAllUniformLocations() {
    locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
    locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
    locationViewMatrix = super.getUniformLocation("viewMatrix");
  }

  @Override
  protected void bindAttributes() {
    super.bindAttribute(0, "inPosition");
    super.bindAttribute(1, "inColor");
    super.bindAttribute(2, "inNormal");
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
