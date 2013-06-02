package de.konsteirama.tests;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.junit.Assert;
import org.junit.Test;

import com.mxgraph.model.mxCell;

import de.konsteirama.jgraphxadapter.JGraphXAdapter;

/**
 * Test methods for the class JGraphXAdapter.
 * 
 * @author KonSteiRaMa
 * 
 */
public class JGraphXAdapterTests {

    /**
     * Test scenarios under normal conditions.
     */
    @Test
    public final void genericTest() {
        ListenableGraph<String, DefaultEdge> jGraphT 
         = new ListenableDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

        // fill graph with data
        String v1 = "Vertex 1";
        String v2 = "Vertex 2";
        String v3 = "Vertex 3";
        String v4 = "Vertex 4";

        jGraphT.addVertex(v1);
        jGraphT.addVertex(v2);
        jGraphT.addVertex(v3);
        jGraphT.addVertex(v4);

        final int expectedEdges = 5;
        jGraphT.addEdge(v1, v2);
        jGraphT.addEdge(v1, v3);
        jGraphT.addEdge(v1, v4);
        jGraphT.addEdge(v2, v3);
        jGraphT.addEdge(v3, v4);

        // Create jgraphx graph and test it
        JGraphXAdapter<String, DefaultEdge> graphX = 
                new JGraphXAdapter<String, DefaultEdge>(jGraphT);
        testMapping(graphX);
        
        // test if all values are in the jgraphx graph
        Object[] expectedArray = {v1, v2, v3, v4};
        Arrays.sort(expectedArray);
        
        Object[] realArray = graphX.getCellToVertexMap().values().toArray(); 
        Arrays.sort(realArray);
        Assert.assertArrayEquals(expectedArray, realArray);
        
        realArray = graphX.getVertexToCellMap().keySet().toArray();
        Arrays.sort(realArray);
        Assert.assertArrayEquals(expectedArray, realArray);
        
        int edgesCount = graphX.getCellToEdgeMap().values().size();
        Assert.assertEquals(expectedEdges, edgesCount);
        
        edgesCount = graphX.getEdgeToCellMap().keySet().size();
        Assert.assertEquals(expectedEdges, edgesCount);
    }


    /**
     * Tests the correct implementation of the GraphListener interface.
     */
    @Test
    public final void listenerTest() {
        ListenableGraph<String, String> jGraphT 
            = new ListenableDirectedGraph<String, String>(String.class);
        
        JGraphXAdapter<String, String> graphX 
            = new JGraphXAdapter<String, String>(jGraphT);
        
        // add some data to the jgrapht graph - changes should be propagated
        // through jgraphxadapters graphlistener interface

        String v1 = "Vertex 1";
        String v2 = "Vertex 2";
        String v3 = "Vertex 3";
        String v4 = "Vertex 4";

        jGraphT.addVertex(v1);
        jGraphT.addVertex(v2);
        jGraphT.addVertex(v3);
        jGraphT.addVertex(v4);

        final int expectedEdges = 5;
        jGraphT.addEdge(v1, v2, "Edge 1");
        jGraphT.addEdge(v1, v3, "Edge 2");
        jGraphT.addEdge(v1, v4, "Edge 3");
        jGraphT.addEdge(v2, v3, "Edge 4");
        jGraphT.addEdge(v3, v4, "Edge 5");

        testMapping(graphX);
        
        // test if all values are in the jgraphx graph
        Object[] expectedArray = {v1, v2, v3, v4};
        Arrays.sort(expectedArray);
        
        Object[] realArray = graphX.getCellToVertexMap().values().toArray(); 
        Arrays.sort(realArray);
        Assert.assertArrayEquals(expectedArray, realArray);
        
        realArray = graphX.getVertexToCellMap().keySet().toArray();
        Arrays.sort(realArray);
        Assert.assertArrayEquals(expectedArray, realArray);
        
        int edgesCount = graphX.getCellToEdgeMap().values().size();
        Assert.assertEquals(expectedEdges, edgesCount);
        
        edgesCount = graphX.getEdgeToCellMap().keySet().size();
        Assert.assertEquals(expectedEdges, edgesCount);
    }
    
    
    /**
     * Tests conditions if graph is initialized without a JgraphT graph.
     */
    @Test
    public final void nullInitializationTest() {
        try {
            new JGraphXAdapter<String, String>(null);
            fail("Expected illegal argument exception");
        } catch (IllegalArgumentException e) {
            // expected result
            Assert.assertTrue(true);
        } catch (Exception e) {
            fail("Unexpected error encountered during "
                    + " creation of JGraphXAdapter with null");
        }
    }

    
    /**
     * Tests the JGraphXAdapter with 1.000 nodes and 1.000 edges.
     */
    @Test
    public final void loadTest() {
        final int maxVertices = 1000;
        final int maxEdges = 1000;

        ListenableGraph<Integer, DefaultEdge> jGraphT 
            = new ListenableDirectedGraph<Integer, DefaultEdge>(
                        DefaultEdge.class);

        for (int i = 0; i < maxVertices; i++) {
            jGraphT.addVertex(i);
        }

        for (int i = 0; i < maxEdges; i++) {
            jGraphT.addEdge(i, (i + 1) % jGraphT.vertexSet().size());
        }

        JGraphXAdapter<Integer, DefaultEdge> graphX = null;

        try {
            graphX = new JGraphXAdapter<Integer, DefaultEdge>(jGraphT);
        } catch (Exception e) {
            fail("Unexpected error while creating JgraphXAdapter with"
                    + maxVertices + " vertices and " + maxEdges + " Edges");
        }

        testMapping(graphX);

    }
    
