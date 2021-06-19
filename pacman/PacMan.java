/**
 * Programming Project 2
 * @author Lailei Forouraghi
 * @date 10/4/2020
 * 
 * PacMan Class. Class to contain and organize position of Pacman in the maze. The
 * PacMan object is like a "state" in the search space
 * 
 */

package pacman;

/**
 * @brief class to contain and organize position of Pacman in the maze. The
 * PacMan object is like a "state" in the search space
 */
public class PacMan {
	
	private int pacX; // x coordinate 
	private int pacY; // y coordinate 
	private int generation; // keep generation for step cost
	private PacMan parent; // link each state to 
	private int cost; // store total cost
	
	private Maze maze; // maze this pacman is part off 
	

	
	// constructor
	public PacMan (int x, int y, Maze maze)
	{
		
		this.pacX = x;
		this.pacY = y;
		this.generation = 0;
		this.cost = 0;
		this.maze = maze;
		
	}
	
	// return parent for path
	public PacMan getParent ()
	{
		return this.parent;
	}
	
	// set parent 
	public void setParent(PacMan parent)
	{
		this.parent = parent;
	}
	
	// get x coordinate 
	public int getX ()
	{
		return this.pacX;
	}
	
	// get y coordinate
	public int getY ()
	{
		return this.pacY;
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
	public int calculateManhattanDistance (int gX, int gY)
	{
		 return (Math.abs(pacX - gX) + Math.abs(pacY - gY)); 
	}
	
	
	
	/**
	 * generate a child pacman state whose x coordinate is one more than
	 * current pacman state
	 * @return child pacman state
	 */
	
	public PacMan generateDown ()
	{
		PacMan down = new PacMan (pacX + 1, pacY, this.maze);
		down.setGeneration(this.generation + 1);
		down.setParent(this);
		return down;
	}
	
	/**
	 * generate a child pacman state whose x coordinate is one less than
	 * current pacman state
	 * @return child pacman state
	 */
	public PacMan generateUp ()
	{
		PacMan up = new PacMan (pacX - 1, pacY, this.maze);
		up.setGeneration(this.generation + 1);
		up.setParent(this);
		return up;
	}
	
	
	/**
	 * generate a child pacman state whose y coordinate is one less than
	 * current pacman state
	 * @return child pacman state
	 */
	public PacMan generateLeft ()
	{
		PacMan left = new PacMan (pacX, pacY - 1, this.maze);
		left.setGeneration(this.generation + 1);
		left.setParent(this);
		return left;
	}
	
	/**
	 * generate a child pacman state whose y coordinate is one more than
	 * current pacman state
	 * @return child pacman state
	 */
	public PacMan generateRight ()
	{
		PacMan right = new PacMan (pacX, pacY + 1, this.maze);
		right.setGeneration(this.generation + 1);
		right.setParent(this);
		return right;
	}
	
	
	/**
	 * @brief function that determines the total
	 * cost for a pacman state.  
	 * G value (cost from start to current position) is sum
	 * of state's generation and space cost (cost of position in maze)
	 * H value is the manhattan distance between ghost and pacman 
	 * multiplied by 10 (that value found empirically).
	 * 
	 * total cost = f(n) = g + h;
	 * 
	 */
	 public void evaluate ()
	 {
		
		// step cost from start to current position, plus the cost of the current position (enenmy in square)?
		int g = generation + maze.getSpaceCost(pacX, pacY);
			
		int ghostX = maze.getGhostX();
		int ghostY = maze.getGhostY();
			
		int h = calculateManhattanDistance(ghostX, ghostY) * 10;
		cost = g + h;
				
	  }
	
	
	 
	 /**
	  * @brief prints coordinates and h and g and f(n) values for pacman state
	  */
	@Override
	public String toString ()
	{
		
		int g = getGeneration() + maze.getSpaceCost(pacX, pacY);
		int h = calculateManhattanDistance(maze.getGhostX(), maze.getGhostY()) * 10;
		return "PacMan located at : (" + pacX + "," + pacY + ")"
				+ " h: " + h
				+ " g: " + g 
				+ " f(n): " + cost;
	}
	
	
	/**
	  * @brief compares two pacman states
	  * @returns true if both pacman states have the same coordinates 
	  * (doesn't check any other values).
	  */
	@Override
	public boolean equals (Object object)
	{
		PacMan compare = (PacMan)object;
		
		if(compare.getX() != this.pacX ||
		   compare.getY() != this.pacY ) return false;
		
		return true;
	}
}
