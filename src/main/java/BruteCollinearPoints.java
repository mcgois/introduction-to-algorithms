import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {


    private int promotedPointsCount = 0;
    private Point[] promotedPoints;

    private int lineSegmentsCount = 0;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
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

        this.promotedPoints = new Point[points.length];

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for(int s = r + 1; s < points.length; s ++) {
                        double slope1 = points[p].slopeTo(points[q]);
                        double slope2 = points[p].slopeTo(points[r]);
                        double slope3 = points[p].slopeTo(points[s]);

                        if (slope1 == slope2 && slope2 == slope3) {
                            createAndStoreSegment(points[p], points[q], points[r], points[s]);
                        }
                    }
                }
            }
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

    private void createAndStoreSegment(Point p, Point q, Point r, Point s) {
        Point[] points = new Point[4];
        points[0] = p;
        points[1] = q;
        points[2] = r;
        points[3] = s;

        Arrays.sort(points);

        this.promotedPoints[promotedPointsCount] = points[0];
        this.promotedPoints[promotedPointsCount+1] = points[3];
        this.promotedPointsCount += 2;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
