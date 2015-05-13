/**
  Application class that should work for Michael's input.
*/
  import java.util.*;
public class BoardApp {
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    EfficientBoard board;
    String currentLine;
    ArrayList<String> puzzleStringList = new ArrayList<>();
    while (input.hasNextLine()) {
      currentLine = input.nextLine();
      if(currentLine.equals("")) {
        // make a new board instance
        board = new EfficientBoard(puzzleStringList);
        board.solveThisPuzzle();
        puzzleStringList.clear();
      } else {
        puzzleStringList.add(currentLine);
      }
    }
    if (!puzzleStringList.isEmpty()) {
      board = new EfficientBoard(puzzleStringList);
      board.solveThisPuzzle();
    }
  }
}