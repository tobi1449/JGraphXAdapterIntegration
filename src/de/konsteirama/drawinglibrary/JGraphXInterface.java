package de.konsteirama.drawinglibrary;

import java.io.StringWriter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;

import de.konsteirama.jgraphxadapter.JGraphXAdapter;

import org.jgrapht.*;
import org.jgrapht.ext.GraphMLExporter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.transform.TransformerConfigurationException;

/**
 * Created with IntelliJ IDEA. User: Tobias Date: 30.05.13 Time: 17:59
 */
public class JGraphXInterface implements DrawingLibraryInterface {

    mxGraphComponent graphComponent;

    GraphManipulation graphManipulation;

    GraphEvent graphEvent;

    /**
     * The instance of DrawingLibraryInterface.
     */
    private JGraphXAdapter<String, DefaultEdge> graph;

    public JGraphXInterface() {
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

        graphComponent = new mxGraphComponent(graph);
    }

    /**
     * Exports the current graph.
     */
    @Override
    public final void Export(String format, String path) {
        if (format == "ps") {

        } else if (format == "svg") {

        } else if (format == "graphml") {
            GraphMLExporter<String, DefaultEdge> exporter = new GraphMLExporter<String, DefaultEdge>();
            
            ListenableGraph g = this.graph.getJGraph();
            
            StringWriter w = new StringWriter();
            
            try {
                exporter.export(w, g);
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            
            System.out.println(w.toString());
        }
    }

    /**
     * Returns an Array of all currently implemented export formats.
     */
    @Override
    public String[] getAvailableExportFormats() {
        return new String[] {"ps", "svg", "graphml"};
    }

    @Override
    public GraphEventInterface getGraphEventInterface() {
        return graphEvent;
    }

    @Override
    public GraphManipulationInterface getGraphManipulationInterface() {
        return graphManipulation;

    }

    @Override
    public JComponent getPanel() {
        return graphComponent;
    }
}
