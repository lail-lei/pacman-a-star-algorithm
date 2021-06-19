/**
 * Programming Project 2
 * @author Lailei Forouraghi
 * @date 10/4/2020
 * 
 * AStarMoving Target Class.
 * AStar class modified to keep track of moving target 
 * 
 */

package pacman;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

/**
 * @brief class to implement modified AStar Search 
 */
public class AStarMovingTarget {
	
	private static PacMan start; // starting position of pacman
	private static Maze maze; // maze being searched
	
	private static PriorityQueue<PacMan> open; // priority queue to maintain search
	private static ArrayList<PacMan> closed; // 'closed' list to keep track of visited states
	
	private static ArrayList<PacMan> path; // path for pacman
	private static ArrayList<Integer[]> ghostPath; // path for ghost
	private static ArrayList[] paths; // list holding both ghost and pacman paths
	
	private static ArrayList<PacMan> children; // hold generated children of a state
	private static Random rand = new Random(); // random number generator for ghost path

	
	
	/**
	 * Modified A* search
	 * @param st start position of pacman
	 * @param mz maze to be searched
	 * @return array paths containing the pacman's path to the ghost 
	 * as first item and the ghosts path (around the maze) as the second item 
	 */
	
	public static ArrayList[] search (PacMan st, Maze mz) 
	{
			
		ghostPath = new ArrayList<Integer[]>();
		path = new ArrayList<PacMan>();
		open = new PriorityQueue<PacMan>(new Comparator<PacMan>() {

	        @Override
	        public int compare(PacMan p1, PacMan p2) {
	        	
	        	int cost1 = p1.getCost();
	        	int cost2 = p2.getCost();
	        	
	        	if (cost1 != cost2) {  
	    	        return cost1 - cost2;  // return lower cost position
	    	    }  
	    	    else {  // if equal in position, return first entered state
	    	        return p1.getGeneration() - p2.getGeneration();  
	    	    }  
	        }
	    });
		start = st;
		closed = new ArrayList<PacMan>();
		maze = mz;
		
		
		// add pacman and ghost starting positions to their paths 
		path.add(start);
		Integer[] coordinates = {maze.getGhostX(), maze.getGhostY()};
		ghostPath.add(coordinates);
		
		
	
		// evaluate start (calc distance to red ghost)
		start.evaluate();
		
		
		PacMan x;
		open.offer(start); //add to queue
		
		while(!open.isEmpty())
		{
			
			x = open.poll();
			
			// if goal met, end maze
			if(isGoalState(x, maze))
			{
				closed.add(x); 
				break;
			}
			
			// generate children of x
			children = generateChildren(x);
			
		
			PacMan inOpen;
			PacMan inClosed;
			
			// for each potential child of x...
			for(PacMan child : children)
			{
				
				inOpen = inOpen(child); // see if in open queue
				inClosed = inClosed(child); // see if in closed list
				child.evaluate(); // calculate costs of child
				
				// if child is not in open or closed queues
				if(inOpen == null && inClosed == null)
				{
					open.offer(child);	// add to open
					continue; // skip to next loop
				}
				
				// if child exists in open
				if(inOpen != null)
				{
					// if the cost to get to child is less than state on open
					if(child.getCost() < inOpen.getCost())
						inOpen.setCost(child.getCost()); // set the cost on open to child cost
				}
				
				// if child exists in closed
				if(inClosed != null)
				{
					// if the cost to get to child is less than state on closed
					if(child.getCost() < inClosed.getCost())
					{
						closed.remove(inClosed); // remove from closed
						open.add(child); // add to open
					
					}
						
				}	
			}			
			
			closed.add(x); // add x to closed
			moveGhost(); // move ghost for next step
			
		}
		
		//create and return pac and ghost paths	
		createPath();
		
		paths = new ArrayList [2];		
		paths[0] = path;
		paths[1] = ghostPath;
		return paths;
	}
	
	/**
	 * Checks if pacman has caught ghost/can end the game
	 * @param pacMan current position of pacman
	 * @param maze maze being searched
	 * @return true if pacMan x coordinate = ghost x coordinate and 
	 * pacman y coordinate = ghost y coordinate
	 */
	
	private static boolean isGoalState (PacMan pacMan, Maze maze)
	{
		
		return pacMan.getX() == maze.getGhostX() && pacMan.getY() == maze.getGhostY();
	}
	

