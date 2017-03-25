package com.stepbeek.fruitbots.ui;


import com.stepbeek.fruitbots.Configuration;
import com.stepbeek.fruitbots.bots.World;

import javax.swing.*;
import java.awt.*;

public class FruitBotsWindow {
    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(1024, 768);
    private static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(800, 800);

    public FruitBotsWindow(World world) {
        EventQueue.invokeLater(() -> {
            WorldGrid panel = new WorldGrid(world);

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
            }

            createWindow(panel);
            startTimer(world, panel);
        });
    }

    private void createWindow(WorldGrid panel) {
        JFrame frame = new JFrame(Configuration.WINDOW_NAME);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(DEFAULT_WINDOW_SIZE);
        frame.setMinimumSize(MINIMUM_WINDOW_SIZE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.repaint();
    }

    private void startTimer(World world, WorldGrid panel) {
        new Timer(Configuration.TICK_DURATION_IN_MILLIS, e -> {
            world.update();
            panel.repaint();
        }).start();
    }

}