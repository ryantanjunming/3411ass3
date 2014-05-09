package agent;

import game.Game;
import game.GameIO;

public class Agent {
	
	public Agent(Game g) {
		
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		new GameIO(g);
		Agent a = new Agent(g);
	}
}
