package de.konsteirama.drawinglibrary;

import org.jgrapht.ListenableGraph;

import javax.swing.*;

public interface DrawingLibraryInterface {

    void Export(Object format, String path);

    Object[] getAvailableExportFormats();

    GraphEventInterface getGraphEventInterface();

    GraphManipulationInterface getGraphManipulationInterface();

    JComponent getPanel();
    
}
