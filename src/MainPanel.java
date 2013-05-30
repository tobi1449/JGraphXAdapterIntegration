import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.konsteirama.isgci.KonTabbedPane;
import de.konsteirama.isgci.KonToolBar;


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
	 * Initializes a new MainPanel with the JGraphXAdapter implementation.
	 */
	public MainPanel() {
		super(new BorderLayout());

		KonTabbedPane tabPane = new KonTabbedPane();
		KonToolBar toolbar = new KonToolBar();

		add(toolbar, BorderLayout.PAGE_START);
		add(tabPane, BorderLayout.CENTER);
	}
}