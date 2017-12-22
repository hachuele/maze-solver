// Name: Eric J. Hachuel
// CS 455 PA3
// Fall 2016

import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls
 */

public class Maze {
   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   
   private LinkedList<MazeCoord> mazePath; //LinkedList providing maze path to solution, if any.
   private boolean[][] mazeDataBoolean; //boolean input array.
   private boolean[][] visitedElements; //boolean array of visited elements in maze.
   private MazeCoord aStartLoc; //Maze start point coordinates.
   private MazeCoord aEndLoc; //Maze end point coordinates.
  

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param endLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
   {
      mazeDataBoolean = mazeData;
      aStartLoc = startLoc;
      aEndLoc = endLoc;
      mazePath = new LinkedList<MazeCoord>();
   }

   /**
   Returns the number of rows in the maze
   @return number of rows
   */
   public int numRows() {
      return mazeDataBoolean.length;   
   }

   /**
   Returns the number of columns in the maze
   @return number of columns
   */   
   public int numCols() {
      return mazeDataBoolean[0].length;   
   } 
 
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
      return mazeDataBoolean[loc.getRow()][loc.getCol()];   
   }
   
   /**
      Returns the entry location of this maze.
     * @return the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
      return new MazeCoord(aStartLoc.getRow(),aStartLoc.getCol()); 
   }
   
   /**
   Returns the exit location of this maze.
     * @return the exit location of this maze.
   */
   public MazeCoord getExitLoc() {
      return new MazeCoord(aEndLoc.getRow(),aEndLoc.getCol());    
   }

   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path to the solution, if any.
    */
   public LinkedList<MazeCoord> getPath() {

      return mazePath;  
   }

   /**
      Find a path through the maze if there is one. Client can access the
      path found via getPath method.
      @return whether path was found.
    */
   public boolean search()  {
      
       //Initialize array of visited Elements
       visitedElements = new boolean [numRows()][numCols()];
       
       //Initialize Starting Maze Coordinates
       MazeCoord currentLocCoords = new MazeCoord(aStartLoc.getRow(),aStartLoc.getCol());
       
       //Fill visitedElements boolean array with all False Elements;
       for(int i = 0; i < numRows(); i++){
           for(int j = 0; j < numCols(); j++){
               visitedElements[i][j] = false;
           }
       }
       //Return helper method with actual recursion 
       return searchRecursion(currentLocCoords); 
   }
   
   /**
    * Helper method for the search() method. This method runs the recursion.
     * @param currentLocCoords the MazeCoord coordinates for the start of the recursion.
     * @return whether the path was found.
    */
   
   public boolean searchRecursion(MazeCoord currentLocCoords){
       
       //BASE CASES: 
       
       //Check if current Location is a wall
       if(this.hasWallAt(currentLocCoords)){
           //Set element as visited and return false.
           visitedElements[currentLocCoords.getRow()][currentLocCoords.getCol()] = true;
           return false;
       }
       //Check if element has been visited, if so, return false.
       if(visitedElements[currentLocCoords.getRow()][currentLocCoords.getCol()]){
           return false;
       }
       
       //Check if current Location is the Exit location and return true.
       if(currentLocCoords.equals(getExitLoc())){
           //Add End Loc to LinkedList as last element
           mazePath.addFirst(currentLocCoords); 
           return true;
       }
       
       else{
           
            //Set current location as visited
            visitedElements[currentLocCoords.getRow()][currentLocCoords.getCol()] = true;

            //RECURSIVE CASES:
            //The following code checks to make sure you are within the maze prior to running recursion.

            //Check if you are on the first row of the maze, then run recursion and add location to solution path.
            if(currentLocCoords.getRow() > 0){
                //Create new coordinates for adjacent location (to be used in recursion check).
                MazeCoord adjacentLoc = new MazeCoord(currentLocCoords.getRow()-1,currentLocCoords.getCol());
                if(searchRecursion(adjacentLoc) == true){
                    mazePath.addFirst(currentLocCoords); 
                    return true;
                }
            }

            //Check if you are on the last row of the maze, then run recursion and add location to solution path.
            if(currentLocCoords.getRow() != numRows()- 1){
                //Create new coordinates for adjacent location (to be used in recursion check).
                MazeCoord adjacentLoc2 = new MazeCoord(currentLocCoords.getRow()+1,currentLocCoords.getCol());
                if(searchRecursion(adjacentLoc2) == true){
                    mazePath.addFirst(currentLocCoords); 
                    return true;
                }
            }


            //Check if you are on the first column of the maze, then run recursion and add location to solution path.
            if(currentLocCoords.getCol() > 0){
                //Create new coordinates for adjacent location (to be used in recursion check).
                MazeCoord adjacentLoc3 = new MazeCoord(currentLocCoords.getRow(),currentLocCoords.getCol()-1);
                if(searchRecursion(adjacentLoc3) == true){
                    mazePath.addFirst(currentLocCoords); 
                    return true;
                }
            }

            //Check if you are on the last column of the maze, then run recursion and add location to solution path.
           if(currentLocCoords.getCol() != numCols() - 1){
               //Create new coordinates for adjacent location (to be used in recursion check).
                MazeCoord adjacentLoc4 = new MazeCoord(currentLocCoords.getRow(),currentLocCoords.getCol()+1);
                if(searchRecursion(adjacentLoc4) == true){
                    mazePath.addFirst(currentLocCoords); 
                    return true;
                }
           }
      }
      return false;
   }
}
