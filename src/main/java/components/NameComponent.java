package components;

import componentArchitecture.Component;

public class NameComponent implements Component {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
