package ca.mcmaster.se2aa4.mazerunner;

public interface VisitorSolver {
    Path visit(BFSSolver solver);
    Path visit(RightHandSolver solver);
    Path visit(TremauxSolver solver);
}
