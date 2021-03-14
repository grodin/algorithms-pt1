import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BruteCollinearPoints {

  private final List<LineSegment> foundSegments;

  public BruteCollinearPoints(final Point[] points) {
    check(points != null);
    for (Point pt : points) check(pt != null);

    foundSegments = computeSegments(throwOnDuplicates(points));
  }

  public int numberOfSegments() {
    return segments().length;
  }

  public LineSegment[] segments() {
    var resultArray = new LineSegment[foundSegments.size()];
    return foundSegments.toArray(resultArray);
  }

  private static List<LineSegment> computeSegments(final Point[] source) {
    final List<LineSegment> foundSegments = new ArrayList<>(4);
    List<Point> segment = new ArrayList<>(4);
    for (int i = 0; i < source.length - 3; i++) {
      for (int j = i + 1; j < source.length - 2; j++) {
        for (int k = j + 1; k < source.length - 1; k++) {
          for (int l = k + 1; l < source.length; l++) {
            var p = source[i];
            var q = source[j];
            var r = source[k];
            var s = source[l];
            if (collinear(p, q, r, s)) {
              segment.clear();
              segment.addAll(List.of(p, q, r, s));
              segment.sort(Comparator.naturalOrder());
              foundSegments.add(new LineSegment(segment.get(0), segment.get(3)));
            }
          }
        }
      }
    }
    return foundSegments;
  }

  private static boolean collinear(Point p, Point q, Point r, Point s) {
    return p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s);
  }

  private static void check(boolean condition) {
    if (!condition) throw new IllegalArgumentException();
  }

  private static Point[] throwOnDuplicates(final Point[] source) {
    for (int i = 0; i < source.length; i++) {
      if (arrayContainsDuplicateOf(source, i)) {
        throw new IllegalArgumentException("Duplicate points found: " + source[i]);
      }
    }
    return source;
  }

  private static <T extends Comparable<T>> boolean arrayContainsDuplicateOf(
      final T[] array,
      final int indexOfValue) {
    final var needle = array[indexOfValue];
    for (int i = 0; i < array.length; i++) {
      if (i != indexOfValue && array[i] != null && needle.compareTo(array[i]) == 0) {
        return true;
      }
    }
    return false;
  }

}
