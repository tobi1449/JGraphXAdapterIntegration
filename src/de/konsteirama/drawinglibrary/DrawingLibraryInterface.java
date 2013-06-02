package de.konsteirama.drawinglibrary;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.JComponent;

/**
 * Interface for interaction with a graph drawing library.
 */
public interface DrawingLibraryInterface {

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
    GraphManipulationInterface getGraphManipulationInterface();

    /**
     * Returns the panel in which the graph is drawn.
     * @return A JComponent which draws the specified graphs
     */
    JComponent getPanel();

    /**
     * Set a new graph which should be drawn.
     * @param g The new graph
     */
    void setGraph(ListenableGraph<String, DefaultEdge> g);
    
}
