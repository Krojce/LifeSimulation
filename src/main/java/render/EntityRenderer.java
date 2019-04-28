package render;

import componentArchitecture.EntityManager;
import components.TransformationComponent;
import model.RawModel;
import model.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shader.entity.EntityShader;
import toolbox.Maths;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EntityRenderer {
    private EntityShader shader;
    private EntityManager entityManager;

    public EntityRenderer(EntityShader shader, Matrix4f projectionMatrix, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<UUID>> entities) {
        for (TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);
            List<UUID> batch = entities.get(model);
            for (UUID entity : batch) {
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

    private void prepareInstance(UUID entity) {
        TransformationComponent transComp = entityManager.getComponent(entity, TransformationComponent.class);
        Vector3f position = new Vector3f(transComp.getPosition().x, transComp.getPosition().y, transComp.getPosition().z);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(position, 0,
                transComp.getOrientationY(), 0, transComp.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
