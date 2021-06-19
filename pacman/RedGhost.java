/**
 * Programming Project 2
 * @author Lailei Forouraghi
 * @date 10/4/2020
 * 
 * RedGhost Class. Class to contain and organize position of RedGhost in the maze. The
 * RedGhost object is like a "state" in the search space
 * 
 */

package pacman;
public class RedGhost {

	
	private int redX; // x coordinate 
	private int redY; // y coordinate 
	private int generation; // keep generation for step cost
	private RedGhost parent; // link each state to 
	private int cost; // store total cost
	
	private Maze maze; // maze this RedGhost is part off 
	

	
	// constructor
	public RedGhost (int x, int y, Maze maze)
	{
		
		this.redX = x;
		this.redY = y;
		this.generation = 0;
		this.cost = 0;
		this.maze = maze;
		
	}
	
	// return parent for path
	public RedGhost getParent ()
	{
		return this.parent;
	}
	
	// set parent 
	public void setParent(RedGhost parent)
	{
		this.parent = parent;
	}
	
	// get x coordinate 
	public int getX ()
	{
		return this.redX;
	}
	
	// get y coordinate
	public int getY ()
	{
		return this.redY;
	}
	
	// set cost 
	public void setCost (int cost)
	{
		this.cost = cost;
	}
	
	//get cost
	public int getCost ()
	{
		return this.cost;
	}
	
	// get generation 
	public int getGeneration ()
	{
		return this.generation;
	}
	
	// set generation
	public void setGeneration (int gen)
	{
		this.generation = gen;
	}
	
	
	/**
	 * @brief main heuristic for calculating distance between pacman and ghost
	 * @param gX the ghost's x coordinate
	 * @param gY the ghost's y coordinate
	 * @return an integer of the number of rows and columns pacman is from the ghost
	 */
//	public int calculateManhattanDistance (int gX, int gY)
//	{
//		 return (Math.abs(redX - gX) + Math.abs(redY - gY)); 
//	}
//	
	
	
	/**
	 * generate a child pacman state whose x coordinate is one more than
	 * current pacman state
	 * @return child pacman state
	 */
	
	public RedGhost generateDown ()
	{
		RedGhost down = new RedGhost (redX + 1, redY, this.maze);
		down.setGeneration(this.generation + 1);
		down.setParent(this);
		return down;
	}
	
	/**
	 * generate a child pacman state whose x coordinate is one less than
	 * current pacman state
	 * @return child pacman state
	 */
	public RedGhost generateUp ()
	{
		RedGhost up = new RedGhost (redX - 1, redY, this.maze);
		up.setGeneration(this.generation + 1);
		up.setParent(this);
		return up;
	}
	
	
	/**
	 * generate a child pacman state whose y coordinate is one less than
	 * current pacman state
	 * @return child pacman state
	 */
	public RedGhost generateLeft ()
	{
		RedGhost left = new RedGhost (redX, redY - 1, this.maze);
		left.setGeneration(this.generation + 1);
		left.setParent(this);
		return left;
	}
	
	/**
	 * generate a child pacman state whose y coordinate is one more than
	 * current pacman state
	 * @return child pacman state
	 */
	public RedGhost generateRight ()
	{
		RedGhost right = new RedGhost (redX, redY + 1, this.maze);
		right.setGeneration(this.generation + 1);
		right.setParent(this);
		return right;
	}
	
	
//	/**
//	 * @brief function that determines the total
//	 * cost for a pacman state.  
//	 * G value (cost from start to current position) is sum
//	 * of state's generation and space cost (cost of position in maze)
//	 * H value is the manhattan distance between ghost and pacman 
//	 * multiplied by 10 (that value found empirically).
//	 * 
//	 * total cost = f(n) = g + h;
//	 * 
//	 */
//	 public void evaluate ()
//	 {
//		
//		// step cost from start to current position, plus the cost of the current position (enenmy in square)?
//		int g = generation + maze.getSpaceCost(redX, redY);
//			
//		int ghostX = maze.getGhostX();
//		int ghostY = maze.getGhostY();
//			
//		//int h = calculateManhattanDistance(ghostX, ghostY) * 10;
//		cost = g + h;
//				
//	  }
	
	
	 
	 /**
	  * @brief prints coordinates and h and g and f(n) values for pacman state
	  */
	@Override
	public String toString ()
	{
		
		int g = getGeneration() + maze.getSpaceCost(redX, redY);
//		int h = calculateManhattanDistance(maze.getGhostX(), maze.getGhostY()) * 10;
		return "RedGhost located at : (" + redX + "," + redY + ")"
//				+ " h: " + h
				+ " g: " + g 
				+ " f(n): " + cost;
	}
	
	
	/**
	  * @brief compares two red ghost states
	  * @returns true if both states have the same coordinates 
	  * (doesn't check any other values).
	  */
	@Override
	public boolean equals (Object object)
	{
		RedGhost compare = (RedGhost)object;
		
		if(compare.getX() != this.redX ||
		   compare.getY() != this.redY ) return false;
		
		return true;
	}
}
