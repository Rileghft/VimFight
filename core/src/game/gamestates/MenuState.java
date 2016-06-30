package game.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import game.Object.BGM;
import game.managers.GameStateManager;
import game.vim.VimFight;

public class MenuState extends GameState {

	private final String title = "VimFight";
	private int currentItem;
	private String[] menuItems;
	private SpriteBatch sb;
	private BitmapFont titleFont;
	private BitmapFont font;
	//for test animation
	//add BGM
	private BGM bgm;
	public MenuState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		soundInit();
	}

	//sound init
	private void soundInit(){
		bgm = gsm.getbgm(4);
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

		menuItems = new String[] {
				"Play",
				"Highscores",
				"How To Play",
				"Quit"
		};
	}

	@Override
	public void update(float delta) {
		handleInput();
	}

	@Override
	public void draw() {
		sb.setProjectionMatrix(VimFight.cam.combined);

		sb.begin();
		//draw title
		titleFont.draw(sb, title, (VimFight.WIDTH - 50*title.length())/2, 600);

		//draw menu
		for(int i =- 0 ; i < menuItems.length; i++) {
			if(currentItem == i)
				font.setColor(Color.RED);
			else
				font.setColor(Color.BLACK);
			font.draw(sb, menuItems[i], (VimFight.WIDTH - 20*(menuItems[i].length()))/2, 400-40*i);
		}
		sb.end();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			if(currentItem > 0){
				currentItem--;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)){
			if(currentItem < menuItems.length -1){
				currentItem++;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			select();
		}
	}

	public void startBGM(){
		bgm.startBGM();
	}

	public void stopBGM(){
		bgm.stopBGM();
	}

	private void select(){
		if(currentItem == 0){
			gsm.push(new PlayState(gsm));
			stopBGM();
		}
		else if(currentItem == 1){
			//"Highscores",

		}
		else if(currentItem == 2){
			//"How To Play",
		}
		else if(currentItem == 3){
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
