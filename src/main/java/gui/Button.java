package gui;

import org.lwjgl.util.vector.Vector2f;
import textures.GuiTexture;
import toolbox.input.MyMouse;

public class Button {

    private GuiTexture guiTexture;
    private MyMouse mouse;
    private String name;
    private boolean bigger, normal = false;
    private Vector2f normalSize, biggerSize;

    public Button(GuiTexture guiTexture, float upScale, MyMouse mouse, String name) {
        this.guiTexture = guiTexture;
        this.mouse = mouse;
        this.name = name;
        this.biggerSize = new Vector2f(guiTexture.getScale().x + upScale, guiTexture.getScale().y + upScale);
        this.normalSize = new Vector2f(guiTexture.getScale().x, guiTexture.getScale().y);
    }

    public void update() {
        if (bigger) {
            guiTexture.setScale(biggerSize);
        } else if (normal) {
            guiTexture.setScale(normalSize);
        }
    }

    public boolean isOnButton() {
        boolean isInX = false;
        boolean isInY = false;
        float mousePosX = (mouse.getX() * 2) - 1;
        float mousePosY = -((mouse.getY() * 2) - 1);
        if (mousePosX >= guiTexture.getPosition().x - (guiTexture.getScale().x) &&
                mousePosX <= guiTexture.getPosition().x + (guiTexture.getScale().x)) {
            isInX = true;
        }
        if (mousePosY >= guiTexture.getPosition().y - (guiTexture.getScale().y) &&
                mousePosY <= guiTexture.getPosition().y + (guiTexture.getScale().y)) {
            isInY = true;
        }
        if (isInX && isInY) {
            bigger = true;
            normal = false;
            return true;
        } else {
            normal = true;
            bigger = false;
            return false;
        }
    }

    public String getName() {
        return name;
    }
}
