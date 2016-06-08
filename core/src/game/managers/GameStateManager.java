package game.managers;

import java.util.ArrayList;

import game.gamestates.GameState;
import game.gamestates.PlayState;

public class GameStateManager {

	//current game state
	private GameState gameState;
	private int HEIGHT;
	private int WIDTH;
	
	public static final int  MENU = 0;
	public static final int PLAY = 1;
	
	public GameStateManager() {
		this.setState(PLAY);
	}
	
	public void setState( int state ) {
		if(gameState != null) gameState.dispose();
		if(state == MENU) {
			//switch to menu state
		}
		if(state == PLAY) {
			gameState = new PlayState(this);
		}
	}
	
	public void update(float delta) {
		gameState.update(delta);
	}
	
	public void draw() {
		gameState.draw();
	}
}
