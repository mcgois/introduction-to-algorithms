import edu.princeton.cs.algs4.*;

public final class SAP {

    private Digraph digraph;

    public SAP(Digraph G) {
        this.digraph = G;
    }

    public int length(int v, int w) {
        int minDistance = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(digraph, w);

        for (int i = 0; i < digraph.V(); i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                int distance =  b1.distTo(i) + b2.distTo(i);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    public int ancestor(int v, int w) {
        int ancestor = -1;
        int minDistance = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(digraph, w);

        for (int i = 0; i < digraph.V(); i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                int distance =  b1.distTo(i) + b2.distTo(i);
                if (distance < minDistance) {
                    minDistance = distance;
                    ancestor = i;
                }
            }
        }

        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int minDistance = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(digraph, w);

        for (int i = 0; i < digraph.V(); i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                int distance =  b1.distTo(i) + b2.distTo(i);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        int ancestor = -1;
        int minDistance = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(digraph, w);

        for (int i = 0; i < digraph.V(); i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                int distance =  b1.distTo(i) + b2.distTo(i);
                if (distance < minDistance) {
                    minDistance = distance;
                    ancestor = i;
                }
            }
        }

        return ancestor;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
