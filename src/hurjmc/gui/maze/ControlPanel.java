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

import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Dimension preferred;


	public ControlPanel(Dimension controlDim) {
		preferred = controlDim;
	}
	@Override
	public Dimension getPreferredSize() {
		return this.preferred;
	}


}
