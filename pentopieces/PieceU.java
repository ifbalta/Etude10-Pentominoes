package pentopieces;
public class PieceU implements PieceIntf{
	int pos = 0;
	int limit = 4;
	
	/* Places the U-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the U-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between U-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] e1 = {{0,0}, {0,2}, {1,0}, {1,1}, {1,2}};
				return e1;
			case 1:
				int[][] e2 = {{0,0}, {0,2}, {0,1}, {1,0}, {1,2}};
				return e2;
			case 2:
				int[][] e3 = {{0,0}, {0,1}, {1,1}, {2,0}, {2,1}};
				return e3;
			case 3:
				int[][] e4 = {{0,0}, {1,0}, {2,0}, {0,1}, {2,1}};
				return e4;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}