    // ========================Helper Methods===============================
    
    /**
     * Tests the mapping of the graph for consistency. Mapping includes: -
     * getCellToEdgeMap - getEdgeToCellMap - getCellToVertexMap -
     * getVertexToCellMap
     * 
     * @param graph
     *            The graph to be tested
     * 
     * @param <E>
     *            The class used for the edges of the JGraphXAdapter
     * 
     * @param <V>
     *            The class used for the vertices of the JGraphXAdapter
     */
    private <V, E> void testMapping(final JGraphXAdapter<V, E> graph) {

        // Edges
        HashMap<mxCell, E> cellToEdgeMap = graph.getCellToEdgeMap();
        HashMap<E, mxCell> edgeToCellMap = graph.getEdgeToCellMap();

        // Test for null
        if (cellToEdgeMap == null) {
            fail("GetCellToEdgeMap returned null");
        }

        if (edgeToCellMap == null) {
            fail("GetEdgeToCellMap returned null");
        }

        // Compare keys to values
        if (!compare(edgeToCellMap.values(), cellToEdgeMap.keySet())) {
            fail("CellToEdgeMap has not the "
                    + "same keys as the values in EdgeToCellMap");
        }

        if (!compare(cellToEdgeMap.values(), edgeToCellMap.keySet())) {
            fail("EdgeToCellMap has not the "
                    + "same keys as the values in CellToEdgeMap");
        }

        // Vertices
        HashMap<mxCell, V> cellToVertexMap = graph.getCellToVertexMap();
        HashMap<V, mxCell> vertexToCellMap = graph.getVertexToCellMap();

        // Test for null
        if (cellToVertexMap == null) {
            fail("GetVertexToCellMap returned null");
        }

        if (vertexToCellMap == null) {
            fail("GetCellToVertexMap returned null");
        }

        // Compare keys to values
        if (!compare(vertexToCellMap.values(), cellToVertexMap.keySet())) {
            fail("CellToVertexMap has not the same "
                    + "keys as the values in VertexToCellMap");
        }

        if (!compare(cellToVertexMap.values(), vertexToCellMap.keySet())) {
            fail("VertexToCellMap has not the same "
                    + "keys as the values in CellToVertexMap");
        }
    }

    /**
     * Compares a collection to a set by creating a new set from
     * the collection and using equals. 
     * 
     * @param collection
     *            The collection that is compared
     * 
     * @param set
     *            The set that is compared
     * 
     * @param <T>
     *            The classtype of the set and collection.
     * 
     * @return True, if set and collection are equivalent; False if not.
     * 
     */
    private <T> boolean compare(
            final Collection<T> collection, final Set<T> set) {

        Set<T> compareSet = new HashSet<T>();
        compareSet.addAll(collection);

        return set.equals(compareSet);
    }
}
