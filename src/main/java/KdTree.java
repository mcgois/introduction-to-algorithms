import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class KdTree {

    private TreeSet<Point2D> points;

    public KdTree() {
        this.points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return this.points.isEmpty();
    }

    public int size() {
        return this.points.size();
    }

    public void insert(Point2D p) {
        checkArguments(p);
        if (!this.points.contains(p)) {
            this.points.add(p);
        }
    }

    private void checkArguments(Object p) {
        if (p == null) {
            throw new NullPointerException("invalid argument: null");
        }
    }

    public boolean contains(Point2D p) {
        checkArguments(p);
        return this.points.contains(p);
    }

    public void draw() {
        for (Point2D p : this.points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkArguments(rect);
        List<Point2D> result = new ArrayList<>();
        for (Point2D p : this.points) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        checkArguments(p);
        double min = Double.MAX_VALUE;
        Point2D minPoint = null;
        for (Point2D point : this.points) {
            if (point.distanceSquaredTo(p) < min) {
                min = point.distanceSquaredTo(p);
                minPoint = point;
            }
        }
        return minPoint;
    }

    public static void main(String[] args) {
        KdTree pointSET = new KdTree();
        pointSET.insert(new Point2D(0.7, 0.2));
        pointSET.insert(new Point2D(0.5, 0.4));
        pointSET.insert(new Point2D(0.2, 0.3));
        pointSET.insert(new Point2D(0.4, 0.7));
        pointSET.insert(new Point2D(0.9, 0.6));


    }
}
