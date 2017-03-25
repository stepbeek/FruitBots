package com.stepbeek.fruitbots.bots;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void testTree_ReturnsFruit_WhenFruitRemains() {
        Tree tree = new Tree(Fruit.NORMAL, 3);

        assertEquals(Optional.of(Fruit.NORMAL), tree.getPieceOfFruit());
        assertEquals(Optional.of(Fruit.NORMAL), tree.getPieceOfFruit());
        assertEquals(Optional.of(Fruit.NORMAL), tree.getPieceOfFruit());
        assertEquals(Optional.empty(), tree.getPieceOfFruit());
    }

    @Test
    public void testTree_RespondsWithFruitAvailable_WhenAsked() throws Exception {
        Tree tree = new Tree(Fruit.NORMAL, 1);

        assertTrue(tree.hasFruit());
        assertEquals(Optional.of(Fruit.NORMAL) ,tree.getPieceOfFruit());
        assertFalse(tree.hasFruit());
    }

    @Test
    public void testTree_WhenBARE_returnsNoFruit() throws Exception {
        Tree tree = Tree.BARE();

        assertFalse(tree.hasFruit());
        assertEquals(Optional.empty(), tree.getPieceOfFruit());
    }
}
