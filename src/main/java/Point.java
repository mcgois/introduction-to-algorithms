/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        // degenerate
        if (that.y == this.y && that.x == this.x) {
            return Double.NEGATIVE_INFINITY;
        }

        // same horizontal line
        if (that.y == this.y) {
            return 0.0;
        }

        if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        }

        double num = (double) (that.y - this.y);
        double den = (double) (that.x - this.x);
        return num / den;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        } else if (this.y > that.y) {
            return 1;
        } else {
            return this.x - that.x;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {

            @Override
            public int compare(Point o1, Point o2) {
                double s1 = Point.this.slopeTo(o1);
                double s2 = Point.this.slopeTo(o2);

                if (s1 < s2) {
                    return -1;
                } else if (s1 > s2) {
                    return +1;
                } else {
                    return 0;
                }
            }

        };
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point[] points = new Point[8];
        points[0] = new Point(10000, 0);
        points[1] = new Point(0, 10000);
        points[2] = new Point(3000, 7000);
        points[3] = new Point(7000, 3000);
        points[4] = new Point(20000, 21000);
        points[5] = new Point(3000, 4000);
        points[6] = new Point(14000, 15000);
        points[7] = new Point(6000, 7000);

        System.out.println("Array original :: " + Arrays.toString(points));
        Arrays.sort(points);
        System.out.println("Array ordenado :: " + Arrays.toString(points));
        Arrays.sort(points, 1, points.length, points[0].slopeOrder());
        System.out.println("Array ordenado por p0:: " + Arrays.toString(points));
        Arrays.sort(points, 2, points.length, points[1].slopeOrder());
        System.out.println("Array ordenado por p1:: " + Arrays.toString(points));

//
//        Arrays.sort(points, points[0].slopeOrder());
//        System.out.println(Arrays.toString(points));
//
//        System.out.println(points[0].slopeTo(points[1]));
//
//        System.out.println(points[0].slopeTo(points[2]));
    }
}
