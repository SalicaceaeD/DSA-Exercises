import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> lineSegments;

    /**
     * Finds all line segments containing 4 points.
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null || points.length == 0) {
            throw new IllegalArgumentException();
        }
        int n = points.length;
        for (int i = 0; i < n; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < i; ++j) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        List<LineSegment> list = new ArrayList<>();
        Point[] pointsArray = points.clone();
        for (int i = 0; i < n; ++i) {
            Arrays.sort(pointsArray);
            Arrays.sort(pointsArray, points[i].slopeOrder());
            int run = 0;
            for (int j = 0; j < n; j = run) {
                while (run < n
                        && points[i].slopeTo(pointsArray[run]) == points[i].slopeTo(pointsArray[j])) {
                    ++run;
                }
                if (run - j < 3) {
                    continue;
                }
                boolean check = true;
                for (int k = j; k < run; ++k) {
                    check &= (points[i].compareTo(pointsArray[k]) < 0);
                }
                if (check) {
                    LineSegment line = new LineSegment(points[i], pointsArray[run - 1]);
                    list.add(line);
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
}