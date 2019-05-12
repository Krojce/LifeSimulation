package entity.movement;

import org.lwjgl.util.vector.Vector3f;
import toolbox.math.Maths;

import java.util.Random;

public class WanderMovementSystem {

    private Vector3f velocity = new Vector3f(0, 0, 0);
    private Vector3f acceleration = new Vector3f(0, 0, 0);

    private float maxSpeed;
    private float maxForce;

    private float wanderRingRadius;
    private float wanderRingDistance;

    private Random random = new Random();

    public WanderMovementSystem(float maxSpeed, float maxForce, float wanderRingRadius, float wanderRingDistance) {
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
        this.wanderRingRadius = wanderRingRadius;
        this.wanderRingDistance = wanderRingDistance;
    }

    public Vector3f seek(Vector3f position, Vector3f velocity) {
        Vector3f target = wandering(position, velocity);
        Vector3f desired = Maths.multiplyVectorByScale(Maths.normalizeVector(Maths.subtractTwoVectors(target, position)), maxSpeed);
        Vector3f steer = Maths.subtractTwoVectors(desired, velocity);
        if (steer.length() > maxForce) {
            steer.scale(maxForce);
        }
        return steer;
    }

    private Vector3f wandering(Vector3f position, Vector3f velocity) {
        Vector3f ringCenter = Maths.sumTwoVectors(position, Maths.multiplyVectorByScale(Maths.normalizeVector(velocity), wanderRingDistance));
        Vector3f ringVector = new Vector3f(wanderRingRadius, 0, 0);
        float angle = random.nextInt(360);
        return Maths.sumTwoVectors(ringCenter, Maths.rotateVector(ringVector, angle));
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getMaxForce() {
        return maxForce;
    }

    public void setMaxForce(float maxForce) {
        this.maxForce = maxForce;
    }

    public float getWanderRingRadius() {
        return wanderRingRadius;
    }

    public void setWanderRingRadius(float wanderRingRadius) {
        this.wanderRingRadius = wanderRingRadius;
    }

    public float getWanderRingDistance() {
        return wanderRingDistance;
    }

    public void setWanderRingDistance(float wanderRingDistance) {
        this.wanderRingDistance = wanderRingDistance;
    }
}
