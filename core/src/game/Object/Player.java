/**
 *
 */
package game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

import game.Map.GameMap;
import game.Map.Position;

/**
 * @author 楊舜宇
 * @since 2016/6/9
 *
 */
public class Player extends Actor {
	//Sprite sprite = new Sprite(new Texture(Gdx.files.internal("actor.png")));
	private static float SQUARE_LENGTH = 30;
	private Position pos;
	private GameMap map;
	private CharacterAnimation animation;

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
			public boolean keyDown(InputEvent event, int keycode) {
				RunnableAction ra = new RunnableAction();
				switch (keycode) {
				case Keys.J:
					map.moveDown(pos);
					animation.startDown();
					break;
				case Keys.K:
					map.moveUp(pos);
					animation.startUp();
					break;
				case Keys.H:
					map.moveLeft(pos);
					animation.startLeft();
					break;
				case Keys.L:
					map.moveRight(pos);
					animation.startRight();
					break;
				case Keys.W:
					map.moveNextWord(pos);
					animation.startJump();
					break;
				case Keys.B:
					map.movePreWord(pos);
					animation.startJump();
					break;
				case Keys.NUM_0:
					map.moveLineBegin(pos);
					animation.startJump();
					break;
				case Keys.NUM_4:
					map.moveLineEnd(pos);
					animation.startJump();
					break;
				default:
					break;
				}
				map.updateScreenMap(pos);
				/*ra.setRunnable(new Runnable() {
					@Override
					public void run() {
						movePlayer();
					}
				});
				Player.this.addAction(ra);*/
				int screenStartRow = map.screenStartRow;
				int screenStartCol = map.screenStartCol;
				int row = pos.y - screenStartRow;
				int col = pos.x - screenStartCol;
				animation.setDstPos(55f + col * SQUARE_LENGTH, 630f - row * SQUARE_LENGTH);

				return true;
			}

			@Override
			public boolean keyTyped(InputEvent event, char character) {

				return super.keyTyped(event, character);
			}
		});

	}

	private void movePlayer() {
		int screenStartRow = map.screenStartRow;
		int screenStartCol = map.screenStartCol;
		int row = pos.y - screenStartRow;
		int col = pos.x - screenStartCol;
		System.out.println(String.format("row=%d, col=%d", row, col));
		setPosition(55f + col * SQUARE_LENGTH, 630f - row * SQUARE_LENGTH);
	}

	private boolean isEdge(float xPos, float yPos) {
		return (xPos > 55f && xPos < 655f) || (yPos < 630f && yPos > 30f);
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
