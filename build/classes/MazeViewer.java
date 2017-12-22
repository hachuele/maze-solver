// Name: Eric J. Hachuel
// USC loginid: hachuelb
// CS 455 PA3
// Fall 2016


import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.Scanner;
import java.io.File;


/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 *      java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start location, 
 * and ending with the exit location. Each maze location is
 * either a wall (1) or free (0). Here is an example of contents of a file for
 * a 3x4 maze, with start location as the top left, and exit location as the bottom right
 * (we count locations from 0, similar to Java arrays):
 * 
 * 3 4 
 * 0111
 * 0000
 * 1110
 * 0 0
 * 2 3
 * 
 */

public class MazeViewer {
   
   private static final char WALL_CHAR = '1';
   private static final char FREE_CHAR = '0';

   public static void main(String[] args)  {

      String fileName = "";

      try {

         if (args.length < 1) {
            System.out.println("ERROR: missing file name command line argument");
         }
         else {
            fileName = args[0];
            
            JFrame frame = readMazeFile(fileName);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
         }

      }
      catch (FileNotFoundException exc) {
         System.out.println("File not found: " + fileName);
      }
      catch (IOException exc) {
         exc.printStackTrace();
      }
   }

   /**
    readMazeFile reads in maze from the file whose name is given and 
    returns a MazeFrame created from it.
   
   @param fileName
             the name of a file to read from (file format shown in class comments, above)
   @returns a MazeFrame containing the data from the file.
        
   @throws FileNotFoundException
              if there's no such file (subclass of IOException)
   @throws IOException
              (hook given in case you want to do more error-checking.
               that would also involve changing main to catch other exceptions)
   */
   private static MazeFrame readMazeFile(String fileName) throws IOException {
       
       File inputFile = new File(fileName);
       Scanner in = new Scanner(inputFile);
       
       //Number of rows and Columns into local variables
       int numberOfRows = in.nextInt();
       int numberOfColumns = in.nextInt();
       
       //Create array of booleans with Number of specified columns and rows
       boolean[][] booleanArray = new boolean[numberOfRows][numberOfColumns];
       
       //NextLine to consume new line and start looping through array
       in.nextLine();
      
       //Loop through array in input file; assign appropriate values to boolean array
       for(int i = 0; i < numberOfRows; i++){
           
           //Read line by line and assign true or false based on binary data ('1' for wall, '0' for free space)
           String nextRow = in.nextLine();
           
           for(int j = 0; j < numberOfColumns; j++){
               
               if(nextRow.charAt(j) == WALL_CHAR){
                   booleanArray[i][j] = true;
               }
               else if(nextRow.charAt(j) == FREE_CHAR){
                   booleanArray[i][j] = false;
               }
           }
       }
      
       //Get entry and exit coordinates after looping through array
       int entryCoordX = in.nextInt();
       int entryCoordY = in.nextInt();
       int exitCoordX = in.nextInt();
       int exitCoordY = in.nextInt();
       
      //Return new MazeFrame with array of true/false values, entry and exit Maze Coordinates
      return new MazeFrame(booleanArray, new MazeCoord(entryCoordX, entryCoordY), new MazeCoord(exitCoordX, exitCoordY));
   }
}
