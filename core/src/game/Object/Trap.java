/**
 *
 */
package game.Object;

import game.Map.Position;

/**
 * @author 楊舜宇
 * @since 20616/7/2
 *
 */
public class Trap extends Item {
	public enum Type {BOMB, SPEAR, MOUSE_TRAP, FIRE};
	Position pos;
	int demage_amount;
	boolean isActive;

	public Trap(Position pos, Type trapType) {
		textureID = trapType.ordinal();
		this.pos = pos;
		switch (trapType) {
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
	public int getTextureID() {
		return textureID;
	}
}
