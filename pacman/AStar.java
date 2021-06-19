/**
 * Programming Project 2
 * @author Lailei Forouraghi
 * @date 10/4/2020
 * 
 * AStar Class.
 * 
 * 
 */
package pacman;

import java.util.Comparator;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;


/**
 * @brief class to implement AStar Search 
 */
public class AStar {
	
	private static PacMan start; // starting position of pacman
	private static Maze maze; // maze being searched
	
	private static PriorityQueue<PacMan> open; // priority queue to maintain search
	private static ArrayList<PacMan> closed; // 'closed' list to keep track of visited states
	
	private static ArrayList<PacMan> path; // path for pacman
	
	private static ArrayList<PacMan> children; // hold generated children of a state


	
	public static ArrayList<PacMan> search (PacMan st, Maze mz) 
	{
		
		
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
		
		// evaluate start
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
			
		}
	
		//create and return pac path
		createPath();
		return path;
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
		 
		 path.add(start);
		 
		 while (!bwpath.isEmpty())
			 path.add(bwpath.pop());
	 }
	 

}
