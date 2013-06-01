package de.konsteirama.isgci;

import javax.swing.JTabbedPane;

import de.konsteirama.drawinglibrary.DrawingLibraryInterface;
import de.konsteirama.drawinglibrary.JGraphXInterface;

public class KonTabbedPane extends JTabbedPane {

    /**
     * Serial Version UID so eclipse shuts up.
     */
    private static final long serialVersionUID = -6438063674744178339L;

    JGraphXInterface graphInterface1;
    
    JGraphXInterface graphInterface2;
    
    public KonTabbedPane() {
        // add a tab
        JGraphXInterface graphInterface1 = new JGraphXInterface();
        add(graphInterface1.getPanel());
        addTab("First Tab", graphInterface1.getPanel());

        // add a second tab
        graphInterface2 = new JGraphXInterface();
        add(graphInterface2.getPanel());
        addTab("Second Tab", graphInterface2.getPanel());
    }
    
    public DrawingLibraryInterface getActiveGraphDrawing() {
        return graphInterface2;
    }
}
