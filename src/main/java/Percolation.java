import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;

    private WeightedQuickUnionUF weightedQuickUnionUF;

    // O(N^2)
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N size of grid cannot be <= 0.");
        }

        this.weightedQuickUnionUF = new WeightedQuickUnionUF(n);
        this.N = n;
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
    }

    public boolean isOpen(int i, int j) {
        checkBoundaries(i, j);
        return false;
    }

    public boolean isFull(int i, int j) {
        checkBoundaries(i, j);
        return false;
    }

    public boolean percolates() {
        return false;
    }

    public static void main(String[] args) {
        System.out.println("blah");
    }

}
