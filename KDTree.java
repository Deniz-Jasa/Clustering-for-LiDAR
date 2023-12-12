/*
 * Name: Deniz Jasarbasic
 * Student Number 300229393
 */

public class KDTree {

    private final int DIM = 3;

    public class KDnode {

        public Point3D point;
        public int axis;
        public double value;
        public KDnode left;
        public KDnode right;

        public KDnode(Point3D pt, int axis) {
            this.point = pt;
            this.axis = axis;
            this.value = pt.get(axis);
            this.left = this.right = null;
        }

    }

    private KDnode root;

    public KDTree() {
        this.root = null;
    }

    // Getter emthod to retreive the root
    public KDnode getRoot() {
        return this.root;
    }

    public void add(Point3D p) {
        KDnode node = insert(p, root, 0);
        if (root == null) {
            root = node;
        }
    }

    // Insert Method:
    public KDnode insert(Point3D p, KDnode node, int axis) {
        if (node == null) {
            return new KDnode(p, axis);
        }
        else if (p.get(axis) <= node.value) {
            node.left = insert(p, node.left, (axis+1) % DIM);
        }
        else {
            node.right = insert(p, node.right, (axis+1) % DIM);
        }

        return node;
    }

}
