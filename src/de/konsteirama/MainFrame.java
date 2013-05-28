package de.konsteirama;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	
	public static void main(String[] args)
	{
		MainFrame frame = new MainFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}
}