package com.kirbydee.main;

import javax.swing.JFrame;

public class Game { // TODO: lombok for intellij

	public static void main(String[] args) {
		
		JFrame window = new JFrame("Kirby's Adventure");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
	
}
