package render;

import model.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shader.terrain.TerrainShader;
import terrain.Terrain;
import toolbox.math.Maths;

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
        loadModelMatrix();
        GL11.glDrawElements(
                GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        unbindData();
    }

    private void prepareTerrain(Terrain terrain) {
        RawModel model = terrain.getModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
    }

    private void unbindData() {
        // clean up
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix() {
        Matrix4f transformationMatrix =
                Maths.createTransformationMatrix(
                        new Vector3f(0, 0, 0), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
