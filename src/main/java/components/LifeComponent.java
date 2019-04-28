package components;

import componentArchitecture.Component;

public class LifeComponent implements Component {

    private int curLife;

    public int getCurLife() {
        return curLife;
    }

    public void setCurLife(int curLife) {
        this.curLife = curLife;
    }

    public void adjustCurLife(float value) {
        curLife += value;
    }
}
