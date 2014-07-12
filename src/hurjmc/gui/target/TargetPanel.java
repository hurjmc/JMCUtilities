/* 
 *  (C) COPYRIGHT IBM CORP. 2014 ALL RIGHTS RESERVED
 *  
 *  Provided for use as educational material.
 * 
 *  Redistribution for other purposes is not permitted.  
 *   
 */

package hurjmc.gui.target;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class TargetPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6351233332495630951L;

	List<Point>   BLOBs   = new ArrayList<Point>();
	List<Circle>  circles = new ArrayList<Circle>();
	List<Text>    text    = new ArrayList<Text>();

	public synchronized void paintComponent(Graphics g) {
		assert javax.swing.SwingUtilities.isEventDispatchThread();
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 301, 301);
		
		g.setColor(Color.black);
		
		for (Point p: BLOBs){
			g.fillOval(p.x-4, p.y-4, 8, 8);
		}
	
		for (Circle c: circles){
			g.drawOval(c.x-c.r, c.y-c.r, 2*c.r, 2*c.r);
		}
		
		for (Text t: text){
			g.drawString(t.s, t.x, t.y);
		}

	}

	public synchronized void setBLOB(int x, int y) {
		BLOBs.add(new Point(x,y));
		this.repaint();		
	}

	public synchronized void setCircle(int x, int y, int r) {
		circles.add(new Circle(x,y,r));
		this.repaint();
	}
	
	public synchronized void setText(int x, int y, String s) {
		text.add(new Text(x,y,s));
		this.repaint();
	}

	public synchronized void clear() {
		BLOBs.clear();
		circles.clear();
		text.clear();
		this.repaint();
	}

}
