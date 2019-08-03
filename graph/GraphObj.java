package graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Warner Tsang
 */
abstract class GraphObj extends Graph {

    /**
     * Tracks all vertices.
     */
    private ArrayList<Integer> vertexTrack;
    /**
     * Adjacency list of edges and nodes.
     */
    private ArrayList<ArrayList<Integer>> adjacentEdges;
    /**
     * Tracks edges in a two element array.
     */
    private ArrayList<int[]> _edges;
    /**
     * Tracks directed edgeIDs.
     */
    private ArrayList<Integer> dEdgeCodes;
    /**
     * Tracks undirected edgeIDs.
     */
    private ArrayList<Integer> uEdgeCodes;



    /** A new, empty Graph. */
    GraphObj() {
        vertexTrack = new ArrayList<>();
        adjacentEdges = new ArrayList<>();
        _edges = new ArrayList<>();
        dEdgeCodes = new ArrayList<>();
        uEdgeCodes = new ArrayList<>();
    }

    /**
     * Getter method for adjacency list.
     * @return ArrayList.
     */
    public ArrayList<ArrayList<Integer>> getAdjacentEdges() {
        return adjacentEdges;
    }

    /**
     * Getter method for vertices.
     * @return ArrayList.
     */
    public ArrayList<Integer> getVertexTrack() {
        return vertexTrack;
    }

    /**
     * Getter method for edge list.
     * @return ArrayList.
     */
    public ArrayList<int[]> getEdges() {
        return _edges;
    }


    @Override
    public int vertexSize() {

        return vertexTrack.size();
    }

    @Override
    public int maxVertex() {

        return vertexTrack.get(vertexTrack.size() - 1);
    }

