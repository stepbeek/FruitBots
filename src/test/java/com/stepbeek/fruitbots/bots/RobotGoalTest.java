package com.stepbeek.fruitbots.bots;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RobotGoalTest {

    @Test
    public void testRobotGoal_whenRequestingNextMove_returnReasonableMove() {
        assertEquals(Orientation.EAST, new RobotGoal(3, 0).getNextMoveProposal(Orientation.NORTH));
        assertEquals(Orientation.WEST, new RobotGoal(-1, 0).getNextMoveProposal(Orientation.NORTH));
        assertEquals(Orientation.NORTH, new RobotGoal(3, 3).getNextMoveProposal(Orientation.NORTH));
        assertEquals(Orientation.SOUTH, new RobotGoal(3, -1).getNextMoveProposal(Orientation.WEST));
    }

    @Test
    public void testRobotGoal_whenUpdatingBasedOnMove_ReturnsNewGoal() {
        assertEquals(new RobotGoal(2, 0), new RobotGoal(3, 0).update(MoveTaken.EAST));
        assertEquals(new RobotGoal(3, 0), new RobotGoal(3, 1).update(MoveTaken.NORTH));
        assertEquals(new RobotGoal(-2, 1), new RobotGoal(-3, 1).update(MoveTaken.WEST));
        assertEquals(new RobotGoal(3, -2), new RobotGoal(3, -3).update(MoveTaken.SOUTH));
        assertEquals(new RobotGoal(3, -3), new RobotGoal(3, -3).update(MoveTaken.TURN_LEFT));
        assertEquals(new RobotGoal(3, -3), new RobotGoal(3, -3).update(MoveTaken.TURN_RIGHT));
        assertEquals(new RobotGoal(3, -3), new RobotGoal(3, -3).update(MoveTaken.NONE));
    }
}
