package com.stepbeek.fruitbots.bots;

/**
 * Class to hold the adjacent squares around a Robot. This can be thought of as the bots range of vision.
 */
public class GridSquareGroup {
    private final GridSquare centre;
    private final GridSquare north;
    private final GridSquare south;
    private final GridSquare east;
    private final GridSquare west;

    GridSquareGroup(GridSquare centre, GridSquare north, GridSquare south, GridSquare east, GridSquare west) {
        this.centre = centre;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public GridSquare getCentre() {
        return centre;
    }

    public GridSquare getNorth() {
        return north;
    }

    public GridSquare getSouth() {
        return south;
    }

    public GridSquare getEast() {
        return east;
    }

    public GridSquare getWest() {
        return west;
    }
}
