/* 
 *  (C) COPYRIGHT IBM CORP. 2014 ALL RIGHTS RESERVED
 *  
 *  Provided for use as educational material.
 * 
 *  Redistribution for other purposes is not permitted.  
 *   
 */

package hurjmc.gui.maze;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeKeyListener implements KeyListener {
	private Keystroke keystroke;
	
	public MazeKeyListener(Keystroke keystroke) {
		this.keystroke = keystroke;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		

	}

	@Override
	public void keyPressed(KeyEvent e) {
		synchronized(keystroke){
			keystroke.setKeyPressed(KeyEvent.getKeyText(e.getKeyCode()));
			keystroke.notify();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
