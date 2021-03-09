import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private final int n;
  private final int trials;

  public PercolationStats(int n, int trials) {
    if (n < 1 || trials < 1) throw new IllegalArgumentException();
    this.n = n;
    this.trials = trials;
  }

  public double mean() {
    return StdStats.mean(multipleTrials());
  }

  public double stddev() {
    return StdStats.stddev(multipleTrials());
  }

  public double confidenceLo() {
    return mean() - confidenceIntervalRadius();
  }

  public double confidenceHi() {
    return mean() + confidenceIntervalRadius();
  }

  private double confidenceIntervalRadius() {
    return 1.96 * stddev() / Math.sqrt(trials);
  }

  public static void main(String[] args) {
    PercolationStats stats = new PercolationStats(
        Integer.parseInt(args[0]),
        Integer.parseInt(args[1])
    );
    final String meanLabel = "mean";
    final String stddevLabel = "stddev";
    final String confidenceIntervalLabel = "95% confidence interval";
    final int width = confidenceIntervalLabel.length();
    final String rightPadFormat = "%-" + width + "." + width + "s";
    StdOut.println(String.format(rightPadFormat, meanLabel) + " = " + stats.mean());
    StdOut.println(String.format(rightPadFormat, stddevLabel) + " = " + stats.stddev());
    StdOut.println(String.format(rightPadFormat, confidenceIntervalLabel) +
        " = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
  }

  private double runOneTrial() {
    final Percolation percolationLattice = new Percolation(n);
    int row, col, index;
    int count = 0;
    final int totalNumberOfSites = n * n;
    while (!percolationLattice.percolates()) {
      index = StdRandom.uniform(totalNumberOfSites);
      row = 1 + index / n;
      col = 1 + index % n;
      if (!percolationLattice.isOpen(row, col)) {
        percolationLattice.open(row, col);
        count += 1;
      }
    }
    return ((double) count) / (totalNumberOfSites);
  }

  private double[] multipleTrials() {
    double[] thresholdEstimates = new double[trials];
    for (int i = 0; i < trials; i++) {
      thresholdEstimates[i] = runOneTrial();
    }
    return thresholdEstimates;
  }
}
