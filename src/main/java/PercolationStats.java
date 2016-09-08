/**
 * Matheus Cabral
 * Semptember 7th, 2016
 *
 * PercolationStats. Executes a Monte Carlo Simulation of the
 * Percolation problem.
 *
 * To execute, run: java PercolationStats "n" "trials", where n is the size of
 * the grid system and trials is the number of iteractions on the Monte Carlo
 * simulation.
 * e.g.
 *
 * java PercolationStats 200 100
 * java PercolationStats 200 10000
 *
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * PercolationStats Class.
 *
 * Formula QuickFind: TRIALS * 7,7622E-10 * N ^ 3.8
 * Formula WeightedQuickUnion: TRIALS * 3,07935E-9 * N ^ 2.5
 *
 */
public class PercolationStats {

    // mean of trial results
    private double mean;

    // stddev of trial results
    private double stddev;

    // lower confidence limit of trial results
    private double confidenceLo;

    // upper confidence limit of trial results
    private double confidenceHi;

    /**
     * Executes the Monte Carlo Simulation to estimate percolation threshold on
     * a n based grid system.
     *
     * @param n - size of the grid.
     * @param trials - number of trials of the Monte Carlo simulation.
     */
    public PercolationStats(int n, int trials) {
        // check limits
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid size of grid and trials.");
        }

        // stores rations of each trial
        double[] ratios = new double[trials];

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

        // computes mean, stddev and confidence limits
        double sqrtLength = Math.sqrt(ratios.length);
        this.mean = StdStats.mean(ratios);
        this.stddev = StdStats.stddev(ratios);
        this.confidenceLo = this.mean - 1.96 * this.stddev / sqrtLength;
        this.confidenceHi = this.mean + 1.96 * this.stddev / sqrtLength;
    }

    /**
     * Returns the mean of trial results.
     *
     * @return double - mean of trial results.
     */
    public double mean() {
        return this.mean;
    }

    /**
     * Returns the standard deviation of trial results.
     * Returns Double.NaN if number of trials is 1.
     *
     * @return double - stardard deviation of trial results.
     */
    public double stddev() {
        return this.stddev;
    }

    /**
     * Returns the 95% confidence lower limit of trial results.
     * Returns Double.NaN if number of trial is 1.
     *
     * @return double - 95% confidence lower limit.
     */
    public double confidenceLo() {
        return this.confidenceLo;
    }

    /**
     * Returns the 95% confidence upper limit of trial results.
     * Returns Double.NaN if number of trial is 1.
     *
     * @return double - 95% confidence upper limit.
     */
    public double confidenceHi() {
        return this.confidenceHi;
    }

    /**
     * Main method to execute simulation.
     *
     * @param args - two integer expected argument.
     *             First must be the size of the grid.
     *             Second must be the number of trials.
     */
    public static void main(String[] args) {
        // read n and trials
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        // Stopwatch
        Stopwatch stopwatch = new Stopwatch();

        // run monte carlo simulation
        PercolationStats stats = new PercolationStats(n, trials);

        // elapsed time
        double elapsedTime = stopwatch.elapsedTime();

        // Output results
        System.out.println("mean                    = "
                + stats.mean());
        System.out.println("stddev                  = "
                + stats.stddev());
        System.out.println("95% confidence interval = "
                + stats.confidenceLo() + ", " + stats.confidenceHi());

        // elapsed time
        System.out.println("elapsed time: " + elapsedTime);
    }

}