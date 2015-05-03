import pentopieces.*;
/**
	Pentomino returns the coordinates of a Pentomino piece.
*/
public class Pentomino{

	PieceIntf pento;
	Piece name;

	public Pentomino(Piece p){
		switch(p){
			case O:
			pento = new PieceO();
			break;
			case P:
			pento = new PieceP();
			break;
			case Q:
			pento = new PieceQ();
			break;
			case R:
			pento = new PieceR();
			break;
			case S:
			pento = new PieceS();
			break;
			case T:
			pento = new PieceT();
			break;
			case U:
			pento = new PieceU();
			break;
			case V:
			pento = new PieceV();
			break;
			case W:
			pento = new PieceW();
			break;
			case X:
			pento = new PieceX();
			break;
			case Y:
			pento = new PieceY();
			break;
			case Z:
			pento = new PieceZ();
			break;
		}
		this.name = p;
	}

	public int[][] placeOf(){
		return pento.place();
	}

	public int[][] rotate(){
		return pento.rotate();
	}

	public Piece pieceName(){
		return name;
	}

	public int getLimit(){
		return pento.getLimit();
	}

	public String toString(){
		return name.named();
	}
}