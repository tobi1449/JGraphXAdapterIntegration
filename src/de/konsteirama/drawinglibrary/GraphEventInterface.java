package de.konsteirama.drawinglibrary;

import java.awt.event.MouseAdapter;

/**
 * Interface for the registration of mouse events.
 */
public interface GraphEventInterface {

    /**
     * Register a MouseAdapter to receive events from the graph panel.
     * @param adapter MouseAdapter
     */
    void registerMouseAdapter(MouseAdapter adapter);

}
