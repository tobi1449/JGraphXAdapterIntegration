package de.konsteirama.drawinglibrary;

import java.awt.Color;
import java.awt.Point;

public interface GraphManipulationInterface<V, E> {
    /**
     * returns a boolean, denoting whether the related graph is able to perform
     * a redo operation.
     *
     * @return
     */
    boolean canRedo();

    boolean canUndo();

    void centerNode(V node);

    void colorNode(V[] node, Color color);

    void markEdge(E[] edge);

    void reapplyHierarchicalLayout();

    void redo();

    void removeNode(V node);

    void renameNode(V node, String newName);

    void resetLayout();

    void undo();
    
    void zoomTo(double factor);

    void zoom(boolean zoomIn);

    void zoom(boolean zoomIn, Point center);
}
