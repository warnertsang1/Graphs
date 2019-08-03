package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author Warner Tsang
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {

        if (!contains(v)) {
            return 0;
        }
        ArrayList<ArrayList<Integer>> adjacent = getAdjacentEdges();
        int incoming = 0;
        for (ArrayList<Integer> vertex : adjacent) {
            if (vertex.contains(v)) {
                incoming++;
            }
        }
        return incoming;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        return super.successors(v);
    }
}
