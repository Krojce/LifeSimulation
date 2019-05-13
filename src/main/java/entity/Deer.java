package entity;

import entity.movement.WanderMovementSystem;
import entity.template.BaseEntity;
import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Deer extends BaseEntity {

    private WanderMovementSystem wanderMovementSystem;

    public Deer(TexturedModel texturedModel, Vector3f position, Vector3f rotation, float scale) {
        super(texturedModel, position, rotation, scale);
        this.wanderMovementSystem = new WanderMovementSystem(1, 0.25f, 80, 10);
    }

    public void update() {
        position = wanderMovementSystem.move(position);
        rotation = wanderMovementSystem.rotate(rotation);
    }
}
