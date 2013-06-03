package de.konsteirama.drawinglibrary;

import com.mxgraph.swing.mxGraphComponent;
import de.konsteirama.jgraphxadapter.JGraphXAdapter;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.GraphMLExporter;
import org.jgrapht.graph.DefaultEdge;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.transform.TransformerConfigurationException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 30.05.13
 * Time: 17:59
 */
public class JGraphXInterface implements DrawingLibraryInterface {

    private mxGraphComponent graphComponent;

    private GraphManipulation graphManipulation;

    private GraphEvent graphEvent;

    private JGraphXAdapter<String, DefaultEdge> graphAdapter;

    /**
     * The instance of DrawingLibraryInterface.
     */
    public JGraphXInterface(ListenableGraph<String, DefaultEdge> g) {

        //Convert to JGraphT-Graph
        graphAdapter = new JGraphXAdapter<String, DefaultEdge>(g);

        //Create the mxGraphComponent used to draw the graph
        graphComponent = new mxGraphComponent(graphAdapter);

        graphManipulation = new GraphManipulation(graphComponent);
        graphEvent = new GraphEvent(graphComponent);
    }

    /**
     * Exports the current graph.
     */
    @Override
    public final void export(String format, String path) {
        if (format == "ps") {

        } else if (format == "svg") {

        } else if (format == "graphml") {
            // Creates a new GraphMLExporter and gets the JGraphT-graph
            GraphMLExporter<String, DefaultEdge> exporter = new GraphMLExporter<String, DefaultEdge>();
            Graph g = this.graphAdapter.getGraph();

            try {
                // Creates a new Filewriter and exports the graph under the given path
                FileWriter w = new FileWriter(path);
                exporter.export(w, g);
            } catch (IOException e) {
                System.out.println("Enter a valid path !");
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns an Array of all currently implemented export formats.
     */
    @Override
    public String[] getAvailableExportFormats() {
        return new String[]{"ps", "svg", "graphml"};
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

    @Override
    public void setGraph(ListenableGraph<String, DefaultEdge> g) {
        graphComponent.setGraph(new JGraphXAdapter<String, DefaultEdge>(g));
    }
}
