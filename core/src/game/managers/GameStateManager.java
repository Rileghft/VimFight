package game.managers;

import game.gamestates.GameState;

public class GameStateManager {

	//current game state
	private GameState gameState;
	
	public static final int  MENU = 0;
	public static final int PLAY = 1;
	
	public GameStateManager() {
		
	}
	
	public void setState( int state ) {
		if(state == MENU) {
			//switch to menu state
		}
		if(state == PLAY) {
			//switch to play state
		}
	}
	
	public void update(float delta) {
		gameState.update(delta);
	}
	
	public void draw() {
		gameState.draw();
	}
}
