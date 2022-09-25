import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final Digraph digraph;
    private final int n;
    /**
     * constructor takes a digraph (not necessarily a DAG).
     */
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        n = G.V();
        digraph = new Digraph(G);
    }

    private boolean isIrrelevant(int v) {
        return v < 0 || v >= n;
    }

    /**
     * length of the shortest ancestral path between v and w; -1 if no such path.
     */
    public int length(int v, int w) {
        if (isIrrelevant(v) || isIrrelevant(w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        int sapLength = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                sapLength = Math.min(sapLength, bfsV.distTo(i) + bfsW.distTo(i));
            }
        }
        if (sapLength == Integer.MAX_VALUE) {
            return -1;
        }
        return sapLength;
    }

    /**
     * a common ancestor of v and w that participates in the shortest ancestral path; -1 if no such path.
     */
    public int ancestor(int v, int w) {
        if (isIrrelevant(v) || isIrrelevant(w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        int sapLength = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < n; ++i) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i) && sapLength > bfsV.distTo(i) + bfsW.distTo(i)) {
                sapLength = bfsV.distTo(i) + bfsW.distTo(i);
                ancestor = i;
            }
        }
        return ancestor;
    }

    private boolean isIrrelevant(Iterable<Integer> v) {
        if (v == null) {
            return true;
        }
        for (Integer x : v) {
            if (x == null || isIrrelevant(x)) {
                return true;
            }
        }
        return false;
    }

    private int getSize(Iterable<Integer> v) {
        int size = 0;
        for (int x : v) {
            ++size;
        }
        return size;
    }

    /**
     * length of the shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path.
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (isIrrelevant(v) || isIrrelevant(w)) {
            throw new IllegalArgumentException();
        }
        if (getSize(v) == 0 || getSize(w) == 0) {
            return -1;
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        int sapLength = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                sapLength = Math.min(sapLength, bfsV.distTo(i) + bfsW.distTo(i));
            }
        }
        if (sapLength == Integer.MAX_VALUE) {
            return -1;
        }
        return sapLength;
    }

    /**
     * a common ancestor that participates in the shortest ancestral path; -1 if no such path.
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (isIrrelevant(v) || isIrrelevant(w)) {
            throw new IllegalArgumentException();
        }
        if (getSize(v) == 0 || getSize(w) == 0) {
            return -1;
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);
        int sapLength = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < n; ++i) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i) && sapLength > bfsV.distTo(i) + bfsW.distTo(i)) {
                sapLength = bfsV.distTo(i) + bfsW.distTo(i);
                ancestor = i;
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        // empty
    }

}