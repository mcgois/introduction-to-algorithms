import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

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

        this.promotedPoints = new Point[2*points.length+1];

        for (int pIndex = 0; pIndex < points.length - 1; pIndex++) {
            Point p = points[pIndex];

            // order by slope of p
            Arrays.sort(points, pIndex + 1, points.length, p.slopeOrder());

            double currentSlope = Double.NaN;
            Point min = p;
            Point max = p;
            int count = 0;
            for (int j = pIndex + 1; j < points.length; j++) {
//                System.out.println("Comparing " + p + " against " + points[j]);
                double jSlope = p.slopeTo(points[j]);

                if (jSlope != currentSlope) {
                    if (count >= 4 && max != min) {
                        promotedPoints[promotedPointsCount] = min;
                        promotedPoints[promotedPointsCount+1] = max;
                        promotedPointsCount += 2;
                    }

                    min = p;
                    max = p;
                    currentSlope = jSlope;
                    count = 2;

                    if (min.compareTo(points[j]) > 0) {
                        min = points[j];
                    }

                    if (max.compareTo(points[j]) < 0) {
                        max = points[j];
                    }
                } else {
                    count++;
                    if (min.compareTo(points[j]) > 0) {
                        min = points[j];
                    }

                    if (max.compareTo(points[j]) < 0) {
                        max = points[j];
                    }
                }
            }

            if (count >= 4 && max != min) {
                promotedPoints[promotedPointsCount] = min;
                promotedPoints[promotedPointsCount+1] = max;
                promotedPointsCount += 2;
            }

        }

        lineSegmentsCount = promotedPointsCount / 2;
        if (lineSegmentsCount > 0) {
            segments = new LineSegment[lineSegmentsCount];
        } else {
            segments = new LineSegment[0];
        }
        for (int i = 0; i < lineSegmentsCount; i++) {
            segments[i] = new LineSegment(promotedPoints[i * 2],
                    promotedPoints[i * 2 + 1]);
        }

    }

    public int numberOfSegments() {
        return lineSegmentsCount;
    }

    public LineSegment[] segments() {
        return segments;
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
