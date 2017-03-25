package com.stepbeek.fruitbots.bots;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class OrientationTest {

    @Test
    public void testWEST_whenTurned_isCorrect() throws Exception {
        assertEquals(Orientation.SOUTH, Orientation.WEST.turnLeft());
        assertEquals(Orientation.NORTH, Orientation.WEST.turnRight());
    }

    @Test
    public void testEAST_whenTurned_isCorrect() throws Exception {
        assertEquals(Orientation.NORTH, Orientation.EAST.turnLeft());
        assertEquals(Orientation.SOUTH, Orientation.EAST.turnRight());
    }

    @Test
    public void testSOUTH_whenTurned_isCorrect() throws Exception {
        assertEquals(Orientation.EAST, Orientation.SOUTH.turnLeft());
        assertEquals(Orientation.WEST, Orientation.SOUTH.turnRight());
    }

    @Test
    public void testNORTH_whenTurned_isCorrect() throws Exception {
        assertEquals(Orientation.WEST, Orientation.NORTH.turnLeft());
        assertEquals(Orientation.EAST, Orientation.NORTH.turnRight());
    }

    @Test
    public void testOrientation_whenAskedForSquare_returnsCorrectSquare() throws Exception {
        GridSquareGroup group = Mockito.mock(GridSquareGroup.class);

        Orientation.NORTH.getSquare(group);
        Mockito.verify(group).getNorth();

        Orientation.SOUTH.getSquare(group);
        Mockito.verify(group).getSouth();

        Orientation.EAST.getSquare(group);
        Mockito.verify(group).getEast();

        Orientation.WEST.getSquare(group);
        Mockito.verify(group).getWest();
    }
}
