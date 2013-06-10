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

    void colorNode(V node, Color color);

    void markEdge(V node1, V node2);

    void reapplyHierarchicalLayout();

    void redo();

    void removeNode(V node);

    void renameNode(V node, String newName);

    void resetLayout();

    void undo();

    void zoom(double factor);

    void zoom(double factor, Point center);
}
