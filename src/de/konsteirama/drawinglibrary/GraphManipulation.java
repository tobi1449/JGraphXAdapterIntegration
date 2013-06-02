package de.konsteirama.drawinglibrary;

import com.mxgraph.swing.mxGraphComponent;

/**
 * This class implements the GraphManipulationInterface. It handles 
 * manipulations that are done on the JGraphX-graph that is viewed on the 
 * panel. Manipulation will be done by the user.
 * @author Natascha
 *
 */
public class GraphManipulation implements GraphManipulationInterface {

	/**
	 * GraphComponent is the panel the graph is drawn in.
	 */
    private mxGraphComponent graphComponent;

    /**
     * Constructor of the class. Creates an instance of the
     * GraphManipulation class that operates on a given
     * graphComponent.
     * @param graphComponent
     */
    public GraphManipulation(mxGraphComponent graphComponent) {
        this.graphComponent = graphComponent;
    }

	/**
	 * Returns a boolean denoting whether the calling graph is able
	 * to perform a redo-operation.
	 */
    @Override
	public boolean canRedo() {
		return false;
	}

    /**
     * Returns a boolean denoting whether the calling graph is able
     * to perform an undo-operation.
     */
	@Override
	public boolean canUndo() {
		return false;
	}

	/**
	 * Centers the view of the panel on the selected node.
	 */
	@Override
	public void centerNode(Object node) {

	}

	/**
	 * Colors a given node in a given color.
	 */
	@Override
	public void colorNode(Object node, Object color) {
		
	}

	/**
	 * Marks the edge between two given nodes by adding a small grey 
	 * arrow and coloring the edge.
	 */
	@Override
	public void markEdge(Object node1, Object node2) {
		
	}
	
	/**
	 * Gives a hierarchical order to the displayed graph.
	 */
	@Override
	public void reapplyHierarchicalLayout() {
		
	}

	/**
	 * Redoes a previously undone action on the graph.
	 */
	@Override
	public void redo() {
		
	}

	/**
	 * Removes the given node from the graph. Removal only effects
	 * the JGraphX-graph.
	 */
	@Override
	public void removeNode(Object node) {
		
	}

	/**
	 * Alters the attribute name of a given node by replacing it
	 * by a given new name. Renaming only effects the JGraphX-graph.
	 */
	@Override
	public void renameNode(Object node, String newName) {
		
	}

	/**
	 * Resets the layout of the JGrapgX-graph on the panel to the original
	 * JGraphT-graph.
	 */
	@Override
	public void resetLayout() {
		
	}

	/**
	 * Undoes a previously performed action on the graph.
	 */
	@Override
	public void undo() {
		
	}

	/**
	 * Zooms the panel to the given factor. It will magnify the graph, if the 
	 * graph is too big for the panel only a 
	 * section of the whole graph will be shown. This method zooms
	 * to the center of the panel.
	 */
	@Override
	public void zoom(double factor) {
		
	}

	/**
	 * Zooms the panel to the given factor, centering on the given coordinates.
	 */
	@Override
	public void zoom(double factor, double centerx, double centery) {
		
	}

}
