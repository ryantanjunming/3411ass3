package agent;

import java.util.Random;

import game.Game;
import game.Game.Board;
import game.GameIO;

public class Agent {
	
	Game g;
	GameIO io;
	
	public Agent(Game g, GameIO io) {
		this.g = g;
		this.io = io;
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
	
	public static void main(String[] args) {
		Game g = new Game();
		GameIO io = new GameIO(g);
		new Agent(g, io);
	}
}
