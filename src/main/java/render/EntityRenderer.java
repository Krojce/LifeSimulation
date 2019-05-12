package render;

import entity.template.BaseEntity;
import model.RawModel;
import model.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shader.entity.EntityShader;
import toolbox.math.Maths;

import java.util.List;
import java.util.Map;

public class EntityRenderer {
    private EntityShader shader;

    public EntityRenderer(EntityShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<BaseEntity>> entities) {
        for (TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);
            List<BaseEntity> batch = entities.get(model);
            for (BaseEntity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }

    }

    public void prepareTexturedModel(TexturedModel texturedModel) {
        RawModel model = texturedModel.getRawModel();

        GL30.glBindVertexArray(model.getVaoID());
        //get the data from the VBOs
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
    }

    private void unbindTexturedModel() {
        //clean up
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(BaseEntity entity) {
        Vector3f rotation = entity.getRotation();
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), rotation.x,
                rotation.y, rotation.z, entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
