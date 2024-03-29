import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class Board {

  private final int SPACE;
  private final int[][] tileIds;


  public Board(final int[][] tiles) {
    this(tiles, null);
  }

  private Board(final Board board, final Coord tileToSwapWithSpace) {
    this(board, tileToSwapWithSpace, board.coordsOf(board.SPACE));
  }

  private Board(final Board board, final Coord tile1, final Coord tile2) {
    tileIds = Arrays.stream(board.tileIds)
        .map(int[]::clone).toArray($ -> board.tileIds.clone());
    SPACE = board.SPACE;
    if (tile1 != null && tile2 != null) {
      exchange(tileIds, tile1, tile2);
    }
  }

  private Board(final int[][] tiles, final Coord tileToSwapWithSpace) {
    assert tiles != null;
    final int dimension = tiles.length;
    assert 2 <= dimension && dimension < 128;
    for (var row : tiles) assert row.length == dimension;

    this.SPACE = dimension * dimension - 1;

    this.tileIds = new int[dimension][dimension];

    for (int row = 0; row < dimension; row++) {
      for (int col = 0; col < dimension; col++) {
        final int entry = tiles[row][col];
        this.tileIds[row][col] = entryToId(entry);
      }
    }

    if (tileToSwapWithSpace != null) {
      exchange(this.tileIds, coordsOf(SPACE), tileToSwapWithSpace);
    }
  }

  private int entryToId(int label) {
    if (label == 0) return SPACE;
    return label - 1;
  }

  private String idToLabel(int id) {
    if (id == SPACE) {
      return String.valueOf(0);
    } else {
      return String.valueOf(id + 1);
    }
  }

  @Override public String toString() {
    var joiner = new StringJoiner("\n ")
        .add(Integer.toString(dimension()));
    for (final int[] row : tileIds) {
      joiner.add(rowString(row));
    }
    return joiner.toString();
  }

  private String rowString(int[] row) {
    return Arrays.stream(row)
        .mapToObj(this::idToLabel)
        .collect(Collectors.joining(" "));
  }

  public int dimension() {
    return tileIds.length;
  }

  public int hamming() {
    var count = 0;
    for (int row = 0; row < dimension(); row++) {
      for (int col = 0; col < dimension(); col++) {
        final int tile = tileIds[row][col];
        if (tile != SPACE) {
          if (tileManhattan(row, col) != 0) {
            count += 1;
          }
        }
      }
    }
    return count;
  }

  public int manhattan() {
    var sum = 0;
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        final int tile = tileIds[i][j];
        if (tile != SPACE)
          sum += tileManhattan(i, j);
      }
    }
    return sum;
  }

  private int tileManhattan(int row, int col) {
    final int entry = tileIds[row][col];
    final var entryCoords = coordsOfEntryOnGoalBoard(entry, dimension());
    return Math.abs(row - entryCoords.row) + Math.abs(col - entryCoords.col);
  }

  private static Coord coordsOfEntryOnGoalBoard(final int entry,
                                                final int dimension) {
    check(0 <= entry && entry < (dimension * dimension));
    return new Coord(entry / dimension, entry % dimension);
  }

  public boolean isGoal() {
    return manhattan() == 0;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Board board = (Board) o;
    return Arrays.deepEquals(tileIds, board.tileIds);
  }

  public Iterable<Board> neighbors() {
    return Move.availableMoves(coordsOf(SPACE), dimension())
        .stream()
        .map(coord -> new Board(this, coord))
        .collect(Collectors.toUnmodifiableList());
  }

  public Board twin() {
    return new Board(this, coordsOf(0), coordsOf(1));
  }

  public static void main(String[] args) {

  }

  private void exchange(final int[][] tiles,
                        final Coord tile1,
                        final Coord tile2) {
    exchange(tiles, tile1.row, tile1.col, tile2.row, tile2.col);
  }


  private static void exchange(int[][] array,
                               int row1, int col1,
                               int row2, int col2) {
    var value = array[row1][col1];
    array[row1][col1] = array[row2][col2];
    array[row2][col2] = value;
  }

  private Coord coordsOf(final int tile) {
    for (int row = 0; row < dimension(); row++) {
      for (int col = 0; col < dimension(); col++) {
        if (tileIds[row][col] == (tile)) return new Coord(row, col);
      }
    }
    throw new NoSuchElementException();
  }

  private static void check(boolean condition) {
    if (!condition) throw new IllegalArgumentException();
  }

  private enum Move {
    ABOVE(-1, 0),
    BELOW(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    ;
    private final int rowRelativeToSpace;
    private final int colRelativeToSpace;

    Move(final int rowRelativeToSpace, final int colRelativeToSpace) {
      this.rowRelativeToSpace = rowRelativeToSpace;
      this.colRelativeToSpace = colRelativeToSpace;
    }

    public Coord apply(Coord coord) {
      return new Coord(coord.row + this.rowRelativeToSpace,
          coord.col + this.colRelativeToSpace);
    }

    public static List<Coord> availableMoves(Coord coordOfSpace,
                                             int dimension) {
      return Arrays.stream(Move.values())
          .map(move -> move.apply(coordOfSpace))
          .filter(coord -> coord.isInBounds(dimension))
          .collect(Collectors.toList());
    }


  }

  private static final class Coord {
    final int row;
    final int col;

    Coord(int row, int col) {
      this.row = row;
      this.col = col;
    }

    boolean isInBounds(final int dimension) {
      return 0 <= this.row && this.row < dimension &&
          0 <= this.col && this.col < dimension;
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
