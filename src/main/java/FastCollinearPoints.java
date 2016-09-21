import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private int promotedPointsCount = 0;
    private Point[] promotedPoints;

    private int lineSegmentsCount = 0;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] pointsOriginal) {
        Point[] points = Arrays.copyOf(pointsOriginal, pointsOriginal.length);

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

        // ordem natural dos pontos
        Arrays.sort(points);

        // resultado final de segmentos
        LinkedList<LineSegment> result = new LinkedList<>();

        for (Point pontoP : points) {

            // cria clone de pontos e ordena de acordo com slope de p
            Point[] slopeOrderedPoints = points.clone();
            Arrays.sort(slopeOrderedPoints, pontoP.slopeOrder());

            // segmento contendo ponto p original
            LinkedList<Point> segment = new LinkedList<>();
            segment.add(pontoP);

            for (int j = 1; j < points.length - 1; j++) {
                // ponto j
                Point slopePoint = slopeOrderedPoints[j];
                double slope = pontoP.slopeTo(slopePoint);

                // ponto k
                Point nextSlopePoint = slopeOrderedPoints[j+1];
                double nextSlope = pontoP.slopeTo(nextSlopePoint);

                // se na mesma reta, adiciona o ponto
                // caso especial para primeiro inner point
                if (slope == nextSlope) {
                    if (segment.peekLast() != slopePoint) {
                        segment.add(slopePoint);
                    }
                    segment.add(nextSlopePoint);
                }

                // momento da inversao ou final do array
                if (slope != nextSlope || j == points.length - 2) {
                    // se segmento possui >= 4 pontos
                    // deve salvar o segmento
                    if (segment.size() > 3) {

                        // so salva o segmento se o primeiro elemento
                        // for menor que o segundo
                        // isso devido a ordem natural
                        // isso evita a adicao de subsegmentos para outro valor de p.
                        Point first = segment.removeFirst();
                        Point second = segment.removeFirst();
                        Point last = segment.removeLast();
                        if (first.compareTo(second) < 0) {
                            result.add(new LineSegment(first, last));
                        }

                    }

                    // limpa o segmento e adiciona p novamente
                    segment.clear();
                    segment.add(pontoP);
                }
            }
        }

        // converte de linked list para array
        segments = new LineSegment[result.size()];
        lineSegmentsCount = segments.length;
        segments = result.toArray(segments);
    }

    public int numberOfSegments() {
        return lineSegmentsCount;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, lineSegmentsCount);
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
