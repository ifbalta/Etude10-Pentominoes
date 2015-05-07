import java.util.*;
/**
  Special node which indicates the beginning of a pentomino's column.
  Contains pointers to its column, as well as the previous and next items
  in the column list.
  The column list is just a list of Column Nodes associated with Pieces.
*/
public class ColumnNode {
  Piece columnName;
  ColumnNode left;
  ColumnNode right;
  Node columnHead;
  int columnSize;

  public ColumnNode(Piece columnName){
    this.columnName = columnName;
    this.columnSize = 0;
  }

  public ColumnNode(Piece columnName, Node columnHead) {
    this(columnName);
    this.columnHead = columnHead;
    this.columnSize = 1;
  }

  public int size() {
    if (columnHead == null) return 0;
    int size = 1;
    Node curr = columnHead;
    while (curr.below != null) {
      size++;
      curr = curr.below;
    }
    return size;
  }
}