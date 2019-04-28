package components;

import componentArchitecture.Component;

public class ReproductionComponent implements Component {

    private int numberOfOffsprings;
    private int reproductionRate;
    private long lastReproductionTick;
    private float reproductionRadius;

    public int getNumberOfOffsprings() {
        return numberOfOffsprings;
    }

    public void setNumberOfOffsprings(int numberOfOffsprings) {
        this.numberOfOffsprings = numberOfOffsprings;
    }

    public int getReproductionRate() {
        return reproductionRate;
    }

    public void setReproductionRate(int reproductionRate) {
        this.reproductionRate = reproductionRate;
    }

    public long getLastReproductionTick() {
        return lastReproductionTick;
    }

    public void setLastReproductionTick(long lastReproductionTick) {
        this.lastReproductionTick = lastReproductionTick;
    }

    public float getReproductionRadius() {
        return reproductionRadius;
    }

    public void setReproductionRadius(float reproductionRadius) {
        this.reproductionRadius = reproductionRadius;
    }
}
