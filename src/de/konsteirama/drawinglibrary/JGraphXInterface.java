package de.konsteirama.drawinglibrary;



import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.mxgraph.swing.mxGraphComponent;

import de.konsteirama.jgraphxadapter.JGraphXAdapter;


import org.jgrapht.ListenableGraph;

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
    
    public JGraphXInterface() {
        graphComponent = new mxGraphComponent(null);
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
    public JComponent getPanel(ListenableGraph<Object, Object> graph) {
        graphComponent.setGraph(new JGraphXAdapter<Object, Object>(graph));
        return graphComponent;
    }
}
