package model;

import java.util.List;

public abstract class PackAnimal extends Animal {
    public PackAnimal(String name, List<String> commands) {
        super(name, commands);
    }
}
