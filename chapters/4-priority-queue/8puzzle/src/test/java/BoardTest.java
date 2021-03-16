import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

  @Test
  void toString_returnsOutput() {
    var board = new Board(new int[][]{
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    });
    var expected = "3\n1 2 3\n4 5 6\n7 8 0";
    assertEquals(expected, board.toString());
  }
}
