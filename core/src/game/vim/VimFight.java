package game.vim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.managers.GameInputProcessor;
import game.managers.GameStateManager;

public class VimFight extends ApplicationAdapter {
	static OrthographicCamera cam;
	public static int WIDTH;
	public static int HEIGHT;
	private GameStateManager gsm;
	
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2, HEIGHT/2);
		cam.update();
		//here to set actor for input Proecessor
		GameInputProcessor gameInputProcessor = new GameInputProcessor();
		Gdx.input.setInputProcessor(gameInputProcessor);
		
		gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
	}
	
	public void resize( int width, int height ) {}
	public void pause() {}
	public void resume() {}
	public void dispose() {}
}
