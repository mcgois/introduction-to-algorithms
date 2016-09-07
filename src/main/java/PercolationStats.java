import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] ratios;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid size of grid and trials.");
        }

        this.ratios = new double[trials];

        // monte carlo trials
        for (int trial = 0; trial < trials; trial++) {
            Percolation percolation = new Percolation(n);
            int countOpen = 0;
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, n + 1); // exclusive on n + 1
                int j = StdRandom.uniform(1, n + 1); // exclusive on n + 1

                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    countOpen++;
                }
            }

            ratios[trial] = ((double) countOpen) / ((double) n * n);
        }
    }

    public double mean() {
        return StdStats.mean(this.ratios);
    }

    public double stddev() {
        return StdStats.stddev(this.ratios);
    }

    public double confidenceLo() {
        double mean = this.mean();
        double stddev = this.stddev();
        return mean - 1.96 * stddev / Math.sqrt(ratios.length);
    }

    public double confidenceHi() {
        double mean = this.mean();
        double stddev = this.stddev();
        return mean + 1.96 * stddev / Math.sqrt(ratios.length);
    }

    public static void main(String[] args) {
        // read n and trials
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        // run monte carlo simulation
        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean                    = "
                + stats.mean());
        System.out.println("stddev                  = "
                + stats.stddev());
        System.out.println("95% confidence interval = "
                + stats.confidenceLo() + ", " + stats.confidenceHi());
    }

}