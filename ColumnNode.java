import java.util.*;
/**
  Special node which indicates the beginning of a pentomino's column.
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