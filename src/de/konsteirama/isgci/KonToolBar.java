package de.konsteirama.isgci;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToolBar;

public class KonToolBar extends JToolBar implements ActionListener {

    /**
     * Serial Version UID so eclipse shuts up.
     */
    private static final long serialVersionUID = -1247045250127542110L;

    /**
     * A Button used to export the current Graph.
     */
    private MainPanel panel;

    public KonToolBar(MainPanel panel) {
        this.panel = panel;

        // set basic layout
        setFloatable(false);
        setRollover(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
