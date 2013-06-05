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

import de.konsteirama.jgraphxadapter.JGraphXAdapter;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.render.ps.EPSTranscoder;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.GraphMLExporter;
import org.jgrapht.graph.DefaultEdge;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.JComponent;
import javax.xml.transform.TransformerConfigurationException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

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
        if (format == "eps") {
            exportEPS(path);
        } else if (format == "svg") {
            exportSVG(path);
        } else if (format == "graphml") {
            exportGraphML(path);
        }
    }

    /**
     * Exports the canvas as an eps under the given path,
     * by converting an existing .svg representation of it.
     * 
     * @param path
     *            The path where the .eps file will be saved to
     */
    private void exportEPS(final String path) {
        // Creates the .svg file
        String temp = "temp.svg";
        exportSVG(temp);
        
        // Create the transcoder and set some settings
        EPSTranscoder transcoder = new EPSTranscoder();

        transcoder.addTranscodingHint(
                EPSTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, 1.0f);
        transcoder.addTranscodingHint(EPSTranscoder.KEY_MAX_HEIGHT, 2048f);
        transcoder.addTranscodingHint(EPSTranscoder.KEY_MAX_WIDTH, 2048f);

        String svgURI;
        try {
            // Create the transcoder input.
            svgURI = new File(temp).toURI().toURL().toString();
            TranscoderInput input = new TranscoderInput(svgURI);

            // Create the transcoder output.
            OutputStream ostream = new FileOutputStream(path);
            TranscoderOutput output = new TranscoderOutput(ostream);

            // Save the image.
            transcoder.transcode(input, output);

            // Flush and close the stream.
            ostream.flush();
            ostream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TranscoderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Deletes the temp svg file
        File file = new File(temp);
        file.delete();
    }

    /**
     * Exports the canvas as an svg under the given path.
     * 
     * @param path
     *            The path where the .svg file will be saved to
     */
    private void exportSVG(final String path) {
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
    }
    
    
    /**
     * Exports the canvas as an GraphML under the given path.
     * 
     * @param path
     *            The path where the .graphml file will be saved to
     */
    private void exportGraphML(final String path) {
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
            w.close();
        } catch (IOException e) {
            System.out.println("Enter a valid path !");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
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
