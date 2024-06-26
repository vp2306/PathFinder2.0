package ca.mcmaster.se2aa4.mazerunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(getParserOptions(), args);
            String filePath = cmd.getOptionValue('i');
            long mazeStartTime = System.currentTimeMillis();
            Maze maze = new Maze(filePath);
            long mazeEndTime = System.currentTimeMillis();

            if (cmd.getOptionValue("p") != null) {
                logger.info("Validating path");
                Path path = new Path(cmd.getOptionValue("p"));
                if (maze.validatePath(path)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            } else if(cmd.hasOption("baseline")){
                long mazeLoadTime = mazeEndTime - mazeStartTime;
                System.out.println("Time to load maze from file (Milliseconds): " + mazeLoadTime);
                benchmark(cmd, maze);
                                
            }else {
                String method = cmd.getOptionValue("method", "righthand");
                Path path = solveMaze(method, maze, new ConcreteVisitorSolver(maze));
                System.out.println(path.getFactorizedForm());
            }
        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
    }
    private static void benchmark(CommandLine cmd, Maze maze) throws Exception{
        //method time
        String method = cmd.getOptionValue("method", "righthand");//getting the value in method and making the deffault value righthand since its slow
        double methodStart = System.currentTimeMillis();
        Path methodPath = solveMaze(method, maze, new ConcreteVisitorSolver(maze));
        double methodEnd = System.currentTimeMillis();
        double methodTime = methodEnd - methodStart;
        System.out.println("Method: " + method + "\nTime (milliseconds): " + Benchmark.sigFigRounder(methodTime));
        //baseline time
        String baseline = cmd.getOptionValue("baseline", "graph");//makin deffault graph since its the one i coded
        double baselineStart = System.currentTimeMillis();
        Path baselinePath = solveMaze(baseline, maze, new ConcreteVisitorSolver(maze));
        double baselineEnd = System.currentTimeMillis();
        double baselineTime = baselineEnd - baselineStart;
        System.out.println("Baseline: "+ baseline + "\nTime (milliseconds): " + Benchmark.sigFigRounder(baselineTime));


        System.out.println("Speedup: " + Benchmark.speedupCalc(baselinePath.getPathSteps().size(), methodPath.getPathSteps().size()));
        
    }
    

    /**
     * Solve provided maze with specified method.
     *
     * @param method Method to solve maze with
     * @param maze Maze to solve
     * @return Maze solution path
     * @throws Exception If provided method does not exist
     */
    private static Path solveMaze(String method, Maze maze, VisitorSolver visitor) throws Exception {
        //MazeSolver solver = null;
        logger.info("Computing path");
        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand algorithm chosen.");
                return visitor.visit(new RightHandSolver());
            }
            case "tremaux" -> {
                logger.debug("Tremaux algorithm chosen.");
                return visitor.visit(new TremauxSolver());
            }
            case "bfs" -> {
                logger.debug("Graph algorithm (BFS) chosen");
                return visitor.visit(new BFSSolver());
            }
            default -> {
                throw new Exception("Maze solving method '" + method + "' not supported.");
            }
        }

    }

    /**
     * Get options for CLI parser.
     *
     * @return CLI parser options
     */
    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));
        options.addOption(new Option("baseline", true, "the baseline algorithm that will be used"));
        return options;
    }
}
