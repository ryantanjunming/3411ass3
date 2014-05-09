package game;

public class Game {
	
	public Board board = new Board();
	public int curBoard = -1;
	public int curPiece = Board.X_PIECE;
	
	public void setCurBoard(int curBoard) {
		this.curBoard = curBoard;
	}
	
	public void setCurPiece(int piece) {
		this.curPiece = piece;
	}
	
	public void addPiece(int pos) {
		board.board[curBoard][pos] = this.curPiece;
		curPiece ^= 1;
	}
	
	public int getCurBoard() {
		return this.curBoard;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public int getBoardPiece(int board, int pos) {
		return this.board.board[board][pos];
	}
	
	public class Board {
		public static final int X_PIECE = 0;
		public static final int Y_PIECE = 1;
		public static final int EMPTY = 2;
		
		public int[][] board = new int[9][9];
	}
}
