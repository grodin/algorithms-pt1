import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

  static int[] row(int... values) {
    return values;
  }

  static Board board(int[]... rows) {
    return new Board(rows);
  }

  @Test
  void toString_returnsOutput() {
    var board = board(
        row(1, 2, 3),
        row(4, 5, 6),
        row(7, 8, 0)
    );
    var expected = "3\n 1 2 3\n 4 5 6\n 7 8 0";
    assertEquals(expected, board.toString());
  }

  /*
    The following board should have manhattan() == 1
    1  2
    0  3
  */
  @Test
  void manhattan_returnsCorrectValueFor2x2() {
    var board = board(
        row(1, 2),
        row(0, 3)
    );
    assertEquals(1, board.manhattan());
  }

  /*
    The following board should have manhattan() == 10
    8 1 3
    4 0 2
    7 6 5
  */
  @Test
  void manhattan_returnsCorrectValue3x3() {
    var board = board(
        row(8, 1, 3),
        row(4, 0, 2),
        row(7, 6, 5)
    );
    assertEquals(10, board.manhattan());
  }

}
