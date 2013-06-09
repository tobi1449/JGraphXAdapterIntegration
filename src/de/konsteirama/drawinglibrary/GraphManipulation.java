package de.konsteirama.drawinglibrary;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxUndoManager;
import com.mxgraph.util.mxUndoableEdit;
import com.mxgraph.view.mxGraph;
<<<<<<< HEAD
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
=======
import de.konsteirama.jgraphxadapter.JGraphXAdapter;

import java.awt.Color;
>>>>>>> fead372e2fff6a642173da15ffc5c2426360caea

/**
 * This class implements the GraphManipulationInterface. It handles
 * manipulations that are done on the JGraphX-graph that is viewed on the panel.
 * Manipulation will be done by the user.
 *
 * @author Natascha
 */
<<<<<<< HEAD
public class GraphManipulation implements GraphManipulationInterface {

	/**
	 * GraphComponent is the panel the graph is drawn in.
	 */
	private mxGraphComponent graphComponent;

	/**
	 * manages the undo-operations on the calling graph
	 */
	private mxUndoManager undoManager;

	/**
	 * Constructor of the class. Creates an instance of the GraphManipulation
	 * class that operates on a given graphComponent.
	 * 
	 * @param graphComponent
	 *            : a JGRaphX graphComponent, shown on the panel
	 */
	public GraphManipulation(mxGraphComponent graphComponent) {
		this.graphComponent = graphComponent;

		// initiation of undoManager variable
		this.undoManager = new mxUndoManager();
		
		//notify undoManager about edits 
		graphComponent.getGraph().getModel().addListener(mxEvent.UNDO, undoHandler);
		graphComponent.getGraph().getView().addListener(mxEvent.UNDO, undoHandler);

	}

	protected mxIEventListener undoHandler = new mxIEventListener() {
		public void invoke(Object source, mxEventObject evt) {
			undoManager.undoableEditHappened((mxUndoableEdit) evt
					.getProperty("edit"));
		}
	};

	/**
	 * Returns a boolean denoting whether the calling graph is able to perform a
	 * redo-operation.
	 * @return if false then there was no undoable action perormed earlier
	 */
	@Override
	public boolean canRedo() {
		
		return undoManager.canRedo();
	}

	/**
	 * Returns a boolean denoting whether the calling graph is able to perform
	 * an undo-operation.
	 */
	@Override
	public boolean canUndo() {
		return undoManager.canUndo();
	}

	/**
	 * Centers the view of the panel on the selected node.
	 * 
	 * @param node
	 *            : a node of the graph
	 */
	@Override
	public void centerNode(Object node) {

	}

	/**
	 * Colors a given node in a given color.
	 * 
	 * @param node
	 *            : a node of the graph
	 * @param color
	 *            : a color-parameter
	 */
	@Override
	public void colorNode(Object node, Object color) {

	}

	/**
	 * Marks the edge between two given nodes by adding a small grey arrow and
	 * coloring the edge.
	 * 
	 * @param node1
	 *            : node where the edge starts
	 * @param node2
	 *            : node where the edge ends
	 */
	@Override
	public void markEdge(Object node1, Object node2) {

	}

	/**
	 * Gives a hierarchical order to the displayed graph.
	 */
	@Override
	public void reapplyHierarchicalLayout() {
		mxGraph graph = graphComponent.getGraph();
		
		graph.getModel().beginUpdate();
		
		mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
		layout.execute(graph.getDefaultParent());
		
		graph.getModel().endUpdate();

	}

	/**
	 * Redoes a previously undone action on the graph.
	 */
	@Override
	public void redo() {
		undoManager.redo();

	}

	/**
	 * Removes the given node from the graph. Removal only effects the
	 * JGraphX-graph.
	 * 
	 * @param node : a JGraphX-graph node object
	 */
	@Override
	public void removeNode(Object node) {
      mxGraph graph = graphComponent.getGraph();
	  Array[] nodes = new Array[1];
      node = nodes[0];
		graph.getModel().beginUpdate();
		
		graph.removeCells(nodes, true);
		
		graph.getModel().endUpdate();
	}

	/**
	 * Alters the attribute name of a given node by replacing it by a given new
	 * name. Renaming only effects the JGraphX-graph.
	 * 
	 * @param node
	 *            : a node of the graph
	 * @param newName
	 *            : the name the node is given
	 */
	@Override
	public void renameNode(Object node, String newName) {
		mxGraph graph = graphComponent.getGraph();
		String name= newName;
		Object renamedNode= node;
		

	}

	/**
	 * Resets the layout of the JGrapgX-graph on the panel to the original
	 * JGraphT-graph.
	 */
	@Override
	public void resetLayout() {
		mxGraph graph = graphComponent.getGraph();

	}

	/**
	 * Undoes a previously performed action on the graph.
	 */
	@Override
	public void undo() {
		undoManager.undo();

	}

