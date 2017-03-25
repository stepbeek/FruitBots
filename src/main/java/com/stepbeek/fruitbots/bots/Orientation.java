package com.stepbeek.fruitbots.bots;

public enum Orientation {
    NORTH {
        @Override
        public Orientation turnLeft() {
            return WEST;
        }

        @Override
        public Orientation turnRight() {
            return EAST;
        }

        @Override
        public GridSquare getSquare(GridSquareGroup group) {
            return group.getNorth();
        }
    }, SOUTH {
        @Override
        public Orientation turnLeft() {
            return EAST;
        }

        @Override
        public Orientation turnRight() {
            return WEST;
        }

        @Override
        public GridSquare getSquare(GridSquareGroup group) {
            return group.getSouth();
        }
    }, EAST {
        @Override
        public Orientation turnLeft() {
            return NORTH;
        }

        @Override
        public Orientation turnRight() {
            return SOUTH;
        }

        @Override
        public GridSquare getSquare(GridSquareGroup group) {
            return group.getEast();
        }
    }, WEST {
        @Override
        public Orientation turnLeft() {
            return SOUTH;
        }

        @Override
        public Orientation turnRight() {
            return NORTH;
        }

        @Override
        public GridSquare getSquare(GridSquareGroup group) {
            return group.getWest();
        }
    };

    public abstract Orientation turnLeft();

    public abstract Orientation turnRight();

    public abstract GridSquare getSquare(GridSquareGroup group);
}
