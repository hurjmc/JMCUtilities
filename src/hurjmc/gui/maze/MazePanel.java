/* 
 *  (C) COPYRIGHT IBM CORP. 2014 ALL RIGHTS RESERVED
 *  
 *  Provided for use as educational material.
 * 
 *  Redistribution for other purposes is not permitted.  
 *   
 */

package hurjmc.gui.maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MazePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List <int[]>   	squares;
	int[]		   	player;
	Dimension      	preferred;
	
	

	public MazePanel() {
		this.squares = new ArrayList<int[]>();
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, preferred.width, preferred.height);

		g.setColor(Color.black);

		for (int[] square : squares) {
			g.fillRect(square[0], square[1], square[2], square[2]);

		}
		
		if (player != null){
			g.setColor(Color.green);
			g.fillOval(player[0], player[1], player[2], player[2]);
		} 
		
	}

	public void setSquare(int x, int y, int size) {
		squares.add(new int[] { x, y, size });
		this.repaint();
	}

	public void setPreferred(Dimension preferred) {
		this.preferred = preferred;
	}

	@Override
	public Dimension getPreferredSize() {
		return this.preferred;
	}

	public void setPlayer(int x, int y, int size) {
		player = new int[] {x, y, size, size};
		this.repaint();
	}

}
