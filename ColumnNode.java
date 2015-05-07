import java.util.*;
public class ColumnNode {
  Piece columnName;
  Node columnHead;

  public ColumnNode(Piece columnName){
    this.columnName = columnName;
  }

  public ColumnNode(Piece columnName, Node columnHead) {
    this(columnName);
    this.columnHead = columnHead;
  }
}