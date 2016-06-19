package game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;

import game.vim.VimFight;

public class Mp {

	private int currentMp;
	private int maxMp;
	private BitmapFont hpAndMpFont;
	private BitmapFont font;
	
	public Mp( int max ) {
		this.maxMp = max;
		this.currentMp = this.maxMp;
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/SourceCodePro-Regular.ttf")
				);
		FreeTypeFontParameter hpAndMpParameter = new FreeTypeFontParameter();
		hpAndMpParameter.size = 22;
		hpAndMpFont = gen.generateFont( hpAndMpParameter );
		hpAndMpFont.setColor(Color.WHITE);
		
		FreeTypeFontParameter FontParameter = new FreeTypeFontParameter();
		FontParameter.size = 40;
		font = gen.generateFont( FontParameter );
		font.setColor(new Color(0.699f,0.434f,0.723f,1));
	}
	
	public void setMax( int max ) {
		this.maxMp = max;
	}
	
	public int getMax() {
		return this.maxMp;
	}
	
	public void setCurrentMp( int currentMp ) {
		this.currentMp = currentMp;
	}
	
	public int getCurrentMp() {
		return this.currentMp;
	}
	
	public void draw(ShapeRenderer sr, SpriteBatch sb, float leftX, float upY) {
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0.461f,0.723f,0.859f,1));
		
		sr.rect(xConverter(leftX+55), yConverter(upY+25), 250*(this.currentMp/(float)this.maxMp), 25);
		sr.end();
		sr.begin(ShapeType.Line);
		sr.setColor(Color.BLACK);
		sr.rect(xConverter(leftX+55), yConverter(upY+25), 250, 25);
		sr.end();
		
		sb.begin();
		font.draw(sb, "MP", xConverter(leftX), yConverter(upY));
		hpAndMpFont.draw(sb, "["+this.currentMp+"/"+this.maxMp+"]", xConverter(leftX+60), yConverter(upY+6), 246, Align.left, false);
		sb.end();
	}
	
	private float xConverter( float x ) {
		return x;
	}

	private float yConverter( float y ) {
		return VimFight.HEIGHT - y;
	}

}
