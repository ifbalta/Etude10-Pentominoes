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
      int surroundingLimit = 4;

      public HoleChecker(Piece[][] board, int x, int y, int[][] locations, String pieceName){
        // if (pieceName.matches("[U]")) surroundingLimit = 3;
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
        // displayBoard(board);


        this.board = board;
        
      }

      /*
      * Constructor for Puzzle 1
      */
      public HoleChecker(Piece[][] board, int[][] locations){
        for (int[] row : locations) {
          board[row[0]][row[1]] = Piece.DUMMY;
          occupied.add(row);
        }
        this.board = board;
      }


    public boolean hasHolesNow(){
      for(int[] xy : occupied){
          // we have a hole.
          if(checkOccupiedSurroundings(xy)) {
    //        System.out.printf("%s %s is an invalid loc\n", xy[0], xy[1]);
            return true; // true if has holes
          }
          if (!allBoardChecker(board)) {
          //  System.out.println("Failed: allBoardChecker");
            return true; // true if we have massive holes
          }
        }
        return false;
    }


    public boolean checkOccupiedSurroundings(int[] occupiedSquare){
      int x = occupiedSquare[0];
      int y = occupiedSquare[1];
   //   System.out.printf("Checking %s %s surroundings\n", x, y);
      // check top
      if(x > 0){
        if(board[x - 1][y] == Piece.EMPTY){
          if (checkEmptySpace(x - 1, y)) {
       //     System.out.printf("[x] %s %s\n", x - 1, y);
            return true; // true if has holes
          }
        }
      }
      //check bottom
      if(x < board.length - 1){
        if(board[x + 1][y] == Piece.EMPTY){
          if (checkEmptySpace(x + 1, y)) {
       //     System.out.printf("[x] %s %s\n", x + 1, y);
            return true; // true if has holes
          }
        }
      }
      //check left
      if(y > 0){
        if(board[x][y - 1] == Piece.EMPTY){
          if (checkEmptySpace(x, y - 1)) {
        //    System.out.printf("[x] %s %s\n", x, y - 1);
            return true; // true if has holes
          }
        }
      }
      // check right
      if(y < board[0].length - 1){
        if(board[x][y + 1] == Piece.EMPTY){
          if (checkEmptySpace(x, y + 1)) {
        //    System.out.printf("[x] %s %s\n", x, y + 1);
            return true; // true if has holes
          }
        }
      }
    //  System.out.println("~valid~");
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
 //     System.out.printf("loc: %s %s is surrounded by %s %svalid\n", x, y, surroundings, surroundings == 4? "in":"");
      return surroundings == surroundingLimit; // if surroundingLimit, then I'm surrounded and can't get out.
    }

    public boolean allBoardChecker (Piece[][] board) {
      if (rowChecker(board) != Piece.DUMMY) return false;
      if (columnChecker(board) != Piece.DUMMY) return false;
      return true;
    }

    /* 
      if there is an empty piece in the middle, evaluate the row.
    */
    private Piece rowChecker(Piece[][] board) {
      Piece signPost;
      // find an occupied row
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length - 1; j++) {
          if (board[i][j] != Piece.EMPTY) {
            if (board[i][j + 1] == Piece.EMPTY){
           //   System.out.printf("EVALUATE row: %s %s\n", i, j);
              signPost = evaluateRow(board, i, j + 1);
              if (signPost != Piece.DUMMY) return signPost; // we hit a bad one
            }
          }
        }
      }
      return Piece.DUMMY;
    }

    /*
      check above it, if also empty ok
       check below, if also empty ok
      check right, if also empty bad.
      keep checking, if we continue finding empty spots, ok
      if we run into another Piece, then we have another problem.
      return offending Piece Type
    */
    private Piece evaluateRow (Piece[][] board, int x, int y) {
      // check top
      if (x > 0) {
        if (board[x - 1][y] == Piece.EMPTY) {
       //   System.out.printf("rowChecker: %s %s ok\n", x - 1, y);
          return Piece.DUMMY;
        }
      }
      // check bottom
      if (x< board.length - 1) {
        if (board[x + 1][y] == Piece.EMPTY) {
       //   System.out.printf("rowChecker: %s %s ok\n", x + 1, y);
         return Piece.DUMMY;
        }
      }
      // check right
      if (y < board[0].length - 1) {
        if (board[x][y + 1] == Piece.EMPTY){
       //   System.out.printf("rowChecker danger: consecutive empties %s %s\n", x, y + 1);
          for (int nextPlace = y + 1; nextPlace < board[0].length; nextPlace++) {
            if (board[x][nextPlace] != Piece.EMPTY) {
           //   System.out.printf("FAILED rowChecker: consecutive empties %s %s\n",x, nextPlace );
              return Piece.INVALID; // bad spot
            } 
          }
        }
      }
      return Piece.DUMMY;
    }

    /* find an occupied column   
       if there is an empty piece in the middle,
       evaluate the column
    */
    private Piece columnChecker(Piece[][] board) { 
      Piece signPost;
      for (int col = 0; col < board[0].length; col++) {
        for (int row = 0; row < board.length - 1; row++) {
          if (board[row][col] != Piece.EMPTY) {
            if (board[row + 1][col] != Piece.EMPTY) {
        //      System.out.printf("EVALUATE column: %s %s\n", row, col);
              signPost = evaluateColumn(board, row + 1, col);
              if (signPost != Piece.DUMMY) {
             //   System.out.println(" ~ FAILED SIGNPOST " + signPost);
                return signPost; // bad spot
              }
            }
          }
        }
      }  
      return Piece.DUMMY;
    }
      /*
        check to the left, if also empty ok
        check to the right, if also empty ok
        check below, if also empty bad.
        keep checking, if we continue finding empty spots, ok
        if we run into another Piece, then we have another problem.
        return offending Piece Type
      */
    private Piece evaluateColumn(Piece[][] board, int row, int col) {
      // check left
      if (col > 0) {
        if (board[row][col - 1] == Piece.EMPTY) {
        //  System.out.printf("columnChecker: %s %s ok\n", row, col - 1);
          return Piece.DUMMY;
        }
      }
      // check right
      if (col < board[0].length - 1) {
        if (board[row][col + 1] == Piece.EMPTY) {
        //  System.out.printf("columnChecker: %s %s ok\n", row, col + 1);
          return Piece.DUMMY;
        }
      }
      // check below
      if (row < board.length - 1) {
        if (board[row + 1][col] == Piece.EMPTY){
         // System.out.printf("columnChecker danger: consecutive empties %s %s\n", row + 1, col);
          for (int nextPlace = row + 1; nextPlace < board.length; nextPlace++) {
         //   System.out.printf("next col: %s %s\n", nextPlace, col);
            if (board[nextPlace][col] != Piece.EMPTY) {
          //     System.out.printf("FAILED columnChecker: consecutive empties %s %s\n", nextPlace, col);
              return Piece.INVALID; // bad spot
            }
          }
        }
      }

      return Piece.DUMMY;
    }

    public static void displayBoard(Piece[][] b){
      System.out.println("hole checker");
      for(Piece[] row : b){
        for(Piece p : row){
          System.out.print(p.named() + " ");
        }
      System.out.println();
    }
  }
}