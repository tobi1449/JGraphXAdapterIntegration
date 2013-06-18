package de.konsteirama.drawinglibrary;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxUndoManager;
import com.mxgraph.util.mxUndoableEdit;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import de.konsteirama.jgraphxadapter.JGraphXAdapter;
import org.jgrapht.Graph;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the GraphManipulationInterface. It handles
 * manipulations that are done on the JGraphX-graph that
 * is viewed on the panel.
 * Manipulation will be done by the user.
 *
 * @param <V> : vertices of the graph
 * @param <E> : edges of the graph
 */
class GraphManipulation<V, E> implements GraphManipulationInterface<V, E> {

    protected mxIEventListener undoHandler = new mxIEventListener() {
        public void invoke(Object source, mxEventObject evt) {
            undoManager.undoableEditHappened((mxUndoableEdit) evt
                    .getProperty("edit"));
        }
    };
    
    /**
     * GraphComponent is the panel the graph is drawn in.
     */
    private mxGraphComponent graphComponent;
    /**
     * Manages the undo-operations on the calling graph.
     */
    private mxUndoManager undoManager;

    /**
     * Currently highlighted cells with their previous color.
     */
    private HashMap<mxICell, Color> markedCells;

    /**
     * Constructor of the class. Creates an instance of the GraphManipulation
     * class that operates on a given graphComponent.
     *
     * @param pGraphComponent : a JGRaphX graphComponent, shown on the panel
     */
    public GraphManipulation(mxGraphComponent pGraphComponent) {
        this.graphComponent = pGraphComponent;

        // initiation of undoManager variable
        this.undoManager = new mxUndoManager();

        //notify undoManager about edits
        graphComponent.getGraph().getModel().
                addListener(mxEvent.UNDO, undoHandler);
        graphComponent.getGraph().getView().
                addListener(mxEvent.UNDO, undoHandler);

        markedCells = new HashMap<mxICell, Color>();

    }

    /**
     * Returns the current graph adapter.
     *
     * @return The current graph adapter
     */
    @SuppressWarnings("unchecked")
    private JGraphXAdapter<V, E> getGraphAdapter() {
        return (JGraphXAdapter<V, E>) graphComponent.getGraph();
    }

    /**
     * Returns the cell associated with the given node.
     *
     * @param node
     * @return
     */
    private mxICell getCellFromNode(V node) {
        return getGraphAdapter().getVertexToCellMap().get(node);
    }

    /**
     * Returns the cell associated with the given edge.
     *
     * @param edge
     * @return
     */
    private mxICell getCellFromEdge(E edge) {
        return getGraphAdapter().getEdgeToCellMap().get(edge);
    }

    /**
     * Returns the cells associated with the given nodes.
     *
     * @param nodes
     * @return
     */
    private mxICell[] getCellsFromNodes(V[] nodes) {
        mxICell[] cells = new mxICell[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            cells[i] = getCellFromNode(nodes[i]);
        }
        return cells;
    }

    /**
     * Returns the cells associated with the given edges.
     *
     * @param edges
     * @return
     */
    private mxICell[] getCellsFromEdges(E[] edges) {
        mxICell[] cells = new mxICell[edges.length];
        for (int i = 0; i < edges.length; i++) {
            cells[i] = getCellFromEdge(edges[i]);
        }
        return cells;
    }

    /**
     * Translates a point from graphComponent to graph.
     *
     * @param p : the point on the canvas
     * @return the actual point on graph
     */
    private Point getPointOnGraph(Point p) {
        mxGraph graph = graphComponent.getGraph();

        double s = graph.getView().getScale();
        Point tr = graph.getView().getTranslate().getPoint();

        double off = graph.getGridSize() / 2;
        double x = graph.snap(p.getX() / s - tr.getX() - off);
        double y = graph.snap(p.getY() / s - tr.getY() - off);

        return new Point((int) x, (int) y);
    }

    @Override
    public boolean canRedo() {
        return undoManager.canRedo();
    }

    @Override
    public boolean canUndo() {
        return undoManager.canUndo();
    }

    @Override
    public void centerNode(V node) {
        mxGraph graph = graphComponent.getGraph();

        graph.getModel().beginUpdate();

        graphComponent.scrollCellToVisible(getCellFromNode(node), true);

        graph.getModel().endUpdate();
    }

    @Override
    public void colorNode(V[] nodes, Color color) {

        mxGraph graph = graphComponent.getGraph();

        graph.getModel().beginUpdate();

        graph.setCellStyles(mxConstants.STYLE_FILLCOLOR,
                mxUtils.hexString(color), getCellsFromNodes(nodes));

        graph.getModel().endUpdate();
    }

    @Override
    public void markEdge(E[] edges) {

        mxGraph graph = graphComponent.getGraph();

        graph.getModel().beginUpdate();

        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR,
                mxUtils.hexString(Color.black), getCellsFromEdges(edges));

