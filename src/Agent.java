import java.util.Random;

public class Agent implements Runnable {
	
	public static final double INITIAL_TIME = 20.0;
	public static final double TIME_PER_TURN = 2.0;
	public int printSearchLimit = 5;
	
	Thread thread;
	
	Game g;
	GameIO io;
	Timer t = new Timer();
	//time in seconds
	double timeLeft = INITIAL_TIME;
	double BAD_MOVE_VALUE = -10000000;
	double WIN_VALUE = 1000000000;
	double bestMoveValue;
	int[] retval = new int[2];

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
			int move = getMove();
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
	
	public double moveScore(int prevMove, int move, boolean x_move) {
		int player = (x_move) ? 1 : -1;
		double score = 0.0;
		if(ScoreChecker.gonnaWin(g.board[prevMove], (x_move) ? Game.X_PIECE : Game.Y_PIECE)){
			return player * WIN_VALUE; //win move
		} else {
			score = (ScoreChecker.preferenceScore(move) * 
					ScoreChecker.leadsToBetterPositionScore(g, prevMove, move, x_move)) * player;
		}
		
		return score;
	}
	
	public int getDepthLimit(double timeLimit) {
		return 11;
	}
	
	public double alphaBetaSearch(double alpha, double beta, int prevMove, int depth, int depth_limit, double score, boolean x_move) {
		
		//if the previous move was a winning move, or the depth limit is
		//reached, simply return.
		if (score <= BAD_MOVE_VALUE || score >= -BAD_MOVE_VALUE
				|| depth == depth_limit) return score;
		
		for (int move = 0; move < 9; move++) {
			if (g.board[prevMove][move] != Game.EMPTY) continue;
			
			g.board[prevMove][move] = (x_move) ? Game.X_PIECE : Game.Y_PIECE;
			double moveScore = moveScore(prevMove, move, x_move);
			
			double newScore = alphaBetaSearch(alpha, beta, move, depth+1, 
					depth_limit, score+moveScore, (x_move) ? false : true);
			
			g.board[prevMove][move] = Game.EMPTY;
			if (x_move) {
				alpha = Math.max(alpha, newScore);
				if (alpha >= beta) break;
			}
			else {
				beta = Math.min(beta, newScore);
				if (beta <= alpha) break;
			}
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
			double score = moveScore(g.curBoard, move, true);
			
			double value = alphaBetaSearch(BAD_MOVE_VALUE, -BAD_MOVE_VALUE, 
					move, 1, depth_limit, score, false);
			
			g.board[g.curBoard][move] = Game.EMPTY;
			if (value > bestMoveValue) {
				bestMoveValue = value;
				bestMove = move;
			}
		}
//		if(printSearchLimit > 0){
//			System.out.println("Search took: " + t.getTimeS());
//			printSearchLimit--;
//		}
		
		
		timeLeft -= t.getTimeS();
		System.out.println(bestMoveValue);
		return bestMove;
	}
	
	
	public static void main(String[] args) {
		Game g = new Game();
		GameIO io = new GameIO(g, args);
		new Agent(g, io);
		io.run();
	}
}

