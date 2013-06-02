package de.konsteirama.drawinglibrary;

import org.jgrapht.ListenableGraph;

import javax.swing.*;

public interface DrawingLibraryInterface {

    void Export(String format, String path);

    String[] getAvailableExportFormats();

    GraphEventInterface getGraphEventInterface();

    GraphManipulationInterface getGraphManipulationInterface();

    JComponent getPanel();
    
}
