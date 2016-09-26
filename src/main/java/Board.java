import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public final class Board {

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
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != (i * blocks.length + j + 1) ) {
                    count++;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int count = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != 0) {
                    int supposedRow = (blocks[i][j] - 1) / blocks.length;
                    int supposedColumn = (blocks[i][j] -1) % blocks.length;
                    int dist = Math.abs(i - supposedRow) + Math.abs(j - supposedColumn);
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
        int[][] blocksCopy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocksCopy.length; i++) {
            for (int j = 0; j < blocksCopy.length; j++) {
                blocksCopy[i][j] = blocks[i][j];
            }
        }

        // find two elements to swap
        do {
            i1 = StdRandom.uniform(0, blocksCopy.length);
            j1 = StdRandom.uniform(0, blocksCopy.length);
            i2 = StdRandom.uniform(0, blocksCopy.length);
            j2 = StdRandom.uniform(0, blocksCopy.length);
        } while(blocksCopy[i1][j1] == 0 || blocksCopy[i2][j2] == 0 || (i1 == i2 && j1 == j2));

        // do swap
        int temp = blocksCopy[i1][j1];
        blocksCopy[i1][j1] = blocksCopy[i2][j2];
        blocksCopy[i2][j2] = temp;

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
        return null;
    }

    public String toString() {
        int capacity = 3 * this.blocks.length * this.blocks.length + this.blocks.length + 3;
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
        Board board = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
//        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
//        Board board = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
//        System.out.println(board);
//        System.out.println(board.hamming());
//        System.out.println(board.manhattan());
//        System.out.println(board.isGoal());
//        System.out.println(board.twin());
    }

}
