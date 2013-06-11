package de.konsteirama.drawinglibrary;

import org.jgrapht.Graph;

import com.mxgraph.swing.mxGraphComponent;

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
     * Set a new graph which should be drawn.
     * @param g The new graph
     */
    void setGraph(Graph<V, E> g);
    
}
