package de.konsteirama.tests;

import static org.junit.Assert.fail;

import java.util.HashMap;


import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
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
		ListenableGraph<String, DefaultEdge> jGraphT = 
			new ListenableDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		// fill graph with data
		String v1 = "Vertex 1";
		String v2 = "Vertex 2";
		String v3 = "Vertex 3";
		String v4 = "Vertex 4";

		jGraphT.addVertex(v1);
		jGraphT.addVertex(v2);
		jGraphT.addVertex(v3);
		jGraphT.addVertex(v4);

		jGraphT.addEdge(v1, v2);
		jGraphT.addEdge(v1, v3);
		jGraphT.addEdge(v1, v4);
		jGraphT.addEdge(v2, v3);
		jGraphT.addEdge(v3, v4);

		// Create jgraphx graph and test it
		JGraphXAdapter<String, DefaultEdge> graphX = 
				new JGraphXAdapter<String, DefaultEdge>(jGraphT);
		testMapping(graphX);

		try {
			graphX.addJGraphTEdge(new DefaultEdge());
			graphX.addJGraphTEdge(new DefaultEdge());
			graphX.addJGraphTEdge(new DefaultEdge());

			graphX.addJGraphTVertex("New Vertex 1");
			graphX.addJGraphTVertex("New Vertex 2");
			graphX.addJGraphTVertex("New Vertex 3");

			// Add the same vertex 2 times
			String v = "Some Vertex";
			graphX.addJGraphTVertex(v);
			graphX.addJGraphTVertex(v);

			// Add the same edge 2 times
			DefaultEdge edge = new DefaultEdge();
			graphX.addJGraphTEdge(edge);
			graphX.addJGraphTEdge(edge);

		} catch (Exception e) {
			fail("Unexpected error during adding new edges or vertices");
		}

		testMapping(graphX);

		// add some data to the jgrapht graph - changes should be propagated
		// through jgraphxadapters graphlistener interface

		String v5 = "Vertex 5";
		String v6 = "Vertex 6";
		String v7 = "Vertex 7";
		String v8 = "Vertex 8";

		jGraphT.addVertex(v5);
		jGraphT.addVertex(v6);
		jGraphT.addVertex(v7);
		jGraphT.addVertex(v8);

		jGraphT.addEdge(v5, v6);
		jGraphT.addEdge(v5, v7);
		jGraphT.addEdge(v5, v8);
		jGraphT.addEdge(v6, v7);
		jGraphT.addEdge(v7, v8);

		testMapping(graphX);
	}

	/**
	 * Tests conditions if graph is initialized without a JgraphT graph.
	 */
	@Test
	public final void nullInitializationTest() {
		JGraphXAdapter<String, String> graph = null;

		try {
			graph = new JGraphXAdapter<String, String>(null);
		} catch (Exception e) {
			fail("Unexpected error encountered during " 
					+ " creation of JGraphXAdapter with null");
		}

		testMapping(graph);

		try {
			graph.addJGraphTEdge("New Edge 1");
			graph.addJGraphTEdge("New Edge 2");
			graph.addJGraphTEdge("New Edge 3");

			graph.addJGraphTVertex("New Vertex 1");
			graph.addJGraphTVertex("New Vertex 2");
			graph.addJGraphTVertex("New Vertex 3");
		} catch (Exception e) {
			fail("Unexpected error during adding new edges or vertices");
		}

		testMapping(graph);
	}

	/**
	 * Tests the mapping of the graph for consistency. Mapping includes: -
	 * getCellToEdgeMap - getEdgeToCellMap - getCellToVertexMap -
	 * getVertexToCellMap
	 * 
	 * @param graph
	 * The graph to be tested
	 * 
	 * @param <E>
	 * The class used for the edges of the JGraphXAdapter
	 * 
	 * @param <V>
	 * The class used for the vertices of the JGraphXAdapter
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
		if (!cellToEdgeMap.keySet().equals(edgeToCellMap.values())) {
			fail("CellToEdgeMap has not the "
					+ "same keys as the values in EdgeToCellMap");
		}

		if (!edgeToCellMap.keySet().equals(cellToEdgeMap.values())) {
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
		if (!cellToVertexMap.keySet().equals(vertexToCellMap.values())) {
			fail("CellToVertexMap has not the same "
					+ "keys as the values in VertexToCellMap");
		}

		if (!vertexToCellMap.keySet().equals(cellToVertexMap.values())) {
			fail("VertexToCellMap has not the same "
					+ "keys as the values in CellToVertexMap");
		}
	}
}
