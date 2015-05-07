/**
  Node represents a piece placement.
  It contains the information about one pentomino square.
*/
public class Node {
  /* Piece name */
  Piece pieceType;
  /* Piece location */
  int[] location;
  int x;
  int y;
  Node above;
  Node below;
  Node left;
  Node right;

  public Node (Piece pieceType, int[] location) {
    this.pieceType = pieceType;
    this.location = location;
    this.x = location[0];
    this.y = location[1];
  }
}