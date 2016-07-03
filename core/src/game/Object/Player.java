/**
 *
 */
package game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import game.Map.GameMap;
import game.Map.Position;
import game.component.Hp;
import game.component.Mp;
import game.component.Score;
import game.component.Status;
import game.gamestates.PlayState;
import vimControl.GameKeys;
import vimControl.VimControl;
import vimControl.VimMode;

/**
 * @author 楊舜宇
 * @since 2016/6/9
 *
 */
public class Player extends Actor implements Creature{
	private static float SQUARE_LENGTH = 30;
	public Position pos;
	private GameMap map;
	private CharacterAnimation animation;
	private boolean isFindCharState = false;
	private int findCharDirection = 1;
	private float accumulateTime = 0f;
	private boolean isImmortal;
	public PlayState playControl;
	public Hp hp;
	public Mp mp;
	public Score score;
	private int lastPosX;
	private int lastPosY;
	private int movePlusMP;
	public int []statistic = new int[10];
	public Status cmdBar;
	public VimControl vim;

	public Player() {
		hp = new Hp(1000);
		mp = new Mp(1000);
		score = new Score();
		hp.setFull();
		mp.setEmpty();
		cmdBar = new Status();
		vim = new VimControl(cmdBar);
		//setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		setTouchable(Touchable.enabled);
		setPosition(55f, 630f);
		pos = new Position(0, 0);
		animation = new CharacterAnimation("images/player1.atlas");
		animation.setSpriteBatch(new SpriteBatch());
		animation.setOrgPos(105f, 630f);

		addListener(new InputListener(){
			@Override
			public boolean keyTyped(InputEvent event, char keyChar) {
				if((int)keyChar == 0) return false;
				VimMode preMode = vim.getCurrentState();
				vim.inputKey(keyChar);
				switch (vim.getCurrentState()) {
				case NORMAL:
					if(preMode == VimMode.COMMAND && keyChar == GameKeys.ENTER) {
						proccessCommandMode(keyChar);
					}
					proccessNormalMode(keyChar);
					break;
				case COMMAND:
					proccessCommandMode(keyChar);
					break;
				default:
					break;
				}
				return true;
			}
		});
	}

	private void proccessCommandMode(char keyChar) {
		if(keyChar == GameKeys.ENTER) {
			String cmd = cmdBar.getCommand();
			if(cmd.equals(":q")) {
				playControl.GameOver();
			}
			else if(cmd.equals(":h")) {
				playControl.help();
			}
			else if(cmd.equals(":p")) {
				playControl.pause();
			}
			cmdBar.clear();
		}
		else if(keyChar == GameKeys.ESC) {
			cmdBar.clear();
		}
		else if(keyChar == GameKeys.BACKSPACE) {
			cmdBar.backSpace();
			if(cmdBar.getCommand().isEmpty()) {
				vim.leaveCommand();
			}
		}
		else {
			cmdBar.append(keyChar);
		}
	}

	private void proccessNormalMode(Character keyChar) {
				lastPosX = pos.x;
				lastPosY = pos.y;
				if(isFindCharState) {
					if(findCharDirection == 1)
						map.moveFindChar(pos, keyChar);
					else map.moveFindPreChar(pos, keyChar);
					isFindCharState = false;
					updateScreen();
				}
				switch (keyChar) {
				case GameKeys.j:
					statistic[0]++;
					movePlusMP = 1;
					map.moveDown(this);
					animation.startDown();
					break;
				case GameKeys.k:
					statistic[1]++;
					movePlusMP = 1;
					map.moveUp(this);
					animation.startUp();
					break;
				case GameKeys.h:
					statistic[2]++;
					movePlusMP = 1;
					map.moveLeft(this);
					animation.startLeft();
					break;
				case GameKeys.l:
					statistic[3]++;
					movePlusMP = 1;
					map.moveRight(this);
					animation.startRight();
					break;
				case GameKeys.w:
					statistic[4]++;
					movePlusMP = 3;
					map.moveNextWord(this);
					animation.startJump();
					break;
				case GameKeys.b:
					statistic[5]++;
					movePlusMP = 3;
					map.movePreWord(this);
					animation.startJump();
					break;
				case GameKeys.NUM_0:
					statistic[6]++;
					movePlusMP = 3;
					map.moveLineBegin(pos);
					animation.startJump();
					break;
				case GameKeys.DOLLAR:
					statistic[7]++;
					movePlusMP = 3;
					map.moveLineEnd(pos);
					animation.startJump();
					break;
				case GameKeys.f:
					statistic[8]++;
					movePlusMP = 10;
					isFindCharState = true;
					findCharDirection = 1;
					break;
				case GameKeys.F:
					statistic[9]++;
					movePlusMP = 10;
					isFindCharState = true;
					findCharDirection = 0;
					break;
				}
				updateScreen();

	}

	public void updateScreen() {
			map.updateScreenMap(pos);
			int screenStartRow = map.screenStartRow;
			int screenStartCol = map.screenStartCol;
			int row = pos.y - screenStartRow;
			int col = pos.x - screenStartCol;
			animation.setDstPos(105f + col * SQUARE_LENGTH, 630f - row * SQUARE_LENGTH);
			if(pos.x != lastPosX || pos.y != lastPosY) {
				score.plus(movePlusMP);
				mp.plus(movePlusMP);
			}
			cmdBar.setRow(pos.y);
			cmdBar.setCol(pos.x);
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	public void demage(int amount) {
		hp.minus(amount);
	}

	public boolean isDead() {
		return (hp.getCurrentHp() <= 0);
	}

	public void convertPos(Position screenPos) {
		setPosition(55f + screenPos.getScreenX(),630f + screenPos.getScreenY());
	}

	@Override
	protected void positionChanged() {
		//sprite.setPosition(getX(), getY());
		super.positionChanged();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		//sprite.draw(batch);
		animation.draw();
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

	@Override
	public void draw(SpriteBatch batch) {
		//leave empty
	}

	@Override
	public void update() {
		accumulateTime += Gdx.graphics.getDeltaTime();
		if(accumulateTime > 10) accumulateTime = 0f;
		int collision_type = map.isCollision(this);
		if(collision_type != 0) {
			if(!isImmortal) {
				map.collision(this);
				isImmortal = true;
				accumulateTime = 0f;
			}
			if(collision_type == 1) {
				map.collision(this);
			}
		}
		if(accumulateTime >= 2f){
			isImmortal = false;
		}
	}

	@Override
	public int getRow() {
		return pos.y;
	}

	@Override
	public int getCol() {
		return pos.x;
	}

	@Override
	public void setRow(int row) {
		pos.y = row;
	}

	@Override
	public void setCol(int col) {
		pos.x = col;
	}

}
