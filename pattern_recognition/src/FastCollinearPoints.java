import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        this.lineSegments = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            Comparator<Point> pointComparator = points[i].slopeOrder();
            Point[] sortedSlopes = new Point[points.length - (i + 1)];
            System.arraycopy(points, i + 1, sortedSlopes, 0, points.length
                    - (i + 1));
            Arrays.sort(sortedSlopes, 0, sortedSlopes.length, pointComparator);
            Point min = points[i];
            Point max = points[i];
            double slope = -1;
            int collinears = 1;
            boolean lineAlreadyAdded = false;
            StdOut.println("---------------------------------------------------------------------");
            StdOut.println("i: " + i + " points[i]: " + points[i]);
            for (int j = 0; j < sortedSlopes.length; j++) {
                StdOut.println("j: " + j + " sortedSlope[j] " + sortedSlopes[j]
                        + " points[i].slopeTo(sort[j]: "
                        + points[i].slopeTo(sortedSlopes[j]));
                if (slope != -1) {
                    if (points[i].slopeTo(sortedSlopes[j]) == slope) {
                        collinears++;
                        if (sortedSlopes[j].compareTo(max) >= 0)
                            max = sortedSlopes[j];
                        else if (sortedSlopes[j].compareTo(min) < 0)
                            min = sortedSlopes[j];
                        StdOut.println("In While loop, j = " + j
                                + "  , collinears = " + collinears);
                        if (j != sortedSlopes.length - 1)
                            continue;
                    }
                    if (collinears >= 4) {
                        StdOut.println("Added Segment: Min: " + min + "  Max: "
                                + max);
                        for (int k = i-1; k>=0; k--) {
                            if (points[i].slopeTo(points[k]) == slope) {
                                //We have already added this Line
                                lineAlreadyAdded = true;
                            }
                        }
                        if (!lineAlreadyAdded)
                            lineSegments.add(new LineSegment(min, max));
                        slope = points[i].slopeTo(sortedSlopes[j]);
                        collinears = 2;
                        if (sortedSlopes[j].compareTo(max) >= 0) {
                            max = sortedSlopes[j];
                            min = points[i];
                        }
                        else if (sortedSlopes[j].compareTo(min) < 0) {
                            min = sortedSlopes[j];
                            max = points[i];
                        }
                        else
                            min = max = points[i];
                    } else {
                        if (sortedSlopes[j].compareTo(max) >= 0) {
                            max = sortedSlopes[j];
                            min = points[i];
                        }
                        else if (sortedSlopes[j].compareTo(min) < 0) {
                            min = sortedSlopes[j];
                            max = points[i];
                        }
                        else
                            min = max = points[i];
                        slope = points[i].slopeTo(sortedSlopes[j]);
                        collinears = 2;
                    }
                } else {
                    collinears += 1;
                    slope = points[i].slopeTo(sortedSlopes[j]);
                    if (sortedSlopes[j].compareTo(max) >= 0) {
                        max = sortedSlopes[j];
                        min = points[i];
                    }
                    else if (sortedSlopes[j].compareTo(min) < 0) {
                        min = sortedSlopes[j];
                        max = points[i];
                    }
                }
            }
        }
    }

    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[lineSegments.size()];
        ret = lineSegments.toArray(ret);
        return ret;
    }

}
