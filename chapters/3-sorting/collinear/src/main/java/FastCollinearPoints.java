import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FastCollinearPoints {
  private final List<LineSegment> foundSegments;

  public FastCollinearPoints(final Point[] points) {
    check(points != null);
    for (Point pt : points) check(pt != null);
    final List<Point> pts = new ArrayList<>(Arrays.asList(points));
    pts.sort(Comparator.naturalOrder());
    throwOnDuplicates(pts);
    foundSegments = computeSegments(pts);
  }

  public int numberOfSegments() {
    return segments().length;
  }

  public LineSegment[] segments() {
    var resultArray = new LineSegment[foundSegments.size()];
    return foundSegments.toArray(resultArray);
  }

  private List<LineSegment> computeSegments(final List<Point> points) {
    final List<LineSegment> foundSegments = new ArrayList<>();
    List<Point> workingArray;
    for (var p : points) {
      workingArray = new ArrayList<>(points);
      workingArray.remove(p);
      workingArray.sort(p.slopeOrder());
      while (!workingArray.isEmpty()) {
        var first = workingArray.get(0);
        final List<Point> segment =
            workingArray.stream()
                .takeWhile((pt) -> p.slopeTo(pt) == p.slopeTo(first))
                .collect(Collectors.toList());

        if (segment.size() >= 3) {
          segment.add(p);
          segment.sort(Comparator.naturalOrder());

          final Point startPoint = segment.get(0);
          final Point endPoint = segment.get(segment.size() - 1);
          if (p == startPoint) {
            foundSegments.add(
                new LineSegment(startPoint, endPoint)
            );
          }
        }
        workingArray.removeAll(segment);
      }
    }


    return foundSegments;
  }

  private static void check(boolean condition) {
    if (!condition) throw new IllegalArgumentException();
  }

  private static void throwOnDuplicates(final List<Point> source) {
    for (int i = 0; i < source.size() - 1; i++) {
      if (comparableEquals(source.get(i), source.get(i + 1))) {
        throw new IllegalArgumentException();
      }
    }
  }

  private static <T extends Comparable<T>> boolean comparatorEquals(Comparator<T> comparator, T val1, T val2) {
    return comparator.compare(val1, val2) == 0;
  }

  private static <T extends Comparable<T>> boolean comparableEquals(T val1,
                                                                    T val2) {
    return comparatorEquals(Comparator.naturalOrder(), val1, val2);
  }


}


