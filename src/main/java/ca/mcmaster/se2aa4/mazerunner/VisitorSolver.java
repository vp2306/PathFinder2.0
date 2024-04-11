package ca.mcmaster.se2aa4.mazerunner;

public interface VisitorSolver {
    //interface to tell concrete class what to implement (visit method)
    Path visit(BFSSolver solver);
    Path visit(RightHandSolver solver);
    Path visit(TremauxSolver solver);
}
