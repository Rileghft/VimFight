/**
 *
 */
package game.Map;

import java.util.ArrayList;

/**
 * @author 楊舜宇
 * @since 2016/6/15
 *
 */
public class Position {
	public Integer x;
	public Integer y;
	private static float SQUARE_LENGTH = 30;

	public Position() {
		x = 0;
		y = 0;
	}

	public Position(int xPos, int yPos) {
		x = xPos;
		y = yPos;
	}

	public ArrayList<Float> getScreenPosition() {
		ArrayList<Float> screenPos = new ArrayList<Float>(2);
		screenPos.add(x * SQUARE_LENGTH);
		screenPos.add(y * SQUARE_LENGTH);
		return screenPos;
	}

	public Float getScreenX() {
		return x * SQUARE_LENGTH;
	}

	public Float getScreenY() {
		return y * SQUARE_LENGTH;
	}
}