        graph.getModel().endUpdate();
    }

    @Override
    public void unmarkEdge(E[] edges) {
        mxGraph graph = graphComponent.getGraph();

        graph.getModel().beginUpdate();

        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR,
                mxUtils.hexString(new Color(100, 130, 185)), getCellsFromEdges(edges));

        graph.getModel().endUpdate();
    }

    @Override
    public void reapplyHierarchicalLayout() {
        mxGraph graph = graphComponent.getGraph();

        graph.getModel().beginUpdate();

        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());

        graph.getModel().endUpdate();
    }

    @Override
    public void redo() {
        undoManager.redo();
    }

    @Override
    public void removeNode(V node) {
        mxGraph graph = graphComponent.getGraph();

        Object[] cells = new Object[]{getCellFromNode(node)};

        // Adds all edges connected to the node
        cells = graph.addAllEdges(cells);

        graph.getModel().beginUpdate();

        // Deletes every cell
        for (Object object : cells) {
            graph.getModel().remove(object);
        }

        graph.getModel().endUpdate();
    }

    @Override
    public void highlightNode(V node, boolean hightlightNeighbors) {

        ArrayList<mxICell> cells = new ArrayList<mxICell>(1);
        mxICell cell = getCellFromNode(node);
        cells.add(cell);

        if (hightlightNeighbors) {
            for (int i = 0; i < cell.getEdgeCount(); i++) {
                mxCell edge = (mxCell) cell.getEdgeAt(i);
                markedCells.put(edge, mxUtils.getColor(getGraphAdapter()
                        .getCellStyle(edge), mxConstants.STYLE_STROKECOLOR));

                mxICell source = edge.getSource();
                mxICell target = edge.getTarget();

                if (!markedCells.containsKey(source)) {
                    markedCells.put(source, mxUtils.getColor(getGraphAdapter(
                            ).getCellStyle(source),
                            mxConstants.STYLE_STROKECOLOR));
                }
                
                if (!markedCells.containsKey(target)) {
                    markedCells.put(target, mxUtils.getColor(getGraphAdapter(
                            ).getCellStyle(target),
                            mxConstants.STYLE_STROKECOLOR));
                }
            }
        }

        getGraphAdapter().setCellStyles(mxConstants.STYLE_STROKECOLOR,
                mxUtils.getHexColorString(
                        Color.yellow), cells.toArray());

    }

    @Override
    public void unHiglightAll() {
        graphComponent.getGraph().getModel().beginUpdate();

        for (mxICell cell : markedCells.keySet()) {
            getGraphAdapter().setCellStyles(mxConstants.STYLE_STROKECOLOR,
                    mxUtils.getHexColorString(markedCells.get(cell)),
                    new Object[]{cell});
        }
        graphComponent.getGraph().getModel().endUpdate();
    }

    @Override
    public void renameNode(V node, String newName) {
        mxGraph graph = graphComponent.getGraph();

        String nodeName = newName;

        graph.getModel().beginUpdate();

        getCellFromNode(node).setValue(newName);

        graph.getModel().endUpdate();
    }

    @Override
    public void resetLayout() {
        Graph<V, E> graphT = getGraphAdapter().getGraph();

        JGraphXAdapter<V, E> newGraphAdapter = new JGraphXAdapter<V, E>(graphT);
        
    }

    @Override
    public void undo() {
        undoManager.undo();
    }


    @Override
    public void zoomTo(double factor) {
        graphComponent.zoomTo(factor, true);
    }


    @Override
    public void zoom(boolean zoomIn) {
        graphComponent.setCenterZoom(true);

        if (zoomIn) {
            graphComponent.zoomIn();
        } else {
            graphComponent.zoomOut();
        }
    }


    @Override
    public void zoom(boolean zoomIn, Point center) {
        Point pointOnCanvas = getPointOnGraph(center);

        zoom(zoomIn);

        Point newPointOnCanvas = getPointOnGraph(center);

        double zoomFactor = zoomIn ? graphComponent.getZoomFactor()
                : 1 / graphComponent.getZoomFactor();

        Rectangle rect = graphComponent.getGraphControl().getVisibleRect();

        rect.setLocation((int) (rect.getX() * zoomFactor),
                (int) (rect.getY() * zoomFactor));

        graphComponent.getGraphControl().scrollRectToVisible(rect);

        /*int x = (int)((pointOnCanvas.getX() -
                newPointOnCanvas.getX()) * zoomFactor);
        int y = (int)((pointOnCanvas.getY() -
                newPointOnCanvas.getY()) * zoomFactor);
        Point delta = new Point(x, y);

        Rectangle rect = graphComponent.getGraphControl().getVisibleRect();
        rect.translate((int)delta.getX(), (int)delta.getY());

        graphComponent.getGraphControl().scrollRectToVisible(rect, true);*/
    }
}
