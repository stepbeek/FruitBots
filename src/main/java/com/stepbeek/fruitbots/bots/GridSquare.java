package com.stepbeek.fruitbots.bots;


/**
 * The GridSquare class is intended as a central point to define the current state of a square on the Grid.
 *
 * The Tree on this square should remain the same throughout its life.
 */
public class GridSquare {

    private final Tree tree;
    private Robot bot;

    public GridSquare(Tree tree) {
        this.tree = tree;
    }

    public GridSquare(Robot bot, Tree tree) {
        this.bot = bot;
        this.tree = tree;
    }

    public void addRobot(Robot newBot) {
        bot = newBot;
    }

    void removeBot() {
        bot = null;
    }

    public Robot getBot() {
        return bot;
    }

    public Tree getTree() {
        return tree;
    }

    public boolean hasBot() {
        return bot != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridSquare)) return false;

        GridSquare square = (GridSquare) o;

        if (bot != null ? !bot.equals(square.bot) : square.bot != null) return false;
        return tree == square.tree;
    }

    @Override
    public int hashCode() {
        int result = bot != null ? bot.hashCode() : 0;
        result = 31 * result + (tree != null ? tree.hashCode() : 0);
        return result;
    }

    public boolean hasTree() {
        return tree != null;
    }
}
