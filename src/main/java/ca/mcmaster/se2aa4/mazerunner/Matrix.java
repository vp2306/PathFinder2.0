package ca.mcmaster.se2aa4.mazerunner;

public class Matrix {
    private int[][] data;

    //methods for the matrix ADT
    public Matrix(int rows, int cols) {
        this.data = new int[rows][cols];
    }

    public int get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, int value) {
        data[row][col] = value;
    }

    public int numRows() {
        return data.length;
    }

    public int numCols() {
        return data[0].length;
    }
}
