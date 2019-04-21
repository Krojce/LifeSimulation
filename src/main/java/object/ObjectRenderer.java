package object;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import shader.ObjectShader;
import toolbox.Maths;

public class ObjectRenderer {
    private ObjectShader shader;

    public ObjectRenderer(ObjectShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    private void prepareInstance(Object object){
        Vector3f position = object.getPosition();
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(position, 0,
                object.getOrientationY(), 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }

    public void render(Object object) {
        prepareInstance(object);
        GL11.glDrawElements(
                GL11.GL_TRIANGLES, object.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        unbindData();
    }

    private void unbindData() {
        // clean up
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}
