package entity;

import entity.movement.WanderMovementSystem;
import entity.template.BaseEntity;
import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Timer;

public class Deer extends BaseEntity {

    private WanderMovementSystem wanderMovementSystem;

    public Deer(TexturedModel texturedModel, Vector3f position, Vector3f rotation, float scale) {
        super(texturedModel, position, rotation, scale);
        this.wanderMovementSystem = new WanderMovementSystem(1, 0.25f, 80, 10);
    }

    public void update() {
        move();
    }

    private void move() {
        float currentTime = Timer.getCurrentTime();

        if (currentTime > 5 && currentTime < 21) {
            rotation = new Vector3f(0, 0, 0);
            position = wanderMovementSystem.move(position);
            rotation = wanderMovementSystem.rotate(rotation);
        } else {
            sleep();
        }
    }

    private void sleep() {
        rotation = new Vector3f(0, 0, 90);
    }
}
