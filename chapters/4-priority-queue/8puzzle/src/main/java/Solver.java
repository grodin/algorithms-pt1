import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.StreamSupport;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {

  private SearchNode foundSearchNode;
  private final boolean solvable;

  public Solver(final Board initialBoard) {
    if (initialBoard == null)
      throw new IllegalArgumentException("Board must be non-null");

    var minPriorityQueue = new MinPQ<>(Comparator.comparing(SearchNode::manhattanPriority));
    minPriorityQueue.insert(new SearchNode(initialBoard, 0, null));

    var twinPriorityQueue = new MinPQ<>(Comparator.comparing(SearchNode::manhattanPriority));
    twinPriorityQueue.insert(new SearchNode(initialBoard.twin(), 0, null));

    SearchNode currentNode;
    SearchNode twinCurrentNode;
    while (true) {//HORRIBLE!
      currentNode = minPriorityQueue.delMin();
      if (currentNode != null && currentNode.board.isGoal()) {
        solvable = true;
        foundSearchNode = currentNode;
        return;
      }
      twinCurrentNode = twinPriorityQueue.delMin();
      if (twinCurrentNode != null && twinCurrentNode.board.isGoal()) {
        solvable = false;
        return;
      }

      addNeighborsToQueue(minPriorityQueue, currentNode);
      addNeighborsToQueue(twinPriorityQueue, twinCurrentNode);
    }
  }

  private static void addNeighborsToQueue(final MinPQ<SearchNode> priorityQueue, final SearchNode currentNode) {
    for (var neighbor : Objects.requireNonNull(currentNode).board.neighbors()) {
      if (currentNode.previousNode == null || !currentNode.previousNode.board.equals(neighbor)) {
        priorityQueue.insert(new SearchNode(
            neighbor, currentNode.movesToReachBoard + 1, currentNode
        ));
      }
    }
  }


  public boolean isSolvable() {
    return solvable;
  }

  public int moves() {
    if (!isSolvable()) {
      return -1;
    } else {
      return (int) StreamSupport
          .stream(solution().spliterator(), false)
          .count() - 1;
    }
  }

  public Iterable<Board> solution() {
    if (foundSearchNode == null)
      return null;
    var solutionBoards = new ArrayList<Board>();
    var currentNode = foundSearchNode;
    while (currentNode != null) {
      solutionBoards.add(currentNode.board);
      currentNode = currentNode.previousNode;
    }
    Collections.reverse(solutionBoards);
    return solutionBoards;
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
      return movesToReachBoard + hamming;
    }

    public int manhattanPriority() {
      return movesToReachBoard + manhattan;
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

    @Override public String toString() {
      return "SearchNode{" +
          "board=" + board +
          ", movesToReachBoard=" + movesToReachBoard +
          ", previousNode=" + previousNode +
          ", hamming=" + hamming +
          '}';
    }
  }

}
