package game.gamestates;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import game.Map.GameMap;
import game.Map.MapRow;
import game.Map.MapSquare;
import game.Object.BGM;
import game.Object.Player;
import game.Object.Trap;
import game.component.Score;
import game.component.Status;
import game.managers.GameStateManager;
import game.vim.VimFight;

public class PlayState extends GameState {

	private ShapeRenderer sr;
	private static int mapWidth = 600;
	private static int mapHeight = 600;
	private float cellWidth;
	private float cellHeight;
	private float mapBegLeftX = 105;
	private float mapBegUpY = 70;
	private float lineNumberLeftX = 1;
	private float lineNumberUpY = 70;
	private float hpLeftX = 50;
	private float hpUpY = 5;
	private float mpLeftX = 360;
	private float mpUpY = 5;
	private float statusLeftX = 55;
	private float statusUpY = 650;
	Player player;
	private SpriteBatch sb;
	private BitmapFont font;
	private BitmapFont lineNumber;
	private Stage stage;
	private int lineNumBeg;
	private Status cmdBar;
	private Score score;
	private float scoreLeftX = 740;
	private float ScoreUpY = 5;
	private ArrayList<Texture> traps;
	
	private GameMap map;
	private ArrayList<MapRow> screenMap;

	//for BGM
	private BGM bgm;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		soundInit();
	}

	//for Sound
	private void soundInit(){
		bgm = gsm.getbgm(2);
	}

	@Override
	public void init() {
		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		player = new Player();
		player.playControl = this;
		cmdBar = player.cmdBar;
		stage.addActor(player);
		stage.setKeyboardFocus(player);
		score = player.score;


		//for draw map
		sr = new ShapeRenderer();
		cellWidth = mapWidth / 20;
		cellHeight = mapHeight / 20;

		sb = new SpriteBatch();
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/SourceCodePro-Regular.ttf")
				);
		FreeTypeFontParameter FontParameter = new FreeTypeFontParameter();
		FontParameter.size = 35;
		font = gen.generateFont( FontParameter );
		font.setColor(Color.BLACK);

		//for draw line number
		FreeTypeFontParameter LineNumberParameter = new FreeTypeFontParameter();
		LineNumberParameter.size = 35;
		lineNumber = gen.generateFont( LineNumberParameter );
		lineNumber.setColor(new Color(0.582f,0.359f,0.570f,1));


		//begin of test data
		FileHandle fhandler = Gdx.files.internal("exampleMap_1.txt");
		BufferedReader mapReader = fhandler.reader(256);
		map = new GameMap(mapReader);
		player.setMap(map);
		//end of test data
		
		//add traps textures
		trapsInit();
	}

	private void trapsInit(){
		traps = new ArrayList<Texture>();
		traps.add(new Texture(Gdx.files.internal("images/traps/8-bit-Bomb.png")));
		traps.add(new Texture(Gdx.files.internal("images/traps/Spikes_in_Sonic_the_Hedgehog_4.png")));
		traps.add(new Texture(Gdx.files.internal("images/traps/Snap_Trap_icon.png")));
	}
	
	@Override
	public void update(float delta) {
		handleInput();
		if(player.isDead()) {
			GameOver();
		}
		setLineNumBeg(map.screenStartRow);
		draw();
	}

	public void GameOver() {
			gsm.setState(new GameOverState(gsm, player.statistic));
			gsm.setScore(score.getScoreNum());
			bgm.stopBGM();
	}

	@Override
	public void draw() {
		sb.setProjectionMatrix(VimFight.cam.combined);

		//draw map
		screenMap = map.getMapScreenRows();
		for(int i = 0 ; i < screenMap.size(); i++ ){
			MapRow row = screenMap.get(i);
			int j = 0;
			for(MapSquare square: row) {
				drawRect(j++, i, square);
			}
		}

		
		drawLineNumber();
		player.hp.draw(sr, sb, hpLeftX, hpUpY);
		player.mp.draw(sr, sb, mpLeftX, mpUpY);
		player.score.draw(sr, sb, scoreLeftX, ScoreUpY);
		//draw command line
		cmdBar.draw(sr, sb, statusLeftX, statusUpY);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	private void drawRect(int x, int y, MapSquare cell){
		//draw a rectangle of cell in the map component
		float posX = xConverter( mapBegLeftX + x*cellWidth );
		float posY = yConverter( mapBegUpY + y*cellHeight );
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0.648f,0.633f,0.711f,1));
		sr.rect(posX, posY, cellWidth, cellHeight);
		sr.end();
		sr.begin(ShapeType.Line);
		sr.setColor(0,0,0,0);

		sr.rect(posX, posY, cellWidth, cellHeight);
		sr.end();
		//因為bitmapFont.draw要輸入的position 是左下角的
		sb.begin();
		font.draw(sb, cell.getChar(), posX, posY + cellHeight);
			Texture texture = getItemTexture(cell);
			if(texture != null)
				sb.draw(texture, posX, posY);
		sb.end();
		}

	private Texture getItemTexture(MapSquare cell){
		Texture texture = null;
		if(cell.getType() == Item.Type.EMPTY){
			
		}else if(cell.getType() == Item.Type.BOME){
			texture = traps.get(0);
		}else if(cell.getType() == Item.Type.SPEAR){
			texture = traps.get(1);
		}else if(cell.getType() == Item.Type.MOUSE_TRAP){
			texture = traps.get(2);
		}else if(cell.getType() == Item.Type.FIRE){
			
		}
		return texture;
	}
	
	private float xConverter( float x ) {
		return x;
	}

	private float yConverter( float y ) {
		return VimFight.HEIGHT - y;
	}

	@Override
	public void handleInput() {
		//test begin
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			System.out.println("esc");
			gsm.pop();
			stopBGM();
		}
		if(Gdx.input.isKeyPressed(Keys.UP)){
			score.plus(50);
		}
		//test end
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
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(0.379f,0.715f,0.727f,1));
			sr.rect(xConverter( this.lineNumberLeftX+5 ), yConverter( this.lineNumberUpY + (i-1)*cellHeight +30), cellWidth+65, cellHeight);
			sr.end();
			sb.begin();
			lineNumber.draw(sb, Integer.toString(i+this.lineNumBeg), xConverter( this.lineNumberLeftX +50), yConverter( this.lineNumberUpY + (i-1)*cellHeight +5), 50, Align.right, false);
			sb.end();
			}

	}

	@Override
	public void startBGM() {
		bgm.startBGM();
	}

	@Override
	public void stopBGM() {
		bgm.stopBGM();
	}

}
