package de.konsteirama.isgci;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.mxgraph.model.mxCell;

public class KonToolBar extends JToolBar implements ActionListener {

    /**
     * Serial Version UID so eclipse shuts up.
     */
    private static final long serialVersionUID = -1247045250127542110L;

    /**
     * A Button used to export the current Graph.
     */
    private JButton renameButton, zoomInButton, zoomOutButton;
    private MainPanel panel;

    public KonToolBar(MainPanel panel) {
        this.panel = panel;

        // set basic layout
        setFloatable(false);
        setRollover(true);

        // add some buttons
        renameButton = new JButton("Rename Node (if selected)");
        renameButton.addActionListener(this);
        
        zoomInButton = new JButton("+");
        zoomInButton.addActionListener(this);
        
        zoomOutButton = new JButton("-");
        zoomOutButton.addActionListener(this);

        this.add(zoomInButton);
        this.add(zoomOutButton);
        this.add(renameButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(renameButton)) {
            Object oCell = panel.getTabPane().getActiveGraphDrawing().getPanel().getGraph().getSelectionCell();
            
            if (oCell != null) {
                mxCell cell = (mxCell) panel.getTabPane().getActiveGraphDrawing().getPanel().getGraph().getSelectionCell();
                panel.getTabPane().getActiveGraphDrawing().getGraphManipulationInterface().renameNode(cell, "Rename");
            }
        } else if (e.getSource().equals(zoomInButton)) {
            panel.getTabPane().getActiveGraphDrawing().getGraphManipulationInterface().zoom(-1);
        } else if (e.getSource().equals(zoomOutButton)) {
            panel.getTabPane().getActiveGraphDrawing().getGraphManipulationInterface().zoom(1);
        }
    }
}
