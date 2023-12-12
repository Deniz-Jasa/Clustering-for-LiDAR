/*
 * Name: Deniz Jasarbasic
 * Student Number 300229393
 */
import java.util.ArrayList;
import java.util.List;

public class NearestNeighborsKD {
    
    public KDTree kdTree;
    List<Point3D> N = new ArrayList<Point3D>();

    // construct with list of points
    public NearestNeighborsKD(List<Point3D> list) {
        this.kdTree = new KDTree();
        
        for (Point3D p : list) {
            kdTree.add(p);
        }
    }

    // gets the neighbors of p (at a distance less than eps)
    public List<Point3D> rangeQuery(Point3D pt, double eps) {
        rangeQuery(pt, eps, N, this.kdTree.getRoot());
        return N;
    }


    private void rangeQuery(Point3D pt, double eps, List<Point3D> N, KDTree.KDnode node) {
        if (node == null) {
            return;
        }

        if (pt.distance(node.point) < eps) {
            N.add(node.point);
        }

        if (pt.get(node.axis) - eps <= node.value) {
            rangeQuery(pt, eps, N, node.left);
        }

        if (pt.get(node.axis) + eps > node.value) {
            rangeQuery(pt, eps, N, node.right);
        }

        return;
    }
}
