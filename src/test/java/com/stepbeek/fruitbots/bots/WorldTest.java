package com.stepbeek.fruitbots.bots;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class WorldTest {

    @Test
    public void testWorld_updatingSmallGrid_WithSingleBot() throws Exception {
        GridSquare[][] squares = new GridSquare[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                squares[i][j] = new GridSquare(Tree.BARE());
            }
        }

        Map<Robot, Coordinate> bots = new HashMap<>();
        MovingLogic logic = Mockito.mock(MovingLogic.class);
        Robot bot = new Robot(Orientation.NORTH, logic, 100);
        bots.put(bot, new Coordinate(0, 0));

        World world = new World(squares, bots);

        // Move North and orientationBasedTurn right
        Mockito.when(logic.orientationBasedOnGroup(Mockito.any(), Mockito.any())).thenReturn(Optional.of(Orientation.NORTH));
        assertEquals(new Coordinate(0, 1), world.update().get(bot));
        Mockito.when(logic.orientationBasedOnGroup(Mockito.any(), Mockito.any())).thenReturn(Optional.of(Orientation.EAST));
        assertEquals(new Coordinate(0, 1), world.update().get(bot));
        assertEquals(Orientation.EAST, bot.getOrientation());

        // Move East and orientationBasedTurn right
        Mockito.when(logic.orientationBasedOnGroup(Mockito.any(), Mockito.any())).thenReturn(Optional.of(Orientation.EAST));
        assertEquals(new Coordinate(1, 1), world.update().get(bot));
        Mockito.when(logic.orientationBasedOnGroup(Mockito.any(), Mockito.any())).thenReturn(Optional.of(Orientation.SOUTH));
        assertEquals(new Coordinate(1, 1), world.update().get(bot));
        assertEquals(Orientation.SOUTH, bot.getOrientation());

        // Move South and orientationBasedTurn right
        Mockito.when(logic.orientationBasedOnGroup(Mockito.any(), Mockito.any())).thenReturn(Optional.of(Orientation.SOUTH));
        assertEquals(new Coordinate(1, 0), world.update().get(bot));
        Mockito.when(logic.orientationBasedOnGroup(Mockito.any(), Mockito.any())).thenReturn(Optional.of(Orientation.WEST));
        assertEquals(new Coordinate(1, 0), world.update().get(bot));
        assertEquals(Orientation.WEST, bot.getOrientation());

        // Move West and orientationBasedTurn right
        Mockito.when(logic.orientationBasedOnGroup(Mockito.any(), Mockito.any())).thenReturn(Optional.of(Orientation.WEST));
        assertEquals(new Coordinate(0, 0), world.update().get(bot));
        Mockito.when(logic.orientationBasedOnGroup(Mockito.any(), Mockito.any())).thenReturn(Optional.of(Orientation.NORTH));
        assertEquals(new Coordinate(0, 0), world.update().get(bot));
        assertEquals(Orientation.NORTH, bot.getOrientation());
    }
}
