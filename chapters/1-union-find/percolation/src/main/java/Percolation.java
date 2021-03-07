import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private enum SiteState {CLOSED, OPEN}

  private final int size;
  private int openSiteCount = 0;
  private final WeightedQuickUnionUF siteConnections;
  private final SiteState[][] sites;
  private final int top; // virtual top site
  private final int bottom; // virtual bottom site

  public Percolation(int n) {
    size = n;
    sites = new SiteState[size][size];

    final int totalSites = size * size;
    siteConnections = new WeightedQuickUnionUF(totalSites + 2);
    top = totalSites;
    bottom = top + 1;

    for (int i = 0; i < size; i++) {
      //connect top row to virtual top site
      siteConnections.union(top, i);

      //connect bottom row to virtual bottom site
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
      if (isOpen(neighbour.row, neighbour.col)) {
        final int neighbourIndex = coordsToIndex(neighbour.row, neighbour.col
            , size);
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
    return isOpen(row, col) &&
        siteConnections.connected(top, coordsToIndex(row, col, size));
  }

  public int numberOfOpenSites() {
    return openSiteCount;
  }

  public boolean percolates() {
    return siteConnections.connected(top, bottom);
  }

  private void checkRowAndColBounds(final int row, final int col) {
    checkRowAndColBounds(row, col, size);
  }

  static List<Coord> neighbours(final int row, final int col, final int size) {
    checkRowAndColBounds(row, col, size);
    final List<Coord> neighbours = new ArrayList<>(4);
    if (col > 1) {
      neighbours.add(new Coord(row, col - 1)); // Left neighbour
    }
    if (col < size) {
      neighbours.add(new Coord(row, col + 1)); // Right neighbour
    }
    if (row > 1) {
      neighbours.add(new Coord(row - 1, col)); // Top neighbour
    }
    if (row < size) {
      neighbours.add(new Coord(row + 1, col)); // Bottom neighbour
    }
    return neighbours;
  }

  static void checkRowAndColBounds(final int row, final int col,
                                   final int size) {
    check(1 <= row && row <= size, "Row must be between 1 and " + size);
    check(1 <= col && col <= size, "Col must be between 1 and " + size);
  }

  static int coordsToIndex(final int row, final int col, final int size) {
    checkRowAndColBounds(row, col, size);
    return (row - 1) * size + col - 1;
  }

  static Coord indexToCoords(final int index, final int size) {
    check(index >= 0 && index < size * size, "Index not within bounds");
    final int row = (index / size) + 1;
    final int col = (index % size) + 1;
    return new Coord(row, col);
  }

  static void check(boolean test, String message) {
    if (!test) {
      throw new IllegalArgumentException(message);
    }
  }

  final static class Coord {
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

    @Override public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      final Coord coord = (Coord) o;
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
