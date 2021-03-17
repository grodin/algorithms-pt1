import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class Board {

  private final Tile SPACE;
  private final Tile[][] tiles;
  private final int dimension;

  public Board(final int[][] tiles) {
    assert tiles != null;
    dimension = tiles.length;
    assert 2 <= dimension && dimension < 128;
    for (var row : tiles) assert row.length == dimension;

    this.SPACE = new Tile(dimension * dimension - 1, "0");

    this.tiles = new Tile[dimension][dimension];

    for (int row = 0; row < dimension; row++) {
      for (int col = 0; col < dimension; col++) {
        this.tiles[row][col] = new Tile(
            labelToId(tiles[row][col]),
            Integer.toString(tiles[row][col]));
      }
    }
  }

  private int labelToId(int label) {
    if (label == 0) return SPACE.id;
    return label - 1;
  }

  @Override public String toString() {
    var joiner = new StringJoiner("\n ").add(Integer.toString(dimension()));
    for (final Tile[] row : tiles) {
      joiner.add(rowString(row));
    }
    return joiner.toString();
  }

  private String rowString(Tile[] row) {
    return Arrays.stream(row)
        .map(tile -> tile.label)
        .collect(Collectors.joining(" "));
  }

  public int dimension() {
    return tiles.length;
  }

  public int hamming() {
    return Integer.MIN_VALUE;
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
    return Math.abs(row - goalRowOfEntry(entry, dimension())) +
        Math.abs(col - goalColOfEntry(entry, dimension()));
  }

  private static int goalRowOfEntry(final int entry, final int dimension) {
    check(0 <= entry && entry < (dimension * dimension));
    return entry / dimension;
  }

  private static int goalColOfEntry(int entry, int dimension) {
    check(0 <= entry && entry < (dimension * dimension));
    return entry % dimension;
  }

  public boolean isGoal() {
    return false;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Board board = (Board) o;
    return Arrays.equals(tiles, board.tiles);
  }

  public Iterable<Board> neighbors() {
    return null;
  }

  public Board twin() {
    return null;
  }

  public static void main(String[] args) {

  }

  private static void check(boolean condition) {
    check(condition, "");
  }

  private static void check(boolean condition, String message) {
    if (!condition) throw new IllegalArgumentException(message);
  }

  private static final class Tile {
    public final int id;
    public final String label;

    private Tile(final int id, final String label) {
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
      return id == tile.id && label.equals(tile.label);
    }

    @Override public int hashCode() {
      return Objects.hash(id, label);
    }
  }

}
