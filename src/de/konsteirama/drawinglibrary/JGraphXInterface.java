package de.konsteirama.drawinglibrary;

import com.mxgraph.canvas.mxICanvas;
import com.mxgraph.canvas.mxSvgCanvas;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxCellRenderer.CanvasFactory;
import com.mxgraph.util.mxDomUtils;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;

import de.konsteirama.drawinglibrary.DrawingLibraryInterface;
import de.konsteirama.drawinglibrary.GraphEvent;
import de.konsteirama.drawinglibrary.GraphEventInterface;
import de.konsteirama.drawinglibrary.GraphManipulation;
import de.konsteirama.drawinglibrary.GraphManipulationInterface;
import de.konsteirama.jgraphxadapter.JGraphXAdapter;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.GraphMLExporter;
import org.jgrapht.graph.DefaultEdge;
import org.xml.sax.SAXException;

import javax.swing.JComponent;
import javax.xml.transform.TransformerConfigurationException;

import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA. User: Tobias Date: 30.05.13 Time: 17:59
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

        // Convert to JGraphT-Graph
        graphAdapter = new JGraphXAdapter<String, DefaultEdge>(g);

        // Applys a hierarchical layout to the given graph
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        // Create the mxGraphComponent used to draw the graph
        graphComponent = new mxGraphComponent(graphAdapter);

        graphManipulation = new GraphManipulation(graphComponent);
        graphEvent = new GraphEvent(graphComponent);
    }

    /**
     * Exports the current graph.
     * 
     * @param format
     *            The actual format (.ps, .svg, .graphml)
     * @param path
     *            The path where the graph will be exported to
     */
    @Override
    public final void export(final String format, final String path) {
        if (format == "ps") {

        } else if (format == "svg") {
            // Creates a new SVGCanvas and converts internal graph to svg
            mxSvgCanvas canvas = (mxSvgCanvas) mxCellRenderer.drawCells(
                    (mxGraph) this.graphAdapter, null, 1, null,
                    new CanvasFactory() {
                        public mxICanvas createCanvas(final int width,
                                final int height) {
                            mxSvgCanvas canvas = new mxSvgCanvas(mxDomUtils
                                    .createSvgDocument(width, height));
                            canvas.setEmbedded(true);

                            return canvas;
                        }

                    });

            try {
                // Saves the svg file under the given path
                mxUtils.writeFile(mxXmlUtils.getXml(canvas.getDocument()), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (format == "graphml") {
            // Creates a new GraphMLExporter and gets the JGraphT-graph
            GraphMLExporter<String, DefaultEdge> exporter = new GraphMLExporter<String, DefaultEdge>();
            Graph<String, DefaultEdge> g = this.graphAdapter.getGraph();

            /*
             * FileWriter could throw an IOException, GraphMLExporter
             * TransformerConf.. and SAXException
             */
            try {
                // Creates a new Filewriter and exports the graph under the
                // given path
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
     * 
     * @return An array of String with the formats
     */
    @Override
    public final String[] getAvailableExportFormats() {
        return new String[] { "ps", "svg", "graphml" };
    }

    @Override
    public final GraphEventInterface getGraphEventInterface() {
        return graphEvent;
    }

    @Override
    public final GraphManipulationInterface getGraphManipulationInterface() {
        return graphManipulation;
    }

    @Override
    public final JComponent getPanel() {
        return graphComponent;
    }

    @Override
    public final void setGraph(final ListenableGraph<String, DefaultEdge> g) {
        graphComponent.setGraph(new JGraphXAdapter<String, DefaultEdge>(g));
    }
}
