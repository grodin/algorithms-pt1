import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
  public static void main(String[] args) {
    String champion = "";
    int index = 0;
    while (!StdIn.isEmpty()) {
      String nextInput = StdIn.readString();
      double p = 1.0 / ++index;
      if (StdRandom.bernoulli(p)) {
        champion = nextInput;
      }
    }
    StdOut.println(champion);
  }
}
