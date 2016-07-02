/**
 *
 */
package game.Object;


import game.Map.Position;

/**
 * @author 楊舜宇
 * @since 2016/7/2
 *
 */
abstract public class Item {
	public enum TYPE{NONE, BOMB, FIRE, SPEAR, MOUSE_TRAP};
	TYPE type;
	Position pos;

	abstract public Item.TYPE getType();
	abstract public void touch(Player player);
}
