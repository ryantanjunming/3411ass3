
import java.util.ArrayList;
import java.util.Random;

/**
 *  The agent uses an iterative deepening alpha-beta search to find the optimal
 *  move at each state. Alpha-beta search is ideal for a search space with a low
 *  branching factor, and in many cases, it is able to fully search the space and
 *  find the optimal move, and as such, we chose to use it.
 *  
 *  One of the main challenges was to ensure the agent searched for an appropriate
 *  amount of time at every point in the game. It is easy enough to check, on average,
 *  how long the agent takes to search to each depth, and hardcode these values into
 *  the agent, but in this game, as the branching factor is constantly decreasing as
 *  the game progresses, this strategy is not the most optimal. We chose to use
 *  an iterative deepening strategy, where the time to search to a specific depth
 *  is calculated while it's running, and then by assuming the worst-case time taken
 *  for the next depth (9 times the previous one), we can ensure it searches to the 
 *  maximum possible depth for the time allocated (typically half the current time
 *  on the clock).
 *
 *  Our scoring function for each move is perhaps the weakest part of the agent.
 *  A possible approach for solving this would be to use a neural network to learn
 *  the optimal move through supervised learning, but as the game is not widespread,
 *  and we are not experts at the game, we were not able to implement this. If implemented,
 *  however, it could be used as both as an evaluation function for alpha-beta search
 *  (assuming the neural network is not very complex, and evaluation of each node is
 *  computationally cheap), or as the decision maker in the early game (if it is
 *  computationally expensive), which is where this agent struggles the most.
 *
 */ 
public class Agent implements Runnable {
	
	public static final double INITIAL_TIME = 20.0;
	public static final double TIME_PER_TURN = 2.0;
	
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
            //Wait until it has to make a move
			while (g.curPiece != Game.X_PIECE || g.curBoard == -1);
			int move = getMove();
			g.addPiece(move+1);
			io.makeMove(move+1);
		}
		System.exit(0);
	}
	
    //UNUSED - Simply for testing
	public int getRandomMove() {
		t.update();
		int move = (new Random()).nextInt(9);
		while (g.board[g.curBoard][move] != Game.EMPTY) {
			move = (new Random()).nextInt(9);
		} 
		return move;
	}
	
    //Given a board state, and a move, determines the value of the move.
    //Note that if a move has value V for our agent, it would have
    //value -V for the opponent.
    //A winning move will have a value equal to WIN_VALUE, or -WIN_VALUE, depending
    //on whose move it is..
	public double moveScore(int prevMove, int move, boolean x_move) {
		int player = (x_move) ? 1 : -1;
		double score = 0.0;
		if(ScoreChecker.gonnaWin(g.board[prevMove], (x_move) ? Game.X_PIECE : Game.Y_PIECE)){
			return player * WIN_VALUE;
		} else{
			score = player * ScoreChecker.preferenceScore(move) * 
					ScoreChecker.leadsToBetterPositionScore(g, prevMove, move, x_move);
	    }
		
		return score;
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
	
    //This is used to assign a certain amount of time to search for the current move,
    //based on the game state, and time left on the clock.
	private double getTimeLimit() {
		if (g.numMovesMade() < 10) {
			return 1.0;
		}
		return timeLeft/2;
	}
	
    //iterative deepening alpha-beta search.
	public int getMove() {
		
		timeLeft += TIME_PER_TURN;
		t.update();
		bestMoveValue = BAD_MOVE_VALUE;
		int bestMove = -1;
		double timeLimit = getTimeLimit();
		Timer depthTimer = new Timer();
		depthTimer.start();
		double timeSoFar = 0;

        //predictedTime holds the expected time taken to search to depth_limit.
		double predictedTime = 0;
		int depth_limit = 1;
		
		//for an index i, representing a move number, gonnaLose[i] contains the depth
		//at which move i loses at.
		int[] gonnaLose = new int[9];
		for (int i = 0; i < 9; i++) gonnaLose[i] = 0;

        //Iterate until the predictedTime, added to the time taken so far is greater
        //than the time allocated for this move.
        //We can also break early if we find a winning move.
		while (timeSoFar + predictedTime < timeLimit && bestMoveValue < -BAD_MOVE_VALUE) {
            //depthTimer is used to hold the time taken to search to depth_limit.
			depthTimer.update();
			bestMove = -1;
			bestMoveValue = BAD_MOVE_VALUE;
			for (int move = 0; move < 9; move++) {
                //If the move is invalid, or it has been found to be a losing move at
                //a previous depth, we don't need to search it.
				if (g.board[g.curBoard][move] != Game.EMPTY || gonnaLose[move] != 0) continue;
				if (bestMove == -1) bestMove = move;
				g.board[g.curBoard][move] = Game.X_PIECE;
				double score = moveScore(g.curBoard, move, true);
				
				double value = alphaBetaSearch(BAD_MOVE_VALUE, -BAD_MOVE_VALUE, 
						move, 1, depth_limit, score, false);
				if (value <= BAD_MOVE_VALUE) {
                    //if the move is a losing move, store the depth at which it causes
                    //the agent to lose.
					gonnaLose[move] = depth_limit;
				}
				g.board[g.curBoard][move] = Game.EMPTY;
				if (value > bestMoveValue) {
					bestMoveValue = value;
					bestMove = move;
				}
			}
			if (bestMove == -1) break;
			double depthTime = depthTimer.getTimeS();
			predictedTime = depthTime*9;
			depth_limit++;
			timeSoFar += depthTime;
		}
        //This handles the case where no move will result in a win, assuming the opponent
        //plays optimally. 
        //When this happens, choose the move which causes us the lose at the greatest
        //depth.
		if (bestMove == -1) {
			int loss_depth = 0;
			for (int i = 0; i < 9; i++) {
				if (gonnaLose[i] > loss_depth) {
					bestMove = i;
					loss_depth = gonnaLose[i];
				}
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
