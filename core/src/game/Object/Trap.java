/**
 *
 */
package game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import game.Map.Position;

/**
 * @author 楊舜宇
 * @since 20616/7/2
 *
 */
public class Trap extends Item {
	private static Sound explode = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.mp3"));
	private static Sound fire = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.mp3"));
	private static Sound spear = Gdx.audio.newSound(Gdx.files.internal("sound/spear.mp3"));
	private static Sound mouse_trap = Gdx.audio.newSound(Gdx.files.internal("sound/mouse_trap.mp3"));
	Position pos;
	private Sound demageSound;
	int demage_amount;
	boolean isActive;

	public Trap(TYPE trapType) {
		type = trapType;
		switch (type) {
		case NONE:
			break;
		case BOMB:
			demage_amount = 300;
			demageSound = explode;
			break;
		case SPEAR:
			demageSound = spear;
			demage_amount = 100;
			break;
		case MOUSE_TRAP:
			demageSound = mouse_trap;
			demage_amount = 50;
			break;
		case FIRE:
			demageSound = fire;
			demage_amount = 150;
			break;
		}
	}

	@Override
	public void touch(Player player) {
		player.demage(demage_amount);
		demageSound.play();
	}

	@Override
	public TYPE getType() {
		return type;
	}
}
