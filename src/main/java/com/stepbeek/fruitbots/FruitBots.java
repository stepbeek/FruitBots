package com.stepbeek.fruitbots;

import com.stepbeek.fruitbots.bots.*;
import com.stepbeek.fruitbots.ui.FruitBotsWindow;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FruitBots {
    public static void main(String[] args) {
        World world = initialiseWorld();
        new FruitBotsWindow(world);
    }

    private static World initialiseWorld() {
        Random randBool = new Random();
        Random fruitSelector = new Random();
        GridSquare[][] squares = new GridSquare[Configuration.WORLD_WIDTH][Configuration.WORLD_HEIGHT];
        for (int x = 0; x < Configuration.WORLD_WIDTH; x++) {
            for (int y = 0; y < Configuration.WORLD_HEIGHT; y++) {
                if (randBool.nextBoolean()) {
                    if (randBool.nextBoolean()) {
                        Fruit fruit = Fruit.values()[fruitSelector.nextInt(Fruit.values().length)];
                        squares[x][y] = new GridSquare(new Tree(fruit, 3));
                    } else {
                        squares[x][y] = new GridSquare(new Tree(Fruit.NORMAL, 0));
                    }
                } else {
                    squares[x][y] = new GridSquare(new Tree(null, 0));
                }
            }
        }

        Map<Robot, Coordinate> bots = new HashMap<>();

        Random rand = new Random();
        for (int i = 0; i < Configuration.NUMBER_OF_BOTS; i++) {
            int x = rand.nextInt(Configuration.WORLD_WIDTH);
            int y = rand.nextInt(Configuration.WORLD_HEIGHT);

            while (squares[x][y].hasBot()) {
                x = rand.nextInt(Configuration.WORLD_WIDTH);
                y = (y + 1) % Configuration.WORLD_HEIGHT;
            }

            Robot bot = new Robot();
            squares[x][y].addRobot(bot);
            bots.put(bot, new Coordinate(x, y));
        }

        return new World(squares, bots);
    }
}
