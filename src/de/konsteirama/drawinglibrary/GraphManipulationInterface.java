package de.konsteirama.drawinglibrary;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 30.05.13
 * Time: 17:51
 */
public interface GraphManipulationInterface {

    public boolean canRedo();

    public boolean canUndo();

    public void centerNode(Object node);

    public void colorNode(Object node, Object color);

    public void markEdge(Object node1, Object node2);

    public void reapplyHierarchicalLayout();

    public void redo();

    public void removeNode(Object node);

    public void renameNode(Object node, String newName);

    public void resetLayout();

    public void undo();

    public void zoom(double factor);

    public void zoom(double factor, double centerx, double centery);
}
