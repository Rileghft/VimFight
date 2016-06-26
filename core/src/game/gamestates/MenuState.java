package game.gamestates;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardEndHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;

import game.Object.CharacterAnimation;
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
	private CharacterAnimation ca;
	private float posX, posY;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		//for test animation
		animationInit();
	}
	//for test animation
	private void animationInit(){
		ca = new CharacterAnimation("images/character_set.atlas");
		ca.setSpriteBatch(sb);
		posX = Gdx.graphics.getWidth()/4*3;
		posY = Gdx.graphics.getHeight()/4*3;
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
		// TODO Auto-generated method stub
		handleInput();
		
		//for test animation
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)){
			posY -= 32;
			ca.setDstPos(posX, posY);
			ca.startDown();
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP)){
			posY += 32;
			ca.setDstPos(posX, posY);
			ca.startUp();
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			posX += 32;
			ca.setDstPos(posX, posY);
			ca.startRight();
		}
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
			posX -= 32;
			ca.setDstPos(posX, posY);
			ca.startLeft();
		}
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			posY -=50;
			ca.setDstPos(posX, posY);
			ca.startJump();
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		sb.setProjectionMatrix(VimFight.cam.combined);
		//for test animation
		ca.draw();
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
	
	private void select(){
		if(currentItem == 0){
			gsm.push(new PlayState(gsm));
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
