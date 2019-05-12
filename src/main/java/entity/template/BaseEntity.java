package entity.template;

import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import terrain.Terrain;

public abstract class BaseEntity implements EntityUpdate {
    protected TexturedModel texturedModel;
    protected Vector3f position;
    protected Vector3f rotation;
    protected float scale;

    public BaseEntity(TexturedModel texturedModel, Vector3f position, Vector3f rotation, float scale) {
        this.texturedModel = texturedModel;
        this.position = new Vector3f(position.x, Terrain.getHeight(position.x, position.z), position.z);
        this.rotation = rotation;
        this.scale = scale;
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setTexturedModel(TexturedModel texturedModel) {
        this.texturedModel = texturedModel;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
