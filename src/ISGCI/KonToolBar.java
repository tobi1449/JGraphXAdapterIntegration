package ISGCI;
import javax.swing.JButton;
import javax.swing.JToolBar;


public class KonToolBar extends JToolBar {

	/**
	 * Serial Version UID so eclipse shuts up.
	 */
	private static final long serialVersionUID = -1247045250127542110L;

	public KonToolBar() {
		
		// set basic layout
		setFloatable(false);
		setRollover(true);
		
		// add some button
	    JButton button = new JButton("Hello, world!");
	    add(button);
	}
}
