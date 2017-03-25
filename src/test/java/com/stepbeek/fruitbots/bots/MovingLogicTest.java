package com.stepbeek.fruitbots.bots;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MovingLogicTest {

    @Test
    public void testDefaultTurningLogic_whenRandomReturnsTrue_TurnLeft() {
        Random random = Mockito.mock(Random.class);
        MovingLogic logic = new MovingLogic.DefaultMovingLogic(random);

        Mockito.when(random.nextBoolean()).thenReturn(true);
        assertEquals(Orientation.WEST, logic.orientationBasedTurn(Orientation.NORTH));
    }


    @Test
    public void testDefaultTurningLogic_whenRandomReturnsTrue_TurnRight() {
        Random random = Mockito.mock(Random.class);
        MovingLogic logic = new MovingLogic.DefaultMovingLogic(random);

        Mockito.when(random.nextBoolean()).thenReturn(true);

        assertEquals(Orientation.WEST, logic.orientationBasedTurn(Orientation.NORTH));
    }

    @Test
    public void testDefaultTurningLogic_whenFruitForward_moveForward() {
        GridSquareGroup group = new GridSquareGroup(new GridSquare(Tree.BARE()),
                new GridSquare(new Tree(Fruit.NORMAL, 3)),
                new GridSquare(new Tree(Fruit.NORMAL, 3)),
                new GridSquare(new Tree(Fruit.NORMAL, 3)),
                new GridSquare(new Tree(Fruit.NORMAL, 3)));

        MovingLogic logic = new MovingLogic() {
            @Override
            Orientation orientationBasedTurn(Orientation orientation) {
                return Orientation.WEST;
            }
        };

        assertEquals(Optional.of(Orientation.NORTH), logic.orientationBasedOnGroup(Orientation.NORTH, group));
    }

    @Test
    public void testDefaultTurningLogic_whenFruitLeft_turnLeft() {
        GridSquareGroup group = new GridSquareGroup(new GridSquare(Tree.BARE()),
                new GridSquare(Tree.BARE()),
                new GridSquare(Tree.BARE()),
                new GridSquare(Tree.BARE()),
                new GridSquare(new Tree(Fruit.NORMAL, 3)));

        MovingLogic logic = new MovingLogic() {
            @Override
            Orientation orientationBasedTurn(Orientation orientation) {
                return Orientation.EAST;
            }
        };

        assertEquals(Optional.of(Orientation.WEST), logic.orientationBasedOnGroup(Orientation.NORTH, group));
    }

    @Test
    public void testDefaultTurningLogic_whenFruitRight_turnRight() {
        GridSquareGroup group = new GridSquareGroup(new GridSquare(Tree.BARE()),
                new GridSquare(Tree.BARE()),
                new GridSquare(Tree.BARE()),
                new GridSquare(new Tree(Fruit.NORMAL, 3)),
                new GridSquare(Tree.BARE()));

        MovingLogic logic = new MovingLogic() {
            @Override
            Orientation orientationBasedTurn(Orientation orientation) {
                return Orientation.EAST;
            }
        };

        assertEquals(Optional.of(Orientation.EAST), logic.orientationBasedOnGroup(Orientation.NORTH, group));
    }

    @Test
    public void testDefaultTurningLogic_whenFruitBehind_turnLeft() {
        GridSquareGroup group = new GridSquareGroup(new GridSquare(Tree.BARE()),
                new GridSquare(Tree.BARE()),
                new GridSquare(new Tree(Fruit.NORMAL, 3)),
                new GridSquare(Tree.BARE()),
                new GridSquare(Tree.BARE()));

        MovingLogic logic = new MovingLogic() {
            @Override
            Orientation orientationBasedTurn(Orientation orientation) {
                return Orientation.EAST;
            }
        };

        assertEquals(Optional.of(Orientation.WEST), logic.orientationBasedOnGroup(Orientation.NORTH, group));
        assertEquals(Optional.of(Orientation.SOUTH), logic.orientationBasedOnGroup(Orientation.WEST, group));
    }

    @Test
    public void AHHHH() throws Exception {
        Random rand = Mockito.mock(Random.class);
        Mockito.when(rand.nextBoolean()).thenReturn(false);
        Robot bot = new Robot(Orientation.WEST, new MovingLogic.DefaultMovingLogic(rand), 23);
        GridSquareGroup group = new GridSquareGroup(new GridSquare(Tree.BARE()),
                new GridSquare(Tree.BARE()),
                null,
                new GridSquare(Tree.BARE()),
                null);


        bot.update(group);
    }
}
