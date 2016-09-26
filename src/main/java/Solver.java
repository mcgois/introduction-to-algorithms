import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public final class Solver {

    private int count = 0;
    private Board previousSearchNode = null;
    private Board searchNode;
    private final MinPQ<Board> priorityQueue;
    private final Board goalBoard;
    private final Queue<Board> solution;

    public Solver(Board initial){
        goalBoard = createGoalBoard(initial.dimension());
        solution = new Queue<>();
        searchNode = initial;
        priorityQueue = new MinPQ<>(new Comparator<Board>(){
            @Override
            public int compare(Board o1, Board o2) {
                return o1.hamming() - o2.hamming();
            }
        });
        priorityQueue.insert(searchNode);

        while(!searchNode.equals(goalBoard)) {
            previousSearchNode = searchNode;
            searchNode = priorityQueue.delMin();
            count++;
            solution.enqueue(searchNode);
            for (Board neighbor : searchNode.neighbors()) {
                if (neighbor.equals(previousSearchNode)) {
                    continue;
                }
                priorityQueue.insert(neighbor);
            }
        }

    }

    private Board createGoalBoard(int dimension) {
        int[][] blks = new int[dimension][dimension];
        int count = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                blks[i][j] = count++;
            }
        }
        blks[dimension-1][dimension-1] = 0;
        return new Board(blks);
    }

    public boolean isSolvable() {
        return true;
    }

    public int moves() {
        return count;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
