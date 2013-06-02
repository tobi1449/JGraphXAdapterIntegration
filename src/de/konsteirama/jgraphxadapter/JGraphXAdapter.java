package de.konsteirama.jgraphxadapter;

import java.util.HashMap;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphListener;
import org.jgrapht.event.GraphVertexChangeEvent;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxGraph;

public class JGraphXAdapter<V, E> extends mxGraph implements
        GraphListener<V, E> {

    private Graph<V, E> graphT;

    private HashMap<V, mxCell> vertexToCellMap = new HashMap<V, mxCell>();

    private HashMap<E, mxCell> edgeToCellMap = new HashMap<E, mxCell>();

    private HashMap<mxCell, V> cellToVertexMap = new HashMap<mxCell, V>();

    private HashMap<mxCell, E> cellToEdgeMap = new HashMap<mxCell, E>();

    /*
     * CONSTRUCTOR
     */

    public JGraphXAdapter(final ListenableGraph<V, E> graphT) {
        // call normal constructor with graph class
        this((Graph<V, E>) graphT);
        
        graphT.addGraphListener(this);
    }
    
    public JGraphXAdapter(final Graph<V, E> graphT) {
        super();
        
        // Don't accept null as jgrapht graph
        if (graphT == null) {
            throw new IllegalArgumentException();
        } else {
            this.graphT = graphT;
        }
        
        // generate the drawing
        insertJGraphT(graphT);
        
        setAutoSizeCells(true);
    }

    /*
     * METHODS
     */

    public HashMap<V, mxCell> getVertexToCellMap() {
        return vertexToCellMap;
    }

    public HashMap<E, mxCell> getEdgeToCellMap() {
        return edgeToCellMap;
    }

    public HashMap<mxCell, E> getCellToEdgeMap() {
        return cellToEdgeMap;
    }

    public HashMap<mxCell, V> getCellToVertexMap() {
        return cellToVertexMap;
    }

    /*
     * GRAPH LISTENER
     */

    @Override
    public final void vertexAdded(GraphVertexChangeEvent<V> e) {
        addJGraphTVertex(e.getVertex());
    }

    @Override
    public final void vertexRemoved(GraphVertexChangeEvent<V> e) {
        mxCell cell = vertexToCellMap.remove(e.getVertex());
        removeCells(new Object[] { cell });
    }

    @Override
    public final void edgeAdded(GraphEdgeChangeEvent<V, E> e) {
        addJGraphTEdge(e.getEdge());
    }

    @Override
    public final void edgeRemoved(GraphEdgeChangeEvent<V, E> e) {
        mxCell cell = edgeToCellMap.remove(e.getEdge());
        removeCells(new Object[] { cell });
    }

    /*
     * PRIVATE METHODS
     */

    private void addJGraphTVertex(V vertex) {

        getModel().beginUpdate();

        try {
            // create a new JGraphX vertex at position 0
            mxCell cell = (mxCell) insertVertex(defaultParent, null, vertex, 
                                                0, 0, 0, 0);
            
            // Set geometry and size
            cell.setGeometry(new mxGeometry());
            cell.getGeometry().setRelative(true);
            updateCellSize(cell);
            
            // Save reference between vertex and cell
            vertexToCellMap.put(vertex, cell);
            cellToVertexMap.put(cell, vertex);
            
        } finally {
            getModel().endUpdate();
        }
    }

    private void addJGraphTEdge(E edge) {

        getModel().beginUpdate();

        try {
            // find vertices of edge
            V sourceVertex = graphT.getEdgeSource(edge);
            V targetVertex = graphT.getEdgeTarget(edge);
            
            // if the one of the vertices is not drawn, don't draw the edge
            if (!(vertexToCellMap.containsKey(sourceVertex) 
               && vertexToCellMap.containsKey(targetVertex))) {
                return;
            }
            
            // get mxCells
            Object sourceCell = vertexToCellMap.get(sourceVertex);
            Object targetCell = vertexToCellMap.get(targetVertex);
            
            // add edge between mxcells
            mxCell cell = (mxCell) insertEdge(defaultParent, null, 
                    edge, sourceCell, targetCell);
            
            // set geometry and size
            cell.setGeometry(new mxGeometry());
            cell.getGeometry().setRelative(true);
            updateCellSize(cell);
            
            // Save reference between vertex and cell
            edgeToCellMap.put(edge, cell);
            cellToEdgeMap.put(cell, edge);
            
        } finally {
            getModel().endUpdate();
        }
    }
    
    
    private void insertJGraphT(Graph<V, E> graphT) {
        
        for (V vertex : graphT.vertexSet()) {
            addJGraphTVertex(vertex);
        }

        for (E edge : graphT.edgeSet()) {
            addJGraphTEdge(edge);
        }

    }
}
