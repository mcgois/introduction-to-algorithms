import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {

    private int[][] blocks;

    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return this.blocks.length;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0 &&
                    blocks[i][j] != (i * dimension() + j + 1)) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int count = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0) {
                    int supposedRow = (blocks[i][j] - 1) / dimension();
                    int supposedColumn = (blocks[i][j] -1) % dimension();
                    int dist = Math.abs(i - supposedRow) +
                               Math.abs(j - supposedColumn);
                    count += dist;
                }
            }
        }
        return count;
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    public Board twin() {
        int i1, j1, i2, j2;

        // copy block
        int[][] blocksCopy = new int[dimension()][dimension()];
        for (int i = 0; i < blocksCopy.length; i++) {
            for (int j = 0; j < blocksCopy.length; j++) {
                blocksCopy[i][j] = blocks[i][j];
            }
        }

        // find two elements to swap
        do {
            i1 = StdRandom.uniform(0, dimension());
            j1 = StdRandom.uniform(0, dimension());
            i2 = StdRandom.uniform(0, dimension());
            j2 = StdRandom.uniform(0, dimension());
        } while(blocksCopy[i1][j1] == 0 ||
                blocksCopy[i2][j2] == 0 ||
                (i1 == i2 && j1 == j2));

        // do swap
        swap(blocksCopy, i1, j1, i2, j2);

        // create new board
        return new Board(blocksCopy);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Board that = (Board) other;
        return Arrays.deepEquals(blocks, that.blocks);
    }

    public Iterable<Board> neighbors() {
        int i0 = 0;
        int j0 = 0;
        int[][] blks1 = new int[dimension()][dimension()];
        int[][] blks2 = new int[dimension()][dimension()];
        int[][] blks3 = new int[dimension()][dimension()];
        int[][] blks4 = new int[dimension()][dimension()];

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                }
                blks1[i][j] = blocks[i][j];
                blks2[i][j] = blocks[i][j];
                blks3[i][j] = blocks[i][j];
                blks4[i][j] = blocks[i][j];
            }
        }

        Queue<Board> result = new Queue<>();


        if (i0 - 1 >= 0) {
            swap(blks1, i0, j0, i0 - 1, j0);
            result.enqueue(new Board(blks1));
        }

        if (i0 + 1 < dimension()) {
            swap(blks2, i0, j0, i0 + 1, j0);
            result.enqueue(new Board(blks2));
        }

        if (j0 - 1 >= 0) {
            swap(blks3, i0, j0, i0, j0 - 1);
            result.enqueue(new Board(blks3));
        }

        if (j0 + 1 < dimension()) {
            swap(blks4, i0, j0, i0, j0 + 1);
            result.enqueue(new Board(blks4));
        }

        return result;
    }

    private void swap(int[][] blks, int i1, int j1, int i2, int j2) {
        int temp = blks[i1][j1];
        blks[i1][j1] = blks[i2][j2];
        blks[i2][j2] = temp;
    }

    public String toString() {
        int n = dimension();
        int capacity = 3 * n * n  + n + 3;
        StringBuilder builder = new StringBuilder(capacity);
        builder.append(dimension());
        builder.append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                builder.append(String.format("%2d ", this.blocks[i][j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}});
        System.out.println(board);
        System.out.println(board.neighbors());
    }

}
