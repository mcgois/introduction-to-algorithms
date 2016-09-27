import edu.princeton.cs.algs4.*;

import java.util.Comparator;

public final class Solver {

    private final MinPQ<Board> queue;
    private final MinPQ<Board> twinQueue;

    private boolean solvable = false;
    private boolean twinSolvable = false;

    private Queue<Board> solution;
    private Queue<Board> twinSolution;

    private Board last = null;
    private Board twinLast = null;

    public Solver(Board initial){
        // creates priority queues
        queue = new MinPQ<>(boardComparator);
        twinQueue = new MinPQ<>(boardComparator);

        // insert initial board
        queue.insert(initial);
        twinQueue.insert(initial.twin());

        // solution
        solution = new Queue<>();
        twinSolution = new Queue<>();

        while(!solvable && !twinSolvable) {
            Board searchNode = queue.delMin();
            Board twinSearchNode = twinQueue.delMin();

            solution.enqueue(searchNode);

            solvable = searchNode.isGoal();
            twinSolvable = searchNode.isGoal();

            for (Board neighbor : searchNode.neighbors()) {
                if (neighbor.equals(last)) {
                    continue;
                }
                queue.insert(neighbor);
            }

            for (Board twinNeighbor : twinSearchNode.neighbors()) {
                if (twinNeighbor.equals(twinLast)) {
                    continue;
                }
                twinQueue.insert(twinNeighbor);
            }

            last = searchNode;
            twinLast = twinSearchNode;
        }
    }


    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (isSolvable()) {
            return solution.size() - 1;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        return solution;
    }

    private static Comparator<Board> boardComparator = new Comparator<Board>() {
        @Override
        public int compare(Board o1, Board o2) {
            return o1.manhattan() - o2.manhattan();
        }
    };

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
