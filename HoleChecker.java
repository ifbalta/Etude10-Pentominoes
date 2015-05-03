/**
  Given a board, a piece and the offset,
  Place the piece on the board and ensure that
  placing the board does not result in holes.

  This is done by checking the top, down, left and right
  of every occupied place. 
  If a surrounding area is Piece.EMPTY and is surrounded by non-Piece.EMPTY squares,
  then this placement is invalid.
*/
import java.util.*;
public class HoleChecker{
      
      Piece[][] board;
      ArrayList<int[]> occupied = new ArrayList<>();

      public HoleChecker(Piece[][] board, int x, int y, int[][] locations){
        
        // place piece
        for(int[] pair : locations){
          int occX = pair[0] + x;
          int occY = pair[1] + y;
          int[] occPair = {occX, occY}; 
          occupied.add(occPair);
          board[occX][occY] = Piece.DUMMY;
        }

        this.board = board;
        
      }

    public boolean checkNow(){
      for(int[] xy : occupied){
          // we have a hole.
          if(!checkOccupiedSurroundings(occupied)) return true;
        }
        return false;
    }


    public boolean checkOccupiedSurroundings(int[] occupiedSquare){
      int x = occupiedSquare[0];
      int y = occupiedSquare[1];
      // check top
      if(x > 0){

      }
      //check bottom

      //check left

      // check right

    }

    public boolean checkEmptySpace(){

    }
}