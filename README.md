# a-star-moving-target
**Use A-star algorithm to help PacMan catch a moving red Ghost.** 
_Homework assignment from an Intro to Artifical Intelligence course_

This is a homework assignment from an intro to Artifical Intelligence course.
The assignment's goal was to implement the A-star algorithm so that PacMan follows the optimal path
from his starting position at the top left corner of the screen to the position of the stationary Red Ghost.
All other ghosts are treated as stationary obstacles and should be avoided. 

The extra credit component of the assignment was to randomly move the Red Ghost 
so that PacMan follows the optimal path to a moving target.

In this implementation, PacMan follows the optimal path toward the moving ghost, 
though sometimes it might not appear to. This assignment was designed to be run 
on a single thread in Java. Therefore, it was simplest to move the ghost everytime 
a PacMan state was evaluated. AStar returns the optimal path AFTER it has evaluated possible
child paths. The optimal path is found by tracing parent states backwards from the final position of PacMan to the first position of PacMan
in the generated list of AStar-evaluated states. Since the ghost moves every time a state 
is evaluated, more ghost states than PacMan states are generated. Therefore, the 
ghost path was shortened to display the ghost path by the length of PacMan's final (optimal) path.
To properly implement a moving target solution, a restructuring of the assignment code might be necessary. For now, though, if a moving target run looks strange, you can run it again. Odds are it will look better the next time.

**Running the code:**  

The main method is found in the Maze.java class. 
The main method will run 5 test cases. 
The first 3 are regular A* star, and the last 2 are modified moving target searches.

**Extra Credit Component**


![plot](./moving-target.gif)
