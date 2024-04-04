package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GraphSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();
    private Maze maze;

    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        int[][] matrix = mazeToMatrixConverter(maze);
        //print matrix
        for(int i = 0; i < matrix.length; i++){
            for (int j =0; j<matrix[i].length; j++){
                System.err.print(matrix[i][j] + " ");
            }
            System.out.println();;
        }
        System.out.println(matrix);
        


        return pathBFS();
    }

    
    private int[][] mazeToMatrixConverter(Maze maze){
        int[][] matrix = new int[maze.getSizeY()][maze.getSizeX()];

        for(int i = 0; i < maze.getSizeX(); i++){
            for(int j = 0; j < maze.getSizeY(); j++){
                if (maze.isWall(new Position(j, i))) {
                    matrix[i][j] = 1; //1 represents a wall
                } else {
                    matrix[i][j] = 0; //0 reps a a pass
                }
            }
        }
                
        return matrix;
    }

    private Path pathBFS(){
        Path path = new Path();

        Direction dir = Direction.RIGHT;
        Position pos = maze.getStart();

        return path;
    }
}
