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
	
	private final String title = "Game Over";
	private final String hint = "Press Enter to Go Back to Menu";
	private BitmapFont titleFont;
	private BitmapFont font;
	private SpriteBatch sb;
	
	protected GameOverState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
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
		titleFont.draw(sb, title, (VimFight.WIDTH - 50*title.length())/2, 600);
		font.draw(sb, hint, (VimFight.WIDTH - 50*title.length())/2-50, 300);
		sb.end();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
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
