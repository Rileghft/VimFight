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

public class Hp {

	private int currentHp;
	private int maxHp;
	private BitmapFont hpAndMpFont;
	private BitmapFont font;
	
	public Hp( int max ) {
		this.maxHp = max;
		this.currentHp = this.maxHp;
		
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
		font.setColor(Color.BLACK);
	}
	
	public void setMax( int max ) {
		this.maxHp = max;
	}
	
	public int getMax() {
		return this.maxHp;
	}
	
	public void setCurrentHp( int currentHp ) {
		this.currentHp = currentHp;
	}
	
	public int getCurrentHp() {
		return this.currentHp;
	}
	
	public void draw(ShapeRenderer sr, SpriteBatch sb, float leftX, float upY) {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		
		sr.rect(xConverter(leftX+55), yConverter(upY+25), 250*(this.currentHp/(float)this.maxHp), 25);
		sr.end();
		sr.begin(ShapeType.Line);
		sr.setColor(Color.BLACK);
		sr.rect(xConverter(leftX+55), yConverter(upY+25), 250, 25);
		sr.end();
		
		sb.begin();
		font.draw(sb, "HP", xConverter(leftX), yConverter(upY));
		hpAndMpFont.draw(sb, "["+this.currentHp+"/"+this.maxHp+"]", xConverter(leftX+60), yConverter(upY+6), 246, Align.left, false);
		sb.end();
	}
	
	private float xConverter( float x ) {
		return x;
	}

	private float yConverter( float y ) {
		return VimFight.HEIGHT - y;
	}

}
