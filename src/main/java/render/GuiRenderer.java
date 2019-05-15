package render;

import gui.ButtonPanel;
import loader.Loader;
import model.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import shader.gui.GuiShader;
import textures.GuiTexture;
import toolbox.Timer;
import toolbox.math.Maths;

import java.util.List;

public class GuiRenderer {
    private final RawModel quad;
    private GuiShader guiShader;

    public GuiRenderer(Loader loader) {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        this.quad = loader.loadToVAO(positions, 2);
        guiShader = new GuiShader();
    }

    public void render(ButtonPanel panel) {
        List<GuiTexture> textures = panel.getGuiTextures();
        guiShader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        for (GuiTexture texture : textures) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTexture());
            Matrix4f matrix = Maths.createTransformationMatrix(texture.getPosition(), texture.getScale());
            guiShader.loadTransformation(matrix);
            guiShader.loadOffset(Timer.getCurrentTime());
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        guiShader.stop();
    }

    public void cleanUp() {
        guiShader.cleanUp();
    }
}