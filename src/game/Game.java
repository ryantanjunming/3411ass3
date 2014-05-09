package game;

public class Game {
	
	public Board board;
	public int curBoard;
	public int curPiece;
	public boolean isFinished;
	
	public void init() {
		for (int i = 0; i < 9; i++) 
			for (int j = 0; j < 9; j++)
				board.board[i][j] = Board.EMPTY;
		curBoard = -1;
		curPiece = Board.X_PIECE;
		isFinished = false;
	}
	
	public void setToFinished() {
		isFinished = true;
	}
	
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
	
	public boolean isFinished() {
		return this.isFinished;
	}
	
	public class Board {
		public static final int X_PIECE = 0;
		public static final int Y_PIECE = 1;
		public static final int EMPTY = 2;
		
		public int[][] board = new int[9][9];
	}
}
