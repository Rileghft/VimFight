package game.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import game.Map.MapSquare;
import game.Object.Player;
import game.managers.GameStateManager;
import game.vim.VimFight;

public class PlayState extends GameState {

	private ShapeRenderer sr;
	private static int mapWidth = 600;
	private static int mapHeight = 600;
	private float cellWidth;
	private float cellHeight;
	private float mapBegLeftX = 55;
	private float mapBegUpY = 70;
	private float lineNumberLeftX = 1;
	private float lineNumberUpY = 70;
	private SpriteBatch sb;
	private BitmapFont font;
	private BitmapFont lineNumber;
	private Stage stage;
	private int lineNumBeg;

	//for test
	private ArrayList<MapSquare> testMap;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		Player player = new Player();
		stage.addActor(player);
		stage.setKeyboardFocus(player);

		//draw map
		sr = new ShapeRenderer();
		cellWidth = mapWidth / 20;
		cellHeight = mapHeight / 20;

		sb = new SpriteBatch();
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/SourceCodePro-Regular.ttf")
				);
		FreeTypeFontParameter FontParameter = new FreeTypeFontParameter();
		FontParameter.size = 40;
		font = gen.generateFont( FontParameter );
		font.setColor(Color.BLACK);
		
		FreeTypeFontParameter LineNumberParameter = new FreeTypeFontParameter();
		LineNumberParameter.size = 35;
		lineNumber = gen.generateFont( LineNumberParameter );
		lineNumber.setColor(Color.BROWN);
		//for test
		testMap = new ArrayList<MapSquare>();
		testMap.add(new MapSquare("A"));
		testMap.add(new MapSquare("B"));
		testMap.add(new MapSquare("C"));
		testMap.add(new MapSquare("D"));
		testMap.add(new MapSquare("E"));
		testMap.add(new MapSquare("F"));
		testMap.add(new MapSquare("G"));
		testMap.add(new MapSquare("H"));
		
		setLineNumBeg(1);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void draw() {
		sr.setColor(0,0,0,0);
		sr.begin(ShapeType.Line);
		sb.setProjectionMatrix(VimFight.cam.combined);
		sb.begin();
		
		//draw map
		for(int i = 0 ; i < 20 ; i++ ){
			for( int j = 0 ; j < 8 ; j++){
				drawRect(j, i, testMap.get(j));
			}
		}

		//draw lineNumber
		drawLineNumber();
		
		//draw Hp
		
		//draw Mp
		
		//draw command line
		
		
		sb.end();
		sr.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	private void drawRect(int x, int y, MapSquare cell){
		//draw a rectangle of cell in the map component
		float posX = xConverter( mapBegLeftX + x*cellWidth );
		float posY = yConverter( mapBegUpY + y*cellHeight );
		
		sr.rect(posX, posY, cellWidth, cellHeight);
		
		//因為bitmapFont.draw要輸入的position 是左下角的
		font.draw(sb, cell.getText(), posX, posY + cellHeight);	
		}
	
	private float xConverter( float x ) {
		return x;
	}

	private float yConverter( float y ) {
		return VimFight.HEIGHT - y;
	}

	@Override
	public void handleInput() {
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void setLineNumBeg( int num ) {
		this.lineNumBeg = num;
	}
	private void drawLineNumber() {
		
		for(int i = 0 ; i < 20 ; i++) {
			
			//因為bitmapFont.draw要輸入的position 是左下角的 所以把posY上移
			//font.draw(sb, text, posX, posY, width, align, wrap) 其中的width是用來做align用的空間
			lineNumber.draw(sb, Integer.toString(i+this.lineNumBeg), xConverter( this.lineNumberLeftX ), yConverter( this.lineNumberUpY + (i-1)*cellHeight +5), 50, Align.right, false);
		}
			
	}
}
