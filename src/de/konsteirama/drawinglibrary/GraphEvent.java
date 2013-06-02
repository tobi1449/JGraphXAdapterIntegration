package de.konsteirama.drawinglibrary;

import com.mxgraph.swing.mxGraphComponent;

import java.awt.event.MouseAdapter;

public class GraphEvent implements GraphEventInterface {

    mxGraphComponent graphComponent;

    public GraphEvent(mxGraphComponent graphComponent)
    {
        this.graphComponent = graphComponent;
    }

	@Override
	public void registerMouseAdapter(MouseAdapter adapter) {

	}
}
