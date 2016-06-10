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

public class Status {
	
	private BitmapFont font;
	private StringBuilder command;
	private int col;
	private int row;
	private String errMsg;
	private boolean isError;
	public static boolean ERROR = true;
	public static boolean NORMAL = false;
	
	public Status () {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("font/SourceCodePro-Regular.ttf")
				);
		FreeTypeFontParameter Parameter = new FreeTypeFontParameter();
		Parameter.size = 22;
		font = gen.generateFont( Parameter );
		font.setColor(Color.BLACK);
		
		command = new StringBuilder();
		col = 0;
		row = 0;
		isError = false;
	}
	
	public void append( String c ) {
		command.append(c);
	}
	
	public String getCommand() {
		return command.toString();
	}
	
	public void clear() {
		command.delete(0, command.length()-1);
	}
	
	public void draw( ShapeRenderer sr, SpriteBatch sb , float leftX, float upY) {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.GRAY);
		
		sr.rect(xConverter(leftX), yConverter(upY+25), 750, 25);
		sr.end();
		
		sb.begin();
		if(!isError){
			font.setColor(Color.BLACK);
			font.draw(sb, getCommand(), xConverter(leftX), yConverter(upY+5));
			font.draw(sb, getRow()+","+getCol(), xConverter(leftX+600), yConverter(upY+5), 50, Align.right, false);
		}
		else{
			font.setColor(Color.RED);
			font.draw(sb, this.errMsg, xConverter(leftX), yConverter(upY+5));;
		}
		sb.end();
	}
	
	public void setErr( String msg ){
		this.errMsg = msg;
	}
	
	public void setStatusState( boolean state ) {
		isError = state;
	}
	
	public void setCol( int col ) {
		this.col = col;
	}
	
	public void setRow( int row ) {
		this.row = row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getRow() {
		return this.row;
	}

	private float xConverter( float x ) {
		return x;
	}

	private float yConverter( float y ) {
		return VimFight.HEIGHT - y;
	}

}
