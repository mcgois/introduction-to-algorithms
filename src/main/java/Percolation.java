/**
 * Matheus Cabral
 * Semptember 7th, 2016
 *
 * Percolation. Creates a grid of NxN sites and controls its percolation.
 *
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation Class.
 *
 * Memory usage: ~ 9N^2 (8N^2+88 + N^2 + 24 + 4)
 *
 */
public class Percolation {

    // N x N size of grid.
    private int n;

    // site status array to control if it is open or not.
    // initialize with blocked (false).
    private boolean[] siteStatus;

    // quick-union data structure
    private WeightedQuickUnionUF weightedQuickUnion;
    private WeightedQuickUnionUF topWeightedQuickUnion;

    // O(N^2)
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N size of grid cannot be <= 0.");
        }

        // first (0) and last (n+1) elements are virtual sites
        this.weightedQuickUnion = new WeightedQuickUnionUF(n * n + 2);
        this.topWeightedQuickUnion = new WeightedQuickUnionUF(n * n + 1);

        // site status (0 - blocked, 1 - open, 2- full). initial blocked.
        this.siteStatus = new boolean[n * n +2];

        // stores n
        this.n = n;

        // initialize virtual sites
        this.siteStatus[0] = true;
        this.siteStatus[n * n +1] = true;
    }

    /**
     * Converts two dimensional matrix index to a array index.
     *
     * @param i - row
     * @param j - column
     *
     * @return int array equivalent index.
     */
    private int toArrayIndex(int i, int j) {
        checkBoundaries(i, j);
        return (i-1) * this.n + j;
    }

    /**
     * Verifies if row/column are within the matrix limits.
     *
     * @param i - given row
     * @param j - given column
     */
    private void checkBoundaries(int i, int j) {
        if (i < 1 || i > n) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }

        if (j < 1 || j > n) {
            throw new IndexOutOfBoundsException("column index i out of bounds");
        }
    }

    /**
     * Opens site on (i,j).
     *
     * @param i - row
     * @param j - column
     */
    public void open(int i, int j) {
        checkBoundaries(i, j);

        // change status to open
        siteStatus[toArrayIndex(i, j)] = true;

        // top
        if (i - 1 >= 1 && siteStatus[toArrayIndex(i - 1, j)]) {
            weightedQuickUnion.union(toArrayIndex(i - 1, j), toArrayIndex(i, j));
            topWeightedQuickUnion.union(toArrayIndex(i - 1, j), toArrayIndex(i, j));
        }

        // bottom
        if (i + 1 <= n && siteStatus[toArrayIndex(i + 1, j)]) {
            weightedQuickUnion.union(toArrayIndex(i, j), toArrayIndex(i + 1, j));
            topWeightedQuickUnion.union(toArrayIndex(i, j), toArrayIndex(i + 1, j));
        }

        // left
        if (j - 1 >= 1 && siteStatus[toArrayIndex(i, j - 1)]) {
            weightedQuickUnion.union(toArrayIndex(i, j - 1), toArrayIndex(i, j));
            topWeightedQuickUnion.union(toArrayIndex(i, j - 1), toArrayIndex(i, j));
        }

        // right
        if (j + 1 <= n && siteStatus[toArrayIndex(i, j + 1)]) {
            weightedQuickUnion.union(toArrayIndex(i, j), toArrayIndex(i, j + 1));
            topWeightedQuickUnion.union(toArrayIndex(i, j), toArrayIndex(i, j + 1));
        }

        // if first row
        if (i == 1) {
            weightedQuickUnion.union(0, toArrayIndex(i, j));
            topWeightedQuickUnion.union(0, toArrayIndex(i, j));
        }

        // if (last row
        if (i == n) {
            weightedQuickUnion.union(this.n * this.n + 1, toArrayIndex(i, j));
        }
    }

    /**
     * Verifies if site (i,j) is open.
     *
     * @param i - row
     * @param j - column
     *
     * @return true if site is open.
     */
    public boolean isOpen(int i, int j) {
        checkBoundaries(i, j);
        int arrayIndex = toArrayIndex(i, j);
        return siteStatus[arrayIndex];
    }

    /**
     * Verifies if site (i,j) is full.
     *
     * @param i - row
     * @param j - column
     *
     * @return true if site is full.
     */
    public boolean isFull(int i, int j) {
        checkBoundaries(i, j);
        return isOpen(i, j) &&
               topWeightedQuickUnion.connected(0, toArrayIndex(i, j));
    }

    /**
     * Verifies if system percolates or not.
     *
     * @return true if percolates.
     */
    public boolean percolates() {
        return weightedQuickUnion.connected(0, n * n +1);
    }

    /**
     * Main method for testing.
     *
     * @param args - program arguments.
     */
    public static void main(String[] args) {
        // n = 1
        Percolation percolation = new Percolation(1);
        System.out.println(percolation.percolates());
        percolation.open(1, 1);
        System.out.println(percolation.percolates());
    }

}
