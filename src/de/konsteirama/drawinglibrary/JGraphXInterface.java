package de.konsteirama.drawinglibrary;



import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;

import de.konsteirama.jgraphxadapter.JGraphXAdapter;


import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 30.05.13
 * Time: 17:59
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

    @Override
    public void Export(Object format, String path) {
        Dimension d = graphComponent.getGraphControl().getSize();
        BufferedImage image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        graphComponent.getGraphControl().paint(g);
        final File outputfile = new File("test.png");
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Object[] getAvailableExportFormats() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
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
