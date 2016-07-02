/**
 *
 */
package game.Object;

import game.Map.Position;
import static game.Object.Item.TYPE;

/**
 * @author 楊舜宇
 * @since 20616/7/2
 *
 */
public class Trap extends Item {
	Position pos;
	int demage_amount;
	boolean isActive;

	public Trap(TYPE trapType) {
		type = trapType;
		switch (type) {
		case NONE:
			break;
		case BOMB:
			demage_amount = 300;
			break;
		case SPEAR:
			demage_amount = 100;
			break;
		case MOUSE_TRAP:
			demage_amount = 50;
			break;
		case FIRE:
			demage_amount = 150;
			break;
		}
	}

	@Override
	public void touch(Player player) {
		player.demage(demage_amount);
	}

	@Override
	public TYPE getType() {
		return type;
	}
}
