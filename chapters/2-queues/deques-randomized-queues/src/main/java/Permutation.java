import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
  public static void main(String[] args) {
    final RandomizedQueue<String> queue = new RandomizedQueue<>();
    final int k = Integer.parseInt(args[0]);
    while (!StdIn.isEmpty()) {
      queue.enqueue(StdIn.readString());
    }
    for (int i = 0; i < k; i++) {
      StdOut.println(queue.dequeue());
    }
  }
}
