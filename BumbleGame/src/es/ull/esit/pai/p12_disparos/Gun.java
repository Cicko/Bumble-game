/**
 * @author Rudolf Cicko
 * @email alu0100824780@ull.edu.es
 * @description The gun class that shoots balls 
 * @subject Programación de aplicaciones interactivas
 */
package es.ull.esit.pai.p12_disparos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Gun {
	private final int GUN_HEIGHT = 100;
	private final int GUN_WIDTH = 5;
	private final int REDUCTION = 1;
	private final Color gunColor = new Color (51, 153, 255);
	private ShooterPanel panel;
	private Graphics g;
	private Point origin;      // The origin of the arrow that represents the gun.
	private Point vector;      // Vector from the origin to mouse position.
	private Point normalizedVector;   // Like the vector but with module = ARROW_LENGTH
	private Point mousePos;    // mouse positions.
	private Point headPos;
	
	/**
	 * Constructor
	 * @param anOrigin is the origin of the arrow
	 * @param aPanel is the main panel
	 */
	public Gun (Point anOrigin, ShooterPanel aPanel) {
		panel = aPanel;
		origin = anOrigin;
	}
	
	public void shoot () {
		
	}
	
	/**
	 * Setter for the mouse position
	 * @param aMousePos
	 */
	public Point setMousePos (Point aMousePos) {
		mousePos = new Point (aMousePos.x, aMousePos.y);
		setVector ();
		//paint();
		return headPos;
	}
	
	/**
	 * this method sets the vector from origin to mouse position
	 */
	public void setVector () {
		vector = new Point (mousePos.x - origin.x, mousePos.y - origin.y);
	}
	
	public void setVector (int x, int y) {
		vector = new Point (x, y);
	}
	
	/**
	 * Getter for the vector from origin to mouse position
	 */
	public Point getVector () {
		return vector;
	}

	/**
	 * This method returns the position of the arrow's head.
	 * @return
	 */
	public Point calculateHeadPos () {
		double module = getModule();
		
		// Vector divided by the module and multiplied by the arrow's length.
		Point vectorFixed = new Point ((int)((double)vector.x * (double) GUN_HEIGHT / module), 
				                       (int)((double)vector.y * (double) GUN_HEIGHT / module));

		
		headPos = new Point (origin.x + vectorFixed.x, origin.y + vectorFixed.y);
		return headPos;
	}
	
	
	/**
	 * This method returns the module of the vector
	 * @return
	 */
	public double getModule () {
		return Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2));
	}
	
	public void setNormalizedVector (int x, int y) {
		normalizedVector = new Point (x, y);
	}
	
	public Point getNormalizedVector () {
		return normalizedVector;
	}
	
	public Point getReducedVector () {
		return new Point (vector.x / REDUCTION, vector.y / REDUCTION);
	}
	
	
	/**
	 * This function draws the arrow depending on the angle 
	 */
	public void paint (Graphics g){
		int width = panel.getWidth();
		int height = panel.getHeight();
		
		//g = panel.getGraphics();
		Graphics2D g2 = (Graphics2D)g;
		
		//panel.getParent().paintComponents(g);
		//g2.clearRect(origin.x, origin.y, headPos.x, headPos.y);
		
		headPos = calculateHeadPos();
		setNormalizedVector (headPos.x - origin.x, headPos.y - origin.y);
		
		g2.setColor(gunColor);
		g2.setStroke(new BasicStroke(GUN_WIDTH));
		g2.drawLine (origin.x, origin.y, headPos.x, headPos.y);
	}
}
