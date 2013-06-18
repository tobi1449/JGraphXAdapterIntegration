package de.konsteirama.drawinglibrary;

import java.awt.Color;
import java.awt.Point;

/**
 * This class is an interface, that handels the mani-
 * pulations on the canvas, when implemented.
 *
 * @param <V> : vertices of the graph
 * @param <E> : edges of the graph
 */
public interface GraphManipulationInterface<V, E> {
    
    /**
     * Returns a boolean denoting whether the calling graph is able to perform
     * a redo-operation.
     *
     * @return if false then there was no undoable action performed earlier
     */
    boolean canRedo();

    /**
     * Returns a boolean denoting whether the calling graph is able to perform
     * an undo-operation.
     *
     * @return if true then there is an action that can be undone
     */
    boolean canUndo();

    /**
     * Centers the view of the panel on the selected node.
     *
     * @param node : a node of the graph
     */
    void centerNode(V node);

    /**
     * Colors a given node in a given color.
     *
     * @param node : an array of nodes of the graph
     * @param color : a color-parameter
     */
    void colorNode(V[] node, Color color);

    /**
     * Marks the edge between two given nodes by adding a small grey arrow and
     * coloring the edge.
     *
     * @param edges : an array of edges of the graph
     */
    void markEdge(E[] edges);
    
    /**
     * Unmarks the edge between two given nodes by removing
     * the small grey arrow and uncoloring the edge.
     *
     * @param edges : an array of edges of the graph
     */
    void unmarkEdge(E[] edges);

    /**
     * Gives a hierarchical order to the displayed graph.
     */
    void reapplyHierarchicalLayout();

    /**
     * Redoes a previously undone action on the graph.
     */
    void redo();

    /**
     * Removes the given node from the graph. Removal only effects the
     * JGraphX-graph.
     *
     * @param node : a JGraphX-graph node object
     */
    void removeNode(V node);

    /**
     * Alters the attribute name of a given node by replacing it by a given new
     * name. Renaming only effects the JGraphX-graph.
     *
     * @param node    : a node of the graph
     * @param newName : the name the node is given
     */
    void renameNode(V node, String newName);

    /**
     * Resets the layout of the JGrapgX-graph on the panel to the original
     * JGraphT-graph.
     */
    void resetLayout();

    /**
     * Undoes a previously performed action on the graph.
     */
    void undo();
    
    /**
     * Zooms the panel to the given factor. It will magnify the graph, if the
     * graph is too big for the panel only a section of the whole graph will be
     * shown. This method zooms to the center of the panel.
     *
     * @param factor : a double that represents the zoom factor
     *               (ranges from 0 to infinite, 1 is 100%)
     */
    void zoomTo(double factor);

    /**
     * Zooms the panel. It will magnify the graph, if the
     * graph is too big for the panel only a section of the whole graph will be
     * shown. This method zooms to the center of the panel.
     *
     * @param zoomIn : a boolean to zoom in or out
     */
    void zoom(boolean zoomIn);

    /**
     * Zooms the panel, centering on the given coordinates.
     *
     * @param zoomIn : a boolean to zoom in or out
     * @param center : the point zoom centers on
     */
    void zoom(boolean zoomIn, Point center);

    /**
     * Highlights a node and if specified its neighbors.
     * 
     * @param node : The node to be highlighted
     * 
     * @param hightlightNeighbors if checked, neighbors will be highlighted 
     * as well
     */
    void highlightNode(V node, boolean hightlightNeighbors);

    /**
     * Un-Highlights all nodes that are currently highlighted before.
     */
    void unHiglightAll();
}
