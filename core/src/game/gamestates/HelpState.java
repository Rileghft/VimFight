package game.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
	private Texture hint1, hint2, left, right;
	private float hintBegLeftX = 105;
	private float hintBegUpY = 170;
	private Texture currentHint;
	private int selected ;
	

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
		font.setColor(Color.YELLOW);
		
		hint1 = new Texture(Gdx.files.internal("images/HowToPlayP1.png"));
		hint2 = new Texture(Gdx.files.internal("images/HowToPlayP2.png"));
		left = new Texture(Gdx.files.internal("images/left.png"));
		right = new Texture(Gdx.files.internal("images/right.png"));
		currentHint = hint1;
		selected = 0;
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
		sb.draw(currentHint, hintBegLeftX, hintBegUpY);
		if(selected == 0)
			sb.draw(right, hintBegLeftX+490, hintBegUpY+100);
		if(selected == 1)
			sb.draw(left, hintBegLeftX+10, hintBegUpY+100);
		font.draw(sb, hint, (VimFight.WIDTH - 15*hint.length())/2-50, 200);
		
		sb.end();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			playState.stage.setKeyboardFocus(playState.player);
			gsm.setNeedClean(true);
			gsm.pop();
		}
		if(Gdx.input.isKeyJustPressed(Keys.H)){
			if( selected > 0 ){
				selected--;
				selectHint();
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.L)){
			if( selected < 1 ){
				selected++;
				selectHint();
			}
		}
		
	}

	private void selectHint(){
		switch (selected) {
		case 0:
			currentHint = hint1;
			break;
		case 1:
			currentHint = hint2;
			break;
		default:
			break;
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
