package game.gamestates;

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

	private final String title = "Survive Time %d Seconds";
	private final String hint = "Press SPACE to Go Back to Menu";
	private BitmapFont titleFont;
	private BitmapFont font;
	private SpriteBatch sb;
	private char[] recordName = {'j', 'k', 'h', 'l', 'w', 'b', '0', '$', 'f', 'F'};
	private int[] recordNum;
	private long surviveTime_sec;

	protected GameOverState(GameStateManager gsm, int[] statistics, long surviveTime) {
		super(gsm);
		//statistics
		//a0, 1, 2, 3, 4, 5, 6, 7, 8, 9
		// j, k, h, l, w, b, 0, $, f, F
		recordNum = statistics;
		surviveTime_sec = surviveTime;
	}

	@Override
	public void init() {
		sb = new SpriteBatch();

		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/SourceCodePro-Regular.ttf")
				);
		FreeTypeFontParameter titleParameter = new FreeTypeFontParameter();
		titleParameter.size = 50;
		titleFont = gen.generateFont(titleParameter);
		titleFont.setColor(Color.WHITE);

		FreeTypeFontParameter itemParameter = new FreeTypeFontParameter();
		itemParameter.size = 30;
		font = gen.generateFont(itemParameter);
		font.setColor(Color.WHITE);

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
		//draw background
		sb.draw(gsm.backGround,0,0);
		sb.end();

		sb.begin();
		titleFont.draw(sb, String.format(title, surviveTime_sec), (VimFight.WIDTH - 50* 13)/2, 600);
		font.draw(sb, hint, (VimFight.WIDTH - 50* 9)/2-50, 120);
		titleFont.draw(sb, "Score: "+gsm.getScore(), (VimFight.WIDTH - 50* 5)/2-30, 220);

		//draw record
		drawRecord();
		sb.end();
	}

	private void drawRecord(){
		for(int i = 0 ; i < 5 ; i++){
			font.draw(sb, recordName[2*i]+": "+recordNum[2*i], (VimFight.WIDTH - 50* 8)/2, 500-50*i);
			font.draw(sb, recordName[2*i+1]+": "+recordNum[2*i+1], (VimFight.WIDTH - 50* 8)/2+280, 500-50*i);
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
