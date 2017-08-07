/**
 * @author Rudolf Cicko
 * @email alu0100824780@ull.edu.es
 * @description The main class 
 * @subject Programación de aplicaciones interactivas
 */
package es.ull.esit.pai.p12_disparos;

import javax.swing.JFrame;

public class Main {
	public static void main (String [] args) {
		 JFrame frame = new ShooterFrame ();
		 frame.setTitle("Ball shooter");
		 frame.setSize(600, 600);
	 	 frame.setLocationRelativeTo(null); // Center the frame
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setVisible(true);
	}
}
