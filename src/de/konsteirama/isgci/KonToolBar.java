package de.konsteirama.isgci;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class KonToolBar extends JToolBar implements ActionListener {

    /**
     * Serial Version UID so eclipse shuts up.
     */
    private static final long serialVersionUID = -1247045250127542110L;

    /**
     * A Button used to export the current Graph.
     */
    private JButton exportButton;
    private MainPanel panel;
    
    public KonToolBar(MainPanel panel) {
		this.panel = panel;
        
		// set basic layout
		setFloatable(false);
		setRollover(true);
		
		// add some buttons
	    exportButton = new JButton("Export");
	    exportButton.addActionListener(this);
	        
	    this.add(exportButton);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(exportButton)) {
            this.panel.getTabPane().getActiveGraphDrawing().Export("graphml", "test.png");
            
            String[] formats = this.panel.getTabPane().getActiveGraphDrawing().getAvailableExportFormats();
            
            System.out.println("Export Button pressed !");
            
            System.out.print("Formats: ");
            for (int i = 0; i < formats.length; i++) {
                System.out.print(formats[i] + " ");
            }
            System.out.println();
        }
    }
}
