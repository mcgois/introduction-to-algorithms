import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Queue;

public class FastCollinearPoints {

    private int promotedPointsCount = 0;
    private Point[] promotedPoints;

    private int lineSegmentsCount = 0;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        // check if points is null
        if (points == null) {
            throw new NullPointerException("points is null");
        }

        // checks if points[i] is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("some element is null");
            }
        }

        // check if two points in array are equal
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("identical points in array");
                }
            }
        }

        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copy[i] = points[i];
        }


        this.promotedPoints = new Point[2*points.length+1];

        for (int pIndex = 0; pIndex < points.length - 3; pIndex++) {
            Point p = points[pIndex];

            // order by slope of p
            Arrays.sort(copy, p.slopeOrder());

            double currentSlope = Double.NaN;

            int currentIndex = 1;
            Point min = p;
            Point max = p;
            int count = 0;
            for (int i = 1; i < copy.length; i++) {
                if (copy[i].slopeTo(p) == currentSlope) {
                    count++;

                    // ponto faz parte do conjunto de mesmo slops
                    if (copy[i].compareTo(min) < 0) {
                        min = copy[i];
                    }

                    if (copy[i].compareTo(max) > 0) {
                        max = copy[i];
                    }

                } else {
                    if (min != max && count >= 4) {
                        salvaSegmento(min, max);
                    }
                    min = p;
                    max = p;

                    currentSlope = copy[i].slopeTo(p);
                    count = 2;

                    if (copy[i].compareTo(min) < 0) {
                        min = copy[i];
                    }

                    if (copy[i].compareTo(max) > 0) {
                        max = copy[i];
                    }

                }

            }

            if (min != max && count >= 4) {
                salvaSegmento(min, max);
            }

//            double slope = copy[0].slopeTo(copy[1]);
//            int countPoints = 2;
//            for (int i = 1; i < copy.length - 1; i++, countPoints++) {
//                double slope2 = copy[i].slopeTo(copy[i+1]);
//                if (slope2 != slope) {
//                    break;
//                }
//            }
//
//            if (countPoints >= 4) {
//                promotedPoints[promotedPointsCount] = copy[0];
//                promotedPoints[promotedPointsCount + 1] = copy[countPoints - 1];
//                promotedPointsCount += 2;
//            }
        }

        lineSegmentsCount = promotedPointsCount / 2;
        if (lineSegmentsCount > 0) {
            segments = new LineSegment[lineSegmentsCount];
        } else {
            segments = new LineSegment[0];
        }
        for (int i = 0; i < lineSegmentsCount; i++) {
            segments[i] = new LineSegment(promotedPoints[i * 2], promotedPoints[i * 2 + 1]);
        }

    }

    private void salvaSegmento(Point min, Point max) {
        promotedPoints[promotedPointsCount] = min;
        promotedPoints[promotedPointsCount+1] = max;
        promotedPointsCount += 2;
    }

    private void createAndStoreSegment(Point p, Point q, Point r, Point s) {
//        Point[] points = new Point[4];
//        points[0] = p;
//        points[1] = q;
//        points[2] = r;
//        points[3] = s;
//
//        Arrays.sort(points);
//
//        this.promotedPoints[promotedPointsCount] = points[0];
//        this.promotedPoints[promotedPointsCount+1] = points[3];
//        this.promotedPointsCount += 2;
    }

    public int numberOfSegments() {
        return lineSegmentsCount;
//        return 0;
    }

    public LineSegment[] segments() {
        return segments;
//        return new LineSegment[0];
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
