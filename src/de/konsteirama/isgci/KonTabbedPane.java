package de.konsteirama.isgci;

import javax.swing.JTabbedPane;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;

import de.konsteirama.drawinglibrary.DrawingLibraryInterface;
import de.konsteirama.jgraphxadapter.JGraphXAdapter;

public class KonTabbedPane extends JTabbedPane {

    /**
     * Serial Version UID so eclipse shuts up.
     */
    private static final long serialVersionUID = -6438063674744178339L;

    /**
     * The instance of DrawingLibraryInterface.
     */
    private JGraphXAdapter<String, DefaultEdge> graph;

    public KonTabbedPane() {
        // create a JGraphT graph
        ListenableGraph<String, DefaultEdge> g = new ListenableDirectedGraph<String, DefaultEdge>(
                DefaultEdge.class);

        // add some sample data (graph manipulated via JGraphT)
        g.addVertex("v1");
        g.addVertex("v2");
        g.addVertex("v3");
        g.addVertex("v4");

        g.addEdge("v1", "v2");
        g.addEdge("v2", "v3");
        g.addEdge("v3", "v1");
        g.addEdge("v4", "v3");

        graph = new JGraphXAdapter<String, DefaultEdge>(g);

        // adds a few cells to the graph
        graph.getModel().beginUpdate();
        double x = 20, y = 20;
        for (mxCell cell : graph.getVertexToCellMap().values()) {
            graph.getModel().setGeometry(cell, new mxGeometry(x, y, 20, 20));
            x += 40;
            if (x > 200) {
                x = 20;
                y += 40;
            }
        }
        graph.getModel().endUpdate();

        // add a tab
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        add(graphComponent);
        addTab("First Tab", graphComponent);

        // add a second tab
        graph = new JGraphXAdapter<String, DefaultEdge>(g);
        graphComponent = new mxGraphComponent(graph);
        addTab("Second Tab", graphComponent);
    }
    
    public DrawingLibraryInterface getActiveGraphDrawing() {
        return (DrawingLibraryInterface) graph;
    }
}