    @Override
    public int edgeSize() {

        return _edges.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {

        if (vertexTrack.contains(v)) {
            return adjacentEdges.get(v - 1).size();
        }
        return 0;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return vertexTrack.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (vertexTrack.contains(u) && vertexTrack.contains(v)) {
            if (isDirected()) {
                return dEdgeCodes.contains(edgeId(u, v));
            } else {
                return uEdgeCodes.contains(edgeId(u, v))
                        || uEdgeCodes.contains(edgeId(v, u));
            }
        }
        return false;
    }

    @Override
    public int add() {

        int index = 0;
        if (adjacentEdges.size() == 0) {
            ArrayList<Integer> first = new ArrayList<>();
            adjacentEdges.add(first);
            vertexTrack.add(1);
            return index + 1;
        }
        while (index < adjacentEdges.size()) {
            if (!vertexTrack.contains(index + 1)
                    && adjacentEdges.get(index).size() == 0) {
                ArrayList<Integer> newVertex = new ArrayList<>();
                adjacentEdges.set(index, newVertex);
                vertexTrack.add(index, index + 1);
                return index + 1;
            }
            index++;
        }
        ArrayList<Integer> newVertex = new ArrayList<>();
        adjacentEdges.add(newVertex);
        vertexTrack.add(adjacentEdges.size());
        return adjacentEdges.size();
    }

    @Override
    public int add(int u, int v) {

        if (vertexTrack.contains(u) && vertexTrack.contains(v)) {
            if (isDirected()) {
                if (dEdgeCodes.contains(edgeId(u, v))) {
                    return edgeId(u, v);
                }
                for (int i = 0; i < adjacentEdges.size(); i++) {
                    ArrayList<Integer> node = adjacentEdges.get(i);
                    if (i == u - 1 && node.contains(v)) {
                        node.add(v);
                    }
                }
                dEdgeCodes.add(edgeId(u, v));
            } else if (!isDirected()) {
                if (uEdgeCodes.contains(edgeId(u, v))
                        || uEdgeCodes.contains(edgeId(v, u))) {
                    return edgeId(u, v);
                }
                for (int j = 0; j < adjacentEdges.size(); j++) {
                    ArrayList<Integer> node = adjacentEdges.get(j);
                    if (j == u - 1 && node.contains(v)) {
                        node.add(v);
                    } else if (j == v - 1 && node.contains(u)) {
                        node.add(u);
                    }
                }
                uEdgeCodes.add(edgeId(u, v));
                uEdgeCodes.add(edgeId(v, u));
            }
            int[] edgePair = new int[2];
            edgePair[0] = u;
            edgePair[1] = v;
            _edges.add(edgePair);
        }
        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {

        if (contains(v)) {
            vertexTrack.remove(vertexTrack.indexOf(v));
            adjacentEdges.get(v - 1).clear();
            for (ArrayList<Integer> nodes : adjacentEdges) {
                if (nodes.contains(v)) {
                    nodes.remove(nodes.indexOf(v));
                }
            }

            for (int i = 0; i < _edges.size(); i++) {
                if (_edges.get(i)[0] == v
                        || _edges.get(i)[1] == v) {
                    int directedID =
                            edgeId(_edges.get(i)[0], _edges.get(i)[1]);
                    int undirectedID =
                            edgeId(_edges.get(i)[1], _edges.get(i)[0]);
                    if (uEdgeCodes.contains(directedID)) {
                        uEdgeCodes.remove(uEdgeCodes.indexOf(directedID));
                        if (uEdgeCodes.contains(undirectedID)) {
                            uEdgeCodes.remove(uEdgeCodes.indexOf(undirectedID));
                        }
                    }
                    if (dEdgeCodes.contains(directedID)) {
                        dEdgeCodes.remove(dEdgeCodes.indexOf(directedID));
                        if (dEdgeCodes.contains(undirectedID)) {
                            dEdgeCodes.remove(dEdgeCodes.indexOf(undirectedID));
                        }
                    }
                    _edges.set(i, null);
                }
            }
            ArrayList<int[]> removedEdges = new ArrayList<>();
            for (int[] elem : _edges) {
                if (elem != null) {
                    removedEdges.add(elem);
                }
            }
            _edges = removedEdges;
        }
    }

    @Override
    public void remove(int u, int v) {

        if (contains(u, v)) {
            if (!isDirected()) {
                uEdgeCodes.remove(uEdgeCodes.indexOf(edgeId(u, v)));
                uEdgeCodes.remove(uEdgeCodes.indexOf(edgeId(v, u)));
                for (int i = 0; i < _edges.size(); i++) {
                    if ((_edges.get(i)[0] == u && _edges.get(i)[1] == v)
                            || (_edges.get(i)[0] == v
                            && _edges.get(i)[1] == u)) {
                        _edges.set(i, null);
                    }
                }

                ArrayList<int[]> edgeCopy = new ArrayList<>();
                for (int[] elem : _edges) {
                    if (elem != null) {
                        edgeCopy.add(elem);
                    }
                }
                _edges = edgeCopy;

                for (int i = 0; i < adjacentEdges.size(); i++) {
                    ArrayList<Integer> nodes = adjacentEdges.get(i);
                    if (i == u - 1 && nodes.contains(v)) {
                        nodes.remove(nodes.indexOf(v));
                    } else if (i == v - 1 && nodes.contains(u)) {
                        nodes.remove(nodes.indexOf(u));
                    }
                }
            } else if (isDirected()) {
                dEdgeCodes.remove(dEdgeCodes.indexOf(edgeId(u, v)));
                for (int i = 0; i < _edges.size(); i++) {
                    if (_edges.get(i)[0] == u
                            && _edges.get(i)[1] == v) {
                        _edges.remove(i);
                        break;
                    }
                }
                for (int j = 0; j < adjacentEdges.size(); j++) {
                    ArrayList<Integer> nodes = adjacentEdges.get(j);
                    if (j == u - 1 && nodes.contains(v)) {
                        nodes.remove(nodes.indexOf(v));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {

        return Iteration.iteration(vertexTrack);
    }

    @Override
    public Iteration<Integer> successors(int v) {

        if (!contains(v)) {
            return Iteration.iteration(new ArrayList<>());
        }
        return Iteration.iteration(adjacentEdges.get(v - 1));
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {

        return Iteration.iteration(_edges);
    }

    @Override
    protected void checkMyVertex(int v) {

        if (!contains(v)) {
            throw new IllegalArgumentException("vertex non-existent");
        }
    }

    @Override
    protected int edgeId(int u, int v) {

        return ((u + v) * (u + v + 1)) / 2 + v;
    }
}
