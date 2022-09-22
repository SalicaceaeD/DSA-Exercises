import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> lineSegments;

    /**
     * Finds all line segments containing 4 points.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null || points.length == 0) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        List<LineSegment> list = new ArrayList<>();
        int n = points.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
                for (int k = j + 1; k < n; ++k) {
                    for (int m = k + 1; m < n; ++m) {
                        double slop1 = points[i].slopeTo(points[j]);
                        double slop2 = points[i].slopeTo(points[k]);
                        double slop3 = points[i].slopeTo(points[m]);
                        if ((slop1 != slop2) || (slop1 != slop3)) {
                            continue;
                        }
                        List<Point> pointList = new ArrayList<>();
                        pointList.add(points[i]);
                        pointList.add(points[j]);
                        pointList.add(points[k]);
                        pointList.add(points[m]);
                        Collections.sort(pointList);
                        Point tmp = pointList.get(0);
                        Collections.sort(pointList, tmp.slopeOrder());
                        list.add(new LineSegment(pointList.get(0), pointList.get(3)));
                    }
                }
            }
        }
        lineSegments = list;
    }

    /**
     * The number of line segments.
     */
    public int numberOfSegments() {
        return lineSegments.size();
    }

    /**
     * The line segments.
     */
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    public static void main(String[] args) {

    }
}