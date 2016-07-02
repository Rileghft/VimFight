package game.managers;

import java.util.Stack;

import com.badlogic.gdx.utils.Array;

import game.Object.BGM;
import game.gamestates.GameState;

public class GameStateManager {
	private int score = 0;
	private Stack<GameState> states;
	
	private Array<BGM> bgmGroup;
	
	public GameStateManager() {
		states = new Stack<GameState>();
		bgmGroup = new Array<BGM>();
		bgmGroup.add(new BGM("BGM/Royalty Free Game Music - 8 Bit Explore by HeatleyBros.mp3"));
		bgmGroup.add(new BGM("BGM/Royalty Free Game Music - 8 Bit Hideout By HeatleyBros.mp3"));
		bgmGroup.add(new BGM("BGM/Royalty Free Music - 8 Bit Adventure by HeatleyBros.mp3"));
		bgmGroup.add(new BGM("BGM/Royalty Free Music - 8 Bit World by HeatleyBros.mp3"));
		this.score = 0;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public BGM getbgm(int index) {
		return bgmGroup.get(index-1);
	}
	
	public void push( GameState state ) {
		states.push(state);
		states.peek().startBGM();
	}
	
	public void pop() {
		states.pop();
		states.peek().startBGM();
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
