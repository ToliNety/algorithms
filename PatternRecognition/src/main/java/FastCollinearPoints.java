import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private LineSegmentHolder segmentHolder = new LineSegmentHolder();

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        Arrays.sort(points);

        for (int i = 0; i < points.length - 3; i++) {
            Point[] relatedPoints = Arrays.copyOfRange(points, i + 1, points.length);
            Point p = points[i];
            Arrays.sort(relatedPoints, p.slopeOrder());

            Point lastPoint = relatedPoints[0];
            double prevSlope = p.slopeTo(lastPoint);
            int sameSlopeCounter = 1;
            boolean continueLoop = true;
            for (int j = 1; j < relatedPoints.length; j++) {
                Point nextPoint = relatedPoints[j];
                double nextSlope = p.slopeTo(nextPoint);
                if (prevSlope == nextSlope) {
                    sameSlopeCounter++;
                    lastPoint = nextPoint;
                } else if (sameSlopeCounter > 2) {
                    segmentHolder.add(p, lastPoint);
                    sameSlopeCounter = 1;
                } else {
                    prevSlope = nextSlope;
                    sameSlopeCounter = 1;
                    lastPoint = nextPoint;
                }
            }
            if (sameSlopeCounter > 2) {
                segmentHolder.add(p, lastPoint);
            }

        }
    }

    public int numberOfSegments() {
        return segmentHolder.numberOfSegments();
    }

    public LineSegment[] segments() {
        return segmentHolder.segments();
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

    private static class LineSegmentHolder{
        private List<LineSegment> segments = new LinkedList<>();
        private List<Double> segmentSlopes = new LinkedList<>();
        private List<Point> segmentLastPoints = new LinkedList<>();

        public void add (Point p, Point q){
            double slope = p.slopeTo(q);
            boolean isFound = false;
            for (int i = 0; i < segmentSlopes.size(); i++) {
                Double slopeIter = segmentSlopes.get(i);
                if (slopeIter.equals(slope) && segmentLastPoints.get(i) != null && segmentLastPoints.get(i).equals(q)){
                        isFound = true;
                        break;
                }
            }

            if(!isFound){
                segmentSlopes.add(slope);
                segmentLastPoints.add(q);
                segments.add(new LineSegment(p, q));
            }

        }

        public LineSegment[] segments(){
            return segments.toArray(new LineSegment[0]);
        }

        public int numberOfSegments() {
            return segments.size();
        }
    }
}
