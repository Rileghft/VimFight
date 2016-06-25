package game.Map;

public class MapSquare {
	private Character c;


	public MapSquare(Character character) {
		c = character;
	}

	public String getChar() {
		return c.toString();
	}

	public void setChar(Character character) {
		c = character;
	}

}
