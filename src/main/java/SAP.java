import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public final class SAP {

    private Digraph digraph;

    public SAP(Digraph G) {
        this.digraph = new Digraph(G);
    }

    public int length(int v, int w) {

        checkBounds(v);
        checkBounds(w);

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

        checkBounds(v);
        checkBounds(w);

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

        checkBounds(v);
        checkBounds(w);

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

        checkBounds(v);
        checkBounds(w);

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

    private void checkBounds(int i) {
        if (i < 0 || i >= digraph.V()) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkBounds(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new NullPointerException();
        }

        for (int vertex : vertices) {
            checkBounds(vertex);
        }

    }

}