	/**
	 * @brief generates all possible children for a paramter pacman state
	 * @param state 
	 * @return an arraylist of all possible children of a pacman state
	 */
	private static ArrayList<PacMan> generateChildren(PacMan state)
	{
		ArrayList<PacMan> list = new ArrayList<PacMan>();
		int x = state.getX();
		int y = state.getY();
		
		
		if(maze.canMoveDown(x, y))
		{
			PacMan down = state.generateDown();
			list.add(down);
			
		}
		
		if(maze.canMoveLeft(x, y))
		{
			PacMan left = state.generateLeft();
			list.add(left);
		}
		
		if(maze.canMoveRight(x, y))
		{
			PacMan right = state.generateRight();
			list.add(right);
		}
		
		
		if(maze.canMoveUp(x,y))
		{
			PacMan up = state.generateUp();
			list.add(up);
			
		}
		
		
		return list;
		
	}
	

	/**
	 * Java's built-in priority queue implementation did not 
	 * include a "contains()" function, so I implemented one here. 
	 * @param p pacman state to be compared against states in the open queue
	 * @return a pacman state located in the priority queue
	 */
	private static PacMan inOpen (PacMan p)
	{
		
		for(PacMan state : open)
		{
			if(state.equals(p)) return state;
		}
		
		return null;
	}
	
	/**
	 * Even though Java's arraylist includes a "contains()" function,
	 * i decided to implement this method so my in open and in closed
	 * comparing methods would be consistently-formated
	 * @param p pacman state to be compared against states in closed list
	 * @return a pacman state located in the closed list
	 */
	
	private static PacMan inClosed (PacMan p)
	{
		
		for(PacMan state : closed)
		{
			if(state.equals(p)) return state;
		}
		
		return null;
	}
	 
	 
	/**
	 * Finds the connected "optimal path"
	 * from the closed list. starts from last element 
	 * of closed list and adds each ancestor to a stack,
	 * then pops the stack into path arraylist. I could
	 * have done this using a single data structure but
	 * since memory isn't a huge concern here, I decided not
	 * to put too much effort into it.
	 * 
	 */
	 
	 private static void createPath()
	 {
		 
		 Stack <PacMan> bwpath = new Stack<PacMan>();
		 
		 PacMan ptr = closed.get(closed.size()-1);
		
		 // only get ancestors of last position
		 while(ptr.getParent() != null)
		 {
			 
			 bwpath.push(ptr);
			 ptr = ptr.getParent(); 
			 
		 } 
		 
		 
		 while (!bwpath.isEmpty())
			 path.add(bwpath.pop());
	 }
	 
	 
	 /**
	  * Randomly generates the next position of the ghost.
	  * Each position is added to the ghost path 
	  */
	 private static void moveGhost ()
	 {
		 
		 
		int ghostX = maze.getGhostX();
		int ghostY = maze.getGhostY();
		
		int key;
	
		boolean moveSelected = false;
		
		while(moveSelected == false)
		{
			
			// at most 4 possible moves
			key = rand.nextInt(4);
			
			switch(key)
			{
				case 0:
					if(maze.canMoveDown(ghostX, ghostY))
					{
						maze.setGhostX(ghostX + 1); // move ghost
						moveSelected = true; // break out of while loop
					}
					// if can't move down, switch is broken and 
					// a new random key is generated
					break;
				case 1:
					if(maze.canMoveUp(ghostX, ghostY))
					{
						maze.setGhostX(ghostX - 1); // move ghost
						moveSelected = true; // break out of while loop
					}
					// if can't move up, switch is broken and 
					// a new random key is generated
					break;
				case 2:
					if(maze.canMoveLeft(ghostX, ghostY))
					{
						maze.setGhostY(ghostY - 1); // move ghost
						moveSelected = true; // break out of while loop
					}
					// if can't move left, switch is broken and 
					// a new random key is generated
					break;
				case 3:
					if(maze.canMoveRight(ghostX, ghostY))
					{
						maze.setGhostY(ghostY + 1); // move ghost
						moveSelected = true; // break out of while loop
					}
					// if can't move right, switch is broken and 
					// a new random key is generated
					break;
			}
			
		}
		 
		
		// store new coordinates in ghost path
		Integer[] coordinates = {maze.getGhostX(), maze.getGhostY()};
		ghostPath.add(coordinates);
		 
	 }
	
	

}
