

public class Game {
	
	public static final int X_PIECE = 0;
	public static final int Y_PIECE = 1;
	public static final int EMPTY = 2;
	
	public int[][] board = new int[9][9];
	public int curBoard = -1;
	public int curPiece;
	public boolean isFinished;
	
	public void init() {
		for (int i = 0; i < 9; i++) 
			for (int j = 0; j < 9; j++)
				board[i][j] = EMPTY;
		curBoard = -1;
		curPiece = X_PIECE;
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
	
	public void changeCurPiece() {
		curPiece ^= 1;
	}
	
	public void addPiece(int pos) {
		board[curBoard][pos-1] = this.curPiece;
		curBoard = pos-1;
		curPiece ^= 1;
	}
	
	public int getCurBoard() {
		return this.curBoard;
	}
	
	public int[][] getBoard() {
		return this.board;
	}
	
	public int getBoardPiece(int board, int pos) {
		return this.board[board][pos];
	}
	
	
	public boolean isFinished() {
		return this.isFinished;
	}
	
}
