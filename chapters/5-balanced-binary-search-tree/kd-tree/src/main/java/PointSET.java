import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {

  private final Set<Point2D> points = new TreeSet<>();

  public boolean isEmpty() {
    return points.isEmpty();
  }

  public int size() {
    return points.size();
  }

  public void insert(Point2D p) {
    points.add(requireNonNull(p));
  }

  public boolean contains(Point2D p) {
    return points.contains(requireNonNull(p));
  }

  public void draw() {
    for (var point: points){
      point.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    requireNonNull(rect);
    return points.stream()
        .filter(rect::contains)
        .collect(Collectors.toUnmodifiableList());
  }

  public Point2D nearest(Point2D p) {
    requireNonNull(p);
    return points.stream()
        .min(Comparator.comparing(p::distanceTo))
        .orElse(null);
  }

  public static void main(String[] args) {}

  private static <R> R requireNonNull(final R value) {
    if (value == null) {
      throw new IllegalArgumentException();
    }
    return value;
  }
}
