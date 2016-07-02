/**
 *
 */
package game.Object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.Map.Position;
import jdk.internal.dynalink.beans.StaticClass;

/**
 * @author 楊舜宇
 * @since 2016/7/2
 *
 */
abstract public class Item {
	Position pos;
	int textureID;

	abstract public int getTextureID();
	abstract public void touch(Player player);
}
