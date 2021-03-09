import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private enum SiteState {
    CLOSED, OPEN
  }

  private final int size;
  private int openSiteCount = 0;
  private final WeightedQuickUnionUF siteConnections;
  private final SiteState[][] sites;
  private final int top; // virtual top site
  private final int bottom; // virtual bottom site

  public Percolation(int n) {
    check(n > 0, "N must be positive");
    size = n;
    sites = new SiteState[size][size];

    final int totalSites = size * size;
    siteConnections = new WeightedQuickUnionUF(totalSites + 2);
    top = totalSites;
    bottom = top + 1;

    for (int i = 0; i < size; i++) {
      // connect top row to virtual top site
      siteConnections.union(top, i);

      // connect bottom row to virtual bottom site
      siteConnections.union(bottom, totalSites - i - 1);
    }
  }

  public void open(int row, int col) {
    // No need to check bounds, will be called immediately in isOpen()
    if (isOpen(row, col)) { // Already open, nothing to do
      return;
    }
    final int indexOfSiteToOpen = coordsToIndex(row, col, size);
    sites[row - 1][col - 1] = SiteState.OPEN;
    openSiteCount += 1;

    // Connect to open neighbours
    for (Coord neighbour : neighbours(row, col, size)) {
      if (neighbour != null && isOpen(neighbour.row, neighbour.col)) {
        final int neighbourIndex =
            coordsToIndex(neighbour.row, neighbour.col, size);
        siteConnections.union(indexOfSiteToOpen, neighbourIndex);
      }
    }
  }

  public boolean isOpen(int row, int col) {
    checkRowAndColBounds(row, col);
    return sites[row - 1][col - 1] == SiteState.OPEN;
  }

  public boolean isFull(int row, int col) {
    checkRowAndColBounds(row, col);
    final int indexToCheck = coordsToIndex(row, col, size);
    boolean isFull = false;
    for (int i = 1; i <= size; i++) {
      isFull = isFull || connected(indexToCheck, coordsToIndex(1, i, size));
    }
    return isOpen(row, col) && isFull;
  }

  private boolean connected(int site, int otherSite) {
    return siteConnections.find(site) == siteConnections.find(otherSite);
  }

  public int numberOfOpenSites() {
    return openSiteCount;
  }

  public boolean percolates() {
    return connected(top, bottom);
  }

  private void checkRowAndColBounds(final int row, final int col) {
    checkRowAndColBounds(row, col, size);
  }

  private static Coord[] neighbours(final int row, final int col,
                                    final int size) {
    checkRowAndColBounds(row, col, size);
    final Coord[] neighbours = new Coord[4];
    if (col > 1) {
      neighbours[0] = new Coord(row, col - 1); // Left neighbour
    }
    if (col < size) {
      neighbours[1] = (new Coord(row, col + 1)); // Right neighbour
    }
    if (row > 1) {
      neighbours[2] = (new Coord(row - 1, col)); // Top neighbour
    }
    if (row < size) {
      neighbours[3] = (new Coord(row + 1, col)); // Bottom neighbour
    }
    return neighbours;
  }

  private static void checkRowAndColBounds(final int row, final int col,
                                           final int size) {
    Percolation.check(1 <= row && row <= size,
        "Row must be between 1 and " + size);
    Percolation.check(1 <= col && col <= size,
        "Col must be between 1 and " + size);
  }

  private static int coordsToIndex(final int row, final int col,
                                   final int size) {
    checkRowAndColBounds(row, col, size);
    return (row - 1) * size + col - 1;
  }

  private static void check(boolean test, String message) {
    if (!test) {
      throw new IllegalArgumentException(message);
    }
  }

  private static final class Coord {
    final int row;
    final int col;

    Coord(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override public String toString() {
      return "Coord{" +
          "row=" + row +
          ", col=" + col +
          '}';
    }

    @Override public boolean equals(final Object other) {
      if (this == other) return true;
      if (other == null || getClass() != other.getClass()) return false;
      final Coord coord = (Coord) other;
      return row == coord.row && col == coord.col;
    }

    @Override public int hashCode() {
      int result = 1;
      result = 31 * result + Integer.hashCode(row);
      result = 31 * result + Integer.hashCode(col);
      return result;
    }
  }
}
