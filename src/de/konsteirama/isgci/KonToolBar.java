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
    private JButton button;
    private MainPanel panel;

    public KonToolBar(MainPanel panel) {
        this.panel = panel;

        // set basic layout
        setFloatable(false);
        setRollover(true);

        // add some buttons
        button = new JButton("Do Stuff");
        button.addActionListener(this);

        this.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button)) {
            Object oCell = panel.getTabPane().getActiveGraphDrawing().getPanel().getGraph().getSelectionCell();
            
            if (oCell != null) {
                mxCell cell = (mxCell) panel.getTabPane().getActiveGraphDrawing().getPanel().getGraph().getSelectionCell();
                panel.getTabPane().getActiveGraphDrawing().getGraphManipulationInterface().renameNode(cell, "biuB");
            }
        }
    }
}
