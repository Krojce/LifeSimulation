package terrain;

import model.RawModel;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import shader.TerrainShader;
import toolbox.Maths;

public class TerrainRenderer {

    private TerrainShader shader;

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Terrain terrain) {
        prepareTerrain(terrain);
        loadModelMatrix(terrain);
        GL11.glDrawElements(
                GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        unbindData();
    }

    public void prepareTerrain(Terrain terrain) {
        RawModel model = terrain.getModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
    }

    private void unbindData() {
        // clean up
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix =
                Maths.createTransformationMatrix(
                        new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }
}