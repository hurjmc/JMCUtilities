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

public class MazeItem {
	Color	color;
	int 	x;
	int		y;
	int 	width;
	int 	height;

	public MazeItem(Color color, int x, int y, int width, int height) {
		super();
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
