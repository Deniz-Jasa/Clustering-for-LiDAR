/*
 * Name: Deniz Jasarbasic
 * Student Number 300229393
 */
import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.util.Scanner;

public class Exp1 {

	// reads a csv file of 3D points (rethrow exceptions!)
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

	public static void save(String filename, List<Point3D> neighbors) throws IOException {
		FileWriter writer = new FileWriter(filename);

		String line = "x,y,z\n";
		writer.write(line);

		for (int i = 0; i < neighbors.size(); i++) {
			Point3D p = neighbors.get(i);

			// Write each indivdual point to the file
			line = p.toString() + "\n";

			writer.write(line);
		}

		writer.close();
	}

	public static void main(String[] args) throws Exception {  
		
		// Find the method type (Linear or KD algorithm), eps value, and file contain all the points.
		String method = args[0];
		double eps = Double. parseDouble(args[1]);
		List<Point3D> points = Exp1.read("input/" + args[2]); // Collect points given a file path.
		List<Point3D> neighbors;

		// Create the query point based of the user's input:
		Point3D query = new Point3D(Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));
		
		// Create the NearestNeighbors instance
		if (method.equals("lin")) {
			NearestNeighbors nn = new NearestNeighbors(points);
			// String fileNameForNN = "pt" + (i+1) + "_" + "lin.txt";
			neighbors = nn.rangeQuery(eps, query);
			// save("experiment1/" + fileNameForNN, neighbors);
		}
		else {
			NearestNeighborsKD nnKD = new NearestNeighborsKD(points);
			// String fileNameForNNKD = "pt" + (i+1) + "" + "kd.txt";
			neighbors = nnKD.rangeQuery(query, eps);
			// save("experiment1/" + fileNameForNNKD, neighbors);
		}

		// Print the results of the neighbors list one line at a time
		for (int i = 0; i < neighbors.size(); i++) {
			System.out.println((i+1) + ": " + neighbors.get(i));
		}
		System.out.println("Number of neighbors: " + neighbors.size());		
	}
}