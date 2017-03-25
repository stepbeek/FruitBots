package com.stepbeek.fruitbots.bots;

import java.util.*;

/**
 * The core world simulation class. The world is presumed to be a a flat grid of squares that is finite in size.
 */
public class World {
    private final GridSquare[][] squares;
    private Map<Robot, Coordinate> bots;

    public World(GridSquare[][] squares, Map<Robot, Coordinate> bots) {
        this.squares = squares;
        this.bots = bots;
    }

    public int getWidth() {
        return squares.length;
    }

    public int getHeight() {
        return squares[0].length;
    }

    public Map<Robot, Coordinate> update() {
        Map<Robot, Coordinate> newBots = new HashMap<>(bots.size());

        for (Robot bot : bots.keySet()) {
            Coordinate coord = bots.get(bot);
            GridSquareGroup group = getGridSquareGroup(coord.getX(), coord.getY());
            MoveTaken move = bot.update(group);

            newBots.put(bot, move.newCoordinate(coord));
        }

        bots = newBots;
        return newBots;
    }

    private GridSquareGroup getGridSquareGroup(int x, int y) {
        GridSquare centre = getSquare(x, y);
        GridSquare north = getSquare(x, y + 1);
        GridSquare south = getSquare(x, y - 1);
        GridSquare east = getSquare(x + 1, y);
        GridSquare west = getSquare(x - 1, y);

        return new GridSquareGroup(centre, north, south, east, west);
    }

    public GridSquare getSquare(int x, int y) {
        if ( x < 0 || y < 0 || x >= squares.length || y >= squares[x].length) {
            return null;
        }

        return squares[x][y];
    }
}
