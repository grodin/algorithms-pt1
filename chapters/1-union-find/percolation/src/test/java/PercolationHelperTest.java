import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PercolationHelperTest {

  @Test
  void coordsToIndexWorks() {
    int row = 3;
    int col = 2;
    int size = 5;

    int expected = 11;

    final int result = Percolation.coordsToIndex(row, col, size);
    assertEquals(expected, result);
  }

  @Test
  void indexToCoordsWorks() {
    final int index = 11;
    final int size = 5;
    final Percolation.Coord expected = new Percolation.Coord(3, 2);

    assertEquals(expected, Percolation.indexToCoords(index, size));
  }
}
