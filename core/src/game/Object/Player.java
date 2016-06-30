/**
 *
 */
package game.Object;

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
import vimControl.GameKeys;

/**
 * @author 楊舜宇
 * @since 2016/6/9
 *
 */
public class Player extends Actor {
	private static float SQUARE_LENGTH = 30;
	private Position pos;
	private GameMap map;
	private CharacterAnimation animation;
	private boolean isFindCharState = false;
	private int findCharDirection = 1;
	public Hp hp;
	public Mp mp;
	private int lastPosX;
	private int lastPosY;
	private int movePlusMP;
	public int []statistic = new int[10];

	public Player() {
		hp = new Hp(1000);
		mp = new Mp(1000);
		hp.setFull();
		mp.setEmpty();
		//setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		setTouchable(Touchable.enabled);
		setPosition(55f, 630f);
		pos = new Position(0, 0);
		animation = new CharacterAnimation("images/player1.atlas");
		animation.setSpriteBatch(new SpriteBatch());
		animation.setOrgPos(55f, 630f);

		addListener(new InputListener(){
			@Override
			public boolean keyTyped(InputEvent event, char keyChar) {
				if((int)keyChar == 0) return false;
				lastPosX = pos.x;
				lastPosY = pos.y;
				if(isFindCharState) {
					if(findCharDirection == 1)
						map.moveFindChar(pos, keyChar);
					else map.moveFindPreChar(pos, keyChar);
					updateMap();
					isFindCharState = false;
					return true;
				}
				switch (keyChar) {
				case GameKeys.j:
					statistic[0]++;
					movePlusMP = 1;
					map.moveDown(pos);
					animation.startDown();
					break;
				case GameKeys.k:
					statistic[1]++;
					movePlusMP = 1;
					map.moveUp(pos);
					animation.startUp();
					break;
				case GameKeys.h:
					statistic[2]++;
					movePlusMP = 1;
					map.moveLeft(pos);
					animation.startLeft();
					break;
				case GameKeys.l:
					statistic[3]++;
					movePlusMP = 1;
					map.moveRight(pos);
					animation.startRight();
					break;
				case GameKeys.w:
					statistic[4]++;
					movePlusMP = 3;
					map.moveNextWord(pos);
					animation.startJump();
					break;
				case GameKeys.b:
					statistic[5]++;
					movePlusMP = 3;
					map.movePreWord(pos);
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
				case GameKeys.d:
					demage(100);
					break;
				default:
					break;
				}
				updateMap();

				return true;
			}
		});

	}

	private void updateMap() {
			map.updateScreenMap(pos);
			int screenStartRow = map.screenStartRow;
			int screenStartCol = map.screenStartCol;
			int row = pos.y - screenStartRow;
			int col = pos.x - screenStartCol;
			animation.setDstPos(55f + col * SQUARE_LENGTH, 630f - row * SQUARE_LENGTH);
			if(pos.x != lastPosX || pos.y != lastPosY) mp.plus(movePlusMP);
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
}
