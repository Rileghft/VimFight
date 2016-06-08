/**
 *
 */
package vimControl;

/**
 * @author 楊舜宇
 * @since 2016/5/29
 *
 */
public interface Mode {
	public void input(int keycode);
	public int getKey();
	public void exit(int endKey);
}
