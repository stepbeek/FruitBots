package com.stepbeek.fruitbots.bots;

import com.stepbeek.fruitbots.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotMap {
    public static final GridSquare UNKNOWN = new GridSquare(Tree.BARE());
    public static final int WEST_X_OFFSET = -1;
    public static final int EAST_X_OFFSET = 1;
    public static final int NORTH_Y_OFFSET = 1;
    public static final int SOUTH_Y_OFFSET = -1;
    private int dimension;
    private int location;
    private GridSquare[][] rollingMap;

    public RobotMap() {
        dimension = Configuration.ROBOT_MEMORY_POINTS % 2 == 0 ? Configuration.ROBOT_MEMORY_POINTS + 1 : Configuration.ROBOT_MEMORY_POINTS;
        location = dimension / 2;
        rollingMap = new GridSquare[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            rollingMap[i] = emptyRow();
        }
    }

    protected RobotMap(GridSquare[][] rollingMap) {
        dimension = rollingMap[0].length;
        location = dimension / 2;
        this.rollingMap = rollingMap;
    }

    void addGroup(GridSquareGroup group) {
        rollingMap[location][location] = group.getCentre();

        addSquare(group.getNorth(), 0, NORTH_Y_OFFSET);
        addSquare(group.getSouth(), 0, SOUTH_Y_OFFSET);
        addSquare(group.getWest(), WEST_X_OFFSET, 0);
        addSquare(group.getEast(), EAST_X_OFFSET, 0);

        if (group.getNorth() == null) {
            for (int i = 0; i < dimension; i++) {
                for (int j = location + 1; j < dimension; j++) {
                    rollingMap[i][j] = null;
                }
            }
        }
        if (group.getSouth() == null) {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < location; j++) {
                    rollingMap[i][j] = null;
                }
            }
        }
        if (group.getWest() == null) {
            for (int i = 0; i < location; i++) {
                for (int j = 0; j < dimension; j++) {
                    rollingMap[i][j] = null;
                }
            }
        }
        if (group.getEast() == null) {
            for (int i = location + 1; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    rollingMap[i][j] = null;
                }
            }
        }

        System.out.print("");
    }

    /**
     * Simply returns any squares that are known to have fruit on them alongside which kind of fruit.
     *
     * @return a map of robot goal detailing displacement between current position and fruit, and type of fruit
     */
    public Map<RobotGoal, Fruit> directionToFruitSquare() {
        Map<RobotGoal, Fruit> goalMap = new HashMap<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (rollingMap[i][j] != null && !UNKNOWN.equals(rollingMap[i][j])) {
                    Tree tree = rollingMap[i][j].getTree();
                    if (tree.hasFruit()) {
                        goalMap.put(new RobotGoal(i - location, j - location), tree.getFruitType());
                    }
                }
            }
        }

        return goalMap;
    }

    /**
     * Note: this method is not optimal, and is likely to a choose a route with one extra turn.
     */
    public List<RobotGoal> directionToEmptySquare() {
        List<RobotGoal> goals = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (rollingMap[i][j] != null && UNKNOWN.equals(rollingMap[i][j])) {
                    goals.add(new RobotGoal(i - location, j - location));
                }
            }
        }

        return goals;
    }

    private void addSquare(GridSquare square, int xOffset, int yOffset) {
        rollingMap[location + xOffset][location + yOffset] = square;
    }

    void move(MoveTaken moveTaken) {
        if (moveTaken == MoveTaken.NORTH) {
            for (int i = 0; i < rollingMap.length; i++) {
                System.arraycopy(rollingMap[i], 1, rollingMap[i], 0, rollingMap[i].length - 1);
                rollingMap[i][rollingMap[i].length - 1] = rollingMap[location][location + 1] == null ? null : UNKNOWN;
            }
        } else if (moveTaken == MoveTaken.SOUTH) {
            for (int i = 0; i < rollingMap.length; i++) {
                System.arraycopy(rollingMap[i], 0, rollingMap[i], 1, rollingMap[i].length - 1);
                rollingMap[i][0] = rollingMap[location][location - 1] == null ? null : UNKNOWN;
            }
        } else if (moveTaken == MoveTaken.EAST) {
            System.arraycopy(rollingMap, 1, rollingMap, 0, rollingMap.length - 1);
            rollingMap[rollingMap.length - 1] = rollingMap[rollingMap.length - 2][location] == null ? nullRow() : emptyRow();
        } else if (moveTaken == MoveTaken.WEST) {
            System.arraycopy(rollingMap, 0, rollingMap, 1, rollingMap.length - 1);
            rollingMap[0] = rollingMap[1][location] == null ? nullRow() : emptyRow();
        }
    }

    private GridSquare[] emptyRow() {
        GridSquare[] row = new GridSquare[dimension];
        for (int i = 0; i < dimension; i++) {
            row[i] = UNKNOWN;
        }
        return row;
    }

    private GridSquare[] nullRow() {
        GridSquare[] row = new GridSquare[dimension];
        for (int i = 0; i < dimension; i++) {
            row[i] = UNKNOWN;
        }
        return row;
    }
}
