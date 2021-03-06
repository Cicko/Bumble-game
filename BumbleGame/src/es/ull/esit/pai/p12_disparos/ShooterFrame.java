/**
 * @author Rudolf Cicko
 * @email alu0100824780@ull.edu.es
 * @description Main frame 
 * @subject Programación de aplicaciones interactivas
 */
package es.ull.esit.pai.p12_disparos;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class ShooterFrame extends JFrame {
	private PointPanel pointsPanel = new PointPanel ();
	private ShooterPanel panel = new ShooterPanel(pointsPanel);
	
	
	public ShooterFrame () {
		panel.setLayout(new BorderLayout(100, 100));
		
		
		add (panel,BorderLayout.CENTER);
		addMouseMotionListener(panel);
		addMouseListener(panel);
		
		// Information icon
        ImageIcon icon = new ImageIcon("src/information-128.png");
        
      //a lable holding an image
        JLabel infoLabel = new JLabel(icon);
        //Add a mouse listener to get the click event
        //
        infoLabel.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
            	
            	 ArrayList<String> info = new ArrayList<String> ();
				 info.add("Author: Rudolf Cicko");
				 info.add("Asignatura: Programación de aplicación interactivas");
				 info.add("Practica: Ball Shooter");
				 info.add("Universidad: Universidad de La Laguna");
				 info.add("Contacto: alu0100824780@ull.edu.es");
				 
				 JFrame frame = new InfoFrame (info);
				 frame.setTitle("Information");
				 frame.setSize(400, 400);
			 	 frame.setLocationRelativeTo(null); // Center the frame
			     frame.setVisible(true);
				 frame.setResizable(false);
            }
        });
        
        JPanel bottomPanel = new JPanel ();
        bottomPanel.setLayout(new BorderLayout(100, 100));

        bottomPanel.add(infoLabel, BorderLayout.EAST);
        bottomPanel.add(pointsPanel, BorderLayout.CENTER);
        
        panel.add(bottomPanel, BorderLayout.NORTH);
	}
}
