package game.Map;

import java.util.ArrayList;
import java.util.Random;

import game.Object.Creature;
import game.Object.Item;
import game.Object.Item.TYPE;
import game.Object.Player;
import game.Object.Tonic;
import game.Object.Trap;

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
		Random rand = new Random();
		switch (rand.nextInt(4)) {
		case 0:
			item = new Trap(TYPE.BOMB);
			break;
		case 1:
			item = new Trap(TYPE.FIRE);
			break;
		case 2:
			item = new Trap(TYPE.SPEAR);
			break;
		case 3:
			item = new Trap(TYPE.MOUSE_TRAP);
			break;
		}
	}

	public void addTonic() {
		Random rand = new Random();
		int choose = rand.nextInt(3);
		if(choose < 2) {
			item = new Tonic(TYPE.HP);
		}
		else {
			item = new Tonic(TYPE.MP);
		}
	}

	public boolean hasItem() {
		return (item == null);
	}

	public Item.TYPE getItemType() {
		if(item == null) return Item.TYPE.NONE;
		return item.getType();
	}

	public void touch(Player player) {
		item.touch(player);
		if(item.getType() == TYPE.HP || item.getType() == TYPE.MP) {
			item = null;
		}
	}

}