	/**
	 * Zooms the panel to the given factor. It will magnify the graph, if the
	 * graph is too big for the panel only a section of the whole graph will be
	 * shown. This method zooms to the center of the panel.
	 * 
	 * @param factor
	 *            : a double that gives the zoom-factor
	 */
	@Override
	public void zoom(double factor) {

	}

	/**
	 * Zooms the panel to the given factor, centering on the given coordinates.
	 * 
	 * @param factor
	 *            : a double that gives the zoom-factor
	 * @param centerx
	 *            : x-coordinate of the point zoom centers on
	 * @param centery
	 *            : y-coordinate of the point zoom centers on
	 */
	@Override
	public void zoom(double factor, double centerx, double centery) {

	}
=======
public class GraphManipulation<V,E> implements GraphManipulationInterface<V,
        E> {

    /**
     * Adapter holding the current graph in JgraphX and JGraphT data structure.
     */
    private JGraphXAdapter<V,E> graphAdapter;

    /**
     * GraphComponent is the panel the graph is drawn in.
     */
    private mxGraphComponent graphComponent;

    /**
     * manages the undo-operations on the calling graph
     */
    private mxUndoManager undoManager;

    /**
     * Constructor of the class. Creates an instance of the GraphManipulation
     * class that operates on a given graphComponent.
     *
     * @param graphComponent : a JGRaphX graphComponent, shown on the panel
     */
    public GraphManipulation(mxGraphComponent graphComponent,
                             JGraphXAdapter<V,E> graphXAdapter) {
        this.graphComponent = graphComponent;
        this.graphAdapter = graphXAdapter;

        // initiation of undoManager variable
        this.undoManager = new mxUndoManager();

        //notify undoManager about edits
        graphComponent.getGraph().getModel().addListener(mxEvent.UNDO, undoHandler);
        graphComponent.getGraph().getView().addListener(mxEvent.UNDO, undoHandler);

    }

    protected mxIEventListener undoHandler = new mxIEventListener() {
        public void invoke(Object source, mxEventObject evt) {
            undoManager.undoableEditHappened((mxUndoableEdit) evt
                    .getProperty("edit"));
        }
    };

    /**
     * Returns a boolean denoting whether the calling graph is able to perform a
     * redo-operation.
     *
     * @return if falase then there was no undoable action perormed earlier
     */
    @Override
    public boolean canRedo() {
        return undoManager.canRedo();
    }

    /**
     * Returns a boolean denoting whether the calling graph is able to perform
     * an undo-operation.
     */
    @Override
    public boolean canUndo() {
        return undoManager.canUndo();
    }

    /**
     * Centers the view of the panel on the selected node.
     *
     * @param node : a node of the graph
     */
    @Override
    public void centerNode(V node) {

    }

    /**
     * Colors a given node in a given color.
     *
     * @param node  : a node of the graph
     * @param color : a color-parameter
     */
    @Override
    public void colorNode(V node, Color color) {

    }

    /**
     * Marks the edge between two given nodes by adding a small grey arrow and
     * coloring the edge.
     *
     * @param node1 : node where the edge starts
     * @param node2 : node where the edge ends
     */
    @Override
    public void markEdge(V node1, V node2) {

    }

    /**
     * Gives a hierarchical order to the displayed graph.
     */
    @Override
    public void reapplyHierarchicalLayout() {
        mxGraph graph = graphComponent.getGraph();

        graph.getModel().beginUpdate();

        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());

        graph.getModel().endUpdate();

    }

    /**
     * Redoes a previously undone action on the graph.
     */
    @Override
    public void redo() {
        undoManager.redo();

    }

    /**
     * Removes the given node from the graph. Removal only effects the
     * JGraphX-graph.
     *
     * @param node : a JGraphX-graph node object
     */
    @Override
    public void removeNode(V node) {

    }

    /**
     * Alters the attribute name of a given node by replacing it by a given new
     * name. Renaming only effects the JGraphX-graph.
     *
     * @param node    : a node of the graph
     * @param newName : the name the node is given
     */
    @Override
    public void renameNode(V node, String newName) {

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
        undoManager.undo();

    }

    /**
     * Zooms the panel to the given factor. It will magnify the graph, if the
     * graph is too big for the panel only a section of the whole graph will be
     * shown. This method zooms to the center of the panel.
     *
     * @param factor : a double that gives the zoom-factor
     */
    @Override
    public void zoom(double factor) {

    }

    /**
     * Zooms the panel to the given factor, centering on the given coordinates.
     *
     * @param factor  : a double that gives the zoom-factor
     * @param centerx : x-coordinate of the point zoom centers on
     * @param centery : y-coordinate of the point zoom centers on
     */
    @Override
    public void zoom(double factor, double centerx, double centery) {

    }
>>>>>>> fead372e2fff6a642173da15ffc5c2426360caea

}
