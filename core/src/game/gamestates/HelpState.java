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

public class HelpState extends GameState {

	private PlayState playState;
	private SpriteBatch sb;
	private final String hint = "Press ESCAPE to Go Back to Game";
	private BitmapFont font;
	
	protected HelpState(GameStateManager gsm, PlayState playState) {
		super(gsm);
		// TODO Auto-generated constructor stub
		this.playState = playState;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		sb = new SpriteBatch();
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/SourceCodePro-Regular.ttf")
				);

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
		font.draw(sb, hint, (VimFight.WIDTH - 15*hint.length())/2, 100);
		System.out.println("hint.length() = "+hint.length());
		sb.end();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			playState.stage.setKeyboardFocus(playState.player);
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
