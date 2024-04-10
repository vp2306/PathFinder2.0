package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainBenchmarkTest {

    @Test
    void speedupCalculationTestValidCase() {
        
        //testing some random numbers to see if sigfig rounding works (normal condition)
        int baselineSteps = 12553;  
        int methodSteps = 354;      
        
        double expectedSpeedup = 35.0;

        double speedup = Main.speedupCalc(baselineSteps, methodSteps);

        assertEquals(expectedSpeedup, speedup);
     

    }

    @Test
    void speedupCalculationTestBoundaryCase() {
        
        //testing some random numbers to see if sigfig rounding works (normal condition)
        int baselineSteps = 0;  
        int methodSteps = 1;      
        
        double expectedSpeedup = 0.0;

        double speedup = Main.speedupCalc(baselineSteps, methodSteps);

        assertEquals(expectedSpeedup, speedup);

        

    }
}
