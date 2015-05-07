public class Node {
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
}