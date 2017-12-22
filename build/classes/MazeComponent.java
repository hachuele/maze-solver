// Name: Eric J. Hachuel
// USC loginid: hachuelb
// CS 455 PA3
// Fall 2016

import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Color;
import java.util.ListIterator;

/**
   MazeComponent class
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{
   private Maze mazeInput;
   
   private static final int START_X = 10; // where to start drawing maze in frame
   private static final int START_Y = 10;
   private static final int BOX_WIDTH = 20;  // width and height of one maze unit
   private static final int BOX_HEIGHT = 20;
   private static final int INSET = 2;  
                    // how much smaller on each side to make entry/exit inner box
   
   //Define Colors for Maze Elements
   private static final Color COLOR_OF_WALL = Color.BLACK; //Color of maze wall elements
   private static final Color COLOR_OF_START = Color.YELLOW; //Color of starting point in maze
   private static final Color COLOR_OF_END = Color.GREEN; //Color of ending point in maze
   private static final Color COLOR_OF_LINE = Color.BLUE; //Color of line through maze
   
   /**
      Constructs the component.
      @param maze the maze to display
   */
   public MazeComponent(Maze maze) 
   {
       mazeInput = maze;
   }
   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
       //Width and Height for the outer box of the maze
       int rectHeight = BOX_HEIGHT * mazeInput.numRows();
       int rectWidth = BOX_WIDTH * mazeInput.numCols();
              
       //Draw Maze Borders (Rectangle)
       g.drawRect(START_X, START_Y, rectWidth, rectHeight);
       
       //Set color of wall elements
       g.setColor(COLOR_OF_WALL);
       
       //Loop through Array to fill all wall rectangles
       for(int i = 0; i < mazeInput.numRows(); i++){
           for(int j = 0; j < mazeInput.numCols(); j++){
               
               MazeCoord currentLoc = new MazeCoord(i,j);
               //Check if wall element and fill with set color
               if(mazeInput.hasWallAt(currentLoc)){
                   g.fillRect(j*BOX_WIDTH + START_X, i*BOX_HEIGHT +START_Y, BOX_WIDTH, BOX_HEIGHT);
               }
           }
       }
       
       //Create Start end End Rectangles
       int insetMultiplier = 2; //multiplier used to apply inset to both sides of box.
       int boxMultiplier =2; //multiplier used to draw line starting point at middle of box.
       int startBufferX = START_X +INSET;
       int startBufferY = START_Y +INSET;
       int mazeEntryColumnWidth = mazeInput.getEntryLoc().getCol()*(BOX_WIDTH);
       int mazeEntryRowHeight = mazeInput.getEntryLoc().getRow()*(BOX_HEIGHT);
       int mazeExitColumnWidth = mazeInput.getExitLoc().getCol()*(BOX_WIDTH);
       int mazeExitRowHeight = mazeInput.getExitLoc().getRow()*(BOX_HEIGHT);
       
       //Fill Start Location
       g.setColor(COLOR_OF_START);
       g.fillRect(mazeEntryColumnWidth + startBufferX, mazeEntryRowHeight + startBufferY,
               BOX_WIDTH-(insetMultiplier*INSET), BOX_HEIGHT-(insetMultiplier*INSET));
       
        //Fill End Location
        g.setColor(COLOR_OF_END);
        g.fillRect(mazeExitColumnWidth + startBufferX, mazeExitRowHeight + startBufferY,
               BOX_WIDTH-(insetMultiplier*INSET), BOX_HEIGHT-(insetMultiplier*INSET));
        

        //Draw Solution Path (if not empty)
        if(!mazeInput.getPath().isEmpty()){
        
        //Initialize Iterator to loop through LinkedList
        ListIterator<MazeCoord> iter = mazeInput.getPath().listIterator();
        g.setColor(COLOR_OF_LINE);
        
        //Set MazeCoords to draw line between currentPoint and nextPoint
        MazeCoord currentPoint = iter.next();
        MazeCoord nextPoint;
        
            //Loop through LinkedList and draw point to point from start point to solution.
            while(iter.hasNext()){
                int pointAX = (currentPoint.getCol()*BOX_WIDTH) + START_X + (BOX_WIDTH/boxMultiplier);
                int pointAY = (currentPoint.getRow()*BOX_HEIGHT) + START_Y + (BOX_HEIGHT/boxMultiplier);
                
                nextPoint = iter.next(); //Sets nextPoint to adjacent MazeCoord
                
                int pointBX = (nextPoint.getCol()*BOX_WIDTH) + START_X + (BOX_WIDTH/boxMultiplier);
                int pointBY = (nextPoint.getRow()*BOX_HEIGHT) + START_Y + (BOX_HEIGHT/boxMultiplier);
                
                g.drawLine(pointAX, pointAY, pointBX, pointBY); //Draws line between given coordinates
               
                currentPoint = nextPoint; //Set currentPoint to nextPoint for next loop iteration.
            }
        }
   }
   
}



