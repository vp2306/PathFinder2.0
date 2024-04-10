package ca.mcmaster.se2aa4.mazerunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Help.TextTable.Cell;

public class BFSSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();
    private Maze maze;

    //constructor
    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        Matrix matrix = MazeToMatrix.convertMazeToMatrix(maze);       
        return calculatePath(pathBFS(matrix)).reverse();
    }

    //citations provided in report
    public PathTracker pathBFS(Matrix matrix) {

        //PathTracker is an ADT to keep track of the parent and child nodes
        PathTracker pathTrack = new PathTracker();
        
        
        Queue<Position> queue = new LinkedList<>();
        //2d array to help determine wther node has been visited
        boolean[][] visited = new boolean[matrix.numRows()][matrix.numCols()];
        Position startNode = maze.getStart();//start node becomes spawn position
        Position destination = maze.getEnd();//destination node is the exit place

        //add the initial node and mark it visited
        queue.add(startNode);
        visited[startNode.y()][startNode.x()] = true;

        // directions to check adjacent node validity and whether they can be added
        int[] directionX = {-1, 1, 0, 0};
        int[] directionY = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            //remove the position to use in calculation
            Position currentPosition = queue.remove();

            
            if (currentPosition.equals(destination)) {
                return pathTrack;
            }

            //loop 4 times in each of the 4 directions surrounding a node to get data about adjacent nodes
            for (int i = 0; i < 4; i++) {
                int x = currentPosition.x() + directionX[i];
                int y = currentPosition.y() + directionY[i];

                // check if surrounding node within bounds, and not visited, and not a wall. if so, add to queue and visited
                if (x >= 0 && x < matrix.numCols() && y >= 0 && y < matrix.numRows() && !visited[y][x] && matrix.get(y, x) != 1) {
                    queue.add(new Position(x, y));
                    visited[y][x] = true;
                    //add to ADT a child:parent pair of positions
                    pathTrack.addChildParentPair(new Position(x, y), currentPosition);
                }
            }
        }
        return pathTrack;
    }

    //method to backtrack from end to start and track the route. 
    private Path calculatePath(PathTracker pathTrack){
        Path path = new Path();
        Position currentPosition = maze.getEnd();
        Direction currentDirection = Direction.LEFT;
        
        do{
            Position parentPosition = pathTrack.getParent(currentPosition);//get the value of current positions parent

            Direction newDirection = getDirection(parentPosition, currentPosition);//new direction will help determine if there were any turns
            
                      
            //if two directions not same, there is a turn
            if (currentDirection.equals(newDirection)) {
                path.addStep('F');
            }
            
            //swithc block helps determine movement of explorer. steps are reversed for L and R since we are backtracking 
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
        int diffX = parentPosition.x() - currentPosition.x(); //change in x direction       
        int diffY = parentPosition.y() - currentPosition.y(); //change in y direction 

        if (diffX == 0 && diffY == -1) {
            return Direction.UP; //if y is negative, we backtrack UP
        } else if(diffX == 1 && diffY == 0){
            return Direction.RIGHT; //X is positive, we backtrack right
        } else if(diffX ==0 && diffY == 1){
            return Direction.DOWN;
        } else if(diffX ==-1 && diffY == 0){
            return Direction.LEFT;
        }

        return Direction.LEFT; //random deffault case
    }

        
}
