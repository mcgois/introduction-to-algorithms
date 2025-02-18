import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node lb;
        private Node rt;

        Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D point) {
        checkArguments(point);
        this.root = put(this.root, point, new RectHV(0, 0, 1, 1), true);
    }

    private Node put(Node node, Point2D point, RectHV rect, boolean xOrientation) {
        // if leaf
        if (node == null) {
            this.size++;
            return new Node(point, rect);
        }

        if (node.point.equals(point)) {
            return node;
        }

        // comparison
        int cmp = xOrientation ? Point2D.X_ORDER.compare(point, node.point)
                               : Point2D.Y_ORDER.compare(point, node.point);

        // split Rect
        RectHV splitRect = splitRect(rect, node.point, xOrientation, cmp);

        // recursion
        if (cmp < 0) node.lb = put(node.lb, point, splitRect, !xOrientation);
        else if (cmp >= 0) node.rt = put(node.rt, point, splitRect, !xOrientation);

        return node;
    }

    private RectHV splitRect(RectHV rect, Point2D point,
                             boolean xOrientation, int comparison) {
        if (xOrientation) {
            if (comparison < 0) {
                return new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
            } else if (comparison > 0) {
                return new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            }
        } else {
            if (comparison < 0) {
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
            } else if (comparison > 0) {
                return new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
            }
        }

        return rect;
    }

    private void checkArguments(Object obj) {
        if (obj == null) {
            throw new NullPointerException("invalid argument: null");
        }
    }

    public boolean contains(Point2D point) {
        checkArguments(point);
        return contains(this.root, point, true);
    }

    private boolean contains(Node node, Point2D point, boolean xOrientation) {
        if (node == null) {
            return false;
        }

        if (node.point.equals(point)) {
            return true;
        }

        // comparison
        double cmp = xOrientation ? Point2D.X_ORDER.compare(point, node.point)
                                  : Point2D.Y_ORDER.compare(point, node.point);

        if (cmp < 0) return contains(node.lb, point, !xOrientation);
        else return contains(node.rt, point, !xOrientation);
    }

    public void draw() {
        draw(this.root, true);
    }

    private void draw(Node node, boolean xOrientation) {
        if (node != null) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.01);
            node.point.draw();

            StdDraw.setPenRadius();
            if (xOrientation) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.line(node.point.x(), node.rect.ymin(),
                             node.point.x(), node.rect.ymax());
            } else {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.line(node.rect.xmin(), node.point.y(),
                             node.rect.xmax(), node.point.y());
            }

            draw(node.lb, !xOrientation);
            draw(node.rt, !xOrientation);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkArguments(rect);
        List<Point2D> result = new ArrayList<>();
        range(rect, this.root, result);
        return result;
    }

    private List<Point2D> range(RectHV rect, Node node, List<Point2D> result) {
        if (node == null) return result;

        if (rect.contains(node.point)) {
            result.add(node.point);
        }

        if (node.lb != null && rect.intersects(node.lb.rect)) {
            range(rect, node.lb, result);
        }

        if (node.rt != null && rect.intersects(node.rt.rect)) {
            range(rect, node.rt, result);
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        checkArguments(p);
        if (this.root == null) {
            return null;
        }
        return nearest(p, this.root, this.root.point, true);
    }

    private Point2D nearest(Point2D queryPoint, Node node,
                            Point2D champion, boolean xOrientation) {

        if (node == null) return champion;

        if (queryPoint.distanceSquaredTo(node.point) <
                queryPoint.distanceSquaredTo(champion)) {
            champion = node.point;
        }

        if (node.rect.distanceSquaredTo(queryPoint) <
                queryPoint.distanceSquaredTo(champion)) {
            if ((xOrientation && queryPoint.x() < node.point.x()) ||
                    (!xOrientation && queryPoint.y() < node.point.y())) {
                champion = nearest(queryPoint, node.lb, champion, !xOrientation);
                champion = nearest(queryPoint, node.rt, champion, !xOrientation);
            } else {
                champion = nearest(queryPoint, node.rt, champion, !xOrientation);
                champion = nearest(queryPoint, node.lb, champion, !xOrientation);
            }
        }

        return champion;
    }

    public static void main(String[] args) {
        KdTree pointSET = new KdTree();
        pointSET.insert(new Point2D(0.5, 0.5));
        pointSET.insert(new Point2D(0.5, 0.5));
        System.out.println(pointSET.size());
    }
}
