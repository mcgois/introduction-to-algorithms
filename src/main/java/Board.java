
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
        return false;
    }

    public Board twin() {
        return null;
    }

    public boolean equals(Object y) {
        return false;
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
//        Board board = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
    }

}
