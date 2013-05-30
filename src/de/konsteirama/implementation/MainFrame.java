package de.konsteirama.implementation;


import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 * MainWindow for testing the JGraphXAdapter implementation
 * as well as the DrawingLibraryInterface and other stuff.
 * 
 * @author KonSteiRaMa
 *
 */
public class MainFrame extends JFrame {

	/**
	 * The initial width of the Mainframe. 
	 */
	private static final int STARTWIDTH = 400;
	
	/**
	 * The initial height of the Mainframe.
	 */
	private static final int STARTHEIGHT = 320;
	
	
	/**
	 * Serial Version UID so eclipse shuts up.
	 */
	private static final long serialVersionUID = -4699101759449965895L;

	
	/**
	 * Starts a new JFrame with the JGraphXAdapter implementation.
	 * 
	 * @param args
	 * Standard args argument
	 */
	public static void main(final String[] args) {
		MainFrame frame = new MainFrame();

		KonTabbedPane tabPane = new KonTabbedPane();
		KonToolBar toolbar = new KonToolBar();
		

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(STARTWIDTH, STARTHEIGHT);
		frame.setVisible(true);

		Container container = frame.getContentPane();
		
		container.setLayout(new BoxLayout(container,
				BoxLayout.Y_AXIS));
		
		container.add(toolbar);
		container.add(tabPane);
	}
}