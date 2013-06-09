package de.konsteirama.isgci;

import de.konsteirama.drawinglibrary.DrawingLibraryInterface;
import de.konsteirama.drawinglibrary.JGraphXInterface;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import javax.swing.JTabbedPane;
import java.util.ArrayList;

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
     * A ArrayList of all currently available JGraphXInterfaces connected via a
     * Tab.
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

        DirectedGraph<String, DefaultEdge> graph = CreateTestGraph();

        /*
         * Adds a tab
         */
        JGraphXInterface graphInterface = new JGraphXInterface<String,
                DefaultEdge>(graph);
        graphInterfaces.add(graphInterface);

        // Adds the graph to the tab and adds the tab to the pane
        add(graphInterface.getPanel());
        addTab("First Tab", graphInterface.getPanel());

        /*
         * Adds a tab
         */
        graphInterface = new JGraphXInterface<String, DefaultEdge>(graph);
        graphInterfaces.add(graphInterface);

        // Adds the graph to the tab and adds the tab to the pane
        add(graphInterface.getPanel());
        addTab("Second Tab", graphInterface.getPanel());
    }

    private DirectedGraph<String, DefaultEdge> CreateTestGraph(){
        ListenableDirectedGraph<String, DefaultEdge> g = new
                ListenableDirectedGraph<String, DefaultEdge>(
                DefaultEdge.class);

        // add some sample data (graph manipulated via JGraphT)
        g.addVertex("v1");
        g.addVertex("v2");
        g.addVertex("v3");
        g.addVertex("v4");

        g.addEdge("v1", "v2");
        g.addEdge("v2", "v3");
        g.addEdge("v3", "v1");
        g.addEdge("v4", "v3");

        return g;
    }

    /**
     * Returns the current DrawingLibraryInterface referenced by the current
     * active tab.
     * 
     * @return an instance of DrawingLibraryInterface
     */
    public final DrawingLibraryInterface getActiveGraphDrawing() {
        int selectedIndex = this.getSelectedIndex();

        return this.graphInterfaces.get(selectedIndex);
    }
}
