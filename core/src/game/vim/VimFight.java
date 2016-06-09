package game.vim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import game.Object.Player;
import game.managers.GameInputProcessor;
import game.managers.GameStateManager;

public class VimFight extends ApplicationAdapter {
	public static OrthographicCamera cam;
	public static int WIDTH;
	public static int HEIGHT;
	private GameStateManager gsm;
	private Stage stage;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2, HEIGHT/2);
		cam.update();
		ScreenViewport viewport = new ScreenViewport();
		stage = new Stage(viewport);
		//here to set actor for input Proecessor
		Gdx.input.setInputProcessor(stage);
		Player player = new Player();
		stage.addActor(player);
		stage.setKeyboardFocus(player);

		gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
	}

	public void resize( int width, int height ) {}
	public void pause() {}
	public void resume() {}
	public void dispose() {}
}
