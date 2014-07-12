/* 
 *  (C) COPYRIGHT IBM CORP. 2014 ALL RIGHTS RESERVED
 *  
 *  Provided for use as educational material.
 * 
 *  Redistribution for other purposes is not permitted.  
 *   
 */

package hurjmc.gui.maze;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JMCMaze {
	// JMCMaze is a singleton class, declare a static variable to hold a
	// reference to the only instance, initialise to null for lazy instantiation
	private static JMCMaze THE_MAZE = null;

	// Declare variables to handle graphics
	MazePanel mazePanel; 			// A graphics panel
	ControlPanel controlPanel; 		// Panel where the buttons etc. live
	JPanel gamePanel; 				// Panel to control game layout

	JFrame frame = null; 			// The main Swing frame
	KeyListener keyListener; 		// Listener for keystrokes
	Keystroke keystroke; 			// Object to hold a keystroke 

	// Get the host screen size and guess at our preferred sizes 
	//  (90% of the available space)
	Dimension sDim = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension mazeDim = new Dimension((sDim.width * 90) / 100,
			(sDim.height * 90) / 100);
	// Dimension controlDim = new Dimension((sDim.width*20)/100,
	// (sDim.height*75)/100);

	// Save requested square size (default is 10 pixels)
	int squareSize = 10;

	// Maze object for this game
	Maze maze;
	Dimension mazeActualDim;

	/*
	 * This is a singleton so make constructor private
	 */
	private JMCMaze() {
		// Create listener
		keystroke = new Keystroke();
		keyListener = new MazeKeyListener(keystroke);

		// Create some panels
		gamePanel = new JPanel();
		mazePanel = new MazePanel();
		// controlPanel = new ControlPanel(controlDim);

		gamePanel.add(mazePanel);
		// gamePanel.add(controlPanel);

		// Create a new Swing frame
		frame = new JFrame("JMC Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(gamePanel);
		frame.setUndecorated(false);

		frame.addKeyListener(keyListener);

	}

	private static JMCMaze getMaze() {
		// Use lazy instantiation because Swing set up must be run on the
		// AWT Event dispatcher thread
		if (THE_MAZE == null) {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						THE_MAZE = new JMCMaze();
					}
				});
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return THE_MAZE;
	}

	/**
	 * Show the target by making the Swing frame visible.
	 */
	public static void show() {
		getMaze().frame.pack();
		getMaze().frame.setVisible(true);
	}

	public static void drawSquare(int x, int y) {
		int size = getMaze().squareSize;
		getMaze().mazePanel.setSquare(x * size, y * size, size);
	}

	public static void drawPlayer(int[] player) {
		int size = getMaze().squareSize;
		getMaze().mazePanel.setPlayer(player[0] * size, player[1] * size, size);
	}

	public static int[][] create(long seed) {
		getMaze().maze = new Maze(getMaze().mazeDim, getMaze().squareSize);
		getMaze().maze.setSeed(seed);
		getMaze().maze.reset();
		getMaze().mazeActualDim = new Dimension(getMaze().maze.width
				* getMaze().squareSize, getMaze().maze.height
				* getMaze().squareSize);
		getMaze().mazePanel.setPreferred(getMaze().mazeActualDim);
		return getMaze().maze.array;
	}

	public static int getSquareSize() {
		return getMaze().squareSize;
	}

	public static void setSquareSize(int squareSize) {
		getMaze().squareSize = squareSize;
	}

	public static String waitForKeystroke() {
		String result = "none";
		synchronized (getMaze().keystroke) {
			try {
				getMaze().keystroke.wait();
				result = getMaze().keystroke.getKeyPressed();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static void beep() {
		Toolkit.getDefaultToolkit().beep();

	}

}
