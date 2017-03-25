package com.stepbeek.fruitbots.bots;

public enum Fruit {
    NORMAL(1), ORGANIC(3), MYTHICAL(10), NONE(0);

    // Could have extra functionality around poisonous fruit by adding an entry here, and modifying robot behaviour to
    // flee poison fruit.

    private final int healthPerFruit;

    Fruit(int healthPerFruit) {
        this.healthPerFruit = healthPerFruit;
    }

    public int getHealthPerFruit() {
        return healthPerFruit;
    }
}
