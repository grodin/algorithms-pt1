import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FastCollinearPointsTest {

  @Test
  void constructorThrowIAEWithDuplicatePoints() {
    var point = new Point(0, 0);
    assertThrows(IllegalArgumentException.class, () -> {
      new FastCollinearPoints(new Point[]{point, point});
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
    var fast = new FastCollinearPoints(points);
    assertEquals(2, fast.numberOfSegments());
  }

  @Test
  void input48() throws IOException {
    final var input48Txt = TestUtils.getResourceFileAsString("input48.txt");
    assert input48Txt != null;
    final var points = Client.getPoints(new In(new Scanner(input48Txt)));
    var fast = new FastCollinearPoints(points);
    final int numberOfSegments = fast.numberOfSegments();
    assertEquals(6, numberOfSegments);
  }
}
