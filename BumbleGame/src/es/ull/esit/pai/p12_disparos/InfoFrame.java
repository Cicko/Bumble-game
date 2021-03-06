/**
 * @author Rudolf Cicko
 * @contact alu0100824780@ull.edu.es
 * @date 30-04-2016
 */
package es.ull.esit.pai.p12_disparos;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Ventana que muestra información del autor
 * @author Rudolf Cicko
 *
 */
public class InfoFrame extends JFrame {

	public InfoFrame (ArrayList<String> info) {
		JPanel panel = new JPanel ();
		add (panel);
		for (int i = 0; i < info.size(); i++) {
			panel.add(new JLabel (info.get(i)));
		}
		
	}
}
