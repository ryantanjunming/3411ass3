

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
	
	double BAD_MOVE_VALUE = -100000000;
	double bestMoveValue;

	public Agent(Game g, GameIO io) {
		this.g = g;
		this.io = io;
		t.start();
		thread = new Thread(this, "Agent");
		thread.start();
	}
	
	public void run() {
		while (!g.isFinished()) {
			while (g.curPiece != Game.X_PIECE || g.curBoard == -1);
			int move = getRandomMove();
			g.addPiece(move+1);
			io.makeMove(move+1);
		}
	}
	
	public int getRandomMove() {
		t.update();
		int move = (new Random()).nextInt(9);
		while (g.board[g.curBoard][move] != Game.EMPTY) {
			move = (new Random()).nextInt(9);
		} 
		return move;
	}
	
	public double moveScore(int move) {
		
//		int moveBoard[] = g.board[move];
//		
//		
		return 0;
	}
	
	public int getDepthLimit(double timeLimit) {
		return 5;
	}
	
	public double alphaBetaSearch(double alpha, double beta, int prevMove, int depth, int depth_limit, double score, boolean x_move, double timeLimit) {
		
		if (score <= BAD_MOVE_VALUE || score >= -BAD_MOVE_VALUE
				|| depth == depth_limit) return score;
		
		for (int move = 0; move < 9; move++) {
			if (g.board[prevMove][move] != Game.EMPTY) continue;
			g.board[prevMove][move] = (x_move) ? Game.X_PIECE : Game.Y_PIECE;
			double moveScore = moveScore(move);

			double newScore = alphaBetaSearch(alpha, beta, move, depth+1, depth_limit, move+moveScore, (x_move) ? false : true, timeLimit);
			g.board[prevMove][move] = Game.EMPTY;
			if (x_move) alpha = Math.max(alpha, newScore);
			else beta = Math.min(beta, newScore);
			if (alpha >= beta) break;
		}
		
		return (x_move) ? alpha : beta;
	}
	
	public int getMove() {
		
		timeLeft += TIME_PER_TURN;
		t.update();
		bestMoveValue = BAD_MOVE_VALUE;
		int bestMove = -1;
		double timeLimit = timeLeft / 2;
		int depth_limit = getDepthLimit(timeLimit);
		for (int move = 0; move < 9; move++) {
			if (g.board[g.curBoard][move] != Game.EMPTY) continue;
			if (bestMove == -1) bestMove = move;
			g.board[g.curBoard][move] = Game.X_PIECE;
			double score = moveScore(move);
			if (score == BAD_MOVE_VALUE) continue;
			double value = alphaBetaSearch(BAD_MOVE_VALUE, -BAD_MOVE_VALUE, move, 1, depth_limit, score, false, timeLimit);
			g.board[g.curBoard][move] = Game.EMPTY;
			if (value > bestMoveValue) {
				bestMoveValue = value;
				bestMove = move;
			}
		}
		
		timeLeft -= t.getTimeS();
		return bestMove;
	}
	
	
	public static void main(String[] args) {
		Game g = new Game();
		GameIO io = new GameIO(g, args);
		new Agent(g, io);
		io.run();
	}
}
