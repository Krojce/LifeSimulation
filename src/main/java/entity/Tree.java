package entity;

import entity.template.BaseEntity;
import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import toolbox.picking.EntityPicker;

public class Tree extends BaseEntity {
    public Tree(TexturedModel texturedModel, Vector3f position, Vector3f rotation, float scale, EntityPicker picker) {
        super(texturedModel, position, rotation, scale, picker);
    }

    public void update() {
        collider.update(position, scale);
    }

    public void handlePicking(Vector3f position) {
        if (position != null) {
            boolean isPicked = picker.checkIfPicked(collider, position);
            if (isPicked) {
                scale *= 1.1f;
            }
        }
    }
}
