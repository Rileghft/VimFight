package game.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import game.component.Hp;
import game.component.Mp;
import game.component.Status;
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
	private float hpLeftX = 50;
	private float hpUpY = 5;
	private float mpLeftX = 360;
	private float mpUpY = 5;
	private float statusLeftX = 5;
	private float statusUpY = 650;
	private Hp hp;
	private Mp mp;
	private SpriteBatch sb;
	private BitmapFont font;
	private BitmapFont lineNumber;
	private Stage stage;
	private int lineNumBeg;
	private Status status;

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

		//for draw map
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

		//for draw line number
		FreeTypeFontParameter LineNumberParameter = new FreeTypeFontParameter();
		LineNumberParameter.size = 35;
		lineNumber = gen.generateFont( LineNumberParameter );
		lineNumber.setColor(Color.BROWN);

		//for draw hp and mp
		hp = new Hp(1000);
		mp = new Mp(200);

		//for draw status
		status = new Status();

		//begin of test data
		testMap = new ArrayList<MapSquare>();
		for(Character c = 'A'; c < 'U'; c++) {
			testMap.add(new MapSquare(c.toString()));
		}

		setLineNumBeg(1);

		hp.setCurrentHp(675);
		mp.setCurrentMp(60);
		status.append("a");
		status.append("b");
		status.append("c");
		status.append("d");
		status.append("e");
		status.append("f");
		status.setCol(5);
		status.setRow(10);
		status.setErr("the error msg must be English and Number");
		status.setStatusState(true);
		status.setStatusState(Status.ERROR);

		//end of test data
	}

	@Override
	public void update(float delta) {
		handleInput();
	}

	@Override
	public void draw() {
		sb.setProjectionMatrix(VimFight.cam.combined);

		//draw map
		for(int i = 0 ; i < 20 ; i++ ){
			for( int j = 0 ; j < 20 ; j++){
				drawRect(j, i, testMap.get(j));
			}
		}

		//draw lineNumber
		drawLineNumber();

		//draw Hp
		hp.draw(sr, sb, hpLeftX, hpUpY);
		//draw Mp
		mp.draw(sr, sb, mpLeftX, mpUpY);
		//draw command line
		status.draw(sr, sb, statusLeftX, statusUpY);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	private void drawRect(int x, int y, MapSquare cell){
		//draw a rectangle of cell in the map component
		float posX = xConverter( mapBegLeftX + x*cellWidth );
		float posY = yConverter( mapBegUpY + y*cellHeight );
		sr.begin(ShapeType.Line);
		sr.setColor(0,0,0,0);

		sr.rect(posX, posY, cellWidth, cellHeight);
		sr.end();
		//因為bitmapFont.draw要輸入的position 是左下角的
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
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			System.out.println("esc");
			gsm.pop();
		}
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
			sb.begin();
			lineNumber.draw(sb, Integer.toString(i+this.lineNumBeg), xConverter( this.lineNumberLeftX ), yConverter( this.lineNumberUpY + (i-1)*cellHeight +5), 50, Align.right, false);
			sb.end();
		}

	}

}
