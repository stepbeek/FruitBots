package com.stepbeek.fruitbots.bots;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RobotMapTest {


    @Test
    public void testRobotMap_whenFindingGoals_returnsAllFruitTreeLocations() {
        GridSquare mythical = new GridSquare(new Tree(Fruit.MYTHICAL, 10));
        GridSquare normal = new GridSquare(new Tree(Fruit.NORMAL, 10));
        GridSquare bare =  new GridSquare(Tree.BARE());

        GridSquare[][] squares = {{RobotMap.UNKNOWN, bare, RobotMap.UNKNOWN},
                {normal, bare, bare},
                {RobotMap.UNKNOWN, mythical, RobotMap.UNKNOWN}};

        RobotMap map = new RobotMap(squares);

        map.move(MoveTaken.EAST);

        Map<RobotGoal, Fruit> result = new HashMap<>();
        result.put(new RobotGoal(-1, -1), Fruit.NORMAL);
        result.put(new RobotGoal(0, 0), Fruit.MYTHICAL);

        assertEquals(result, map.directionToFruitSquare());
    }

    @Test
    public void testRobotMap_whenAddingGroup_updatesArray() {
        GridSquare[][] squares = {{RobotMap.UNKNOWN, RobotMap.UNKNOWN, RobotMap.UNKNOWN},
                {RobotMap.UNKNOWN, RobotMap.UNKNOWN, RobotMap.UNKNOWN},
                {RobotMap.UNKNOWN, RobotMap.UNKNOWN, RobotMap.UNKNOWN}};
        RobotMap map = new RobotMap(squares);

        GridSquare centre = Mockito.mock(GridSquare.class);
        GridSquare north = Mockito.mock(GridSquare.class);
        GridSquare south = Mockito.mock(GridSquare.class);
        GridSquare east = Mockito.mock(GridSquare.class);
        GridSquare west = Mockito.mock(GridSquare.class);
        GridSquareGroup group = new GridSquareGroup(centre, north, south, east, west);

        map.addGroup(group);

        GridSquare[][] updatedSquares = {{RobotMap.UNKNOWN, west, RobotMap.UNKNOWN},
                {south, centre, north},
                {RobotMap.UNKNOWN, east, RobotMap.UNKNOWN}};

        assertEquals(updatedSquares, squares);
    }

    @Test
    public void testRobotMap_whenAddingGroup_updatesArrayNulls() {
        GridSquare[][] squares = {{RobotMap.UNKNOWN, RobotMap.UNKNOWN, RobotMap.UNKNOWN},
                {RobotMap.UNKNOWN, RobotMap.UNKNOWN, RobotMap.UNKNOWN},
                {RobotMap.UNKNOWN, RobotMap.UNKNOWN, RobotMap.UNKNOWN}};
        RobotMap map = new RobotMap(squares);

        GridSquare centre = Mockito.mock(GridSquare.class);
        GridSquare south = Mockito.mock(GridSquare.class);
        GridSquareGroup group = new GridSquareGroup(centre, null, south, null, null);

        map.addGroup(group);

        GridSquare[][] updatedSquares = {{null, null, null},
                {south, centre, null},
                {null, null, null}};

        assertEquals(updatedSquares, squares);
    }

    @Test
    public void testRobotMap_whenMovingNorth_updatesArray() {
        GridSquare centre = Mockito.mock(GridSquare.class);
        GridSquare north = Mockito.mock(GridSquare.class);
        GridSquare south = Mockito.mock(GridSquare.class);
        GridSquare east = Mockito.mock(GridSquare.class);
        GridSquare west = Mockito.mock(GridSquare.class);

        GridSquare[][] squares = {{RobotMap.UNKNOWN, west, RobotMap.UNKNOWN},
                {south, centre, north},
                {RobotMap.UNKNOWN, east, RobotMap.UNKNOWN}};

        RobotMap map = new RobotMap(squares);

        map.move(MoveTaken.NORTH);

        GridSquare[][] updatesSquares = {{west, RobotMap.UNKNOWN, RobotMap.UNKNOWN},
                {centre, north, RobotMap.UNKNOWN},
                {east, RobotMap.UNKNOWN, RobotMap.UNKNOWN}};

        assertEquals(updatesSquares, squares);
    }


    @Test
    public void testRobotMap_whenMovingNorth_respectsMapEdges() {
        GridSquare centre = Mockito.mock(GridSquare.class);
        GridSquare north = Mockito.mock(GridSquare.class);
        GridSquare south = Mockito.mock(GridSquare.class);
        GridSquare east = Mockito.mock(GridSquare.class);
        GridSquare west = Mockito.mock(GridSquare.class);

        GridSquare[][] squares = {{null, null, null},
                {south, centre, north},
                {RobotMap.UNKNOWN, east, RobotMap.UNKNOWN}};

        RobotMap map = new RobotMap(squares);

        map.move(MoveTaken.EAST);

        GridSquare[][] updatesSquares = {{west, RobotMap.UNKNOWN, RobotMap.UNKNOWN},
                {centre, north, RobotMap.UNKNOWN},
                {east, RobotMap.UNKNOWN, RobotMap.UNKNOWN}};

        assertEquals(updatesSquares, squares);
    }


    @Test
    public void testRobotMap_whenMovingSouth_updatesArray() {
        GridSquare centre = Mockito.mock(GridSquare.class);
        GridSquare north = Mockito.mock(GridSquare.class);
        GridSquare south = Mockito.mock(GridSquare.class);
        GridSquare east = Mockito.mock(GridSquare.class);
        GridSquare west = Mockito.mock(GridSquare.class);

        GridSquare[][] squares = {{RobotMap.UNKNOWN, west, RobotMap.UNKNOWN},
                {south, centre, north},
                {RobotMap.UNKNOWN, east, RobotMap.UNKNOWN}};

        RobotMap map = new RobotMap(squares);

        map.move(MoveTaken.SOUTH);

        GridSquare[][] updatesSquares = {{RobotMap.UNKNOWN, RobotMap.UNKNOWN, west},
                {RobotMap.UNKNOWN, south, centre},
                {RobotMap.UNKNOWN, RobotMap.UNKNOWN, east}};

        assertEquals(updatesSquares, squares);
    }


    @Test
    public void testRobotMap_whenMovingEast_updatesArray() {
        GridSquare centre = Mockito.mock(GridSquare.class);
        GridSquare north = Mockito.mock(GridSquare.class);
        GridSquare south = Mockito.mock(GridSquare.class);
        GridSquare east = Mockito.mock(GridSquare.class);
        GridSquare west = Mockito.mock(GridSquare.class);

        GridSquare[][] squares = {{RobotMap.UNKNOWN, west, RobotMap.UNKNOWN},
                {south, centre, north},
                {RobotMap.UNKNOWN, east, RobotMap.UNKNOWN}};

        RobotMap map = new RobotMap(squares);

        map.move(MoveTaken.EAST);

        GridSquare[][] updatesSquares = {{south, centre, north},
                {RobotMap.UNKNOWN, east, RobotMap.UNKNOWN},
                {RobotMap.UNKNOWN, RobotMap.UNKNOWN, RobotMap.UNKNOWN}};

        assertEquals(updatesSquares, squares);
    }


    @Test
    public void testRobotMap_whenMovingWest_updatesArray() {
        GridSquare centre = Mockito.mock(GridSquare.class);
        GridSquare north = Mockito.mock(GridSquare.class);
        GridSquare south = Mockito.mock(GridSquare.class);
        GridSquare east = Mockito.mock(GridSquare.class);
        GridSquare west = Mockito.mock(GridSquare.class);

        GridSquare[][] squares = {{RobotMap.UNKNOWN, west, RobotMap.UNKNOWN},
                {south, centre, north},
                {RobotMap.UNKNOWN, east, RobotMap.UNKNOWN}};

        RobotMap map = new RobotMap(squares);

        map.move(MoveTaken.WEST);

        GridSquare[][] updatesSquares = {{RobotMap.UNKNOWN, RobotMap.UNKNOWN, RobotMap.UNKNOWN},
                {RobotMap.UNKNOWN, west, RobotMap.UNKNOWN},
                {south, centre, north}};

        assertEquals(updatesSquares, squares);
    }

    @Test
    public void testRobotMap_whenFindingNearestEmptySquare_ConsiderDiagonals() {
        GridSquare known = Mockito.mock(GridSquare.class);

        GridSquare[][] squares = {{known, known, known, known, known},
                {known, known, known, known, known},
                {known, known, known, known, known},
                {known, known, known, known, known},
                {known, known, known, known, RobotMap.UNKNOWN}};

        RobotMap map = new RobotMap(squares);

        assertEquals(new RobotGoal(2, 2), map.directionToEmptySquare().get(0));
        map.move(MoveTaken.NORTH);
        map.move(MoveTaken.NORTH);
        map.addGroup(new GridSquareGroup(known, null, known, known, known));
        assertEquals(new RobotGoal(2, 0), map.directionToEmptySquare().get(0));
        map.move(MoveTaken.EAST);
        map.move(MoveTaken.EAST);
        assertEquals(new RobotGoal(0, 0), map.directionToEmptySquare().get(0));
    }

    @Test
    public void testRobotMap_whenFindingNearestEmptySquare_ConsiderMapEdges() {
        GridSquare known = Mockito.mock(GridSquare.class);

        GridSquare[][] squares = {{null, null, RobotMap.UNKNOWN},
                {known, known, known},
                {known, known, known}};

        RobotMap map = new RobotMap(squares);

        assertEquals(new RobotGoal(-1, 1), map.directionToEmptySquare().get(0));
        map.move(MoveTaken.NORTH);
        assertEquals(new RobotGoal(-1, 0), map.directionToEmptySquare().get(0));
    }
}
