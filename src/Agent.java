

import java.util.Random;

public class Agent implements Runnable {
	
	public static final double INITIAL_TIME = 30.0;
	public static final double TIME_PER_TURN = 2.0;
	
	Thread thread;
	
	Game g;
	GameIO io;
	Timer t = new Timer();
	//time in seconds
	double timeLeft = INITIAL_TIME;
	
	double BAD_MOVE_VALUE = -1000000;
	double bestMoveValue;
	
	public Agent(Game g, GameIO io) {
		this.g = g;
		this.io = io;
		t.start();
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	public void run() {
		io.run();
		while (!g.isFinished()) {
			while (g.curPiece != Game.X_PIECE);
			int move = getRandomMove();
			g.addPiece(move+1);
			io.makeMove(move+1);
		}
	}
	
	public int getRandomMove() {
		int move = (new Random()).nextInt() % 9;
		while (g.board[g.curBoard][move] != Game.EMPTY) {
			move = (new Random()).nextInt() % 9;
		}
		return move;
	}
	
	public double alphaBetaSearch(double alpha, double beta, int moveNumber, double score) {
		return 0;
	}
	
	public int getMove() {
		timeLeft += TIME_PER_TURN;
		t.update();
		bestMoveValue = BAD_MOVE_VALUE;
		
		for (int move = 0; move < 9; move++) {
			if (g.board[g.curBoard][move] == Game.EMPTY) {
				g.board[g.curBoard][move] = Game.X_PIECE;
				
				
				//double value = alphaBetaSearch(BAD_MOVE_VALUE, -BAD_MOVE_VALUE, move, )
				g.board[g.curBoard][move] = Game.EMPTY;
			}
		}
		
		timeLeft -= t.getTimeS();
		return 0;
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		GameIO io = new GameIO(g, args);
		new Agent(g, io);
	}
}
