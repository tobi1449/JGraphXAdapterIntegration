package de.konsteirama.drawinglibrary;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUndoManager;
import com.mxgraph.util.mxUndoableEdit;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import de.konsteirama.jgraphxadapter.JGraphXAdapter;
import java.awt.Color;

import org.jgrapht.Graph;


/**
 * This class implements the GraphManipulationInterface. It handles
 * manipulations that are done on the JGraphX-graph that is viewed on the panel.
 * Manipulation will be done by the user.
 * @author Natascha
 *
 * @param <V> : vertices of the graph
 * @param <E> : edges of the graph
 */
public class GraphManipulation<V, E> 
             implements GraphManipulationInterface<V, E> {

    /**
     * Adapter holding the current graph in JgraphX and JGraphT data structure.
     */
    private JGraphXAdapter<V, E> graphAdapter;

    /**
     * GraphComponent is the panel the graph is drawn in.
     */
    private mxGraphComponent graphComponent;

    /**
     * Manages the undo-operations on the calling graph.
     */
    private mxUndoManager undoManager;

    /**
     * Constructor of the class. Creates an instance of the GraphManipulation
     * class that operates on a given graphComponent.
     *
     * @param graphComponent : a JGRaphX graphComponent, shown on the panel
     * 
     * @param graphXAdapter : 
     */
    public GraphManipulation(mxGraphComponent graphComponent,
                             JGraphXAdapter<V, E> graphXAdapter) {
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
     * @return if falase then there was no undoable action performed earlier
     */
    @Override
    public boolean canRedo() {
        return undoManager.canRedo();
    }

    /**
     * Returns a boolean denoting whether the calling graph is able to perform
     * an undo-operation.
     * 
     * @return if true then there is an action that can be undone
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
        mxGraph graph = graphComponent.getGraph();
        
       graph.getModel().beginUpdate();
       
       graphComponent.scrollCellToVisible(node, true);
       
       graph.getModel().endUpdate();

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
        
        mxGraph graph = graphComponent.getGraph();
        
        Object[] cells = new Object[1];
        cells[0] = node;
        
        graph.getModel().beginUpdate();

        graph.removeCells(cells, true);

        graph.getModel().endUpdate();

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
        mxGraph graph = graphComponent.getGraph();
        
        String nodeName = newName;
        
        graphComponent.labelChanged(node, nodeName, evt);
    }

    /**
     * Resets the layout of the JGrapgX-graph on the panel to the original
     * JGraphT-graph.
     */
    @Override
    public void resetLayout() {
        
        Graph<V, E> graphT = graphAdapter.getGraph();
        
        JGraphXAdapter<V, E> newGraphAdapter = new JGraphXAdapter<V, E>(graphT);
        
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
        // could be that we have to use the Betrag of factor
        if (factor != graphComponent.getZoomFactor()) {
            graphComponent.setZoomFactor(factor);
        }
        
        if (!graphComponent.isCenterZoom()) {
          graphComponent.setCenterZoom(true); 
        }
        
        //factor isn't a good measure ask if it could be changed
        if (factor < 0) {
        graphComponent.zoomIn(); 
        }
        else {
        graphComponent.zoomOut();
        }

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
}
