/*
 * Name: Deniz Jasarbasic
 * Student Number 300229393
 */

import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Exp2 {

    public static List<Point3D> read(String filename) throws Exception {

        List<Point3D> points = new ArrayList<>();
        double x, y, z;

        Scanner sc = new Scanner(new File(filename));
        // sets the delimiter pattern to be , or \n \r
        sc.useDelimiter(",|\n|\r");

        // skipping the first line x y z
        sc.next();
        sc.next();
        sc.next();

        // read points
        while (sc.hasNext()) {
            x = Double.parseDouble(sc.next());
            y = Double.parseDouble(sc.next());
            z = Double.parseDouble(sc.next());
            points.add(new Point3D(x, y, z));
        }

        sc.close(); // closes the scanner

        return points;
    }

    public static void main(String[] args) throws Exception {

        String method = args[0];
        double eps = Double.parseDouble(args[1]);
        String fileName = args[2];
        int step = Integer.parseInt(args[3]);

        long nearestNeighborsDurationSum = 0;
        int numOfNeighbors = 0;

        List<Point3D> points = Exp2.read("input/" + fileName);

        // Instance of NearestNeighbors variations
        NearestNeighbors nn = new NearestNeighbors(points);
        NearestNeighborsKD nnKD = new NearestNeighborsKD(points);

        for (int j = 0; j < points.size(); j += step) {

            if (method.equals("lin")) {
                /****************** Nearest Neighbors Time Complexity ******************/
                long startTime = System.nanoTime();
                nn.rangeQuery(eps, points.get(j));
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                nearestNeighborsDurationSum += duration;
            } 
            else {
                /****************** Nearest Neighbors KD Time Complexity ******************/
                long startTimeKD = System.nanoTime();
                nnKD.rangeQuery(points.get(j), eps);
                long endTimeKD = System.nanoTime();
                long durationKD = (endTimeKD - startTimeKD) / 1000000;
                nearestNeighborsDurationSum += durationKD;
            }

            // Keep track of the number of neighbors
            numOfNeighbors++;
        }

        /****************** Output Results ******************/
        System.out.println("Average run time (ms) using " + method + " algorithm for file " + fileName + ": " + (double) nearestNeighborsDurationSum / numOfNeighbors);
    }
}
