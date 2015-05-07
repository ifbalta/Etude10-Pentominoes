import java.util.*;
/**
  Special node which indicates the beginning of a pentomino's column.
  Contains pointers to its column, as well as the previous and next items
  in the column list.
  The column list is just a list of Column Nodes associated with Pieces.
  Very confusing property visibility, I know.
*/
public class ColumnNode {
  Piece columnName;
  ColumnNode left;
  ColumnNode right;
  Node columnHead;
  int columnSize = 0;

  // default constructor
  public ColumnNode(){
    // do nothing, just to get compiler to stop complaining.
  }

  public ColumnNode(Piece columnName){
    this.columnName = columnName;
    this.columnSize = 0;
  }

  public ColumnNode(Piece columnName, Node columnHead) {
    this(columnName);
    this.columnHead = columnHead;
    this.columnSize = 1;
  }


  public ColumnNode getRight(){
    return right;
  }

  public void setRight(ColumnNode replacement){
    this.right = replacement;
  }

  public ColumnNode getLeft(){
    return left;
  }

  public void setLeft(ColumnNode replacement){
    this.left = replacement;
  }

  public String toString(){
    return columnName.named();
  }

}