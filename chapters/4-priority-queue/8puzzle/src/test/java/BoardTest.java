import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
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

  /*
    The following board should have hamming() == 1
    1  2
    0  3
  */
  @Test
  void hamming_returnsCorrectValueFor2x2() {
    var board = board(
        row(1, 2),
        row(0, 3)
    );
    assertEquals(1, board.hamming());
  }

  /*
    The following board should have hamming() == 5
    8 1 3
    4 0 2
    7 6 5
  */
  @Test
  void hamming_returnsCorrectValue3x3() {
    var board = board(
        row(8, 1, 3),
        row(4, 0, 2),
        row(7, 6, 5)
    );
    assertEquals(5, board.hamming());
  }

  static <T> List<T> collectToList(Iterable<T> iterable) {
    var items = new ArrayList<T>();
    iterable.forEach(items::add);
    return items;
  }

  /*
    The following board should have 4 neighbors()
    8 1 3
    4 0 2
    7 6 5
  */
  @Test
  void neighborsIsCorrect4Neighbors() {
    var board = board(
        row(8, 1, 3),
        row(4, 0, 2),
        row(7, 6, 5)
    );
    var neighbors = collectToList(board.neighbors());
    var expected = List.of(
        board(
            row(8, 0, 3),
            row(4, 1, 2),
            row(7, 6, 5)
        ),
        board(
            row(8, 1, 3),
            row(0, 4, 2),
            row(7, 6, 5)
        ),
        board(
            row(8, 1, 3),
            row(4, 2, 0),
            row(7, 6, 5)
        ),board(
            row(8, 1, 3),
            row(4, 6, 2),
            row(7, 0, 5)
        )
    );
    assertThat(neighbors).containsExactlyElementsIn(expected);
  }

  /*
    The following board should have 3 neighbors()
    8 1 3
    0 4 2
    7 6 5
  */
  @Test
  void neighborsIsCorrect3Neighbors() {
    var board = board(
        row(8, 1, 3),
        row(0, 4, 2),
        row(7, 6, 5)
    );
    var neighbors = collectToList(board.neighbors());
    var expected = List.of(
        board(
            row(0, 1, 3),
            row(8, 4, 2),
            row(7, 6, 5)
        ),
        board(
            row(8, 1, 3),
            row(4, 0, 2),
            row(7, 6, 5)
        ),
        board(
            row(8, 1, 3),
            row(7, 4, 2),
            row(0, 6, 5)
        )
    );
    assertThat(neighbors).containsExactlyElementsIn(expected);
  }

  /*
    The following board should have 3 neighbors()
    8 1 3
    0 4 2
    7 6 5
  */
  @Test
  void neighborsIsCorrect2Neighbors() {
    var board = board(
        row(0, 1, 3),
        row(8, 4, 2),
        row(7, 6, 5)
    );
    var neighbors = collectToList(board.neighbors());
    var expected = List.of(
        board(
            row(8, 1, 3),
            row(0, 4, 2),
            row(7, 6, 5)
        ),
        board(
            row(1, 0, 3),
            row(8, 4, 2),
            row(7, 6, 5)
        )
    );
    assertThat(neighbors).containsExactlyElementsIn(expected);
  }


}
