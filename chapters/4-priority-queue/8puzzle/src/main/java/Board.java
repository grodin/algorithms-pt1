import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class Board {

  private static final int SPACE = 0;
  private final int[][] tiles;

  public Board(final int[][] tiles) {
    assert tiles != null;
    assert 2 <= tiles.length && tiles.length < 128;
    for (var row : tiles) assert row.length == tiles.length;
    this.tiles = tiles;
  }

  @Override public String toString() {
    var joiner = new StringJoiner("\n").add(Integer.toString(dimension()));
    for (final int[] row : tiles) {
      joiner.add(rowString(row));
    }
    return joiner.toString();
  }

  private String rowString(int[] row) {
    return Arrays.stream(row)
        .mapToObj(Integer::toString)
        .collect(Collectors.joining(" "));
  }

  public int dimension() {
    return tiles.length;
  }

  public int hamming() {
    return Integer.MIN_VALUE;
  }

  public int manhattan() {
    return Integer.MIN_VALUE;
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

  private static Board goalBoard(int size) {
    assert size >= 2;
    var tiles = new int[size][size];
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        tiles[row][col] = row * size + col + 1;
      }
    }
    tiles[size - 1][size - 1] = Board.SPACE;
    return new Board(tiles);
  }
}
