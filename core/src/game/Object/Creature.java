/**
 *
 */
package game.Object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author 楊舜宇
 *  @since 2016/7/2
 *
 */
public interface Creature {

	public int getRow();
	public int getCol();
	public void setRow(int row);
	public void setCol(int col);
	public void demage(int demage_amount);
	public boolean isDead();
	public void draw(SpriteBatch batch);
	public void update();
}
