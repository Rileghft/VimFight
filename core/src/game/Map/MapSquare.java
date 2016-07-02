package game.Map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.Object.Creature;
import game.Object.Item;
import game.Object.Trap;

public class MapSquare {
	private Character c;
	private boolean isBlocked;
	public ArrayList<Item> items;
	private ArrayList<Creature> creatures;


	public MapSquare(Character character) {
		isBlocked = false;
		c = character;
		items = new ArrayList<Item>();
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

}
