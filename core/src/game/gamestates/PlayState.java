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

import game.Map.MapSquare;
import game.managers.GameStateManager;
import game.vim.VimFight;

public class PlayState extends GameState {

	private ShapeRenderer sr;
	private static int mapWidth = 600;
	private static int mapHeight = 600;
	private float cellWidth;
	private float cellHeight;
	private float mapBegLeftX = 10;
	private float mapBegUpY = 50;
	private SpriteBatch sb;
	private BitmapFont font;

	//for test
	private ArrayList<MapSquare> testMap;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		sr = new ShapeRenderer();
		cellWidth = mapWidth / 20;
		cellHeight = mapHeight / 20;

		sb = new SpriteBatch();
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/SourceCodePro-Regular.ttf")
				);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;
		font = gen.generateFont( parameter );
		font.setColor(Color.BLACK);

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

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void draw() {
		sr.setColor(0,0,0,0);
		sr.begin(ShapeType.Line);
		for(int i = 0 ; i < 20 ; i++ ){
			for( int j = 0 ; j < 8 ; j++){
				drawRect(i, j, testMap.get(j));
			}
		}

		sr.end();
	}

	private void drawRect(int x, int y, MapSquare cell){
		//draw a rect of cell in the map componet
		float posX = xConverter( mapBegLeftX + x*cellWidth );
		float posY = yConverter( mapBegUpY + y*cellHeight );
		sr.rect(posX, posY, cellWidth, cellHeight);

		sb.setProjectionMatrix(VimFight.cam.combined);
		sb.begin();

		font.draw(sb, cell.getText(), posX, posY + cellHeight);

		sb.end();

		}
	private float xConverter( float x ) {
		return x;
	}

	private float yConverter( float y ) {
		return VimFight.HEIGHT - y;
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
