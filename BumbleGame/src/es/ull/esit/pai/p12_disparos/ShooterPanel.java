/**
 * @author Rudolf Cicko
 * @email alu0100824780@ull.edu.es
 * @description Shooter panel that contain balls and the gun that shoot more balls.
 * @subject Programación de aplicaciones interactivas
 */
package es.ull.esit.pai.p12_disparos;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.print.attribute.standard.Media;
import javax.swing.JPanel;

public class ShooterPanel extends JPanel implements MouseMotionListener, MouseListener {
	private final int SAME_COLOR = 1;
	private final int TIMER_DELAY = 10;         // miliseconds before the timer starts
	private final int TIMER_INTERVAL = 10;      // interval between frames
	private final int NEW_ROW_INTERVAL = 2000;
	private final int Y_GUN_MARGIN = 10;
	private Timer timer = new Timer ();
	private Timer timer2 = new Timer ();
	private AudioClip soundGood;
	private AudioClip soundFail;
	private Gun gun;
	private Ball actualPreparedBall = null;            // the actual prepared ball to be shooted.
	private Ball shootedBall = null;
	private boolean canShoot;
	private ArrayList<Ball> balls;                     // All balls that are in the screen.
	private int numRows;
	private int numBallsRow;        // num balls per row
	private PointPanel pointsPanel;
	
	public ShooterPanel (PointPanel aPointsPanel) {
		pointsPanel = aPointsPanel;
		numRows = 0;
		Point gunOrigin = new Point (300, 600 - Y_GUN_MARGIN); 
		gun = new Gun (gunOrigin, this);
		balls = new ArrayList<Ball> ();
		numBallsRow = 600 / Ball.getSize();
		
		URL url = this.getClass().getResource("La.wav");
		URL url2 = this.getClass().getResource("EndGame.wav");	
		
		soundGood = Applet.newAudioClip(url);
		soundFail = Applet.newAudioClip(url2);
		
		timer.schedule(new TimerTask() {
			public void run () {	
				if (shootedBall != null) {
					shootedBall.move();
					repaint();
					if (shootedBall.isCollided()) {
						canShoot = true;
						balls.add(shootedBall);
						shootedBall = null;
					}
				
					int counter = 0;
					for (int i = 0; i < balls.size(); i++) {
						balls.get(i).move();
						if (shootedBall != null && shootedBall.colliding(balls.get(i))) {
							shootedBall.setStatic();
							int xPos;
							if (numRows % 2 == 0)
								xPos= (getWidth() / numBallsRow) * ((int) ((float)shootedBall.getPosition().x / (float)Ball.getSize()) + 1) - Ball.getSize() / 2;
							else
								xPos= (getWidth() / numBallsRow) * (shootedBall.getPosition().x / Ball.getSize() + 1);
							shootedBall.setPosition(xPos, Ball.getSize() * (numRows + 1) + Ball.getSize() / 2);
							shootedBall.collided(balls.get(i));
							if (shootedBall.getColor().equals(balls.get(i).getColor())) {
								ArrayList<Ball> ballsToRemove = new ArrayList<Ball> ();
								searchSameColorNeighbourds (balls.get(i), ballsToRemove);
								
								if (ballsToRemove.size() == 1) 
									soundGood.play();
								if (ballsToRemove.size() > 1)
									soundFail.play();
								
								
								
								for (int j = 0; j < ballsToRemove.size(); j++) {
									pointsPanel.incrementPoints(shootedBall.getNumBouncings());
									balls.remove(ballsToRemove.get(j));
								}
								shootedBall = null;
								canShoot = true;
							}
							counter++;
						}
					}	
				}
			}
		}, TIMER_DELAY, TIMER_INTERVAL);
		
		/*
		timer2.schedule (new TimerTask () {
			public void run () {
				createNewRow();
				repaint();
			}
		}, 1000, NEW_ROW_INTERVAL);
		*/
		
		createNewRow();
		createNewRow();
		createNewRow();
		createNewRow();
		
		canShoot = true;
	}
	
	public void searchSameColorNeighbourds (Ball ball, ArrayList<Ball> ballsToRemove) {
		for (int i = 0;i < balls.size(); i++) {
			if (ball.colliding(balls.get(i)) && 
			    ball.getColor().equals(balls.get(i).getColor()) && 
			    !ballsToRemove.contains(balls.get(i))) {
					ballsToRemove.add(balls.get(i));
					searchSameColorNeighbourds(balls.get(i), ballsToRemove);
			}
		}
	}
	
	/**
	 * Añade una nueva hilera de bolas.
	 */
	public void createNewRow () {
		
		int xSeparation = 600 / numBallsRow;
		int yPos = Ball.getSize() + Ball.getSize() / 2 + 1; 
		for (int i = 0;i < numBallsRow-1; i++) {
			int xPos;
			if (numRows % 2 == 0) 
				xPos = xSeparation * i + Ball.getSize() / 2 + 1;
			else	
				xPos = xSeparation * i + Ball.getSize() + 1;
			Point pos = new Point(xPos, yPos);
			balls.add(new Ball(pos, getRandomColor(), this, true));
		}
		numRows++;
		for (int i = 0;i < balls.size() - numBallsRow + 1; i++) {
			balls.get(i).moveY(Ball.getSize());
		}
	}
	
	
	
	public Gun getGun () {
		return gun;
	}
	
	/**
	 * This method shot a ball from the gun with the specified angle
	 * @param angle is the angle used to shoot the ball.
	 */
	public void shoot () {
		if (canShoot) {
			canShoot = false;
			shootedBall = actualPreparedBall;
			actualPreparedBall = null;
			shootedBall.shoot (gun.getReducedVector());
		}
	}
	
	
	public Color getRandomColor () {
		Color color = null;
		final double NUM_COLORS = 5.0;
		int candidate = (int) (Math.random() * NUM_COLORS);
		switch (candidate) {
		case 0:
			color = Color.red;
			break;
		case 1:
			color = Color.blue;
			break;
		case 2:
			color = Color.yellow;
			break;
		case 3:
			color = Color.green;
			break;
		case 4:
			color = Color.ORANGE;
			break;
		}
		return color;
	}
	
	/**
	 * Main method for drawing things on the panel
	 */
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		gun.paint(g);
		
		if(actualPreparedBall != null) {
			actualPreparedBall.paint(g);
		}
		if(shootedBall != null) {
			shootedBall.paint(g);
		}
		
		for (int i = 0;i < balls.size(); i++) {
			balls.get(i).paint(g);
		}
	}


	/**
	 * Method that is automatically invoked when the mouse is moved.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		Point arrowHeadPos = gun.setMousePos (e.getPoint());
		repaint();
		
		if (actualPreparedBall == null) {
			actualPreparedBall = new Ball (arrowHeadPos, getRandomColor(), this, false);
		}
		else {
			actualPreparedBall.setPosition(arrowHeadPos);
		}
	}
	

	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		shoot();
	}
	
    // NOT USED METHODS
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
