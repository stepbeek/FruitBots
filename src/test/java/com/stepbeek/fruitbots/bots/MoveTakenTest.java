package com.stepbeek.fruitbots.bots;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoveTakenTest {

    @Test
    public void testMoveTaken_whenAskedForNewCoordinate_ReturnCoordinate() throws Exception {
        Coordinate origin = new Coordinate(0, 0);

        assertEquals(new Coordinate(0, 1), MoveTaken.NORTH.newCoordinate(origin));
        assertEquals(new Coordinate(0, -1), MoveTaken.SOUTH.newCoordinate(origin));
        assertEquals(new Coordinate(-1, 0), MoveTaken.WEST.newCoordinate(origin));
        assertEquals(new Coordinate(1, 0), MoveTaken.EAST.newCoordinate(origin));
        assertEquals(new Coordinate(0, 0), MoveTaken.NONE.newCoordinate(origin));
        assertEquals(new Coordinate(0, 0), MoveTaken.TURN_LEFT.newCoordinate(origin));
        assertEquals(new Coordinate(0, 0), MoveTaken.TURN_RIGHT.newCoordinate(origin));
    }

    @Test
    public void testMoveTaken_whenAskedForMoveFromOrientation_returnMoveTaken() throws Exception {
        assertEquals(MoveTaken.NORTH, MoveTaken.fromOrientation(Orientation.NORTH));
        assertEquals(MoveTaken.SOUTH, MoveTaken.fromOrientation(Orientation.SOUTH));
        assertEquals(MoveTaken.EAST, MoveTaken.fromOrientation(Orientation.EAST));
        assertEquals(MoveTaken.WEST, MoveTaken.fromOrientation(Orientation.WEST));
    }
}
