package com.stepbeek.fruitbots.bots;

/**
 * A move taken to describe a move and transform the previous coordinate into a new coordinate. If a new, better kind
 * of robot was introduced, this class would be the only thing needing changed (other than the robot itself) to
 * update the movements taken by the robot.
 */
public enum MoveTaken {
    NORTH {
        @Override
        public Coordinate newCoordinate(Coordinate previous) {
            return new Coordinate(previous.getX(), previous.getY() + 1);
        }
    }, SOUTH {
        @Override
        public Coordinate newCoordinate(Coordinate previous) {
            return new Coordinate(previous.getX(), previous.getY() - 1);
        }
    }, EAST {
        @Override
        public Coordinate newCoordinate(Coordinate previous) {
            return new Coordinate(previous.getX() + 1, previous.getY());
        }
    }, WEST {
        @Override
        public Coordinate newCoordinate(Coordinate previous) {
            return new Coordinate(previous.getX() - 1, previous.getY());
        }
    }, NONE {
        @Override
        public Coordinate newCoordinate(Coordinate previous) {
            return previous;
        }
    },TURN_LEFT {
        @Override
        public Coordinate newCoordinate(Coordinate previous) {
            return previous;
        }
    }, TURN_RIGHT {
        @Override
        public Coordinate newCoordinate(Coordinate previous) {
            return previous;
        }
    };

    public abstract Coordinate newCoordinate(Coordinate previous);

    public static MoveTaken fromOrientation(Orientation orientation) {
        if (orientation == Orientation.NORTH) return NORTH;
        if (orientation == Orientation.SOUTH) return SOUTH;
        if (orientation == Orientation.WEST) return WEST;
        if (orientation == Orientation.EAST) return EAST;

        // This code isn't reachable, but the compiler isn't clever enough to realise that.
        return NONE;
    }
}
