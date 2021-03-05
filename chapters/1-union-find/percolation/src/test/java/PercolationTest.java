import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercolationTest {

  @Test
  void allClosedDoesntPercolate() {
    Percolation percolation = new Percolation(5);

    assertFalse(percolation.percolates());
  }

  @Test
  void openColumnPercolates() {
    Percolation percolation = new Percolation(5);
    for (int row = 1; row < 6; row++) {
      percolation.open(row, 3);
    }
    assertTrue(percolation.percolates());
  }

  @Test
  void allClosedHasZeroOpenSites() {
    Percolation percolation = new Percolation(6);

    assertEquals(0, percolation.numberOfOpenSites());
  }

}
