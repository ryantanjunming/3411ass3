package agent;

import java.util.Random;

import utilities.Timer;
import game.Game;
import game.GameIO;

public class Agent {
	
	public static final double INITIAL_TIME = 30.0;
	public static final double TIME_PER_TURN = 2.0;
	
	Game g;
	GameIO io;
	Timer t = new Timer();
	//time in seconds
	double timeLeft = INITIAL_TIME;
	
	public Agent(Game g, GameIO io) {
		this.g = g;
		this.io = io;
		t.start();
	}
	
	public void run() {
		while (!g.isFinished()) {
			while (g.curPiece != Game.X_PIECE);
			int move = getRandomMove();
			io.makeMove(move);
		}
	}
	
	public int getRandomMove() {
		int move = (new Random()).nextInt() % 10 + 1;
		while (g.board[g.curBoard][move] != Game.EMPTY) {
			move = (new Random()).nextInt() % 10 + 1;
		}
		return move;
	}
	
	
	public int getMove() {
		timeLeft += TIME_PER_TURN;
		t.update();
		
		
		timeLeft -= t.getTimeS();
		return 0;
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		GameIO io = new GameIO(g);
		new Agent(g, io);
	}
}
