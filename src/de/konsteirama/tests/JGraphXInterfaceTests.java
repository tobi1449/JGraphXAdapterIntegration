package de.konsteirama.tests;

import de.konsteirama.drawinglibrary.JGraphXInterface;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.junit.Assert;
import org.junit.Test;
import sun.awt.image.FileImageSource;
import sun.awt.image.ImageFormatException;
import sun.awt.image.JPEGImageDecoder;
import sun.awt.image.PNGImageDecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Test methods for the class JGraphXInterface.
 * 
 * @author KonSteiRaMa
 * 
 */
public class JGraphXInterfaceTests {
    /**
     * Tests the different export methods.
     */
    @Test
    public final void exportTest() {
        // create a new graph
        DirectedGraph<String, DefaultEdge> jGraphT =
                new ListenableDirectedGraph<String, DefaultEdge>(
                DefaultEdge.class);

        // fill graph with data
        String v1 = "Vertex 1";
        String v2 = "Vertex 2";
        String v3 = "Vertex 3";
        String v4 = "Vertex 4";

        jGraphT.addVertex(v1);
        jGraphT.addVertex(v2);
        jGraphT.addVertex(v3);
        jGraphT.addVertex(v4);

        jGraphT.addEdge(v1, v2);
        jGraphT.addEdge(v1, v3);
        jGraphT.addEdge(v1, v4);
        jGraphT.addEdge(v2, v3);
        jGraphT.addEdge(v3, v4);

        // create the canvas
        JGraphXInterface<String, DefaultEdge> jgraphx =
                new JGraphXInterface<String, DefaultEdge>(
                jGraphT);

        // Test EPS
        jgraphx.export("eps", "test.eps");

        File file = new File("test.eps");
        Assert.assertEquals(true, file.exists());

        file.delete();

        // Test SVG
        jgraphx.export("svg", "test.svg");

        file = new File("test.svg");
        Assert.assertEquals(true, file.exists());

        file.delete();

        // Test JPG
        jgraphx.export("jpg", "test.jpg");

        file = new File("test.jpg");
        Assert.assertEquals(true, file.exists());

        file.delete();

        // Test PNG
        jgraphx.export("png", "test.png");

        file = new File("test.png");
        Assert.assertEquals(true, file.exists());

        file.delete();

        // Test GraphML
        jgraphx.export("graphml", "test.graphml");

        file = new File("test.graphml");
        Assert.assertEquals(true, file.exists());

        file.delete();
    }

    /**
     * Tests if an exported jpg is valid.
     */
    @Test
    public final void testValidJPG() {
        // create a new graph
        DirectedGraph<String, DefaultEdge> jGraphT = 
                new ListenableDirectedGraph<String, DefaultEdge>(
                DefaultEdge.class);

        // fill graph with data
        String v1 = "Vertex 1";
        String v2 = "Vertex 2";

        jGraphT.addVertex(v1);
        jGraphT.addVertex(v2);

        jGraphT.addEdge(v1, v2);

        // create the canvas
        JGraphXInterface<String, DefaultEdge> jgraphx = 
                new JGraphXInterface<String, DefaultEdge>(
                jGraphT);

        jgraphx.export("jpg", "test.jpg");

        File file = new File("test.jpg");

        try {
            JPEGImageDecoder decoder = new JPEGImageDecoder(
                    new FileImageSource("test.jpg"), new FileInputStream(
                            "test.jpg"));
            decoder.produceImage();
        } catch (IOException e) {
            Assert.fail("Unexpected IOException");
        } catch (ImageFormatException e) {
            Assert.fail("Unexpected ImageFormatException");
        }

        file.delete();
    }

    /**
     * Tests if an exported png is valid.
     */
    @Test
    public final void testValidPNG() {
        // create a new graph
        DirectedGraph<String, DefaultEdge> jGraphT = 
                new ListenableDirectedGraph<String, DefaultEdge>(
                DefaultEdge.class);

        // fill graph with data
        String v1 = "Vertex 1";
        String v2 = "Vertex 2";

        jGraphT.addVertex(v1);
        jGraphT.addVertex(v2);

        jGraphT.addEdge(v1, v2);

        // create the canvas
        JGraphXInterface<String, DefaultEdge> jgraphx =
                new JGraphXInterface<String, DefaultEdge>(
                jGraphT);

        jgraphx.export("png", "test.png");

        File file = new File("test.png");

        try {
            PNGImageDecoder decoder = new PNGImageDecoder(new FileImageSource(
                    "test.png"), new FileInputStream("test.png"));
            decoder.produceImage();
        } catch (IOException e) {
            Assert.fail("Unexpected IOException");
        } catch (ImageFormatException e) {
            Assert.fail("Unexpected ImageFormatException");
        }

        file.delete();
    }
}
