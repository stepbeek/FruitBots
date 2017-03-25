package com.stepbeek.fruitbots.ui;

import com.stepbeek.fruitbots.bots.Fruit;
import com.stepbeek.fruitbots.bots.GridSquare;
import com.stepbeek.fruitbots.bots.Orientation;
import com.stepbeek.fruitbots.bots.World;

import javax.swing.*;
import java.awt.*;

import static com.stepbeek.fruitbots.bots.Orientation.*;

/**
 * The grid of the world that displays in the window. Contains any logic about how to draw the world.
 */
public class WorldGrid extends JPanel {

    private static final int BLOCK_SIZE = 50;
    private static final int ORIENTATION_SIZE = 10;
    private final World world;

    WorldGrid(World world) {
        this.world = world;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                GridSquare square = world.getSquare(x, y);

                setGridSquareColor(g, square);
                g.fillRect(BLOCK_SIZE + (BLOCK_SIZE * x), BLOCK_SIZE + (BLOCK_SIZE * y), BLOCK_SIZE, BLOCK_SIZE);

                if (square.hasBot()) {
                    drawOrientationDot(g, x, y, square);
                    drawRobotLife(g, x, y, square.getBot().getLifeRemaining());
                }
            }
        }

        // Draw Grid Lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= world.getWidth(); i++) {
            g.drawLine(((i * BLOCK_SIZE) + BLOCK_SIZE), BLOCK_SIZE, (i * BLOCK_SIZE) + BLOCK_SIZE, BLOCK_SIZE + (BLOCK_SIZE * world.getHeight()));
        }
        for (int i = 0; i <= world.getHeight(); i++) {
            g.drawLine(BLOCK_SIZE, ((i * BLOCK_SIZE) + BLOCK_SIZE), BLOCK_SIZE * (world.getWidth() + 1), ((i * BLOCK_SIZE) + BLOCK_SIZE));
        }
    }

    private void drawRobotLife(Graphics g, int x, int y, int lifeRemaining) {
        g.drawString(String.format("%d", lifeRemaining), BLOCK_SIZE + (BLOCK_SIZE * x) + 5 * BLOCK_SIZE / 12, BLOCK_SIZE + (BLOCK_SIZE * y) + 7 * BLOCK_SIZE / 12);
    }

    private void setGridSquareColor(Graphics g, GridSquare square) {
        if (square.hasBot()) {
            g.setColor(Color.BLACK);
        } else {
            if (square.hasTree()) {
                if (square.getTree().hasFruit()) {
                    Fruit fruit = square.getTree().getFruitType();
                    if (fruit == Fruit.NORMAL) {
                        g.setColor(Color.RED);
                    } else if (fruit == Fruit.ORGANIC) {
                        g.setColor(Color.GREEN);
                    } else if (fruit == Fruit.MYTHICAL) {
                        g.setColor(Color.ORANGE);
                    } else {
                        g.setColor(Color.RED);
                    }
                } else {
                    g.setColor(Color.LIGHT_GRAY);

                }
            } else {
                g.setColor(Color.WHITE);
            }
        }
    }

    private void drawOrientationDot(Graphics g, int x, int y, GridSquare square) {
        g.setColor(Color.WHITE);
        Orientation orientation = square.getBot().getOrientation();

        if (orientation == NORTH)
            g.drawOval(BLOCK_SIZE + (BLOCK_SIZE * x) + BLOCK_SIZE / 2 - ORIENTATION_SIZE / 2, 2 * BLOCK_SIZE + (BLOCK_SIZE * y) - BLOCK_SIZE / 10 - ORIENTATION_SIZE, ORIENTATION_SIZE, ORIENTATION_SIZE);
        if (orientation == SOUTH)
            g.drawOval(BLOCK_SIZE + (BLOCK_SIZE * x) + BLOCK_SIZE / 2 - ORIENTATION_SIZE / 2, BLOCK_SIZE + (BLOCK_SIZE * y) + BLOCK_SIZE / 10, ORIENTATION_SIZE, ORIENTATION_SIZE);
        if (orientation == WEST)
            g.drawOval(BLOCK_SIZE + (BLOCK_SIZE * x) + BLOCK_SIZE / 10, BLOCK_SIZE + (BLOCK_SIZE * y) + BLOCK_SIZE / 2 - ORIENTATION_SIZE / 2, ORIENTATION_SIZE, ORIENTATION_SIZE);
        if (orientation == EAST)
            g.drawOval(2 * BLOCK_SIZE + (BLOCK_SIZE * x) - ORIENTATION_SIZE - BLOCK_SIZE / 10, BLOCK_SIZE + (BLOCK_SIZE * y) + BLOCK_SIZE / 2 - ORIENTATION_SIZE / 2, ORIENTATION_SIZE, ORIENTATION_SIZE);
    }
}