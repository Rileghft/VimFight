package game.gamestates;

import game.managers.GameStateManager;

public class PlayState extends GameState {

	public PlayState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		System.out.println("Play state update");
	}

	@Override
	public void draw() {
		System.out.println("Play state draw");
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
