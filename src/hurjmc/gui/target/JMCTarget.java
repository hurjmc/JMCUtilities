/* 
 *  (C) COPYRIGHT IBM CORP. 2014 ALL RIGHTS RESERVED
 *  
 *  Provided for use as educational material.
 * 
 *  Redistribution for other purposes is not permitted.  
 *   
 */

package hurjmc.gui.target;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class JMCTarget {
	// Declare a logger for debugging 
	private static Logger    logger = Logger.getLogger("hurjmc.gui.target");
	
	// JMCTarget is a singleton class, declare static variable to hold
	// reference to the only instance, initialise to null for lazy instantiation
	private static JMCTarget THE_TARGET = null ;
	
	// Declare variable to handle graphics
	TargetPanel       gPanel;			// A graphics panel
	TargetMouseClick  mouseClick;		// Object to hold a mouse click
	MouseListener     mouseListener;	// Listener for mouse events
	JFrame            frame = null;		// The main Swing frame
	
	// Get the host screen size and set the target size
	Dimension         sDim = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension		  tDim = new Dimension(301,301);
	
	// Variables to handle animation
	ActionListener    animator = null;
	Timer             timer = null;
	Point			  position = new Point(10,10);
	Point	          velocity = new Point(10,10);
	
	/*
	 * This is a singleton so make constructor private
	 */
	private JMCTarget(){
		logger.finer("> ENTRY");
        
		// Create a TargetPanel and set the cursor to be a cross-hair
		gPanel = new TargetPanel();
        gPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        
        // Create a mouse click object and mouse listener
		mouseClick = new TargetMouseClick(0,0);
		mouseListener = new TargetMouseListener(mouseClick);  
		
		// Create a new Swing frame
		frame = new JFrame("JMC Target");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(mouseListener);
		frame.getContentPane().add(gPanel);
		frame.setUndecorated(true);
		frame.setSize(tDim);
		
		// Set up animation
		animator = new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		          move();
		      }};

		timer = new Timer(75, animator);
		logger.finer("< EXIT");
	}
	
	/*
	 *	Move the frame to its new position  
	 */
	protected void move() {
		calculateNewPosition(position, velocity, sDim, tDim);
		frame.setLocation(position);		
	}

	/*
	 * Calculate the new position based on the current position, the 
	 * velocity, and the host screen size. 
	 */
	private void calculateNewPosition(Point position, Point velocity, Dimension sDim, Dimension tDim) {
		
		// Move the target
		position.translate(velocity.x, velocity.y);
		
		// Check if we have hit the edge of the screen and change
		// the velocity to make the target 'bounce' if necessary.
		if (position.x < 0 || (position.x + tDim.width) > sDim.width){
 			velocity.setLocation(-velocity.x, velocity.y);
		} 
			
		if (position.y < 0 || (position.y + tDim.height) > sDim.height){ 
			velocity.setLocation(velocity.x, -velocity.y);
		}
	}
	
	private static JMCTarget getTarget() {
		// Use lazy instantiation because Swing set up must be run on the 
		// AWT Event dispatcher thread
		if (THE_TARGET == null){
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						THE_TARGET = new JMCTarget();					
					}
				});
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return THE_TARGET;
	}
	
	/**
	 * Show the target by making the Swing frame visible.	  
	 */
	public static void show() {
		getTarget().frame.setVisible(true);
	}

	/**
	 * Hide the target by making the Swing frame invisible. 
	 */
	public static void hide() {
		getTarget().frame.setVisible(false);
	}

	/**
	 * Start the target moving. 
	 */
	public static void startAnimation() {
		getTarget().timer.start();
		
	}
	
	/**
	 * Stop the target moving. 
	 */
	public static void stopAnimation() {
		getTarget().timer.stop();
	}


	/**
	 * Display a 'YES/NO' dialog with the specified message. 
	 * 
	 * @param String message - the message to display.
	 * @return boolean - true if user selects 'YES' box.
	 * 
	 */
	public static boolean displayMessage(String message) {
		return JOptionPane.showOptionDialog(getTarget().frame,
				                            message, 
				                            "Results", 
				                            JOptionPane.YES_NO_OPTION, 
				                            JOptionPane.PLAIN_MESSAGE, 
				                            null, 
				                            null,
				                            null) == JOptionPane.YES_OPTION;
				
		
	}
	/**
	 * Stops the program running until the mouse is clicked somewhere in the 
	 * frame. 
	 * 
	 * @see public static int getMouseX()
	 * @see public static int getMouseY() 
	 * 
	 */
	public static void waitForClick() {
		
		synchronized(getTarget().mouseClick){
			try {
				THE_TARGET.mouseClick.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
	/**
	 * Returns the x co-ordinate of the last mouse click in the 
	 * frame. 
	 * 
	 * @return int x co-ordinate of last mouse click
	 * @see public static void waitForClick(
	 * 
	 */
	public static int getMouseX() {
		return getTarget().mouseClick.x;
	}
	
	/**
	 * Returns the y co-ordinate of the last mouse click in the 
	 * frame. 
	 * 
	 * @return int y co-ordinate of last mouse click
	 * @see public static void waitForClick(
	 * 
	 */
	public static int getMouseY() {		
		return getTarget().mouseClick.y;
	}

	/**
	 * Draw a blob at the specified co-ordinates. 
	 * 
	 * @param int x - x co-ordinate of blob.
	 * @param int y - y co-ordinate of blob. 
	 * 
	 */
	public static void drawBLOB(int x, int y) {
		getTarget().gPanel.setBLOB(x,y);
	}

	/**
	 * Draw a circle centre at the specified co-ordinates, with radius r. 
	 * 
	 * @param int x - x co-ordinate of centre.
	 * @param int y - y co-ordinate of centre.
	 * @param int r - radius.
	 * 
	 */
	public static void drawCircle(int x, int y, int r) {
		getTarget().gPanel.setCircle(x,y,r);
	}

	/**
	 * Write some text at the specified co-ordinates. 
	 * 
	 * @param int x - x co-ordinate of centre.
	 * @param int y - y co-ordinate of centre.
	 * @param String t - text.
	 * 
	 */
	public static void drawText(int x, int y, String s) {
		getTarget().gPanel.setText(x,y,s);
	}

	/**
	 * Clear everything drawn on the target. 
	 */
	public static void clear() {
		getTarget().gPanel.clear();
		
	}
	
	/**
	 * Pause the game for 'i' milliseconds
	 * 
	 * @param int i - time in milliseconds for which to pause.
	 * 
	 */
	public static void pause(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
