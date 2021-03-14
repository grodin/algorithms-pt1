import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

  final Point origin = new Point(0, 0);

  @Test
  void slopeTo_ReturnsPositiveZeroForHorizontalSlopes() {
    double expectedSlope = 0.0;
    assertEquals(expectedSlope, origin.slopeTo(new Point(2, 0)));
    assertEquals(expectedSlope, origin.slopeTo(new Point(-20, 0)));
  }

  @Test
  void slopeTo_returnsNegativeInfinityForRepeatedPoints() {
    assertEquals(Double.NEGATIVE_INFINITY, origin.slopeTo(origin));
    var point = new Point(35, 35);
    assertEquals(Double.NEGATIVE_INFINITY, point.slopeTo(new Point(35, 35)));
  }

  @Test
  void slopeTo_returnsCorrectPositiveSlope() {
    var point = new Point(10, 10);
    assertEquals(1.0, origin.slopeTo(point));
    assertEquals(1.0, point.slopeTo(origin));
  }

  @Test
  void slopeTo_returnsCorrectNegativeSlope() {
    var point = new Point(10, -10);
    assertEquals(-1.0, origin.slopeTo(point));
    assertEquals(-1.0, point.slopeTo(origin));
  }

  @Test
  void sortingUsingComparableInstanceIsCorrect() {
    final Point pt1 = new Point(-10, -3);
    final Point pt2 = new Point(-10, -2);
    final Point pt3 = new Point(1, 2);
    var points = new Point[]{origin, pt1, pt2, pt3};
    Arrays.sort(points);

    var expectedPoints = new Point[]{pt1, pt2, origin, pt3};
    assertArrayEquals(expectedPoints, points);
  }

}
