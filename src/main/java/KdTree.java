import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
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

        Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
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
        if (!contains(p)) {
            this.root = put(this.root, p, new RectHV(0, 0, 1, 1), true);
            this.size++;
        }
    }

    private Node put(Node h, Point2D p, RectHV rect, boolean xOrientation) {
        // if leaf
        if (h == null) {
            return new Node(p, rect);
        }

        // comparison
        double cmp = xOrientation ? p.x() - h.p.x(): p.y() - h.p.y();
        RectHV splitRect = splitRect(rect, h.p, xOrientation, cmp);

        if (cmp < 0) h.lb = put(h.lb, p, splitRect, !xOrientation);
        else if (cmp > 0) h.rt = put(h.rt, p, splitRect, !xOrientation);

        return h;
    }

    private RectHV splitRect(RectHV rect, Point2D p, boolean xOrientation, double comparison) {
        if (xOrientation) {
            if (comparison < 0) {
                return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
            } else if (comparison > 0) {
                return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            }
        } else {
            if (comparison < 0) {
                return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
            } else if (comparison > 0) {
                return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
            }
        }

        return rect;
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
        draw(this.root, true);
    }

    private void draw(Node h, boolean xOrientation) {
        if (h != null) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.01);
            h.p.draw();

            StdDraw.setPenRadius();
            if (xOrientation) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.line(h.p.x(), h.rect.ymin(), h.p.x(), h.rect.ymax());
            } else {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.line(h.rect.xmin(), h.p.y(), h.rect.xmax(), h.p.y());
            }

            draw(h.lb, !xOrientation);
            draw(h.rt, !xOrientation);
        }
    }

//    public Iterable<Point2D> range(RectHV rect) {
//        checkArguments(rect);
//        return range(this.root, rect);
//    }
//
//    private Iterable<Point2D> range(Node node, RectHV rect2) {
//        List<Point2D> result = new ArrayList<>();
//
//        if (node != null) {
//            result.add(node.p);
//            for (Point2D pLeft : range(node.lb, rect2)) {
//                result.add(pLeft);
//            }
//            for (Point2D pRight : range(node.rt, rect2)) {
//                result.add(pRight);
//            }
//        }
//
//
////        if (node == null) {
////            return new ArrayList<>();
////        }
////
////        List<Point2D> result = new ArrayList<>();
//////        if (rect.contains(node.p)) {
////            result.add(node.p);
//////        }
////
//
//        return result;
//    }

    public Iterable<Point2D> range(RectHV rect) {
        checkArguments(rect);
        List<Point2D> result = new ArrayList<>();
        range(rect, this.root, result);
        return result;
    }

    private List<Point2D> range(RectHV rect, Node h, List<Point2D> result) {
        if (h == null) return result;

        if (rect.contains(h.p)) {
            result.add(h.p);
        }

        if (h.lb != null && rect.intersects(h.lb.rect)) {
            range(rect, h.lb, result);
        }

        if (h.rt != null && rect.intersects(h.rt.rect)) {
            range(rect, h.rt, result);
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        checkArguments(p);
        return nearest(p, this.root, this.root.p, true);
    }

    private Point2D nearest(Point2D queryPoint, Node h, Point2D champion, boolean xOrientation) {
        if (h == null) return champion;

        if (queryPoint.distanceSquaredTo(h.p) < queryPoint.distanceSquaredTo(champion)) {
            champion = h.p;
        }

        if (h.rect.distanceSquaredTo(queryPoint) < queryPoint.distanceSquaredTo(champion)) {
            if ((xOrientation && queryPoint.x() < h.p.x()) ||
                    (!xOrientation && queryPoint.y() < h.p.y())) {
                champion = nearest(queryPoint, h.lb, champion, !xOrientation);
                champion = nearest(queryPoint, h.rt, champion, !xOrientation);
            } else {
                champion = nearest(queryPoint, h.rt, champion, !xOrientation);
                champion = nearest(queryPoint, h.lb, champion, !xOrientation);
            }
        }

        return champion;
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
