package ca.mcmaster.se2aa4.mazerunner;

public class MazeToMatrix {
    public static Matrix convertMazeToMatrix(Maze maze) {
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
}

