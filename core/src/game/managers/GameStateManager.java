package game.managers;

import java.util.ArrayList;
import java.util.Stack;

import game.gamestates.GameState;
import game.gamestates.PlayState;

public class GameStateManager {

	private Stack<GameState> states;
	
	public GameStateManager() {
		states = new Stack<GameState>();
	}
	
	public void push( GameState state ) {
		states.push(state);
	}
	
	public void pop() {
		states.pop();
	}
	
	public void setState( GameState state ) {
		states.pop();
		states.push(state);
	}
	
	public void update(float delta) {
		states.peek().update(delta);
	}
	
	public void draw() {
		states.peek().draw();
	}
}
