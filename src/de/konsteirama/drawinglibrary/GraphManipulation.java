package de.konsteirama.drawinglibrary;

import com.mxgraph.swing.mxGraphComponent;

public class GraphManipulation implements GraphManipulationInterface {

    mxGraphComponent graphComponent;

    public GraphManipulation(mxGraphComponent graphComponent)
    {
        this.graphComponent = graphComponent;
    }

	@Override
	public boolean canRedo() {
		return false;
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public void centerNode(Object node) {

	}

	@Override
	public void colorNode(Object node, Object color) {
		
	}

	@Override
	public void markEdge(Object node1, Object node2) {
		
	}

	@Override
	public void reapplyHierarchicalLayout() {
		
	}

	@Override
	public void redo() {
		
	}

	@Override
	public void removeNode(Object node) {
		
	}

	@Override
	public void renameNode(Object node, String newName) {
		
	}

	@Override
	public void resetLayout() {
		
	}

	@Override
	public void undo() {
		
	}

	@Override
	public void zoom(double factor) {
		
	}

	@Override
	public void zoom(double factor, double centerx, double centery) {
		
	}

}
