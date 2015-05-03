package pentopieces;
public class PieceP implements PieceIntf{
	int pos = 0;
	int limit = 8;
	
	/* Places the P-pentomino */
	public int[][] place(){
		return coords(pos);
	}
	/* Rotates the P-pentomino */
	public int[][] rotate(){
		pos = (pos == (limit - 1))? 1:pos + 1;
		return coords(pos);
	}

	/* Switches between P-pentomino rotations */
	public int[][] coords(int i){
		switch(i){
			case 0:
				int[][] l1 = {{0,0},{0,1},{1,0},{1,1},{2,0}};
				return l1;
			case 1:
				int[][] l2 = {{0,0},{0,1},{1,0},{1,1},{2,1}};
				return l2;
			case 2:
				int[][] l3 = {{0,0},{0,1},{0,2},{1,0},{1,1}};
				return l3;
			case 3:
				int[][] l4 = {{0,0},{0,1},{1,2},{1,0},{1,1}};
				return l4;
			case 4:
				int[][] l5 = {{0,0},{1,0},{2,0},{1,1},{2,1}};
				return l5;
			case 5:
				int[][] l6 = {{1,0},{2,0},{0,1},{1,1},{2,1}};
				return l6;
			case 6:
				int[][] l7 = {{0,1},{0,2},{1,0},{1,1},{1,2}};
				return l7;
			case 7:
				int[][] l8 = {{0,1},{0,2},{0,0},{1,1},{1,2}};
				return l8;
		}
		return null;
	}

	public int getLimit(){
		return limit;
	}
}