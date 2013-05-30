package de.konsteirama.implementation;
import javax.swing.JButton;
import javax.swing.JToolBar;


public class KonToolBar extends JToolBar {

	/**
	 * Serial Version UID so eclipse shuts up.
	 */
	private static final long serialVersionUID = -1247045250127542110L;

	public KonToolBar() {
		
	    JButton button = new JButton("Hello, world!");
	    add(button);
	}
}
