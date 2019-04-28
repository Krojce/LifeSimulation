package entity;

import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Bear extends BaseEntity {
    public Bear(TexturedModel texturedModel, Vector3f position, Vector3f rotation, float scale) {
        super(texturedModel, position, rotation, scale);
    }
}
