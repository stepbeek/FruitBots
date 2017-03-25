package com.stepbeek.fruitbots.bots;

import com.stepbeek.fruitbots.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RobotTest {

    private Robot bot;
    private GridSquare centre;
    private GridSquare north;
    private GridSquare south;
    private GridSquare east;
    private GridSquare west;
    private GridSquareGroup group;
    private MovingLogic movingLogic;

    @Before
    public void setUp() {
        movingLogic = Mockito.mock(MovingLogic.class);
        bot = new Robot(Orientation.NORTH, movingLogic, Configuration.ROBOT_STARTING_LIFE);

        centre = new GridSquare(Tree.BARE());
        centre.addRobot(bot);

        north = new GridSquare(Tree.BARE());
        south = new GridSquare(Tree.BARE());
        east = new GridSquare(Tree.BARE());
        west = new GridSquare(Tree.BARE());

        group = new GridSquareGroup(centre, north, south, east, west);
    }

    @Test
    public void testRobot_whenUpdating_movesForwardWhenPossible() throws Exception {
        Mockito.when(movingLogic.orientationBasedOnGroup(Orientation.NORTH, group)).thenReturn(Optional.of(Orientation.NORTH));
        assertEquals(MoveTaken.NORTH, bot.update(group));

        assertEquals(bot, north.getBot());
        assertNull(centre.getBot());
    }

    @Test
    public void testRobot_whenMovingForward_usesOneLife(){
        Mockito.when(movingLogic.orientationBasedOnGroup(Orientation.NORTH, group)).thenReturn(Optional.of(Orientation.NORTH));
        assertEquals(MoveTaken.NORTH, bot.update(group));
        assertEquals(Configuration.ROBOT_STARTING_LIFE - 1, bot.getLifeRemaining());
    }

    @Test
    public void testRobot_whenTurning_UsesOneLife() throws Exception {
        GridSquareGroup groupWithoutNorth = new GridSquareGroup(centre, null, south, east, west);
        Mockito.when(movingLogic.orientationBasedOnGroup(Orientation.NORTH, group)).thenReturn(Optional.of(Orientation.EAST));

        assertEquals(MoveTaken.TURN_RIGHT, bot.update(groupWithoutNorth));

        assertEquals(Configuration.ROBOT_STARTING_LIFE - 1, bot.getLifeRemaining());
    }

    @Test
    public void testRobot_whenUpdating_TurnsWhenForwardNull() throws Exception {
        GridSquareGroup groupWithoutNorth = new GridSquareGroup(centre, null, south, east, west);
        Mockito.when(movingLogic.orientationBasedOnGroup(Orientation.NORTH, groupWithoutNorth)).thenReturn(Optional.of(Orientation.EAST));

        assertEquals(MoveTaken.TURN_RIGHT, bot.update(groupWithoutNorth));

        assertEquals(bot, centre.getBot());
        assertEquals(Orientation.EAST, bot.getOrientation());
    }

    @Test
    public void testRobot_whenUpdating_TurnsWhenForwardIsOccupied() {
        Mockito.when(movingLogic.orientationBasedOnGroup(Orientation.NORTH, group)).thenReturn(Optional.of(Orientation.WEST));

        Robot otherBot = new Robot();
        north.addRobot(otherBot);

        assertEquals(MoveTaken.TURN_LEFT, bot.update(group));


        assertEquals(otherBot, north.getBot());
        assertEquals(bot, centre.getBot());
        assertEquals(Orientation.WEST, bot.getOrientation());
    }

    @Test
    public void testRobot_WhenOnASquareWithFruit_StaysAndEats() {
        GridSquare fruitSquare = new GridSquare(new Tree(Fruit.NORMAL, 3));
        GridSquareGroup groupWithFruitTree = new GridSquareGroup(fruitSquare, north, south, east, west);

        assertEquals(MoveTaken.NONE, bot.update(groupWithFruitTree));
        assertEquals(Configuration.ROBOT_STARTING_LIFE + Fruit.NORMAL.getHealthPerFruit(), bot.getLifeRemaining());

        assertEquals(MoveTaken.NONE, bot.update(groupWithFruitTree));
        assertEquals(Configuration.ROBOT_STARTING_LIFE + 2 * Fruit.NORMAL.getHealthPerFruit(), bot.getLifeRemaining());

        assertEquals(MoveTaken.NONE, bot.update(groupWithFruitTree));
        assertEquals(Configuration.ROBOT_STARTING_LIFE + 3 * Fruit.NORMAL.getHealthPerFruit(), bot.getLifeRemaining());
    }

    @Test
    public void testRobot_WhenNoFruitOnCurrentSquare_PursuesClosestFruit() {
        bot = new Robot();
        GridSquare fruitSquare = new GridSquare(new Tree(Fruit.NORMAL, 3));
        GridSquareGroup groupWithFruitTree = new GridSquareGroup(centre, north, fruitSquare, east, west);

        assertEquals(MoveTaken.TURN_LEFT, bot.update(groupWithFruitTree));
        assertEquals(MoveTaken.TURN_LEFT, bot.update(groupWithFruitTree));
        assertEquals(MoveTaken.SOUTH, bot.update(groupWithFruitTree));

        GridSquareGroup postMoveGroup = new GridSquareGroup(fruitSquare, centre, null, null, null);

        assertEquals(MoveTaken.NONE, bot.update(postMoveGroup));
        assertEquals(MoveTaken.NONE, bot.update(postMoveGroup));
        assertEquals(MoveTaken.NONE, bot.update(postMoveGroup));
    }

    @Test
    public void testRobot_WhenNoNorthSquareAndNoFruit_TurnRight() {
        bot = new Robot();
        GridSquareGroup groupWithoutNorth = new GridSquareGroup(centre, null, south, null, null);

        assertEquals(MoveTaken.TURN_RIGHT, bot.update(groupWithoutNorth));
        assertEquals(MoveTaken.TURN_RIGHT, bot.update(groupWithoutNorth));
        assertEquals(MoveTaken.SOUTH, bot.update(groupWithoutNorth));
    }
}
