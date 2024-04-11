package ca.mcmaster.se2aa4.mazerunner;

public class ConcreteVisitorSolver implements VisitorSolver {

    private Maze maze;
    
    public ConcreteVisitorSolver(Maze maze){
        this.maze = maze;
    }
    //implementations of visit from the interface

    @Override
    public Path visit(BFSSolver solver){
        return solver.solve(maze);
    }

    @Override
    public Path visit(RightHandSolver solver){
        return solver.solve(maze);
    }

    @Override
    public Path visit(TremauxSolver solver){
        return solver.solve(maze);
    }
}
