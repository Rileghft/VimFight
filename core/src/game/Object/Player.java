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

	public Player() {
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
					map.moveDown(pos);
					animation.startDown();
					break;
				case GameKeys.k:
					map.moveUp(pos);
					animation.startUp();
					break;
				case GameKeys.h:
					map.moveLeft(pos);
					animation.startLeft();
					break;
				case GameKeys.l:
					map.moveRight(pos);
					animation.startRight();
					break;
				case GameKeys.w:
					map.moveNextWord(pos);
					animation.startJump();
					break;
				case GameKeys.b:
					map.movePreWord(pos);
					animation.startJump();
					break;
				case GameKeys.NUM_0:
					map.moveLineBegin(pos);
					animation.startJump();
					break;
				case GameKeys.DOLLAR:
					map.moveLineEnd(pos);
					animation.startJump();
					break;
				case GameKeys.f:
					isFindCharState = true;
					findCharDirection = 1;
					break;
				case GameKeys.F:
					isFindCharState = true;
					findCharDirection = 0;
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
	}

	public void setMap(GameMap map) {
		this.map = map;
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
