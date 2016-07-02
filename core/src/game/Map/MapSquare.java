package game.Map;

import java.util.ArrayList;


import game.Object.Creature;
import game.Object.Item;
import game.Object.Player;

public class MapSquare {
	private Character c;
	private boolean isBlocked;
	public Item item;
	private ArrayList<Creature> creatures;


	public MapSquare(Character character) {
		isBlocked = false;
		c = character;
		creatures = new ArrayList<Creature>();
	}

	public String getChar() {
		return c.toString();
	}

	public void setChar(Character character) {
		c = character;
	}

	public void addItem() {

	}

	public Item.TYPE getItemType() {
		if(item == null) return Item.TYPE.NONE;
		return item.getType();
	}

	public void touch(Player player) {
		item.touch(player);
	}

}
