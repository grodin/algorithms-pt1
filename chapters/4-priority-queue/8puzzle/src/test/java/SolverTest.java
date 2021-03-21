import com.google.common.truth.Truth;

import org.junit.jupiter.api.Test;

class SolverTest {

  /*
   * The following board should be solvable
   *
   *    0  1  3
   *    4  2  5
   *    7  8  6
   */
  @Test
  void givenPuzzleIsSolvable() {
    var board = SliderTestUtils.board(
        SliderTestUtils.row(0, 1, 3),
        SliderTestUtils.row(4, 2, 5),
        SliderTestUtils.row(7, 8, 6)
    );
    final Solver solver = new Solver(board);
    Truth.assertThat(solver.isSolvable()).isTrue();
  }

  /*
   * The following board should have a solution length of 4
   *
   *    0  1  3
   *    4  2  5
   *    7  8  6
   */
  @Test
  void puzzle04IsSolvableIn4Moves() {
    var board = SliderTestUtils.board(
        SliderTestUtils.row(0, 1, 3),
        SliderTestUtils.row(4, 2, 5),
        SliderTestUtils.row(7, 8, 6)
    );
    final Solver solver = new Solver(board);
    Truth.assertThat(solver.moves()).isEqualTo(4);
  }

  /*
   * The following board should have a solution length of 4
   *
   *    1  2  3
   *    0  7  6
   *    5  4  8
   */
  @Test
  void puzzle07IsSolvableIn7Moves() {
    var board = SliderTestUtils.board(
        SliderTestUtils.row(1, 2, 3),
        SliderTestUtils.row(0, 7, 6),
        SliderTestUtils.row(5, 4, 8)
    );
    final Solver solver = new Solver(board);
    Truth.assertThat(solver.moves()).isEqualTo(7);
  }
}
