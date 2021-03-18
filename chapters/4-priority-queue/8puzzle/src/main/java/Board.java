import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class Board {

  private final Tile SPACE;
  private final Tile[][] tiles;


  public Board(final int[][] tiles) {
    this(tiles, null);
  }

  private Board(final Board board, final Coord tileToSwap) {
    tiles = Arrays.stream(board.tiles)
        .map(Tile[]::clone).toArray($ -> board.tiles.clone());
    SPACE = board.SPACE;
    if (tileToSwap != null) {
      exchange(tiles, tileToSwap, coordsOf(SPACE));
    }
  }

  private Board(final int[][] tiles, final Coord tileToSwap) {
    assert tiles != null;
    final int dimension = tiles.length;
    assert 2 <= dimension && dimension < 128;
    for (var row : tiles) assert row.length == dimension;

    this.SPACE = new Tile(dimension * dimension - 1, 0);

    this.tiles = new Tile[dimension][dimension];

    for (int row = 0; row < dimension; row++) {
      for (int col = 0; col < dimension; col++) {
        final int entry = tiles[row][col];
        this.tiles[row][col] =
            new Tile(entryToId(entry), entry);
      }
    }

    if (tileToSwap != null) {
      exchange(this.tiles, coordsOf(SPACE), tileToSwap);
    }
  }

  private int entryToId(int label) {
    if (label == 0) return SPACE.id;
    return label - 1;
  }

  @Override public String toString() {
    var joiner = new StringJoiner("\n ")
        .add(Integer.toString(dimension()));
    for (final Tile[] row : tiles) {
      joiner.add(rowString(row));
    }
    return joiner.toString();
  }

  private String rowString(Tile[] row) {
    return Arrays.stream(row)
        .map(tile -> Integer.toString(tile.label))
        .collect(Collectors.joining(" "));
  }

  public int dimension() {
    return tiles.length;
  }

  public int hamming() {
    var count = 0;
    for (int row = 0; row < dimension(); row++) {
      for (int col = 0; col < dimension(); col++) {
        final Tile tile = tiles[row][col];
        if (!tile.equals(SPACE)) {
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
        final Tile tile = tiles[i][j];
        if (!tile.equals(SPACE))
          sum += tileManhattan(i, j);
      }
    }
    return sum;
  }

  private int tileManhattan(int row, int col) {
    final int entry = tiles[row][col].id;
    final var entryCoords = cooardsOfEntryOnGoalBoard(entry, dimension());
    return Math.abs(row - entryCoords.row) + Math.abs(col - entryCoords.col);
  }

  private static Coord cooardsOfEntryOnGoalBoard(final int entry, final int dimension) {
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
    return Arrays.deepEquals(tiles, board.tiles);
  }

  public Iterable<Board> neighbors() {
    return Move.availableMoves(coordsOf(SPACE), dimension())
        .stream()
        .map(coord -> new Board(this, coord))
        .collect(Collectors.toUnmodifiableList());
  }

  public Board twin() {
    return null;
  }

  public static void main(String[] args) {

  }

  private <T> void exchange(final T[][] tiles, final Coord coordsOf,
                            final Coord tileToSwap) {
    exchange(tiles, coordsOf.row, coordsOf.col, tileToSwap.row, tileToSwap.col);
  }


  private static <T> void exchange(T[][] array,
                                   int row1, int col1,
                                   int row2, int col2) {
    var value = array[row1][col1];
    array[row1][col1] = array[row2][col2];
    array[row2][col2] = value;
  }

  private Coord coordsOf(final Tile tile) {
    for (int row = 0; row < dimension(); row++) {
      for (int col = 0; col < dimension(); col++) {
        if (tiles[row][col].equals(tile)) return new Coord(row, col);
      }
    }
    throw new NoSuchElementException();
  }

  private static void check(boolean condition) {
    check(condition, "");
  }

  private static void check(boolean condition, String message) {
    if (!condition) throw new IllegalArgumentException(message);
  }

  private static final class Tile {
    public final int id;
    public final int label;

    private Tile(final int id, final int label) {
      this.id = id;
      this.label = label;
    }

    @Override public String toString() {
      return "Tile{" +
          "id=" + id +
          ", label='" + label + '\'' +
          '}';
    }

    @Override public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      final Tile tile = (Tile) o;
      return id == tile.id && label == tile.label;
    }

    @Override public int hashCode() {
      return Objects.hash(id, label);
    }
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
