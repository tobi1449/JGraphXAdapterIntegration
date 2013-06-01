package de.konsteirama.isgci;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

import de.konsteirama.drawinglibrary.DrawingLibraryInterface;
import de.konsteirama.drawinglibrary.JGraphXInterface;

/**
 * Represents the TabbedPane, beneath the Toolbar.
 * 
 * @author highyield
 *
 */
public class KonTabbedPane extends JTabbedPane {

    /**
     * Serial Version UID so eclipse shuts up.
     */
    private static final long serialVersionUID = -6438063674744178339L;

    /**
     * A ArrayList of all currently available JGraphXInterfaces
     * connected via a Tab.
     */
    private ArrayList<JGraphXInterface> graphInterfaces;
    
    /**
     * The constructor of KonTabbedPane.
     * 
     * Creates two tabs and adds a JGraphXInterface to each one
     */
    public KonTabbedPane() {
        // Initialize the ArrayList
        graphInterfaces = new ArrayList<JGraphXInterface>();
        
        /*
         * Adds a tab
         */
        JGraphXInterface graphInterface = new JGraphXInterface();
        graphInterfaces.add(graphInterface);
        
        // Adds the graph to the tab and adds the tab to the pane
        add(graphInterface.getPanel());
        addTab("First Tab", graphInterface.getPanel());
        
        /*
         * Adds a tab
         */
        graphInterface = new JGraphXInterface();
        graphInterfaces.add(graphInterface);
        
        // Adds the graph to the tab and adds the tab to the pane
        add(graphInterface.getPanel());
        addTab("Second Tab", graphInterface.getPanel());  
    }
    
    /**
     * Returns the current DrawingLibraryInterface 
     * referenced by the current active tab.
     * 
     * @return an instance of DrawingLibraryInterface
     */
    public final DrawingLibraryInterface getActiveGraphDrawing() {
        int selectedIndex = this.getSelectedIndex();
                
        return this.graphInterfaces.get(selectedIndex);
    }
}
