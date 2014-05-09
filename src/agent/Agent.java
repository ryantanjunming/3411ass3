package agent;

import java.util.Random;

import game.Game;
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
			int move = getMove();
			io.makeMove(move);
		}
	}
	
	public int getMove() {
		return (new Random()).nextInt() % 10 + 1;
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		GameIO io = new GameIO(g, args);
		new Agent(g, io);
	}
}
