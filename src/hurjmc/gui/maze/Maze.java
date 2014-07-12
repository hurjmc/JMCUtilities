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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Maze {
	
	int[][] array;
	int width; 
	int height;
	
	private Random random = new Random(25);
	private List<int[]> possible = new ArrayList<int[]>();
	
	Maze(Dimension mazeDim, int squareSize) {
		int w = mazeDim.width/squareSize;
		int h = mazeDim.height/squareSize;

		// Make sure w and h are odd numbers 
		w/=2; w*=2; w--; 
		h/=2; h*=2; h--;
		
		width = w;
		height = h;
	}

	void setSeed(long seed) {
		random = new Random(seed);		
	}
	
	void reset(){
		int i = 0;
		array = new int[width][height];
		
		for (i = 0; i < width; i++){
			array[i][0] = 1;
			array[i][height-1] = 1;
		}
		
		for (i = 0; i < height; i++){
			array[0][i] = 1;
			array[width-1][i] = 1;
		}
		
		array[0][1]=2;
		array[width-1][height-2]=2;
		
		int[] start = {1,1};
		possible.add(start);
		while (!possible.isEmpty()){
			 int[] chosen = possible.get(random.nextInt(possible.size()));
			 link(chosen[0], chosen[1]);
			 possible.remove(chosen);
		}

	}
	private void link(int x, int y) {
		int[][] newlink = new int[4][2];
		int links = 0;
		// Mark chosen node as added to the graph
		array[x][y] = 2;
		
		// Check adjacent nodes 
		if (x > 1) 
			links = check(x-2, y, newlink, links);
		if (x < (width-2))
			links = check(x+2, y, newlink, links);
		if (y > 1) 
			links = check(x, y-2, newlink, links);
		if (y < (height-2))
			links = check(x, y+2, newlink, links);
			
		// Attach chosen node to graph
		
		if (links > 0){
			int link = random.nextInt(links);
			int arcX = (x+(newlink[link][0]))/2;
			int arcY = (y+(newlink[link][1]))/2;
			array[arcX][arcY] = 2;
		}
		
	}


	private int check(int x, int y, int[][] newlink, int links) {
		if (array[x][y] == 0){
			array[x][y] = 1;
			possible.add(new int[]{x,y});
		}
		else if(array[x][y] == 2){
			newlink[links++] = new int[]{x,y};		
		}
		return links;
	}


}
