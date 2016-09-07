import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;

    private int[] siteStatus;
    private static final int STATUS_BLOCKED = 0;
    private static final int STATUS_OPEN = 1;

    private WeightedQuickUnionUF weightedQuickUnionUF;

    // O(N^2)
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N size of grid cannot be <= 0.");
        }

        // first (0) and last (n+1) elements are virtual sites
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(n*n+2);

        // site status (0 - blocked, 1 - open, 2- full). initial blocked.
        this.siteStatus = new int[n*n+2];

        // stores n
        this.N = n;

        // initialize virtual sites
        this.siteStatus[0] = STATUS_OPEN;
        this.siteStatus[n*n+1] = STATUS_OPEN;

        // connect virtual sites to all elements in first row and last row.
        for (int j = 1; j <= N; j++) {

            int indexFirstRow = toArrayIndex(1, j);
            weightedQuickUnionUF.union(0, indexFirstRow);

            int indexLastRow = toArrayIndex(N, j);
            weightedQuickUnionUF.union(N*N+1, indexLastRow);
        }

    }

    private int toArrayIndex(int i, int j) {
        return (i-1) * this.N + j;
    }

    private void checkBoundaries(int i, int j) {
        if (i < 1 || i > N) {
            throw new IndexOutOfBoundsException("i index out of boundaries.");
        }

        if (j < 1 || j > N) {
            throw new IndexOutOfBoundsException("j index out of boundaries.");
        }
    }

    public void open(int i, int j) {
        checkBoundaries(i, j);

        siteStatus[toArrayIndex(i, j)] = STATUS_OPEN;

        // top
        if (i - 1 >= 1 && siteStatus[toArrayIndex(i - 1, j)] == STATUS_OPEN) {
            weightedQuickUnionUF.union(toArrayIndex(i - 1, j), toArrayIndex(i, j));
        }

        // bottom
        if (i + 1 <= N && siteStatus[toArrayIndex(i + 1, j)] == STATUS_OPEN) {
            weightedQuickUnionUF.union(toArrayIndex(i, j), toArrayIndex(i + 1, j));
        }

        // left
        if (j - 1 >= 1 && siteStatus[toArrayIndex(i, j - 1)] == STATUS_OPEN) {
            weightedQuickUnionUF.union(toArrayIndex(i,j-1), toArrayIndex(i, j));
        }

        // right
        if (j + 1 <+ N && siteStatus[toArrayIndex(i, j + 1)] == STATUS_OPEN) {
            weightedQuickUnionUF.union(toArrayIndex(i, j), toArrayIndex(i, j + 1));
        }
    }

    public boolean isOpen(int i, int j) {
        checkBoundaries(i, j);
        int arrayIndex = toArrayIndex(i, j);
        return siteStatus[arrayIndex] == STATUS_OPEN;
    }

    public boolean isFull(int i, int j) {
        checkBoundaries(i, j);
        return weightedQuickUnionUF.connected(0, toArrayIndex(i, j));
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, N*N+1);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(1, 2);
        percolation.open(3, 2);
        percolation.open(4, 2);
        System.out.println(percolation.percolates());
        percolation.open(5, 2);
        System.out.println(percolation.percolates());
    }

}
