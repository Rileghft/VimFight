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
	private String message;
	private boolean isError;

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

	public void append(char c ) {
		isError = false;
		command.append(c);
		message = command.toString();
	}

	public void backSpace() {
		int origin_len = command.length();
		command.setLength(origin_len - 1);
	}

	public String getCommand() {
		return command.toString();
	}

	public void clear() {
		command = new StringBuilder();
		message = "";
	}

	public void draw( ShapeRenderer sr, SpriteBatch sb , float leftX, float upY) {
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0.270f,0.262f,0.297f,1));

		sr.rect(xConverter(leftX), yConverter(upY+25), 600, 25);
		sr.end();

		sb.begin();
		if(!isError){
			font.setColor(Color.BLACK);
			font.draw(sb, getCommand(), xConverter(leftX), yConverter(upY+5));
		}
		else{
			font.setColor(Color.RED);
			font.draw(sb, this.message, xConverter(leftX), yConverter(upY+5));;
		}
		font.draw(sb, getRow()+","+getCol(), xConverter(leftX+500), yConverter(upY+5), 50, Align.right, false);
		sb.end();
	}

	public void setErr(String msg){
		isError = true;
		command = new StringBuilder();
		this.message = msg;
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
