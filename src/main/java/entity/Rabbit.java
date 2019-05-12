package entity;

import entity.template.BaseEntity;
import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Rabbit extends BaseEntity {
    public Rabbit(TexturedModel texturedModel, Vector3f position, Vector3f rotation, float scale) {
        super(texturedModel, position, rotation, scale);
    }

    public void update() {

    }
}
