package checkerss;

import java.util.ArrayList;

public class Piece {
	
	private int x,y;
	private boolean isWhite;
	private GUIcheckers board;
	private boolean isKing;
	
	public Piece(int x, int y, boolean isWhite, boolean isKing, GUIcheckers board) {
		super();
		this.x = x;
		this.y = y;
		this.isWhite = isWhite;
		this.board = board;
		this.isKing = isKing;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	public GUIcheckers getBoard() {
		return board;
	}

	public void setBoard(GUIcheckers board) {
		this.board = board;
	}

	public boolean isKing() {
		return isKing;
	}

	public void setKing(boolean isKing) {
		this.isKing = isKing;
	}	
	
	
	public boolean canMove(int x, int y) {
		
		Piece p = board.getPiece(x, y);
		
		//cannot move into a square that already has a piece
		if(p != null) {
			return false;
		}
		
		// if not king, piece can only move towards opponent side
		if(!this.isKing) {
			if(this.isWhite && this.getX()>x) {
				return false;
			}
			if(!this.isWhite && this.getX()<x) {
				return false;
			}
		}
		
		//can only move diagonal one square (2 square only if killing a piece)
		if(this.getX() == x || this.getY() == y) {
			return false;
		}
		
		if(this.getX() != x && this.getY() != y) {
			if(Math.abs(this.getX()-x) != Math.abs(this.getY()-y)) {
				return false;
			}
			
			if(Math.abs(this.getX()-x)>=2 || Math.abs(this.getY()-y)>=2) {
				
				if(Math.abs(this.getX()-x)==2 && Math.abs(this.getY()-y)==2) {
					//special case for 2 square moves
					
					int row, col;
					if(x>this.getX()) row = this.getX()+1;
					else row = this.getX()- 1;
				
					if(y>this.getY()) col = this.getY()+1;
					else col = this.getY()-1;
					
					Piece piece = board.getPiece(row, col);
					if(piece == null) {
						return false;
					}else {
						if(this.isWhite() && !piece.isWhite()) {
							return true;
						}else if(!this.isWhite() && piece.isWhite()){
							return true;
						}
					}					
				}
				
				return false;
			}
		}
		
		return true;
	}
	
	
	public ArrayList<Pair> getLegalMoves(){
		ArrayList<Pair> moves = new ArrayList();
		
//		for(int i=1; i<3; i++) {
//			for(int j=1; j<3; j++) {
//				if(this.canMove(this.getX()+i, this.getY()+j)) {
//					moves.add(new Pair(this.getX()+i,this.getY()+j));
//				}
//				if(this.canMove(this.getX()-i, this.getY()+j)) {
//					moves.add(new Pair(this.getX()-i,this.getY()+j));
//				}
//				if(this.canMove(this.getX()+i, this.getY()-j)) {
//					moves.add(new Pair(this.getX()+i,this.getY()-j));
//				}
//				if(this.canMove(this.getX()-i, this.getY()-j)) {
//					moves.add(new Pair(this.getX()-i,this.getY()-j));
//				}
//			}
//		}
		
		for(int i=1; i<8; i++) {
			for(int j=1; j<8; j++) {
				int row = this.getX()+i, col = this.getY()+j;
				if(row>=0 && row<8 && col>=0 && col<8 && this.canMove(row, col)) {
					moves.add(new Pair(row, col));
				}
				
				row = this.getX()-i; col = this.getY()+j;
				if(row>=0 && row<8 && col>=0 && col<8 && this.canMove(row, col)) {
					moves.add(new Pair(row, col));
				}
				
				row = this.getX()+i; col = this.getY()-j;
				if(row>=0 && row<8 && col>=0 && col<8 && this.canMove(row, col)) {
					moves.add(new Pair(row, col));
				}
				
				row = this.getX()-i; col = this.getY()-j;
				if(row>=0 && row<8 && col>=0 && col<8 && this.canMove(row, col)) {
					moves.add(new Pair(row, col));
				}
				
			}
		}
		
		return moves;
	}

}
