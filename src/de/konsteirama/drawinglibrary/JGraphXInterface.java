package de.konsteirama.drawinglibrary;



import com.mxgraph.swing.mxGraphComponent;

import de.konsteirama.jgraphxadapter.JGraphXAdapter;


import org.jgrapht.ListenableGraph;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 30.05.13
 * Time: 17:59
 */
public class JGraphXInterface implements DrawingLibraryInterface {

    mxGraphComponent graphComponent;
    /* */
    GraphManipulation graphManipulation;
    /* */
    GraphEvent graphEvent;
    
    public JGraphXInterface() {
        graphComponent = new mxGraphComponent(null);
    }

    @Override
    public void Export(Object format, String path) {

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
