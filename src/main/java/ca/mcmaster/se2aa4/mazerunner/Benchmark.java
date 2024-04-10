package ca.mcmaster.se2aa4.mazerunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.cli.CommandLine;

public class Benchmark  {

    //to calculate speedup
    public static double speedupCalc(int baseline, int method){
        return sigFigRounder(baseline / method);
    }

    //get the right sigfigs
    public static double sigFigRounder(double numberToRound){
        return new BigDecimal(Double.toString(numberToRound)).round(new java.math.MathContext(2,RoundingMode.HALF_UP)).doubleValue();
    }
     

}
