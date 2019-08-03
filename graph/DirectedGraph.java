package graph;


import java.util.ArrayList;


/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Warner Tsang
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
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
        if (!contains(v)) {
            return Iteration.iteration(new ArrayList<>());
        }
        ArrayList<ArrayList<Integer>> edges = getAdjacentEdges();
        ArrayList<Integer> predecessorIterator = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).contains(v)) {
                predecessorIterator.add(i + 1);
            }
        }
        return Iteration.iteration(predecessorIterator);
    }
}
