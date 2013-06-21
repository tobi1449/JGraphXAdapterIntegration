package de.konsteirama.drawinglibrary;

import com.mxgraph.swing.mxGraphComponent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Internal mouse adapter for double and right click support.
 */
class InternalMouseAdapter<V,E> extends MouseAdapter {

    /** The parent object. */
    private GraphManipulation<V, E> graphManipulation;
    
    /** the actual canvas. */
    private mxGraphComponent graphComponent;

    /**
     * Constructor of the InternalMouseAdapter
     * 
     * @param graphComponent : The actual canvas
     * @param graphManipulation : The Interface to interact with the canvas
     */
    protected InternalMouseAdapter(mxGraphComponent graphComponent,
            GraphManipulation<V, E> graphManipulation) {
        this.graphComponent = graphComponent;
        this.graphManipulation = graphManipulation;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            graphManipulation.zoom(true, e.getPoint());
        } else {
            graphManipulation.zoom(false, e.getPoint());
        }
    }
}
