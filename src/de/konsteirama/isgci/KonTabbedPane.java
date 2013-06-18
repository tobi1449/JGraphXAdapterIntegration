package de.konsteirama.isgci;

import de.konsteirama.drawinglibrary.DrawingLibraryFactory;
import de.konsteirama.drawinglibrary.DrawingLibraryInterface;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import javax.swing.JTabbedPane;
import java.util.ArrayList;

/**
 * Represents the TabbedPane, beneath the Toolbar.
 * 
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
    private ArrayList<DrawingLibraryInterface> graphInterfaces;

    /**
     * The constructor of KonTabbedPane.
     * 
     * Creates two tabs and adds a JGraphXInterface to each one
     */
    public KonTabbedPane() {
        // Initialize the ArrayList
        graphInterfaces = new ArrayList<DrawingLibraryInterface>();

        DirectedGraph<String, DefaultEdge> graph = CreateTestGraph();

        /*
         * Adds a tab
         */
        DrawingLibraryInterface<String, DefaultEdge> graphInterface =
                DrawingLibraryFactory.createNewInterface(graph);
        
        graphInterfaces.add(graphInterface);

        // Adds the graph to the tab and adds the tab to the pane
        add(graphInterface.getPanel());
        addTab("First Tab", graphInterface.getPanel());

        /*
         * Adds a tab
         */
        graphInterface = DrawingLibraryFactory.createNewInterface(graph);
        graphInterfaces.add(graphInterface);

        // Adds the graph to the tab and adds the tab to the pane
        add(graphInterface.getPanel());

        graphInterface.getGraphManipulationInterface().highlightNode(graph
                .vertexSet().iterator().next(), true);

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
        
        g.addVertex("v5");
        g.addVertex("v6");
        g.addVertex("v7");
        
        g.addEdge("v5", "v6");
        g.addEdge("v6", "v7");
        g.addEdge("v7", "v5");
        g.addEdge("v4", "v7");
        
        g.addVertex("v8");
        g.addVertex("v9");
        g.addVertex("v10");
        
        g.addEdge("v8", "v9");
        g.addEdge("v9", "v10");
        g.addEdge("v10", "v8");
        g.addEdge("v4", "v10");  
        
        g.addVertex("v11");
        g.addVertex("v12");
        g.addVertex("v13");
        
        g.addEdge("v11", "v12");
        g.addEdge("v12", "v13");
        g.addEdge("v13", "v11");
        g.addEdge("v4", "v13");  

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
