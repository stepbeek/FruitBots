package com.stepbeek.fruitbots.bots;

import java.util.Optional;

public class Tree {
    private final Fruit fruit;
    private int piecesOfFruitRemaining;

    public Tree(Fruit fruit, int piecesOfFruitRemaining) {
        this.fruit = fruit;
        this.piecesOfFruitRemaining = piecesOfFruitRemaining;
    }

    public static Tree BARE() {
        return new Tree(Fruit.NONE, 0);
    }

    public Optional<Fruit> getPieceOfFruit() {
        if (piecesOfFruitRemaining-- > 0) return Optional.of(fruit);

        return Optional.empty();
    }

    public boolean hasFruit() {
        return piecesOfFruitRemaining > 0;
    }

    public Fruit getFruitType() {
        return fruit;
    }
}
