package ca.mcmaster.se2aa4.mazerunner;

import java.util.LinkedList;
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
        int[][] matrix = mazeToMatrixConverter(maze);
        //print matrix
        for(int i = 0; i < matrix.length; i++){
            for (int j =0; j<matrix[0].length; j++){
                System.err.print(matrix[i][j] + " ");
            }
            System.out.println();;
        }
        System.out.println(matrix);
        System.out.println("rows: " + matrix[0].length);
        System.out.println("cols: " + matrix.length); 
        
        return pathBFS(matrix);
    }

    private int[][] mazeToMatrixConverter(Maze maze) {
        int[][] matrix = new int[maze.getSizeY()][maze.getSizeX()];

        for (int i = 0; i < maze.getSizeY(); i++) {
            for (int j = 0; j < maze.getSizeX(); j++) {
                Position pos = new Position(j, i); 
                if (maze.isWall(pos)) {
                    matrix[i][j] = 1; // if wall found
                } else {
                    matrix[i][j] = 0; // if no wall (pass)
                }
            }
        }

        return matrix;
    }

    private Path pathBFS(int[][] matrix) {
        Path path = new Path();

        Queue<Position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        Position startNode = maze.getStart();
        Position destination = maze.getEnd();

        queue.add(startNode);
        visited[startNode.y()][startNode.x()] = true;

        // directions to check adjacent node validity and whether they can be added
        int[] directionX = {-1, 1, 0, 0};
        int[] directionY = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Position currentPosition = queue.remove();
            System.out.println(currentPosition + " visited");

            if (currentPosition.equals(destination)) {
                return path;
            }

            for (int i = 0; i < 4; i++) {
                int x = currentPosition.x() + directionX[i];
                int y = currentPosition.y() + directionY[i];

                // check if surrounding node within bounds, and not visited, and not a wall. if so, add to queue and visited
                if (x >= 0 && x < matrix[0].length && y >= 0 && y < matrix.length && !visited[y][x] && matrix[y][x] != 1) {
                    queue.add(new Position(x, y));
                    visited[y][x] = true;
                }
            }
        }

        return path;
    }

        
}
