package com.stepbeek.fruitbots.bots;

import com.stepbeek.fruitbots.Configuration;

import java.util.*;

/**
 * The robot agent that travels around the grid.
 */
public class Robot {
    private final RobotMap map = new RobotMap();
    private Orientation orientation;
    private MovingLogic movingLogic;
    private int lifeRemaining;
    private RobotGoal goal;
    private Queue<Orientation> moveQueue = new LinkedList<>();


    public Robot() {
        this(Orientation.NORTH, new MovingLogic.DefaultMovingLogic(), Configuration.ROBOT_STARTING_LIFE);
    }

    Robot(Orientation orientation, MovingLogic movingLogic, int lifeRemaining) {
        this.orientation = orientation;
        this.movingLogic = movingLogic;
        this.lifeRemaining = lifeRemaining;
    }

    public int getLifeRemaining() {
        return lifeRemaining;
    }

    MoveTaken update(GridSquareGroup group) {
        if (lifeRemaining <= 0) {
            return MoveTaken.NONE;
        }

        // Update the map with what's nearby
        map.addGroup(group);

        // If food available, then eat and clear state
        Optional<MoveTaken> moveTaken = hasEaten(group);
        if (moveTaken.isPresent()) {
            clearState();
            return moveTaken.get();
        }

        // If there is food within reach, just move to food and clear state
        Optional<Orientation> newOrientationOptional = movingLogic.orientationBasedOnGroup(orientation, group);
        if (newOrientationOptional.isPresent()) {
            return goToFoodInSight(group, newOrientationOptional.get());
        }

        // If queue isn't empty, try to run through queue
        Orientation nextMove = moveQueue.poll();
        if (nextMove != null) {
            return makeMove(nextMove, group);
        }


        // If fruit goal exists, set one
        setGoalFromMap();

        //Otherwise just move to an unexplored square
        setGoalFromUnknowns();

        // If a goal is specific, move towards it.
        if (goal != null) {
            return actionGoal(group);
        }

        // If no other action should be taken, do nothing
        return MoveTaken.NONE;
    }

    private void setGoalFromUnknowns() {
        if (goal == null) {
            List<RobotGoal> otherGoals = map.directionToEmptySquare();

            for (RobotGoal potentialGoal : otherGoals) {
                if (goal == null) goal = potentialGoal;
                if (potentialGoal.magnitude() < goal.magnitude()) {
                    goal = potentialGoal;
                }
            }
        }
    }

    private MoveTaken goToFoodInSight(GridSquareGroup group, Orientation newOrientation) {
        if (newOrientation == orientation) {
            clearState();
            return makeMove(newOrientation, group);
        } else {
            clearState();
            return makeMove(newOrientation, group);
        }
    }

    private void clearState() {
        goal = null;
        moveQueue.clear();
    }

    private MoveTaken actionGoal(GridSquareGroup group) {
        Orientation newOrientation = goal.getNextMoveProposal(orientation);
        if (newOrientation == orientation) {
            GridSquare forward = orientation.getSquare(group);
            if (forward != null && forward.hasBot()) {
                GridSquare right = orientation.turnRight().getSquare(group);
                if (right != null && !right.hasBot()) {
                    manoeuvre(orientation, orientation.turnRight());
                    return makeMove(orientation.turnRight(), group);
                }

                GridSquare left = orientation.turnLeft().getSquare(group);
                if (left != null && !left.hasBot()) {
                    manoeuvre(orientation, orientation.turnLeft());
                    return makeMove(orientation.turnLeft(), group);
                }


                return makeMove(orientation.turnRight().turnRight(), group);
            }
            return makeMove(orientation, group);
        } else {
            return makeMove(newOrientation, group);
        }
    }

    private void manoeuvre(Orientation originalOrientation, Orientation newOrientation) {
        clearState();
        moveQueue.add(newOrientation);
        moveQueue.add(originalOrientation);
        moveQueue.add(originalOrientation);
        moveQueue.add(originalOrientation);
        moveQueue.add(newOrientation.turnLeft().turnLeft());
        moveQueue.add(newOrientation.turnLeft().turnLeft());
        moveQueue.add(originalOrientation);
    }

    private void setGoalFromMap() {
        // If no goal has been set yet, try to set one
        if (goal == null) {
            // If there is food available on the map, then move to it
            // NOTE: For now, we do not care about fruit type. We simply move to the nearest available fruit square.
            Map<RobotGoal, Fruit> goalMap = map.directionToFruitSquare();
            for (Map.Entry<RobotGoal, Fruit> pair : goalMap.entrySet()) {
                RobotGoal pairGoal = pair.getKey();
                if (goal == null) goal = pairGoal;

                if (pairGoal.magnitude() < goal.magnitude()) {
                    goal = pairGoal;
                }
            }
        }
    }

    private void updateState(MoveTaken move) {
        map.move(move);
        if (goal != null) {
            goal = goal.update(move);

            if (new RobotGoal(0, 0).equals(goal)) {
                goal = null;
            }
        }

    }

    private MoveTaken makeMove(Orientation moveOrientation, GridSquareGroup group) {
        lifeRemaining--;
        if (moveOrientation == orientation) {
            MoveTaken move = tryToMoveForward(group);
            updateState(move);
            return move;
        }
        MoveTaken move = turn(orientation);
        updateState(move);
        return move;
    }

    private MoveTaken turn(Orientation newOrientation) {
        if (newOrientation == orientation.turnLeft()) {
            orientation = newOrientation;
            return MoveTaken.TURN_LEFT;
        } else if (newOrientation == orientation.turnRight()) {
            orientation = newOrientation;
            return MoveTaken.TURN_RIGHT;
        } else {
            orientation = orientation.turnRight();
            return MoveTaken.TURN_RIGHT;
        }
    }

    private MoveTaken tryToMoveForward(GridSquareGroup group) {
        GridSquare forward = orientation.getSquare(group);

        // If the robot has discovered its goal is off the edge of the map, give up
        if (forward == null) goal = null;

        if (forward != null && !forward.hasBot()) {
            group.getCentre().removeBot();
            forward.addRobot(this);
            return MoveTaken.fromOrientation(orientation);
        } else {
            orientation = orientation.turnRight();
            return MoveTaken.TURN_RIGHT;
        }
    }

    private Optional<MoveTaken> hasEaten(GridSquareGroup group) {
        if (group.getCentre().hasTree() && group.getCentre().getTree().hasFruit()) {
            Tree tree = group.getCentre().getTree();
            Optional<Fruit> fruitOptional = tree.getPieceOfFruit();


            return fruitOptional.flatMap(fruit -> {
                if (fruit.getHealthPerFruit() > 0) {
                    lifeRemaining += fruit.getHealthPerFruit();
                    return Optional.of(MoveTaken.NONE);
                }

                return Optional.empty();
            });

        }

        return Optional.empty();
    }


    public Orientation getOrientation() {
        return orientation;
    }
}
