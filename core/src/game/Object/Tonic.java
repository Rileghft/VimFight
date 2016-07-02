/**
 *
 */
package game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * @author 楊舜宇
 * @since 2016/7/3
 *
 */
public class Tonic extends Item {
	private static int supply_amount = 400;
	private static Sound pickup = Gdx.audio.newSound(Gdx.files.internal("sound/pickup.mp3"));

	public Tonic(TYPE tonicTYpe) {
		type = tonicTYpe;
	}

	@Override
	public TYPE getType() {
		return type;
	}

	@Override
	public void touch(Player player) {
		if(type == TYPE.HP) {
			player.hp.plus(supply_amount);
		}
		else {
			player.mp.plus(supply_amount);
		}
		pickup.play();
	}

}
