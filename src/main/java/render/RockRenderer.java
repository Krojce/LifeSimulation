package render;

import camera.Camera;
import loader.Loader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import resources.Rock;
import shader.rock.RockShader;
import toolbox.math.Maths;

public class RockRenderer {
    private RockShader shader;
    private Rock rock;

    public RockRenderer(RockShader shader, Loader loader, Rock rock) {
        this.rock = rock;
        this.shader = shader;
        rock.setModel(loader.loadToVAO(rock.getVERTICES(), 3));
    }

    public void render(Camera camera, Matrix4f projectionMatrix) {
        shader.start();
        shader.loadViewMatrix(camera);
        Vector3f rotation = rock.getRotation();
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(rock.getPosition(), rotation.x,
                rotation.y, rotation.z, rock.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadProjectionMatrix(projectionMatrix);
        GL30.glBindVertexArray(rock.getModel().getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, rock.getModel().getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }
}

