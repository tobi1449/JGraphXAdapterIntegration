package de.konsteirama.jgraphxadapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.VertexFactory;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphListener;
import org.jgrapht.event.GraphVertexChangeEvent;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

/**
 * Adapter to draw a JGraphT graph with the JGraphX drawing library. Implements
 * a GraphListener to react to changes in the JGraphT-graph if a listenablegraph
 * is available. This class also has an optional (toggleable) JGraphX listener,
 * to send changes on the JGraphX-graph to the JGraphT graph if you so desire.
 * 
 * Known Bugs: If this class is used with String-Edges, please note that there
 * is a bug with the method JgraphT.addEdge(vertex1, vertex2); The edge will be
 * created with an empty String "" as value and saved (in JGraphT as well as in
 * this class), which results in the edge not saving correctly. So, if you're
 * using Strings as Edgeclass please use the method addEdge(vertex1, vertex2,
 * "Edgename"); with a unique edgename.
 * 
 * @author Original: JeanYves Tinevez
 * @author Improvements: KonSteiRaMa
 * 
 * @param <V>
 *            Vertex
 * @param <E>
 *            Edge
 */
public class JGraphXAdapter<V, E> extends mxGraph implements
        GraphListener<V, E>, mxIEventListener {

    /**
     * The graph to be drawn. Has vertices "V" and edges "E".
     */
    private Graph<V, E> graphT;

    /**
     * Maps the JGraphT-Vertices onto JGraphX-mxCells. {@link #cellToVertexMap}
     * is for the opposite direction.
     */
    private HashMap<V, mxCell> vertexToCellMap = new HashMap<V, mxCell>();

    /**
     * Maps the JGraphT-Edges onto JGraphX-mxCells.
     * {@link #cellToEdgeMap} is for the opposite direction.
     */
    private HashMap<E, mxCell> edgeToCellMap = new HashMap<E, mxCell>();

    /**
     * Maps the JGraphX-mxCells onto JGraphT-Edges.
     * {@link #edgeToCellMap} is for the opposite direction.
     */
    private HashMap<mxCell, V> cellToVertexMap = new HashMap<mxCell, V>();

    /**
     * Maps the JGraphX-mxCells onto JGraphT-Vertices.
     * {@link #vertexToCellMap} is for the opposite direction.
     */
    private HashMap<mxCell, E> cellToEdgeMap = new HashMap<mxCell, E>();

    /**
     * Provides a way to add JGraphT vertices if the MxListener is active.
     */
    private VertexFactory<V> vertexFactory;
    
    /**
     * Saves whether or not the mxIEventListener is currently attached
     * to this object. 
     */
    private boolean isMxListenerActivated;
    
    /**
     * Disables jgrapht graph change events from being handled by this
     * GraphListener implementation while the jgraphx listener is
     * adding/removing nodes/edges to prevent an infinite loop.
     */
    private boolean isMxListenerInProgress;
    
    //                        Constructors
    // ----------------------------------------------------------------
    
    /**
     * Constructs and draws a new ListenableGraph. If the graph changes
     * through as ListenableGraph, the JGraphXAdapter will automatically
     * add/remove the new edge/vertex as it implements the GraphListener
     * interface.
     * Throws a IllegalArgumentException if the graph is null.
     * @param graph casted to graph
     */
    public JGraphXAdapter(ListenableGraph<V, E> graph) {
        // call normal constructor with graph class
        this((Graph<V, E>) graph);
        
        graph.addGraphListener(this);
    }
    
    /**
     * Constructs and draws a new mxGraph from a jGraphT graph. Changes on 
     * the jgraphT graph will not edit this mxGraph any further; use the 
     * constructor with the ListenableGraph parameter instead or use this 
     * graph as a normal mxGraph.
     * 
     * Throws an IllegalArgumentException if the parameter is null.
     * @param graph is a graph
     */
    public JGraphXAdapter(Graph<V, E> graph) {
        super();
        
        // Don't accept null as jgrapht graph
        if (graph == null) {
            throw new IllegalArgumentException();
        } else {
            this.graphT = graph;
        }
        
        // generate the drawing
        insertJGraphT(graph);
        
        setAutoSizeCells(true);
        
        // TODO delete
        activateMxListener();
    }


    
   
    //                            Getter
    // ----------------------------------------------------------------
    
    /**
     * Returns Hashmap which maps the vertices onto their visualization mxCells.
     * @return {@link #vertexToCellMap}
     */
    public HashMap<V, mxCell> getVertexToCellMap() {
        return vertexToCellMap;
    }

    /**
     * Returns Hashmap which maps the edges onto their visualization mxCells.
     * @return {@link #edgeToCellMap}
     */
    public HashMap<E, mxCell> getEdgeToCellMap() {
        return edgeToCellMap;
    }

    /**
     * Returns Hashmap which maps the visualization mxCells onto their edges.
     * @return {@link #cellToEdgeMap}
     */
    public HashMap<mxCell, E> getCellToEdgeMap() {
        return cellToEdgeMap;
    }

    /**
     * Returns Hashmap which maps the visualization mxCells onto their vertices.
     * @return {@link #cellToVertexMap}
     */
    public HashMap<mxCell, V> getCellToVertexMap() {
        return cellToVertexMap;
    }
    
    /**
     * Returns the JGraphT graph upon which the graph is visualized.
     * @return {@link #graphT}
     */
    public Graph<V, E> getGraph() {
        return graphT;
    }

    //                     GraphListener Interface
    // ----------------------------------------------------------------
    
    @Override
    public void vertexAdded(GraphVertexChangeEvent<V> e) {
        if (!isMxListenerInProgress) {
            addJGraphTVertex(e.getVertex());
        }
    }

    @Override
    public void vertexRemoved(GraphVertexChangeEvent<V> e) {
        if (isMxListenerInProgress) {
            return;
        }
        
        mxCell cell = vertexToCellMap.remove(e.getVertex());
        removeCells(new Object[] { cell });
        
        // remove vertex from hashmaps
        cellToVertexMap.remove(cell);
        vertexToCellMap.remove(e.getVertex());
        
        // remove all edges that connected to the vertex
        ArrayList<E> removedEdges = new ArrayList<E>();
        
        // first, generate a list of all edges that have to be deleted
        // so we don't change the cellToEdgeMap.values by deleting while
        // iterating
        // we have to iterate over this because the graphT has already
        // deleted the vertex and edges so we can't query what the edges were
        for (E edge : cellToEdgeMap.values()) {
            if (!graphT.edgeSet().contains(edge)) {
                removedEdges.add(edge);
            }
        }
        
        // then delete all entries of the previously generated list
        for (E edge : removedEdges) {
            removeEdge(edge);
        }
    }

    @Override
    public void edgeAdded(GraphEdgeChangeEvent<V, E> e) {
        if (!isMxListenerInProgress) {
            addJGraphTEdge(e.getEdge());
        }
    }

    @Override
    public void edgeRemoved(GraphEdgeChangeEvent<V, E> e) {
        if (!isMxListenerInProgress) {
            removeEdge(e.getEdge());
        }
    }
    
    
    
    //                        mxIEventListener
    // ----------------------------------------------------------------
    
    /**
     * Sets the vertexfactory to the parameter. This is needed if
     * the mxListener is active and needs to create a new vertex.
     * 
     * @param factory
     *          The factory that is used to create new JGraphT vertices.
     */
    public void setVertexFactory(VertexFactory<V> factory) {
        vertexFactory = factory;
    }
    
    
    /**
     *  Toggles whether the changes on the JGraphX graph are linked to the
     *  jgrapht graph or not.
     *  Needs a {@link #vertexFactory} that is set via {@link setVertexFactory}!
     *  
     *  @see {@link #activateMxListener()}
     *  @see {@link #deactivateMxListener()}
     */
    public void toggleMxListener() {
        if (isMxListenerActivated) {
            deactivateMxListener();
        } else {
            activateMxListener();
        }
    }
    
    /**
     * Links the changes of the JGraphX graph to the JGraphT graph, e.g. if
     * a vertex is deleted in the JGraphX graph, the vertex will be deleted
     * on the jgrapht graph (if available). If an edge is added and one or
     * both vertices are not already in the JgraphT graph, they will be added
     * to it.
     * If the listener is already active this method will do nothing.
     * Needs a {@link #vertexFactory} that is set via {@link setVertexFactory}!
     * 
     * @see {@link #deactivateMxListener()}
     * @see {@link #toggleMxListener()}
     */
    public void activateMxListener() {
        if (isMxListenerActivated) {
            return;
        }
        
        addListener(mxEvent.ADD, this);
        addListener(mxEvent.ADD_CELLS, this);
        // connect_cell seems to be the same as cell_connected
        //addListener(mxEvent.CELL_CONNECTED, this);
        addListener(mxEvent.CELLS_ADDED, this);
        addListener(mxEvent.CELLS_REMOVED, this);
        addListener(mxEvent.CHANGE, this);
        addListener(mxEvent.CLEAR, this);
        addListener(mxEvent.CONNECT, this);
        addListener(mxEvent.CONNECT_CELL, this);
        addListener(mxEvent.FLIP_EDGE, this);
        addListener(mxEvent.INSERT, this);
        addListener(mxEvent.REDO, this);
        addListener(mxEvent.REMOVE_CELLS, this);
        addListener(mxEvent.REMOVE_CELLS_FROM_PARENT, this);
        addListener(mxEvent.UNDO, this);
    }
    
    /**
     * Removes the JGraphX listener, so changes will no longer be linked to
     * the JGraphT graph. This does not affect the changes from a JGraphT to 
     * the JGraphX graph. 
     * If the listener wasn't activated before, this method will do nothing.
     * 
     * @see {@link #activateMxListener()}
     * @see {@link #toggleMxListener()}
     */
    public void deactivateMxListener() {
        if (!isMxListenerActivated) {
            return;
        }
        
        removeListener(this);
    }
    
    @Override
    public void invoke(Object sender, mxEventObject evt) {
        isMxListenerInProgress = true;
        
        String eventName = evt.getName();
        
        
        if (eventName.equals(mxEvent.ADD)) {
            // TODO
        } else if (eventName.equals(mxEvent.ADD_CELLS)) {
            // TODO            
        } else if (eventName.equals(mxEvent.CELLS_ADDED)) {
            // TODO
        } else if (eventName.equals(mxEvent.CELLS_REMOVED)) {
            // TODO
        } else if (eventName.equals(mxEvent.CHANGE)) {
            // TODO
        } else if (eventName.equals(mxEvent.CLEAR)) {
            // TODO
        } else if (eventName.equals(mxEvent.CONNECT)) {
            // TODO
        } else if (eventName.equals(mxEvent.CONNECT_CELL)) {
            Object prop = evt.getProperty("edge");
            
            if (prop != null && prop instanceof mxCell) {
                mxCell changedCell = (mxCell) prop;
                changeCell(changedCell);
            }
            
        } else if (eventName.equals(mxEvent.FLIP_EDGE)) {
            // TODO
        } else if (eventName.equals(mxEvent.INSERT)) {
            // TODO
        } else if (eventName.equals(mxEvent.REDO)) {
            // TODO
        } else if (eventName.equals(mxEvent.REMOVE_CELLS)) {
            // TODO
        } else if (eventName.equals(mxEvent.REMOVE_CELLS_FROM_PARENT)) {
            // TODO
        } else if (eventName.equals(mxEvent.UNDO)) {
            // TODO
        }
        
        isMxListenerInProgress = false;
    }
    
   

    //                     Private Methods
    // ----------------------------------------------------------------
    
    /**
     * Updates a cell that was changed in an event by adding/removing
     * the corresponding vertices and edges from the hashmaps and jgrapht graph.
     * 
     * @param changedCell
     *          The cell that was updated
     */
    private void changeCell(mxCell changedCell) {
        // has nothing to do with apple
        mxICell iTarget = changedCell.getTarget();
        mxICell iSource = changedCell.getSource();
        
        // TODO: find out if we can change the hashmaps to mxicells
        if (!(iTarget instanceof mxCell && iSource instanceof mxCell)) {
            return;
        }

        mxCell target = (mxCell) iTarget;
        mxCell source = (mxCell) iSource;
        
        // Cell was "created"
        if (!cellToEdgeMap.containsKey(changedCell)
             && target != null && source != null)  {
            
            // if the target is not in the grapht yet, add it
            if (!cellToVertexMap.containsKey(target)) {
                
                V vertex = vertexFactory.createVertex();
                cellToVertexMap.put(target, vertex);
                vertexToCellMap.put(vertex, target);
            }
            
            // if the source is not in the grapht yet, add it
            if (!cellToVertexMap.containsKey(source)) {
                
                V vertex = vertexFactory.createVertex();
                cellToVertexMap.put(source, vertex);
                vertexToCellMap.put(vertex, source);
            }
            
            // add the edge to grapht
            V tSource = cellToVertexMap.get(source);
            V tTarget = cellToVertexMap.get(target);
            
            graphT.addEdge(tSource, tTarget);
            
            
            // cell only has one vertex or was deleted
        } else if (cellToEdgeMap.containsKey(changedCell) 
                && (target == null || source == null)) {
            
            // delete the edge from grapht
            E edge = cellToEdgeMap.get(changedCell);
            
            cellToEdgeMap.remove(changedCell);
            edgeToCellMap.remove(edge);
            
            graphT.removeEdge(edge);
            
            
            // cell was dragged from one vertex to another
        } else if (cellToEdgeMap.containsKey(changedCell) 
                && target != null && source != null) {
            
            // remove "old" edge, add "new" edge to change vertices
            E edge = cellToEdgeMap.get(changedCell);
            
            V tsource = graphT.getEdgeSource(edge);
            V ttarget = graphT.getEdgeTarget(edge);
            
            graphT.removeEdge(edge);
            graphT.addEdge(tsource, ttarget, edge);
        }
                 
    }
    
    /**
     * Removes a jgrapht edge and its visual representation from this graph
     * completely.
     * 
     * @param edge
     *            The edge that will be removed
     */
    private void removeEdge(E edge) {
        mxCell cell = edgeToCellMap.remove(edge);
        removeCells(new Object[] { cell });
        
        // remove edge from hashmaps
        cellToEdgeMap.remove(cell);
        edgeToCellMap.remove(edge);
    }
    
    
    /**
     * Draws a new vertex into the graph.
     * 
     * @param vertex vertex to be added to the graph
     */
    private void addJGraphTVertex(V vertex) {

        getModel().beginUpdate();

        try {
            // create a new JGraphX vertex at position 0
            mxCell cell = (mxCell) insertVertex(defaultParent, null, vertex, 
                                                0, 0, 0, 0);
            
            // update cell size so cell isn't "above" graph
            updateCellSize(cell);
            
            // Save reference between vertex and cell
            vertexToCellMap.put(vertex, cell);
            cellToVertexMap.put(cell, vertex);
            
        } finally {
            getModel().endUpdate();
        }
    }

    /**
     * Draws a new egde into the graph.
     * 
     * @param edge edge to be added to the graph. Source and target
     *  vertices are needed.
     */
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
            
            // update cell size so cell isn't "above" graph
            updateCellSize(cell);
            
            // Save reference between vertex and cell
            edgeToCellMap.put(edge, cell);
            cellToEdgeMap.put(cell, edge);
            
        } finally {
            getModel().endUpdate();
        }
    }
    
    /**
     * Draws a given graph with all its vertices and edges.
     * 
     * @param graph the graph to be added to the existing graph.
     */
    private void insertJGraphT(Graph<V, E> graph) {
        
        for (V vertex : graph.vertexSet()) {
            addJGraphTVertex(vertex);
        }

        for (E edge : graph.edgeSet()) {
            addJGraphTEdge(edge);
        }

    }
}
