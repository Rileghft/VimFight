package game.Map;

public class MapSquare {
	
	private String text;
	
	public MapSquare( String text ) {
		this.text = text;
	}
	
	public void setText( String text ) {
		this.text = text;
	}
	public String getText() {
		return this.text;
	}
}
