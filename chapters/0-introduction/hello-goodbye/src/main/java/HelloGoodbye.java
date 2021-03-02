/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class HelloGoodbye {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Needs at least two arguments");
      System.exit(1);
    }
    String name1 = args[0]; // In Java args[0] does NOT contain the program name
    String name2 = args[1];
    StdOut.println("Hello " + name1 + " and " + name2 + ".");
    StdOut.println("Goodbye " + name2 + " and " + name1 + ".");
  }
}
