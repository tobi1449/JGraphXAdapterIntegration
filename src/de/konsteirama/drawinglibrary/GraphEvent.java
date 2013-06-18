package de.konsteirama.drawinglibrary;

import com.mxgraph.swing.mxGraphComponent;

import java.awt.event.MouseAdapter;

/**
 * Implementation of the GraphEventInterface.
 */
class GraphEvent implements GraphEventInterface {

    /** The actual canvas. */
    private mxGraphComponent graphComponent;

    /**
     * Gets a canvas and saves it.
     * 
     * @param pGraphComponent : The canvas
     */
    protected GraphEvent(mxGraphComponent pGraphComponent) {
        this.graphComponent = pGraphComponent;
    }

    /**
     * Register a MouseAdapter to receive events from the graph panel.
     *
     * @param adapter MouseAdapter
     */
    @Override
    public void registerMouseAdapter(MouseAdapter adapter) {
        graphComponent.addMouseListener(adapter);
        graphComponent.getGraphControl().addMouseListener(adapter);
        graphComponent.addMouseWheelListener(adapter);
    }
}
