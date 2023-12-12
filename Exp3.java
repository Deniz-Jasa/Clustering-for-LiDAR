/*
 * Name: Deniz Jasarbasic
 * Student Number 300229393
 */


import java.util.ArrayList;
import java.util.List;

public class Exp3 {
    public static void main(String[] args) throws Exception {

        List<Point3D> points = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            points = Exp2.read("input/Point_Cloud_" + (i + 1) + ".csv");

            DBScan d = new DBScan(points);
            DBScanKD dKD = new DBScanKD(points);
            d.setEps(0.5);
            dKD.setEps(0.5);
            d.setMinPts(10);
            dKD.setMinPts(10);

            /****************** DBScan Time Complexity ******************/
            long startTime = System.nanoTime();
            d.findCluster();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 100000;

            /****************** DBScanKD Time Complexity ******************/
            long startTimeKD = System.nanoTime();
            d.findCluster();
            long endTimeKD = System.nanoTime();
            long durationKD = (endTimeKD - startTimeKD) / 100000;

            /****************** Results ******************/
            System.out.println("DBScan (total time) for file " + (i + 1) + ": "+ (double) duration);
            System.out.println("DBScanKD (total time) for file " + (i + 1) + ": "+ (double) durationKD);
            System.out.println();
        }

    }
}
