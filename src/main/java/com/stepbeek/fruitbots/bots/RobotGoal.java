package com.stepbeek.fruitbots.bots;

public class RobotGoal {
    private final int xOffset;
    private final int yOffset;

    public RobotGoal(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public RobotGoal update(MoveTaken move) {
        Coordinate origin = new Coordinate(0, 0);
        Coordinate newCoordinate = move.newCoordinate(origin);

        return new RobotGoal(xOffset - newCoordinate.getX(), yOffset - newCoordinate.getY());
    }

    public Orientation getNextMoveProposal(Orientation current) {
        if (selectOrientation(current)) {
            return current;
        } else if (selectOrientation(current.turnLeft())) {
            return current.turnLeft();
        } else if (selectOrientation(current.turnRight())) {
            return current.turnRight();
        } else {
            return current.turnLeft().turnLeft();
        }
    }

    private boolean selectOrientation(Orientation current) {
        Coordinate origin = new Coordinate(0, 0);

        Coordinate newCoord = MoveTaken.fromOrientation(current).newCoordinate(origin);
        return Math.abs(xOffset - newCoord.getX()) < Math.abs(xOffset) || Math.abs(yOffset - newCoord.getY()) <  Math.abs(yOffset);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RobotGoal)) return false;

        RobotGoal robotGoal = (RobotGoal) o;

        if (xOffset != robotGoal.xOffset) return false;
        return yOffset == robotGoal.yOffset;
    }

    @Override
    public int hashCode() {
        int result = xOffset;
        result = 31 * result + yOffset;
        return result;
    }

    /**
     * Because we can only move on a grid the magnitude of a movement is the sum of the constituent parts.
     * @return magnitude using the grid metric
     */
    public int magnitude() {
        return Math.abs(xOffset) + Math.abs(yOffset);
    }

    @Override
    public String toString() {
        return "RobotGoal{" +
                "xOffset=" + xOffset +
                ", yOffset=" + yOffset +
                '}';
    }
}
