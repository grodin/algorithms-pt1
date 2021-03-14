import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BruteCollinearPointsTest {

  @Test
  void constructorThrowsIAEWithDuplicatePoints() {
    var point = new Point(0, 0);
    assertThrows(IllegalArgumentException.class, () -> {
      new BruteCollinearPoints(new Point[]{point, point});
    });
  }

  @Test
  void input8() {
    final Point endPoint1 = new Point(10000, 0);
    final Point endPoint2 = new Point(0, 10000);
    var points = new Point[]{
        endPoint1,
        endPoint2,
        new Point(3000, 7000),
        new Point(7000, 3000),
        new Point(20000, 21000),
        new Point(3000, 4000),
        new Point(14000, 15000),
        new Point(6000, 7000),
    };
    var brute = new BruteCollinearPoints(points);
    assertEquals(2, brute.numberOfSegments());
  }
}
