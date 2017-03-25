package com.stepbeek.fruitbots.bots;

import java.util.Optional;
import java.util.Random;

/**
 * Used by robots to help decide which way to orientationBasedTurn. For now just random depending on current orientation.
 */
public abstract class MovingLogic {

    abstract Orientation orientationBasedTurn(Orientation orientation);

    public Optional<Orientation> orientationBasedOnGroup(Orientation currentOrientation, GridSquareGroup group) {
        if (orientationHasFruitAndIsValid(currentOrientation, group)) {
            return Optional.of(currentOrientation);
        } else if (orientationHasFruitAndIsValid(currentOrientation.turnLeft(), group)) {
            return Optional.of(currentOrientation.turnLeft());
        } else if (orientationHasFruitAndIsValid(currentOrientation.turnRight(), group)) {
            return Optional.of(currentOrientation.turnRight());
        } else if (orientationHasFruitAndIsValid(currentOrientation.turnLeft().turnLeft(), group)) {
            return Optional.of(currentOrientation.turnLeft());
        }
        return Optional.empty();
    }

    private boolean orientationHasFruitAndIsValid(Orientation currentOrientation, GridSquareGroup group) {
        return currentOrientation.getSquare(group) != null
                && !currentOrientation.getSquare(group).hasBot()
                && currentOrientation.getSquare(group).hasTree()
                && currentOrientation.getSquare(group).getTree().hasFruit();
    }

    static class DefaultMovingLogic extends MovingLogic {
        private final Random rand;

        DefaultMovingLogic() {
            this(new Random());
        }

        DefaultMovingLogic(Random rand) {
            this.rand = rand;
        }

        @Override
        public Orientation orientationBasedTurn(Orientation orientation) {
            if (rand.nextBoolean()) {
                return orientation.turnLeft();
            } else {
                return orientation.turnRight();
            }
        }
    }
}

