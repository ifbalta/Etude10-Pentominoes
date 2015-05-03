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
          if(occX > 0 && occX < board.length - 1 && occY > 0 && occY < board[0].length - 1){
            int[] occPair = {occX, occY}; 
            occupied.add(occPair);
            board[occX][occY] = Piece.DUMMY;
          }
        }
        System.out.println("trial board");
        displayBoard(board);


        this.board = board;
        
      }

    public boolean hasHolesNow(){
      for(int[] xy : occupied){
          // we have a hole.
          if(checkOccupiedSurroundings(xy)) return true; // true if has holes
        }
        return false;
    }


    public boolean checkOccupiedSurroundings(int[] occupiedSquare){
      int x = occupiedSquare[0];
      int y = occupiedSquare[1];
      // check top
      if(x > 0){
        if(board[x - 1][y] == Piece.EMPTY){
          if (checkEmptySpace(x - 1, y)) return true; // true if has holes
        }
      }
      //check bottom
      if(x < board.length - 1){
        if(board[x + 1][y] == Piece.EMPTY){
          if (checkEmptySpace(x + 1, y)) return true; // true if has holes
        }
      }
      //check left
      if(y > 0){
        if(board[x][y - 1] == Piece.EMPTY){
          if (checkEmptySpace(x, y - 1)); return true; // true if has holes
        }
      }
      // check right
      if(y < board[0].length - 1){
        if(board[x][y + 1] == Piece.EMPTY){
          if (checkEmptySpace(x, y + 1)); return true; // true if has holes
        }
      }
      return false; // nobody has holes.
    }

    public boolean checkEmptySpace(int x, int y){
      int surroundings = 0;
      // check top
      if(x > 0 && board[x - 1][y] != Piece.EMPTY) surroundings++;
      // check bottom
      if(x < board.length - 1 && board[x + 1][y] != Piece.EMPTY) surroundings++;
      // check left
      if(y > 0 && board[x][y - 1] != Piece.EMPTY) surroundings++;
      // check right
      if(y < board[0].length - 1 && board[x][y + 1] != Piece.EMPTY) surroundings++;
      return surroundings == 4; // if 4, then I'm surrounded and can't get out.
    }

    public static void displayBoard(Piece[][] b){
    for(Piece[] row : b){
      for(Piece p : row){
        System.out.print(p.named() + " ");
      }
      System.out.println();
    }
  }
}