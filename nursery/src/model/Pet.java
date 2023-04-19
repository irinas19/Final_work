package model;

import java.util.List;

public abstract class Pet extends Animal {
    public Pet(String name, List<String> commands) {
        super(name, commands);
    }
}