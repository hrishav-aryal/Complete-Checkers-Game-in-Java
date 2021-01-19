package checkerss;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GUIcheckers extends JFrame{
	
	ArrayList<Piece> whiteCheckers = new ArrayList();
	ArrayList<Piece> blackCheckers = new ArrayList();
	
	JButton[][] board;
	
	int turnCounter = 0;
	boolean isWhiteTurn;
	
	Piece activePiece = null;
	
	public void start() {
		
		initializeBoard();
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				JButton btn = board[i][j];
				
				final int r = i;
				final int c = j;
				
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						mouseClicked(r, c);
						
					}
		        });
				
			}
		}	
	}
	
	
	public void mouseClicked(int x, int y) {
		
		isWhiteTurn = true;
		if(isWhiteTurn) {
			if(turnCounter%2==1) isWhiteTurn = false;
		}
		
		Piece clickedPiece = this.getPiece(x, y);
		
		if(activePiece == null && clickedPiece != null &&
				((isWhiteTurn && clickedPiece.isWhite()) || (!isWhiteTurn && !clickedPiece.isWhite()))) {
			activePiece = clickedPiece;
			setBtnActive(board[activePiece.getX()][activePiece.getY()]);
			
		}
		else if(activePiece != null && clickedPiece != null &&clickedPiece.equals(activePiece)) {
			setBtnInactive(board[activePiece.getX()][activePiece.getY()], activePiece);
			activePiece = null;
		}
		else if(activePiece != null && activePiece.canMove(x, y) && 
				((isWhiteTurn && activePiece.isWhite()) || ((!isWhiteTurn && !activePiece.isWhite())))) {
			
			int tempRow = activePiece.getX();
			int tempCol = activePiece.getY();
			setBtnInactive(board[tempRow][tempCol], activePiece);
			
			//check if piece turned into a king
			if(activePiece.isWhite()) {
				if(x==7) {
					activePiece.setKing(true);
					//setnewIcon for a king
				}
			}else {
				if(x==0) {
					activePiece.setKing(true);
					//setnewIcon for a king
				}
			}
			
			boolean pieceKilled = false;
			//remove if a piece is killed
			if(Math.abs(tempRow - x) == 2 ) {
				// a piece is killed
				pieceKilled = true;
				if(isWhiteTurn) {
					blackCheckers.remove(getPieceKilled(tempRow, tempCol, x, y));
				}else {
					whiteCheckers.remove(getPieceKilled(tempRow, tempCol, x, y));
				}
			}
			
			activePiece.setX(x);
			activePiece.setY(y);
			
			//check if another piece can be killed
			if(pieceKilled) {
				if(!canAnotherPieceBeKilled()) {
					turnCounter++;
				}
			}else {
				turnCounter++;
			}
			
			
			activePiece = null;

			drawNewBoard(tempRow, tempCol, x, y);
		}
	}
	
	public Piece getPieceKilled(int r, int c, int tr, int tc) {
		
		int row, col;
		if(tr>r) row = r+1;
		else row = r- 1;
	
		if(tc>c) col = c+1;
		else col = c-1;

		
		board[row][col].setIcon(null);
		
		return this.getPiece(row, col);
	}
	
	
	private void drawNewBoard(int tempRow, int tempCol, int x, int y) {
		
		if(isWhiteTurn) {
			board[x][y].setIcon(setWhiteIcon());
			board[tempRow][tempCol].setIcon(null);
		}else {
			board[x][y].setIcon(setBlackIcon());
			board[tempRow][tempCol].setIcon(null);
		}
		
		checkWin();
		
	}
	
	public int checkWin() {
		//code here
		if(blackCheckers.isEmpty()) {
			JOptionPane.showMessageDialog(null, "White is the winner");
			return 0;
		}
		if(whiteCheckers.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Black is the winner");
			return 0;
		}
		
		
		boolean blackWin = true, whiteWin = true;
		for(Piece p: blackCheckers) {
			 if(!p.getLegalMoves().isEmpty()) {
				 whiteWin = false;
				 break;
			 }
		 }
			 
		for(Piece p: whiteCheckers) {
			 if(!p.getLegalMoves().isEmpty()) {
				 blackWin = false;
				 break;
			 }
		}
		
		if(blackWin && whiteWin) {
			JOptionPane.showMessageDialog(null, "Game Draw");
			return 0;
		}
		
		if(whiteWin) {
			JOptionPane.showMessageDialog(null, "White is the winner");
			return 0;
		}
		if(blackWin) {
			JOptionPane.showMessageDialog(null, "Black is the winner");
			return 0;
		}
		return 0;
	}
	
	
	public boolean canAnotherPieceBeKilled() {
		//logic to check if another piece can really be killed
		int x = activePiece.getX();
		int y = activePiece.getY();
		
		int row, col;
		
		row = x+2; col = y+2;
		if(row>=0 && row<8 && col>=0 && col<8) {
			if(activePiece.canMove(row, col)) {
				return true;
			}
		}
		
		row = x-2; col = y+2;
		if(row>=0 && row<8 && col>=0 && col<8) {
			if(activePiece.canMove(row, col)) {
				return true;
			}
		}
		
		row = x+2; col = y-2;
		if(row>=0 && row<8 && col>=0 && col<8) {
			if(activePiece.canMove(row, col)) {
				return true;
			}
		}
		
		row = x-2; col = y-2;
		if(row>=0 && row<8 && col>=0 && col<8) {
			if(activePiece.canMove(row, col)) {
				return true;
			}
		}
			
		
		return false;
	}


	public void initializeBoard() {
		
		board = new JButton[8][8];
		
		Color black = Color.black;
		Color white = Color.white;
		
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(j%2==0) {
					board[i][j] = new JButton();
					board[i][j].setBackground(white);
					add(board[i][j]);
				}else {
					board[i][j] = new JButton();
					board[i][j].setBackground(black);
					add(board[i][j]);
				}
			}
			
			if(i%2==0) {
				black = Color.white;
				white = Color.black;
			}else {
				black = Color.black;
				white = Color.white;
			}
		}
		
		
		//placing checkers pieces
		for(int i=0; i<3; i++) {
			for(int j=0; j<8; j++) {
				
				if(!(i%2==0 && j%2==0) && !(i%2!=0 && j%2!=0)) {
					whiteCheckers.add(new Piece(i, j, true, false, GUIcheckers.this));
					JButton btn = board[i][j];
					btn.setIcon(setWhiteIcon());
				}
			}
		}
		
		for(int i=5; i<8; i++) {
			for(int j=0; j<8; j++) {
				
				if(!(i%2==0 && j%2==0) && !(i%2!=0 && j%2!=0)) {
					blackCheckers.add(new Piece(i, j, false, false, GUIcheckers.this));
					JButton btn = board[i][j];
					btn.setIcon(setBlackIcon());
				}
			}
		}
		
		
		this.setTitle("Checkers Board");
		this.setLayout(new GridLayout(8,8));
		this.setSize(700,700);
		this.setVisible(true);
		
	}
	
	
	public Piece getPiece(int x, int y) {
		
		for(Piece p: whiteCheckers) {
			if(p.getX()==x && p.getY()==y) {
				return p;
			}
		}
		
		for(Piece p: blackCheckers) {
			if(p.getX()==x && p.getY()==y) {
				return p;
			}
		}
		return null;
	}
	
	public Icon setWhiteIcon() {
		ImageIcon img = new ImageIcon(getClass().getResource("/Images/white.jpg"));
		Image im = img.getImage();
		Image newimg = im.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH );
		Icon icon = new ImageIcon(newimg);
		
		return icon;
	}
	
	public Icon setBlackIcon() {
		ImageIcon img = new ImageIcon(getClass().getResource("/Images/black.jpg"));
		Image im = img.getImage();
		Image newimg = im.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH );
		Icon icon = new ImageIcon(newimg);
		
		return icon;
	}
	
	public void setBtnActive(JButton btn) {
		btn.setBackground(Color.yellow);
	}
	
	public void setBtnInactive(JButton btn, Piece p) {
		if((p.getX()%2==0 && p.getY()%2 == 0) || (p.getX()%2!=0 && p.getY()%2 != 0)) {
			btn.setBackground(Color.white);
		}else {
			btn.setBackground(Color.black);
		}
		
	}

}
