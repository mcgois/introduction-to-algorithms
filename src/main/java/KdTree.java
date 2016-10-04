import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        Node(Point2D p) {
            this.p = p;
        }
    }

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return root != null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        checkArguments(p);
        // if (!contains(p)) {
        this.root = put(this.root, p, true);
        this.size++;
    }

    private Node put(Node h, Point2D p, boolean xOrientation) {
        // if leaf
        if (h == null) {
            return new Node(p);
        }

        // comparison
        double cmp = xOrientation ? p.x() - h.p.x(): p.y() - h.p.y();

        if (cmp < 0) h.lb = put(h.lb, p, !xOrientation);
        else if (cmp > 0) h.rt = put(h.rt, p, !xOrientation);
        else if (cmp == 0) h.p = p;

        return h;
    }

    private void checkArguments(Object p) {
        if (p == null) {
            throw new NullPointerException("invalid argument: null");
        }
    }

    public boolean contains(Point2D p) {
        checkArguments(p);
        return contains(this.root, p, true);
    }

    private boolean contains(Node h, Point2D p, boolean xOrientation) {
        if (h == null) {
            return false;
        }

        if (h.p.equals(p)) {
            return true;
        }

        // comparison
        double cmp = xOrientation ? p.x() - h.p.x(): p.y() - h.p.y();

        if (cmp < 0) return contains(h.lb, p, !xOrientation);
        else return contains(h.rt, p, !xOrientation);
    }

    public void draw() {
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkArguments(rect);
        return null;
    }

    public Point2D nearest(Point2D p) {
        checkArguments(p);
        return null;
    }

    public static void main(String[] args) {
        KdTree pointSET = new KdTree();
        pointSET.insert(new Point2D(0.7, 0.2));
        pointSET.insert(new Point2D(0.5, 0.4));
        pointSET.insert(new Point2D(0.2, 0.3));
        pointSET.insert(new Point2D(0.4, 0.7));
        pointSET.insert(new Point2D(0.9, 0.6));

        System.out.println(pointSET.contains(new Point2D(0.2, 0.3)));
    }
}
