package ca.mcmaster.se2aa4.mazerunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Help.TextTable.Cell;

public class GraphSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();
    private Maze maze;

    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        Matrix matrix = mazeToMatrixConverter(maze);       
        return calculatePath(pathBFS(matrix)).reverse();
    }

    public Matrix mazeToMatrixConverter(Maze maze) {
        Matrix matrix = new Matrix(maze.getSizeY(), maze.getSizeX());

        for (int i = 0; i < maze.getSizeY(); i++) {
            for (int j = 0; j < maze.getSizeX(); j++) {
                Position pos = new Position(j, i); 
                if (maze.isWall(pos)) {
                    matrix.set(i, j, 1); // if wall found
                } else {
                    matrix.set(i, j, 0); // if no wall (pass)
                }
            }
        }

        return matrix;
    }

    public Map<Position, Position> pathBFS(Matrix matrix) {
        Map <Position, Position> childParentMap = new HashMap<>();
        

        Queue<Position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[matrix.numRows()][matrix.numCols()];
        Position startNode = maze.getStart();
        Position destination = maze.getEnd();

        queue.add(startNode);
        visited[startNode.y()][startNode.x()] = true;

        // directions to check adjacent node validity and whether they can be added
        int[] directionX = {-1, 1, 0, 0};
        int[] directionY = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Position currentPosition = queue.remove();


            if (currentPosition.equals(destination)) {
                return childParentMap;
            }

            for (int i = 0; i < 4; i++) {
                int x = currentPosition.x() + directionX[i];
                int y = currentPosition.y() + directionY[i];

                // check if surrounding node within bounds, and not visited, and not a wall. if so, add to queue and visited
                if (x >= 0 && x < matrix.numCols() && y >= 0 && y < matrix.numRows() && !visited[y][x] && matrix.get(y, x) != 1) {
                    queue.add(new Position(x, y));
                    visited[y][x] = true;
                    childParentMap.put(new Position(x, y), currentPosition);
                }
            }
        }
        // Print contents of parentMap
        

        return childParentMap;
    }

    private Path calculatePath(Map<Position, Position> childParentMap){
        Path path = new Path();
        Position currentPosition = maze.getEnd();
        Direction currentDirection = Direction.LEFT;
        
        do{
            Position parentPosition = childParentMap.get(currentPosition);//get the value of current positions parent

            Direction newDirection = getDirection(parentPosition, currentPosition);
            
                      
            //if two directions not same, there is a turn
            if (currentDirection.equals(newDirection)) {
                path.addStep('F');
            }
            
            switch (currentDirection) {
                case UP:
                    switch (newDirection) {
                        case RIGHT:
                            path.addStep('L');
                            path.addStep('F');
                            break;
                        case LEFT:
                            path.addStep('R');
                            path.addStep('F');
                            break;
                        
                        default:
                            break;
                    }
                    break;

                case RIGHT:
                    switch (newDirection) {
                        case UP:
                            path.addStep('R');
                            path.addStep('F');
                            break;
                        case DOWN:
                            path.addStep('L');
                            path.addStep('F');
                            break;
                        
                        default:
                            break;
                    }
                    break;

                case DOWN:
                    switch (newDirection) {
                        case RIGHT:
                            path.addStep('R');
                            path.addStep('F');
                            break;
                        case LEFT:
                            path.addStep('L');
                            path.addStep('F');
                            break;
                        
                        default:
                            break;
                    }
                    break;
                case LEFT:
                    switch (newDirection) {
                        case UP:
                            path.addStep('L');
                            path.addStep('F');
                            break;
                        case DOWN:
                            path.addStep('R');
                            path.addStep('F');
                            break;
                        
                        default:
                            break;
                    }
                    break;
            
                default:
                    break;
            }            

            //update direction
            currentDirection = newDirection;
            currentPosition = parentPosition;

        } while(!currentPosition.equals(maze.getStart()));
        return path;
    }

    private Direction getDirection(Position parentPosition, Position currentPosition){
        int diffX = parentPosition.x() - currentPosition.x();
        int diffY = parentPosition.y() - currentPosition.y();

        if (diffX == 0 && diffY == -1) {
            return Direction.UP;
        } else if(diffX == 1 && diffY == 0){
            return Direction.RIGHT;
        } else if(diffX ==0 && diffY == 1){
            return Direction.DOWN;
        } else if(diffX ==-1 && diffY == 0){
            return Direction.LEFT;
        }

        return Direction.LEFT; //random deffault case
    }

        
}
