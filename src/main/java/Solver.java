import edu.princeton.cs.algs4.*;

import java.util.Comparator;

public final class Solver {

    private final MinPQ<Node> queue;
    private final MinPQ<Node> twinQueue;

    private boolean solvable = false;
    private boolean twinSolvable = false;
    private Node lastNode;
    private Queue<Board> solution;

    public Solver(Board initial){
        // creates priority queues
        queue = new MinPQ<>(manhattanPriorityComparator);
        twinQueue = new MinPQ<>(manhattanPriorityComparator);

        // insert initial board
        queue.insert(new Node(initial, 0, null));
        twinQueue.insert(new Node(initial.twin(), 0, null));

        // solution
        solution = new Queue<>();

        while(!solvable && !twinSolvable) {
            // extract searchNode
            Node searchNode = queue.delMin();
            Node twinSearchNode = twinQueue.delMin();

            // enqueue solution
            solution.enqueue(searchNode.board);

            // checks if solvable
            solvable = searchNode.board.isGoal();
            twinSolvable = twinSearchNode.board.isGoal();

            // neighboors processing
            for (Board neighbor : searchNode.board.neighbors()) {
                if (searchNode.previousNode != null && neighbor.equals(searchNode.previousNode.board)) {
                    continue;
                }
                queue.insert(new Node(neighbor, searchNode.moves + 1, searchNode));
            }

            // twin neighboors processing
            for (Board twinNeighbor : twinSearchNode.board.neighbors()) {
                if (twinSearchNode.previousNode != null && twinNeighbor.equals(twinSearchNode.previousNode.board)) {
                    continue;
                }
                twinQueue.insert(new Node(twinNeighbor, twinSearchNode.moves + 1, twinSearchNode));
            }

            // saves last node
            lastNode = searchNode;
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (isSolvable()) {
            return lastNode.moves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> result = new Stack<>();
            Node n = lastNode;
            while(n != null) {
                result.push(n.board);
                n = n.previousNode;
            }
            return result;
        }
        return null;
    }

    private static Comparator<Node> manhattanPriorityComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return (o1.board.manhattan() + o1.moves) - (o2.board.manhattan() + o2.moves);
        }
    };

    private static Comparator<Node> hammingPriorityComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return (o1.board.hamming() + o1.moves) - (o2.board.hamming() + o2.moves);
        }
    };

    private final class Node {
        private final Board board;
        private final Node previousNode;
        private final int moves;

        Node(Board board, int moves, Node previousNode) {
            this.board = board;
            this.moves = moves;
            this.previousNode = previousNode;
        }
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
