package de.konsteirama.drawinglibrary;

import com.mxgraph.swing.mxGraphComponent;

import java.awt.event.MouseAdapter;

/**
 * Implementation of the GraphEventInterface.
 */
class GraphEvent implements GraphEventInterface {

    private mxGraphComponent graphComponent;

    protected GraphEvent(mxGraphComponent graphComponent) {
        this.graphComponent = graphComponent;
    }

    /**
     * Register a MouseAdapter to receive events from the graph panel.
     *
     * @param adapter MouseAdapter
     */
    @Override
    public void registerMouseAdapter(MouseAdapter adapter) {
        graphComponent.addMouseListener(adapter);        
        graphComponent.addMouseWheelListener(adapter);
    }
}
