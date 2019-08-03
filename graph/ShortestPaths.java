package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.List;


/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Warner Tsang
 */
public abstract class ShortestPaths {

    /**
     * The graph being searched.
     */
    protected final Graph _G;
    /**
     * The starting vertex.
     */
    private final int _source;
    /**
     * The target vertex.
     */
    private final int _dest;
    /**
     * Traversal tree set.
     */
    private TreeSet<Integer> _traversal;
    /**
     * Stores path.
     */
    private ArrayList<Integer> _path;


    /**
     * The shortest paths in G from SOURCE.
     */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /**
     * A shortest path in G from SOURCE to DEST.
     */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        _path = new ArrayList<>();
        _traversal = new TreeSet<>(heuristic);
    }

    /**
     * Initialize the shortest paths.  Must be called before using
     * getWeight, getPredecessor, and pathTo.
     */
    public void setPaths() {

        _traversal.clear();
        for (int i : _G.vertices()) {
            if (i == _source) {
                continue;
            }
            setWeight(i, Double.POSITIVE_INFINITY);
            _traversal.add(i);
        }
        setWeight(_source, 0);
        _traversal.add(_source);
        while (!_traversal.isEmpty()) {
            int parent = _traversal.pollFirst();
            if (parent == _dest) {
                return;
            } else {
                Iteration<Integer> iter = _G.successors(parent);
                while (iter.hasNext()) {
                    int children = iter.next();
                    double newWeightParent = getWeight(parent);
                    double newWeightChild = getWeight(parent, children);
                    double currentWeight = getWeight(children);
                    if (newWeightParent + newWeightChild < currentWeight) {
                        _traversal.remove(children);
                        setWeight(children, newWeightParent + newWeightChild);
                        setPredecessor(children, parent);
                        _traversal.add(children);
                    }
                }
            }
        }
    }
    /**
     * Returns the starting vertex.
     */
    public int getSource() {

        return _source;
    }

    /**
     * Returns the target vertex, or 0 if there is none.
     */
    public int getDest() {

        return _dest;
    }


    /**
     * Returns the current weight of vertex V in the graph.  If V is
     * not in the graph, returns positive infinity.
     */
    public abstract double getWeight(int v);

    /**
     * Set getWeight(V) to W. Assumes V is in the graph.
     */
    protected abstract void setWeight(int v, double w);

    /**
     * Returns the current predecessor vertex of vertex V in the graph, or 0 if
     * V is not in the graph or has no predecessor.
     */
    public abstract int getPredecessor(int v);

    /**
     * Set getPredecessor(V) to U.
     */
    protected abstract void setPredecessor(int v, int u);

    /**
     * Returns an estimated heuristic weight of the shortest path from vertex
     * V to the destination vertex (if any).  This is assumed to be less
     * than the actual weight, and is 0 by default.
     */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /**
     * Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     * not in the graph, returns positive infinity.
     */
    protected abstract double getWeight(int u, int v);

    /**
     * Returns a list of vertices starting at _source and ending
     * at V that represents a shortest path to V.  Invalid if there is a
     * destination vertex other than V.
     */
    public List<Integer> pathTo(int v) {

        while ((getPredecessor(v)) != 0) {
            _path.add(v);
            v = getPredecessor(v);
        }
        if (!_path.contains(_source)) {
            _path.add(_source);
        }
        ArrayList<Integer> pathCopy = new ArrayList<>();
        for (int i = _path.size(); i > 0; i--) {
            pathCopy.add(_path.get(i - 1));
        }
        _path = pathCopy;
        return _path;
    }

    /**
     * Returns a list of vertices starting at the source and ending at the
     * destination vertex. Invalid if the destination is not specified.
     */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /**
     * Comparator for the tree set.
     */
    private final Comparator<Integer> heuristic = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            double firstEdge = getWeight(o1) + estimatedDistance(o1);
            double secondEdge = getWeight(o2) + estimatedDistance(o2);
            if (firstEdge > secondEdge) {
                return 1;
            } else if (firstEdge < secondEdge) {
                return -1;
            }
            return o1 - o2;
        }
    };
}
