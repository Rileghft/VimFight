package game.gamestates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import game.managers.GameStateManager;
import game.vim.VimFight;

public class GameOverState extends GameState{

	private final String title = "Game Over";
	private final String hint = "Press SPACE to Go Back to Menu";
	private BitmapFont titleFont;
	private BitmapFont font;
	private SpriteBatch sb;
	private char[] recordName = {'j', 'k', 'h', 'l', 'w', 'b', '0', '$', 'f', 'F'};
	private int[] recordNum;

	protected GameOverState(GameStateManager gsm, int[] statistics) {
		super(gsm);
		//statistics
		//a0, 1, 2, 3, 4, 5, 6, 7, 8, 9
		// j, k, h, l, w, b, 0, $, f, F
		recordNum = statistics;
	}

	@Override
	public void init() {
		sb = new SpriteBatch();

		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/SourceCodePro-Regular.ttf")
				);
		FreeTypeFontParameter titleParameter = new FreeTypeFontParameter();
		titleParameter.size = 80;
		titleFont = gen.generateFont(titleParameter);
		titleFont.setColor(Color.BLACK);

		FreeTypeFontParameter itemParameter = new FreeTypeFontParameter();
		itemParameter.size = 30;
		font = gen.generateFont(itemParameter);
		font.setColor(Color.BLACK);

	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		handleInput();
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		sb.setProjectionMatrix(VimFight.cam.combined);
		sb.begin();
		titleFont.draw(sb, title, (VimFight.WIDTH - 50*title.length())/2, 650);
		font.draw(sb, hint, (VimFight.WIDTH - 50*title.length())/2-50, 120);

		//draw record
		drawRecord();
		sb.end();
	}

	private void drawRecord(){
		for(int i = 0 ; i < 5 ; i++){
			font.draw(sb, recordName[2*i]+": "+recordNum[2*i], (VimFight.WIDTH - 50*title.length())/2, 500-50*i);
			font.draw(sb, recordName[2*i+1]+": "+recordNum[2*i+1], (VimFight.WIDTH - 50*title.length())/2+280, 500-50*i);
		}
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			gsm.pop();
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startBGM() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopBGM() {
		// TODO Auto-generated method stub

	}

}
