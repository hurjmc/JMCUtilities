/* 
 *  (C) COPYRIGHT IBM CORP. 2014 ALL RIGHTS RESERVED
 *  
 *  Provided for use as educational material.
 * 
 *  Redistribution for other purposes is not permitted.  
 *   
 */

package hurjmc.gui.target;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;

public class TargetMouseListener implements MouseListener {
	private TargetMouseClick mouseClick;
	private static Logger    logger = Logger.getLogger("hurjmc.gui.target");
	
	public TargetMouseListener(TargetMouseClick mouseClick) {
		this.mouseClick = mouseClick; 
	}

	public void mouseClicked(MouseEvent e) {
		//System.out.println(e);
		//System.out.println("Event Q thread - Priority = "+Thread.currentThread().getPriority());
	}

	public void mouseEntered(MouseEvent e) {
		//System.out.println(e);

	}

	public void mouseExited(MouseEvent e) {
		//System.out.println(e);

	}

	public void mousePressed(MouseEvent e) {
		logger.finest("listener "+Thread.currentThread().getName()+" "+e);
		synchronized(mouseClick){
			mouseClick.x = e.getX();
			mouseClick.y = e.getY();
			mouseClick.notify();
		}


	}

	public void mouseReleased(MouseEvent e) {
		//System.out.println(e);

	}

}
