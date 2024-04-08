package ca.mcmaster.se2aa4.mazerunner;

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
                Path path = solveMaze(method, maze);
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

        //method calcs
        String method = cmd.getOptionValue("method", "righthand");//getting the value in method and making the deffault value righthand since its slow
        long methodStart = System.currentTimeMillis();
        Path methodPath = solveMaze(method, maze);
        long methodEnd = System.currentTimeMillis();
        long methodTime = methodEnd - methodStart;
        System.out.println("Method: " + method + "\nTime (milliseconds): " + methodTime);

        //baseline calcs
        String baseline = cmd.getOptionValue("baseline", "graph");//makin deffault graph since its the one i coded
        long baselineStart = System.currentTimeMillis();
        Path baselinePath = solveMaze(baseline, maze);
        long baselineEnd = System.currentTimeMillis();
        long baselineTime = baselineEnd - baselineStart;
        System.out.println("Baseline: "+ baseline + "\n Time (milliseconds): " + baselineTime);
        // System.out.println("baseline steps" + baselinePath.getPathSteps().size());
        // System.out.println("method steps" + methodPath.getPathSteps().size());
        double speedup = (double) baselinePath.getPathSteps().size() / methodPath.getPathSteps().size();
        System.out.printf("Speedup: %.2f%n", speedup);
        
    }

    /**
     * Solve provided maze with specified method.
     *
     * @param method Method to solve maze with
     * @param maze Maze to solve
     * @return Maze solution path
     * @throws Exception If provided method does not exist
     */
    private static Path solveMaze(String method, Maze maze) throws Exception {
        MazeSolver solver = null;
        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand algorithm chosen.");
                solver = new RightHandSolver();
            }
            case "tremaux" -> {
                logger.debug("Tremaux algorithm chosen.");
                solver = new TremauxSolver();
            }
            case "graph" -> {
                logger.debug("Graph algorithm (BFS) chosen");
                solver = new GraphSolver();
            }
            default -> {
                throw new Exception("Maze solving method '" + method + "' not supported.");
            }
        }

        logger.info("Computing path");
        return solver.solve(maze);
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
