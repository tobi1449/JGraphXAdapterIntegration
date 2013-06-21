package de.konsteirama.isgci;

import de.konsteirama.drawinglibrary.DrawingLibraryFactory;
import de.konsteirama.drawinglibrary.DrawingLibraryInterface;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Main window for testing the JGraphXAdapter implementation as well as the
 * DrawingLibraryInterface and other stuff.
 * 
 * @author KonSteiRaMa
 * 
 */
public class MainPanel extends JPanel {

    /**
     * Serial Version UID so eclipse shuts up.
     */
    private static final long serialVersionUID = -4699101759449965895L;
    /**
     * The Pane, that holds all available tabs .
     */
    private KonTabbedPane tabPane;

    /**
     * The Toolbar of the MainFrame.
     */
    private KonToolBar toolbar;

    /**
     * Starts a new MainPanel.
     * 
     * @param args
     *            Standard args argument
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                // Create and set up the window.
                JFrame frame = new JFrame("JGraphXIntegration");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Add content to the window.
                frame.add(new MainPanel());

                // Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    /**
     * Initializes a new MainPanel with the JGraph implementation.
     */
    public MainPanel() {
        super(new BorderLayout());

        tabPane = new KonTabbedPane();
        toolbar = new KonToolBar(this);

        //this.add(toolbar, BorderLayout.PAGE_START);
        //this.add(tabPane, BorderLayout.CENTER);

        DrawingLibraryInterface drawinglib = DrawingLibraryFactory
                .createNewInterface(CreateTestGraph());

        this.add(drawinglib.getPanel());

    }
    private DirectedGraph<String, DefaultEdge> CreateTestGraph(){
        ListenableDirectedGraph<String, DefaultEdge> g = new
                ListenableDirectedGraph<String, DefaultEdge>(
                DefaultEdge.class);

        // add some sample data (graph manipulated via JGraphT)
        String testString = "X&#x305;<sub>3</sub>";
        g.addVertex(testString);
        g.addVertex("v2");
        g.addVertex("v3");
        g.addVertex("v4");

        g.addEdge(testString, "v2");
        g.addEdge("v2", "v3");
        g.addEdge("v3", testString);
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

    public KonTabbedPane getTabPane() {
        return tabPane;
    }
}