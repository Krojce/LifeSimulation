package entity;

import entity.movement.WanderMovementSystem;
import entity.template.BaseEntity;
import model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import terrain.Terrain;
import toolbox.math.Maths;

public class Deer extends BaseEntity {

    private WanderMovementSystem wanderMovementSystem;

    public Deer(TexturedModel texturedModel, Vector3f position, Vector3f rotation, float scale) {
        super(texturedModel, position, rotation, scale);
        this.wanderMovementSystem = new WanderMovementSystem(10, 0.25f, 80, 10);
    }

    public void update() {
        System.out.println(wanderMovementSystem.getVelocity().toString());
        move();
    }

    private void move() {
        wanderMovementSystem.setAcceleration(wanderMovementSystem.seek(getPosition(), wanderMovementSystem.getAcceleration()));

        wanderMovementSystem.setVelocity(Maths.sumTwoVectors(wanderMovementSystem.getAcceleration(), wanderMovementSystem.getVelocity()));

        if (wanderMovementSystem.getVelocity().length() > wanderMovementSystem.getMaxSpeed()) {
            wanderMovementSystem.setVelocity(Maths.clampVectorToValue(wanderMovementSystem.getVelocity(), 0, wanderMovementSystem.getMaxSpeed()));
        }

        setPosition(Maths.sumTwoVectors(getPosition(), wanderMovementSystem.getVelocity()));


        if (getPosition().x > Terrain.getSIZE()) {
            setPosition(new Vector3f(0, getPosition().y, getPosition().z));
        }
        if (getPosition().x < 0) {
            setPosition(new Vector3f(Terrain.getSIZE(), getPosition().y, getPosition().z));
        }
        if (getPosition().z > Terrain.getSIZE()) {
            setPosition(new Vector3f(getPosition().x, getPosition().y, Terrain.getSIZE()));
        }
        if (getPosition().z < 0) {
            setPosition(new Vector3f(getPosition().x, getPosition().y, 0));
        }

        setPosition(new Vector3f(getPosition().x, Terrain.getHeight(getPosition().x, getPosition().z), getPosition().z));

        float angle = (float) Math.toDegrees(Vector3f.angle(
                new Vector3f(0, 0, 800),
                wanderMovementSystem.getVelocity()
        ));
        if (wanderMovementSystem.getVelocity().x < 0) {
            angle = -angle;
        }

        setRotation(new Vector3f(getRotation().x, angle, getRotation().z));
    }
}
