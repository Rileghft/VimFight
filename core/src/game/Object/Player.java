/**
 *
 */
package game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

/**
 * @author 楊舜宇
 * @since 2016/6/9
 *
 */
public class Player extends Actor {
	Sprite sprite = new Sprite(new Texture(Gdx.files.internal("actor.png")));
	private static float SQUARE_LENGTH = 30;

	public Player() {
		setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		setTouchable(Touchable.enabled);
		setPosition(55f, 630f);

		addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				RunnableAction ra = new RunnableAction();
				switch (keycode) {
				case Keys.J:
					ra.setRunnable(new Runnable() {
						@Override
						public void run() {
							float xPos = Player.this.getX();
							float yPos = Player.this.getY();
							Player.this.setPosition(xPos, yPos - SQUARE_LENGTH);
						}
					});
					break;
				case Keys.K:
					ra.setRunnable(new Runnable() {
						@Override
						public void run() {
							float xPos = Player.this.getX();
							float yPos = Player.this.getY();
							Player.this.setPosition(xPos, yPos + SQUARE_LENGTH);
						}
					});
					break;
				case Keys.H:
					ra.setRunnable(new Runnable() {
						@Override
						public void run() {
							float xPos = Player.this.getX();
							float yPos = Player.this.getY();
							Player.this.setPosition(xPos - SQUARE_LENGTH, yPos);
						}
					});
					break;
				case Keys.L:
					ra.setRunnable(new Runnable() {
						@Override
						public void run() {
							float xPos = Player.this.getX();
							float yPos = Player.this.getY();
							Player.this.setPosition(xPos + SQUARE_LENGTH, yPos);
						}
					});
					break;

				default:
					ra.setRunnable(new Runnable() {
						@Override
						public void run() {
						}
					});
					break;
				}
				Player.this.addAction(ra);

				return true;
			}
		});

	}

	@Override
	protected void positionChanged() {
		sprite.setPosition(getX(), getY());
		super.positionChanged();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}
}
