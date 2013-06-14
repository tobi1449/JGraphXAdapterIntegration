package de.konsteirama.isgci;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
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
    private JButton renameButton, zoomInButton, zoomOutButton, centerButton, reapplyButton, undoButton, redoButton, colorButton;
    private JTextField zoomField;
    private MainPanel panel;

    public KonToolBar(MainPanel panel) {
        this.panel = panel;

        // set basic layout
        setFloatable(false);
        setRollover(true);

        // add some buttons
        renameButton = new JButton("Rename Node");
        renameButton.addActionListener(this);
        
        zoomInButton = new JButton("+");
        zoomInButton.addActionListener(this);
        
        zoomOutButton = new JButton("-");
        zoomOutButton.addActionListener(this);
        
        centerButton = new JButton("Center Node");
        centerButton.addActionListener(this);
        
        reapplyButton = new JButton("Reapply Hierarchy");
        reapplyButton.addActionListener(this);
        
        undoButton = new JButton("<-");
        undoButton.addActionListener(this);    
        
        redoButton = new JButton("->");
        redoButton.addActionListener(this);
        
        zoomField = new JTextField("100");
        zoomField.addActionListener(this);
        
        colorButton = new JButton("Color Node");
        colorButton.addActionListener(this);

        this.add(zoomInButton);
        this.add(zoomField);
        this.add(zoomOutButton);
        this.add(centerButton);
        this.add(reapplyButton);
        this.add(renameButton);
        this.add(undoButton);
        this.add(redoButton);
        this.add(colorButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(renameButton)) {
            Object oCell = panel.getTabPane().getActiveGraphDrawing()
                    .getPanel().getGraph().getSelectionCell();
            
            if (oCell != null) {
                mxCell cell = (mxCell) oCell;
                panel.getTabPane().getActiveGraphDrawing().getGraphManipulationInterface().renameNode(cell, cell.getValue() + "x");
            }
        } else if (e.getSource().equals(centerButton)) {
            Object oCell = panel.getTabPane().getActiveGraphDrawing()
                    .getPanel().getGraph().getSelectionCell();
            
            if (oCell != null) {
                mxCell cell = (mxCell) oCell;
                panel.getTabPane().getActiveGraphDrawing()
                    .getGraphManipulationInterface().centerNode(cell);
            }
        } else if (e.getSource().equals(zoomInButton)) {
            panel.getTabPane().getActiveGraphDrawing()
                .getGraphManipulationInterface().zoom(true);
        } else if (e.getSource().equals(zoomOutButton)) {
            panel.getTabPane().getActiveGraphDrawing()
                .getGraphManipulationInterface().zoom(false);
        } else if (e.getSource().equals(redoButton)) {
            panel.getTabPane().getActiveGraphDrawing()
                .getGraphManipulationInterface().redo();
        } else if (e.getSource().equals(undoButton)) {
            panel.getTabPane().getActiveGraphDrawing()
                .getGraphManipulationInterface().undo();
        } else if (e.getSource().equals(zoomField)) {
            panel.getTabPane().getActiveGraphDrawing()
                .getGraphManipulationInterface()
                .zoomTo((double) Integer.parseInt(zoomField.getText()) / 100);
        } else if (e.getSource().equals(reapplyButton)) {
            panel.getTabPane().getActiveGraphDrawing()
                .getGraphManipulationInterface().reapplyHierarchicalLayout();
        } else if (e.getSource().equals(colorButton)) {
            Object oCell = panel.getTabPane().getActiveGraphDrawing()
                    .getPanel().getGraph().getSelectionCell();
            
            if (oCell != null) {
                mxCell cell = (mxCell) oCell;
                panel.getTabPane().getActiveGraphDrawing().getGraphManipulationInterface().colorNode(new mxCell[] {cell}, Color.RED);
            }  
        }
    }
}
