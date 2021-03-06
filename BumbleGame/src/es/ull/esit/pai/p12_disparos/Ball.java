/**
 * @author Rudolf Cicko
 * @email alu0100824780@ull.edu.es
 * @description Ball class that represents a ball that is shooted by the gun.
 * @subject Programación de aplicaciones interactivas.
 */
package es.ull.esit.pai.p12_disparos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Ball {
	private final int DISTANCE_TRESHOLD = 10;
	private final int REDUCTION = 30;         // Movement distance reduction, if higher than the movement is smoother.
	private final static int SIZE = 30;              // Like diameter.
	private final int HALF_SIZE = SIZE / 2;   // Like radius.
	private ShooterPanel panel;              // The main panel 
	private Point position;
	private Color color;
	private boolean shooted;         
	private Point vector;                 // Movement vector
	private boolean collided;
	private final int SAME_COLOR = 1;
	private final int NOT_SAME_COLOR = 0;
	private boolean staticBall;              // Not movable ball
	private boolean bounced;
	private int numBouncing;
	
	/**
	 * Constructor
	 * @param pos  initial position of the ball.
	 * @param aColor color of the ball.
	 * @param aPanel panel where the ball is painted.
	 * @param isStaticBall if the ball is static. (not flyable).
	 */
	public Ball (Point pos, Color aColor, ShooterPanel aPanel, boolean isStaticBall) {
		staticBall = isStaticBall;
		panel = aPanel;
		setColor (aColor);
		setPosition (pos);
		shooted = false;
		numBouncing = 1;
		collided = false;
		bounced = false;
	}
	
	
	public void setPosition (int x, int y) {
		setPosition (new Point(x, y));
	}
	/**
	 * Setter for position
	 * @param pos is the new position
	 */
	public void setPosition (Point pos) {
		if (staticBall || outOfBounds(pos) || !collided)
			position = pos;
		else {
			collided(null);    // The ball collided with the wall.
			//panel.getGun().setVector(panel.getGun().getVector().x / 2, panel.getGun().getVector().y); 
		}	
		//paint();
	}
	
	/**
	 * Check if the ball is out of bounds.
	 * @param pos
	 * @return true if the ball hit a wall.
	 */
	public boolean outOfBounds (Point pos) {
		boolean wallCollision = pos.x - HALF_SIZE <= 0 ||
								pos.x >= panel.getWidth() - HALF_SIZE;
		
								
		if (wallCollision && !bounced) {
			setVector ((int) ((float)-getVector().x * 2.0f), (int) ((float)getVector().y * 2.0f));
			bounced = true;
			numBouncing++;
		}
		else if (!wallCollision && bounced) {
			bounced = false;
		}
		
		return pos.y - HALF_SIZE <= 0 || 
			   wallCollision;
	}
	
	public static int getSize () {
		return SIZE;
	}


	/**
	 * Getter for the position
	 * @return the actual position
	 */
	public Point getPosition () {
		return position;
	}
	
	/**
	 * Setter for the color of the ball
	 * @param aColor
	 */
	public void setColor (Color aColor) {
		color = aColor;
	}
	
	/**
	 * Get how many times the ball bounced.
	 * @return 
	 */
	public int getNumBouncings () {
		return numBouncing;
	}
	
	/**
	 * Getter for the color of the ball
	 * @return
	 */
	public Color getColor () {
		return color;
	}
	/**
	 * This function make know to the ball that is shooted with the specified vector.
	 * @param vector is the vector of the shooting
	 */
	public void shoot (Point aNormalizedVector) {
		vector = aNormalizedVector;
		shooted = true;
	}
	
	/**
	 * This method move the ball depending on the vector.
	 */
	public void move () {
		if (shooted) {
			setPosition (new Point (getPosition().x + (int) ((float) vector.x / (float) REDUCTION), 
					                getPosition().y + (int) ((float) vector.y / (float) REDUCTION)));
		}
		else {
			System.out.println ("You can not move the ball until you shoot it");
		}
	}
	
	public void moveTo (Point newPos) {
		setPosition (newPos);
	}
	
	public void move (Point newMovement) {
		setPosition (new Point(getPosition().x + newMovement.x, getPosition().y + newMovement.y));
	}
	
	public void moveY (int newY) {
		setPosition (new Point(getPosition().x, getPosition().y + newY));
	}
	
	public void setStatic () {
		staticBall = true;
		vector = new Point(0, 0);
	}
	
	/**
	 * 
	 * @return true if the ball is shooted.
	 */
	public boolean isShooted () {
		return shooted;
	}
	
	public void setVector (int x, int y) {
		vector = new Point (x, y);
	}
	
	public Point getVector () {
		return vector;
	}
	
	/**
	 * This function make know to the ball that was collided with another ball or the wall.
	 * @param other ball.
	 * 
	 * @return 1 if the other ball have got the same color.
	 *         0 if the other ball have different color.
	 *         -1 if the ball collided with the wall.
	 */
	public int collided (Ball other) {
		collided = true;
		
		if (other != null) {
			if (other.getColor().equals(this.getColor()))
				return SAME_COLOR;
			else
				return NOT_SAME_COLOR;
		}
		return -1;
	}
	
	
	public boolean colliding (Ball other) {
		Point2D pos = (Point2D) position;
		Point2D otherPos = (Point2D) other.getPosition();
		
		return pos.distance(otherPos) - DISTANCE_TRESHOLD < SIZE;
	}
	
	
	
	/**
	 * 
	 * @return true if the ball collided
	 */
	public boolean isCollided () {
		return collided;
	}
	
	
	/**
	 * Paint the ball.
	 */
	public void paint (Graphics g) {
		//Graphics g = panel.getGraphics();
		g.setColor(color);
		
		g.fillOval(getPosition().x - HALF_SIZE, getPosition().y - HALF_SIZE, SIZE, SIZE);
	}
	

}
