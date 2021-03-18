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
}
