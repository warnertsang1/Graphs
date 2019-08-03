package graph;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Warner Tsang
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void testDegrees() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 5);
        g.add(4, 2);
        g.add(5, 3);
        assertEquals(5, g.maxVertex());
        assertEquals(5, g.edgeSize());
        assertEquals(0, g.outDegree(6));
        assertEquals(3, g.outDegree(1));
        assertEquals(2, g.outDegree(2));
        assertEquals(2, g.inDegree(3));
        g.remove(5);
        assertEquals(4, g.maxVertex());
        g.add();
        assertEquals(5, g.maxVertex());
        assertEquals(3, g.edgeSize());
    }

    @Test
    public void testContains() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 5);
        g.add(4, 2);
        g.add(5, 3);

        assertTrue(g.contains(5));
        assertFalse(g.contains(111));
        assertTrue(g.contains(1, 2));
        assertTrue(g.contains(2, 1));
        assertFalse(g.contains(4, 1));
        assertFalse(g.contains(1, 4));
        assertTrue(g.contains(5, 3));
        assertFalse(g.contains(2, 3));
    }



    @Test
    public void testPredIterator() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(2, 5);
        g.add(5, 4);
        g.add(5, 4);
        int count = 0;
        for (int i = 1; i < g.getAdjacentEdges().size() + 1; i++) {
            Iteration<Integer> predIterator = g.predecessors(i);
            while (predIterator.hasNext()) {
                predIterator.next();
                count++;
            }
        }
        assertEquals(count, 8);
    }

    @Test
    public void testSuccIterator() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(2, 3);
        g.add(5, 1);
        g.add(4, 2);
        g.add(5, 4);
        g.add(2, 4);
        g.remove(8);
        int count = 0;
        for (int i = 1; i < g.getAdjacentEdges().size() + 1; i++) {
            Iteration<Integer> succIterator = g.successors(i);
            while (succIterator.hasNext()) {
                succIterator.next();
                count++;
            }
        }
        assertEquals(count, 10);
    }

    @Test
    public void edgeIterator() {
        UndirectedGraph g = new UndirectedGraph();
        for (int i = 0; i < 5; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 5);
        g.add(5, 4);
        Iteration<int[]> edgeIterator = g.edges();
        ArrayList<int[]> iterChecker = new ArrayList<>();
        while (edgeIterator.hasNext()) {
            iterChecker.add(edgeIterator.next());
        }
        assertEquals(4, iterChecker.size());
        assertEquals(4, g.edgeSize());
    }
    @Test
    public void testDirected() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 6; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 6);
        g.add(2, 3);
        g.add(2, 4);
        g.add(2, 6);
        g.add(3, 2);
        g.add(3, 4);
        g.add(4, 1);
        g.add(4, 5);
        g.add(5, 1);
        g.add(5, 6);
        g.add(6, 2);
        g.add(6, 5);

        assertEquals(6, g.maxVertex());
        assertEquals(13, g.edgeSize());
        assertEquals(3, g.outDegree(2));
        assertEquals(1, g.inDegree(3));
        assertEquals(2, g.outDegree(1));
        assertEquals(3, g.inDegree(2));
    }

    @Test
    public void testDirectContains() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 6; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 4);
        g.add(2, 3);
        g.add(2, 4);
        g.add(3, 2);
        g.add(3, 4);
        g.add(4, 1);
        g.add(4, 5);
        g.add(5, 1);
        g.add(5, 6);
        g.add(6, 3);

        assertTrue(g.contains(1, 2));
        assertFalse(g.contains(2, 1));
        assertTrue(g.contains(4, 1));
        assertTrue(g.contains(3, 4));
    }

    @Test
    public void testDirectPredIterators() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 6; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(1, 6);
        g.add(2, 3);
        g.add(3, 4);
        g.add(4, 5);
        g.add(5, 1);
        g.add(6, 5);
        Iteration<Integer> predOne = g.predecessors(1);
        int predCount = 0;
        while (predOne.hasNext()) {
            predCount++;
            predOne.next();
        }
        assertEquals(1, predCount);
    }

    @Test
    public void testDirectSuccIterators() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 6; i++) {
            g.add();
        }
        g.add(1, 2);
        g.add(2, 3);
        g.add(3, 2);
        g.add(3, 4);
        g.add(4, 5);
        g.add(5, 1);
        g.add(5, 6);
        g.add(6, 5);
        Iteration<Integer> succOne = g.successors(2);
        int count = 0;
        while (succOne.hasNext()) {
            count++;
            succOne.next();
        }
        assertEquals(1, count);

        Iteration<Integer> succTwo = g.successors(5);
        int countTwo = 0;
        while (succTwo.hasNext()) {
            countTwo++;
            succTwo.next();
        }
        assertEquals(2, countTwo);
    }

    @Test
    public void directEdgeIterators() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 6; i++) {
            g.add();
        }
        g.add(1, 4);
        g.add(1, 6);
        g.add(2, 3);
        g.add(3, 2);
        g.add(3, 4);
        g.add(4, 1);
        g.add(5, 6);
        g.add(6, 3);
        g.add(6, 5);
        int count = 0;
        Iteration<int[]> edgeIterator = g.edges();
        ArrayList<int[]> iterChecker = new ArrayList<>();
        while (edgeIterator.hasNext()) {
            count++;
            iterChecker.add(edgeIterator.next());
        }
        assertEquals(g.edgeSize(), iterChecker.size());
        assertEquals(9, count);
    }

    @Test
    public void testDirectRemove() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 6; i++) {
            g.add();
        }
        g.add(1, 4);
        g.add(1, 6);
        g.add(2, 3);
        g.add(2, 6);
        g.add(3, 2);
        g.add(3, 4);
        g.add(3, 5);
        g.add(3, 6);
        g.add(4, 1);
        g.add(4, 5);
        g.add(4, 6);
        g.add(5, 6);
        g.add(6, 3);
        g.add(6, 5);
        g.remove(6, 5);
        assertEquals(g.edgeSize(), 13);
        g.remove(6);
        g.add();
        g.add(6, 5);
        assertEquals(1, g.outDegree(1));
        assertEquals(3, g.outDegree(3));
        assertEquals(0, g.inDegree(6));
        assertEquals(1, g.outDegree(6));
        assertEquals(6, g.maxVertex());
    }

    @Test
    public void testDirectRemoveAll() {
        DirectedGraph g = new DirectedGraph();
        for (int i = 0; i < 6; i++) {
            g.add();
        }
        g.add(1, 4);
        g.add(1, 6);
        g.add(2, 3);
        g.add(2, 6);
        g.add(3, 2);
        g.add(3, 4);
        g.add(3, 5);
        g.add(3, 6);
        g.add(4, 1);
        g.add(4, 5);
        g.add(4, 6);
        g.add(5, 6);
        g.add(6, 3);
        g.add(6, 5);
        g.remove(1);
        g.remove(2);
        g.remove(3);
        g.remove(6);
        assertEquals(2, g.vertexSize());
        assertEquals(1, g.inDegree(5));
        assertEquals(0, g.outDegree(5));
        assertEquals(1, g.outDegree(4));
        g.add();
        g.add();
        g.add();
        assertEquals(5, g.maxVertex());
        assertEquals(1, g.inDegree(5));
        assertEquals(0, g.outDegree(5));
        assertEquals(1, g.outDegree(4));
    }

    @Test
    public void testUndirectRemove() {
        UndirectedGraph G = new UndirectedGraph();
        for (int i = 0; i < 6; i++) {
            G.add();
        }
        G.add(1, 3);
        G.add(1, 5);
        G.add(2, 5);
        G.add(3, 6);
        G.add(4, 1);
        G.add(4, 2);
        G.add(4, 6);
        G.add(5, 1);
        G.add(5, 2);
        G.add(5, 6);
        G.add(6, 1);

        assertEquals(9, G.edgeSize());
        G.remove(3);
        G.remove(4);
        G.remove(5);
        assertEquals(6, G.maxVertex());
        G.remove(6);
        assertEquals(2, G.maxVertex());
        assertEquals(0, G.edgeSize());
    }

    @Test
    public void testUndirectRemoveAll() {
        UndirectedGraph G = new UndirectedGraph();
        for (int i = 0; i < 6; i++) {
            G.add();
        }
        G.add(1, 3);
        G.add(1, 5);
        G.add(2, 5);
        G.add(3, 6);
        G.add(4, 1);
        G.add(4, 2);
        G.add(4, 6);
        G.add(5, 1);
        G.add(5, 2);
        G.add(5, 6);
        G.add(6, 1);
        G.remove(6);
        G.remove(3);
        G.remove(1);
        G.remove(2);
        G.remove(4);
        assertEquals(1, G.vertexSize());
        G.add();
        G.add();
        G.add();
        G.add(1, 3);
        G.add(1, 2);
        G.add(2, 5);
        G.add(2, 4);
        G.add(5, 6);
        G.add(5, 3);
        assertEquals(G.edgeSize(), G.getEdges().size());
        assertEquals(4, G.edgeSize());

    }

}
