import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> segments = new LinkedList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        Arrays.sort(points);

        for (int i = 0; i < points.length - 3; i++) {
            boolean isNotFound = true;
            for (int j = i + 1; j < points.length - 2 && isNotFound; j++) {
                for (int k = j + 1; k < points.length - 1 && isNotFound; k++) {
                    for (int l = k + 1; l < points.length && isNotFound; l++) {
                        Point p0 = points[i];
                        Point p1 = points[j];
                        Point p2 = points[k];
                        Point p3 = points[l];
                        double baseSlope = p0.slopeTo(p1);
                        if (baseSlope == p1.slopeTo(p2) && baseSlope == p2.slopeTo(p3)){
                            segments.add(new LineSegment(p0, p3));
                            isNotFound = false;
                        }

                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
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
