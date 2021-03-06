/**
 * @author Rudolf Cicko
 * @email alu0100824780@ull.edu.es
 * @description Panel para mostrar la puntuación conseguida.
 */
package es.ull.esit.pai.p12_disparos;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PointPanel extends JPanel {
	private final int POINTS_INCREMENT = 10;
	private JLabel pointsLabel;
	private int points;
	
	public PointPanel () {
		pointsLabel = new JLabel ();
		points = 0;
		pointsLabel.setText(String.valueOf(points));
		
		add(pointsLabel);
	}
	
	public void incrementPoints (int numBouncings) {
		points += POINTS_INCREMENT;
		pointsLabel.setText(String.valueOf(points * numBouncings)  + " Points");
	}
	
}
