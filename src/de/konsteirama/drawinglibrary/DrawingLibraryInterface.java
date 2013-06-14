package de.konsteirama.drawinglibrary;

import org.jgrapht.Graph;

import com.mxgraph.swing.mxGraphComponent;

import java.awt.Point;

/**
 * Interface for interaction with a graph drawing library.
 *
 * @param <V> Vertices
 * @param <E> Edges
 */
public interface DrawingLibraryInterface<V, E> {

    /**
     * Export the currently drawn graph to the path using the specified format.
     * @param format The export format
     * @param path The path where the exported file should be saved
     */
    void export(String format, String path);

    /**
     * Returns all available formats for exporting.
     * @return Available formats in an array
     */
    String[] getAvailableExportFormats();

    /**
     * Returns the Interface for registering events.
     * @return An instance of the GraphEventInterface
     */
    GraphEventInterface getGraphEventInterface();

    /**
     * Returns the interface for manipulating the shown graph.
     * @return An instance of the GraphManipulationInterface
     */
    GraphManipulationInterface<V, E> getGraphManipulationInterface();

    /**
     * Returns the panel in which the graph is drawn.
     * @return A mxGraphComponent which draws the specified graphs
     */
    mxGraphComponent getPanel();

    /**
     * Returns the node located at the specified point
     * @param p Location to look for a node
     * @return Node located at the given point or null if there is no node
     */
    V getNodeAt(Point p);

    /**
     * Returns the edge located at the specified point
     * @param p Location to look for an edge
     * @return Edge located at the given point or null if there is no edge
     */
    E getEdgeAt(Point p);

    /**
     * Sets a new graph which should be drawn.
     * @param g The new graph
     */
    void setGraph(Graph<V, E> g);

    /**
     * Returns the current graph.
     * @return the current graph
     */
    Graph<V,E> getGraph();
    
}
