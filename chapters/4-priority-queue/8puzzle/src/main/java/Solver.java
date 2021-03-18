import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {

  private final Board initialBoard;

  public Solver(final Board initialBoard) {
    if (initialBoard == null)
      throw new IllegalArgumentException("Board must be non-null");
    this.initialBoard = initialBoard;
  }

  public boolean isSolvable() {
    return moves() != -1;
  }

  public int moves() {
    final List<Board> solution = listSolution();
    if (solution == null) {
      return -1;
    } else {
      return solution.size();
    }
  }

  public Iterable<Board> solution() {
    return listSolution();
  }

  /**
   * @return null if initial board has no solution
   */
  private List<Board> listSolution() {
    var solution = new ArrayList<SearchNode>();
    var priorityQueue = new MinPQ<>(Comparator.comparing(SearchNode::hammingPriority));
    final SearchNode initialNode = new SearchNode(initialBoard, 0, null);
    priorityQueue.insert(initialNode);

    SearchNode currentNode = initialNode;
    while (!currentNode.board.isGoal()) {
      currentNode = priorityQueue.delMin();
      solution.add(currentNode);
      for (var neighbor : currentNode.board.neighbors()) {
        if (currentNode.previousNode == null || !currentNode.previousNode.board.equals(neighbor)) {
          priorityQueue.insert(
              new SearchNode(neighbor,
                  currentNode.movesToReachBoard + 1,
                  currentNode));
        }
      }
    }
    return solution.stream().map(node -> node.board).collect(Collectors.toList());
  }

  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }

  private final static class SearchNode {

    public final Board board;
    public final int movesToReachBoard;
    public final SearchNode previousNode;
    private final int hamming;
    private final int manhattan;

    private SearchNode(final Board board,
                       final int movesToReachBoard,
                       final SearchNode previousNode) {
      this.board = board;
      this.movesToReachBoard = movesToReachBoard;
      this.previousNode = previousNode;
      this.hamming = board.hamming();
      this.manhattan = board.manhattan();
    }

    public int hammingPriority() {
      return hamming;
    }

    public int manhattanPriority() {
      return manhattan;
    }

    @Override public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      final SearchNode that = (SearchNode) o;
      return movesToReachBoard == that.movesToReachBoard && board.equals(that.board) && previousNode.equals(that.previousNode);
    }

    @Override public int hashCode() {
      return Objects.hash(board, movesToReachBoard, previousNode);
    }

  }
}
