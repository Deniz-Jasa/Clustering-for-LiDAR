import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

/*
 * Name: Deniz Jasarbasic
 * Student Number 300229393
 */
public class DBScan {

    /************************************** Algorithm class variables **************************************/
    private int numberOfClusters;
    private List<Point3D> points;
    private Stack<Point3D> outputStack;
    private double eps;
    private double minPts;
    private static String inputFileName;

    /************************************** Constructor **************************************/
    public DBScan(List<Point3D> lt) {
        this.points = lt;
        outputStack = new Stack<>();
    }

    /************************************** Setter and getter methods **************************************/
    public String getInputFileName() { return DBScan.inputFileName; }

    public int getNumberOfClusters() {
        return this.numberOfClusters;
    }

    public List<Point3D> getPoints() {
        return this.points;
    }

    public double setEps(double eps) { 
        // Filter for invalid epsilon values:
        if (eps < 0.0d) {
            throw new IllegalArgumentException();
        }
        this.eps = eps; 
        return this.eps; 
    }

    public double setMinPts(double minPts) {
        // Filter for invalid minPts values:
        if (minPts < 0) {
            throw new IllegalArgumentException();
        }
        this.minPts = minPts;
        return this.minPts;
    }

    /************************************** Computational methods **************************************/
    public void findCluster() {
        // Reset # of clusters and instantiate the NearestNeighnors object:
        numberOfClusters = 0;
        NearestNeighbors neighborObject = new NearestNeighbors(this.points);

        // Run through the entire list of points!
        for (Point3D point : points) {

            if (point.label != 0) { // Already processed, skip!
                continue;
            }

            // Find nearby neighbors
            List<Point3D> N = neighborObject.rangeQuery(this.eps, point);

            // Density check
            if (N.size() < minPts) {
                point.label = -1;
                continue;
            }
            
            // Neighbors to expand
            numberOfClusters++;
            point.label = numberOfClusters; // Set label as cluster number

            // Push neighbors values
            for (int i = 0; i < N.size(); i++) {
                outputStack.push(N.get(i));
            }

            // Looping through neighbours list
            while (outputStack.size() > 0) {
                Point3D Q = outputStack.pop();

                if (Q.label == -1) {
                    // Set label of GPSCoord in neighbours list to cluster number
                    Q.label = numberOfClusters;
                }

                // If label is not 0 (already processed), skip!
                if (Q.label != 0) {
                    continue;
                }

                Q.label = numberOfClusters;
                N = neighborObject.rangeQuery(this.eps, Q);
                // Density check (core point):
                if (N.size() >= minPts) {
                    for (int i = 0; i < N.size(); i++) { // Add to pre-existing neighbours list
                        outputStack.push(N.get(i));
                    }
                }
            }
        }
    }

    // Input (reading data) methods:
    public static List<Point3D> read(String filename) {

        inputFileName = filename;
        Stack<Point3D> tempList = new Stack<>();

        // Pull from the input folder:
        String filePath = "input/" + inputFileName;
        String line;
        int start = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                // Skip the first line (x,y,z):
                if (start > 0) {
                    // Parse each line and extract each cordinate into an array ([x,y,z]):
                    String[] parsedString = line.split(",");
                    // Push a new Point3D object which contains each cordinate that was extracted above:
                    tempList.push(new Point3D(Double.parseDouble(parsedString[0]), Double.parseDouble(parsedString[1]), Double.parseDouble(parsedString[2])));
                }
                start++;
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return tempList;
    }

    /** Method **/
    public void save(String filename) {

        // Output file path and unique name (append the eps and minpts values)
        File fullOutputFileName = new File("output/" + filename + "_" + String.valueOf(eps) + "_" + String.valueOf(minPts) + "_" + String.valueOf(numberOfClusters) + ".csv");

        try (PrintWriter writer = new PrintWriter(fullOutputFileName)) {

			// Append column titles (x,y,z,C,R,G,B)
      		writer.append("x,");	
      		writer.append("y,");
     		writer.append("z,");
  			writer.append("C,");
            writer.append("R,");
            writer.append("G,"); 
            writer.append("B"); 		
  			writer.append("\n");

            for (int i=0; i< points.size(); i++){
                Point3D P = points.get(i);
                Random random = new Random(P.label);

                //Randomly generate color (R,G,B) for each cluster:
                double R = random.nextDouble();
                double G = random.nextDouble();
                double B = random.nextDouble();
                writer.append(P.getX() + "," + P.getY() + "," + P.getZ() + "," + P.label + "," + R + "," + G + "," + B);
                writer.append("\n");
            }

     		writer.flush(); // Flushing the stream
      		writer.close(); // Closing PrintWriter object

    	} 
        catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        
        List<Point3D> fl = DBScan.read("Point_Cloud_2.csv");
        DBScan a = new DBScan(fl);

        // Route args array values to the eps and minPts variables:
        a.setEps(Double.parseDouble(args[0]));
        a.setMinPts(Double.parseDouble(args[1]));
        
        // Find clusters and save to output file!
        a.findCluster();
        a.save(a.getInputFileName());
    }
}