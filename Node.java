/**
  Node represents a piece placement.
  It contains the information about one pentomino square.
  Contains pointers to its row (left and right) and column (up and down)
*/
public class Node {
  /* Associated column */
  ColumnNode nodeColumn;
  /* Piece name */
  Piece pieceType;
  /* Piece location */
  int[] location;
  int x;
  int y;
  Node up;
  Node down;
  Node left;
  Node right;

  public Node (Piece pieceType, int[] location) {
    this.pieceType = pieceType;
    this.location = location;
    this.x = location[0];
    this.y = location[1];
  }

  public int[] locationOf(){
    return location;
  }
}