package game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import game.vim.VimFight;

public class Score {

	private int score;
	private BitmapFont font;
	private Texture star;
	private int[]starLimit = {100, 500, 1000, 1500, 3000};

	public Score(){
		star = new Texture(Gdx.files.internal("images/star.png"));
		this.score = 0;

		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/SourceCodePro-Regular.ttf")
				);
		FreeTypeFontParameter itemParameter = new FreeTypeFontParameter();
		itemParameter.size = 30;
		font = gen.generateFont(itemParameter);
		font.setColor(Color.WHITE);
	}

	public void set( int score ) {
		this.score = score;
	}

	public void plus( int adder ){
		this.score += adder;
	}

	public void draw(ShapeRenderer sr, SpriteBatch sb, float leftX, float upY) {
		sb.begin();
		font.draw(sb, "Score", xConverter(leftX), yConverter(upY) );
		font.draw(sb, Integer.toString(score), xConverter(leftX+30), yConverter(upY+30), 10, Align.center, false);
		for(int i = 0 ; starLimit[i] <= score && i < 5; i++){
			sb.draw(star, xConverter(leftX-20), yConverter(upY+600-i*100));
		}
		sb.end();
	}

	private float xConverter( float x ) {
		return x;
	}

	private float yConverter( float y ) {
		return VimFight.HEIGHT - y;
	}

}
