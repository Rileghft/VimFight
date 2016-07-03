package game.gamestates;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import game.Map.GameMap;
import game.Map.MapRow;
import game.Map.MapSquare;
import game.Object.BGM;
import game.Object.Player;
import game.Object.Item.TYPE;
import game.Object.Item;
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
	public Stage stage;
	private int lineNumBeg;
	private Status cmdBar;
	private Score score;
	private float scoreLeftX = 740;
	private float ScoreUpY = 5;
	private ArrayList<Texture> traps;

	//for fire animation
	private TextureAtlas fireAtlas;
	private static float FRAME_DURATION = 0.5f;
	private Animation fireAnimation;
	private TextureRegion currentFrame;
	private float elapsed_time = 0f;
	public long surviveTime = 0;
	public long startTime = 0;

	private GameMap map;
	private ArrayList<MapRow> screenMap;
	private Texture hp_tonic = new Texture(Gdx.files.internal("images/potion/hp.png"));
	private Texture mp_tonic = new Texture(Gdx.files.internal("images/potion/mp.png"));

	//for BGM
	private boolean isFirst = true;
	private BGM bgm;

	//for show level
	private TextureAtlas level0_Atlas, level1_Atlas, level2_Atlas, level3_Atlas, level4_Atlas, level5_Atlas;
	private static float level_FRAME_DURATION = .36f;
	private Animation level0_Animation, level1_Animation,level2_Animation,level3_Animation,level4_Animation,level5_Animation;
	private TextureRegion levelCurrentFrame;
	private float level_elapsed_time = 0;
	private boolean enableLevel = false;
	private int level = 0;
	private boolean startAddTrap = false;
	private float addTrapTimer = 0f;
	private int addItemTimeInterval = 180;
	private int trapAddNum = 0;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		soundInit();
		startTime = System.currentTimeMillis();
	}

	//for Sound
	private void soundInit(){
		bgm = gsm.getbgm(2);
	}

	private void fireTrapAnimationInit(){
		fireAtlas = new TextureAtlas(Gdx.files.internal("images/traps/fire.atlas"));
		Array<AtlasRegion> fireFrames = fireAtlas.findRegions("burning");
		fireAnimation = new Animation(FRAME_DURATION, fireFrames, PlayMode.LOOP);
	}

	private void showLevelInit(){
		level0_Atlas = new TextureAtlas(Gdx.files.internal("images/Levelcard/Lv0.atlas"));
		level1_Atlas = new TextureAtlas(Gdx.files.internal("images/Levelcard/Lv1.atlas"));
		level2_Atlas = new TextureAtlas(Gdx.files.internal("images/Levelcard/Lv2.atlas"));
		level3_Atlas = new TextureAtlas(Gdx.files.internal("images/Levelcard/Lv3.atlas"));
		level4_Atlas = new TextureAtlas(Gdx.files.internal("images/Levelcard/Lv4.atlas"));
		level5_Atlas = new TextureAtlas(Gdx.files.internal("images/Levelcard/Lv5.atlas"));

		Array<AtlasRegion> Frames0 = level0_Atlas.findRegions("showing");
		level0_Animation = new Animation(level_FRAME_DURATION, Frames0, PlayMode.LOOP_PINGPONG);
		Array<AtlasRegion> Frames1 = level1_Atlas.findRegions("showing");
		level1_Animation = new Animation(level_FRAME_DURATION, Frames1, PlayMode.LOOP_PINGPONG);
		Array<AtlasRegion> Frames2 = level2_Atlas.findRegions("showing");
		level2_Animation = new Animation(level_FRAME_DURATION, Frames2, PlayMode.LOOP_PINGPONG);
		Array<AtlasRegion> Frames3 = level3_Atlas.findRegions("showing");
		level3_Animation = new Animation(level_FRAME_DURATION, Frames3, PlayMode.LOOP_PINGPONG);
		Array<AtlasRegion> Frames4 = level4_Atlas.findRegions("showing");
		level4_Animation = new Animation(level_FRAME_DURATION, Frames4, PlayMode.LOOP_PINGPONG);
		Array<AtlasRegion> Frames5 = level5_Atlas.findRegions("showing");
		level5_Animation = new Animation(level_FRAME_DURATION, Frames5, PlayMode.LOOP_PINGPONG);
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
		map.spreadTraps(3000);
		player.setMap(map);
		//end of test data

		//add traps textures
		trapsInit();

		showLevelInit();
	}

	private void trapsInit(){
		traps = new ArrayList<Texture>();
		traps.add(new Texture(Gdx.files.internal("images/traps/bomb.png")));
		traps.add(new Texture(Gdx.files.internal("images/traps/spear.png")));
		traps.add(new Texture(Gdx.files.internal("images/traps/mouse_trap.png")));
		fireTrapAnimationInit();
		elapsed_time = 0;
	}

	@Override
	public void update(float delta) {
		if(isFirst) {
			isFirst = false;
			help();
		}
		handleInput();
		addTrapTimer += Gdx.graphics.getDeltaTime();
		levelControl(player.score.getScoreNum());
		if(player.isDead()) {
			stage.unfocus(player);
			GameOver();
		}
		setLineNumBeg(map.screenStartRow);
		draw();
	}

	public void changeLevel( int level ){
		level_elapsed_time = 0;
		enableLevel = true;
		this.level = level;
	}

	public void GameOver() {
			surviveTime += System.currentTimeMillis() - startTime;
			System.out.println("Survive Time: " + surviveTime / 1000);
			gsm.setState(new GameOverState(gsm, player.statistic, surviveTime / 1000));
			gsm.setScore(score.getScoreNum());
			bgm.stopBGM();
	}

	public void levelControl(int score) {
		if(score >= 3000 ) {
			if(level != 5) {
				changeLevel(5);
				addItemTimeInterval = 20;
			}
			if(addTrapTimer >= addItemTimeInterval) {
				map.spreadTraps(200);
				map.spreadTonic(50);
				addTrapTimer = 0f;
			}
		}
		else if(score >= 1500) {
			if(level != 4) {
				changeLevel(4);
				addItemTimeInterval = 30;
			}
			if(addTrapTimer >= addItemTimeInterval) {
				map.spreadTraps(100);
				map.spreadTonic(200);
				addTrapTimer = 0f;
			}
		}
		else if(score >= 1000) {
			if(level != 3) {
				changeLevel(3);
				addItemTimeInterval = 60;
			}
			if(addTrapTimer >= addItemTimeInterval) {
				map.spreadTraps(1000);
				map.spreadTonic(300);
				addTrapTimer = 0f;
			}
		}
		else if(score >= 500) {
			if(level != 2) {
				changeLevel(2);
				addItemTimeInterval = 90;
			}
			if(addTrapTimer >= addItemTimeInterval) {
				map.spreadTraps(2000);
				map.spreadTonic(500);
				addTrapTimer = 0f;
			}
		}
		else if(score >= 100) {
			if(level != 1) {
				startAddTrap = true;
				changeLevel(1);
				addItemTimeInterval = 120;
			}
			if(addTrapTimer >= addItemTimeInterval) {
				map.spreadTraps(5000);
				addTrapTimer = 0f;
			}
		}
		else {
			addTrapTimer = 0;
		}

	}

	@Override
	public void draw() {
		sb.setProjectionMatrix(VimFight.cam.combined);

		sb.begin();
		//draw background
		sb.draw(gsm.backGround,0,0);
		sb.end();

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
		player.update();
		player.hp.draw(sr, sb, hpLeftX, hpUpY);
		player.mp.draw(sr, sb, mpLeftX, mpUpY);
		player.score.draw(sr, sb, scoreLeftX, ScoreUpY);
		//draw command line
		cmdBar.draw(sr, sb, statusLeftX, statusUpY);
		elapsed_time += Gdx.graphics.getDeltaTime();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		drawLevel();
	}

	private void drawLevel(){
		if(enableLevel){
			level_elapsed_time += Gdx.graphics.getDeltaTime();
			switch (level) {
			case 0:
				levelCurrentFrame = level0_Animation.getKeyFrame(level_elapsed_time);
				break;
			case 1:
				levelCurrentFrame = level1_Animation.getKeyFrame(level_elapsed_time);
				break;
			case 2:
				levelCurrentFrame = level2_Animation.getKeyFrame(level_elapsed_time);
				break;
			case 3:
				levelCurrentFrame = level3_Animation.getKeyFrame(level_elapsed_time);
				break;
			case 4:
				levelCurrentFrame = level4_Animation.getKeyFrame(level_elapsed_time);
				break;
			case 5:
				levelCurrentFrame = level5_Animation.getKeyFrame(level_elapsed_time);
				break;
			default:
				break;
			}
			if(level_elapsed_time > 3)
				enableLevel = false;
			sb.begin();
			sb.draw(levelCurrentFrame, 300, 350);
			sb.end();
		}
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
			Texture texture = getItemTexture(cell, posX, posY);
			if(texture != null)
				sb.draw(texture, posX, posY);
		sb.end();
		}

	private Texture getItemTexture(MapSquare cell, float posX, float posY){
		Texture texture = null;
		if(cell.getItemType() == Item.TYPE.NONE){

		}else if(cell.getItemType() == Item.TYPE.BOMB){
			texture = traps.get(0);
		}else if(cell.getItemType() == Item.TYPE.SPEAR){
			texture = traps.get(1);
		}else if(cell.getItemType() == Item.TYPE.MOUSE_TRAP){
			texture = traps.get(2);
		}else if(cell.getItemType() == Item.TYPE.FIRE){
			//System.out.println("elapsed_time = "+ elapsed_time);
			currentFrame = fireAnimation.getKeyFrame(elapsed_time);
			sb.draw(currentFrame, posX, posY);
		}else if(cell.getItemType() == TYPE.HP) {
			texture = hp_tonic;
		}else if(cell.getItemType() == TYPE.MP) {
			texture = mp_tonic;
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
		//for test changeLevel
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			changeLevel(0);
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

	public void help() {
		// TODO Auto-generated method stub
		stage.unfocus(player);
		gsm.setNeedClean(false);
		gsm.push(new HelpState(gsm, this));
		surviveTime += System.currentTimeMillis() - startTime;
	}

	public void pause() {
		// TODO Auto-generated method stub
		stage.unfocus(player);
		gsm.setNeedClean(false);
		gsm.push(new PauseState(gsm, this));
		surviveTime += System.currentTimeMillis() - startTime;
	}

}
