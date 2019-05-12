package gui;

import loader.Loader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import textures.GuiTexture;
import toolbox.input.MyMouse;

import java.util.ArrayList;
import java.util.List;

public class ButtonPanel {
    private final Loader loader;
    private final float padding = 0.01f;
    private MyMouse mouse;
    private List<Button> buttons = new ArrayList<Button>();
    private List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
    private float ratioY = (float) 1024 / Display.getHeight();
    private final float height = ratioY / 2;
    private float ratioX = (float) 1024 / Display.getWidth();
    private final float width = ratioX / 2;
    private String[] guiNames = {"deer-gui", "bear-gui", "boar-gui", "rabbit-gui", "tree-gui"};
    private String[] entityNames = {"deer", "bear", "boar", "rabbit", "tree"};
    private String activeEntity = "deer";

    public ButtonPanel(MyMouse mouse, Loader loader) {
        this.mouse = mouse;
        this.loader = loader;
        init();
    }

    private void init() {
        for (int i = 0; i < guiNames.length; i++) {
            float scaleX = width / guiNames.length;
            float scaleY = height / guiNames.length;
            Vector2f position = new Vector2f(1.3f * i * scaleY - 1 + scaleX + padding, scaleY + padding - 1);
            GuiTexture gui = new GuiTexture(loader.loadTexture(guiNames[i]), position, new Vector2f(scaleX, scaleY));
            Button button = new Button(gui, padding, mouse, entityNames[i]);
            buttons.add(button);
            guiTextures.add(gui);
        }
    }

    public void update() {
        for (Button button : buttons) {
            button.update();
            if (button.isOnButton()) {
                activeEntity = button.getName();
            }
        }
    }

    public String getActiveEntity() {
        return activeEntity;
    }

    public List<GuiTexture> getGuiTextures() {
        return guiTextures;
    }
}
