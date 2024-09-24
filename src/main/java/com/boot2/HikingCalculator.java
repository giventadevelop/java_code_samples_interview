package com.boot2;

import java.util.ArrayList;
import java.util.List;

public class HikingCalculator {

    public static void main(String[] args) {

        int steps = Integer.parseInt(args[0]);

        String hikerPath = args[1];

        System.out.println( "Steps :"+  steps + " hikerPath :" + hikerPath );

        int numOfValleys = calculateStepscovered(steps,hikerPath );

        System.out.println( "numOfValleys :"+  numOfValleys );

        List collection = new ArrayList();

    }

    private static int calculateStepscovered(int steps, String hikerPath) {

        int pathLength =  hikerPath.length();
   String valley = "UD";

     if( hikerPath.indexOf(valley) > 0)
        return   hikerPath.indexOf(valley);
        if(hikerPath.charAt(0) == 'U' &&   hikerPath.charAt(pathLength-1 )=='U' ){
            return 1;
        }

        if(hikerPath.charAt(0) == 'D' &&   hikerPath.charAt(pathLength-1 )=='D' ){
            return 1;
        }

        return pathLength;
    }
}
