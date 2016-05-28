/**
 *
 */
package vimControl;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

/**
 * @author 楊舜宇
 * @since 2016/5/28
 *
 */
public class VimControl {
	private int input;
	public StateMachine<VimControl, VimMode> mode;

	public VimControl() {
		input = Keys.ESCAPE;
		mode = new DefaultStateMachine<VimControl, VimMode>(this, VimMode.NORMAL);
	}

	public int getKey() {
		return input;
	}

	public void inputKey(int keycode) {
		input = keycode;
		mode.update();
	}

	public VimMode getCurrentState() {
		return mode.getCurrentState();
	}

	public void proccessCommand() {
		// TODO Auto-generated method stub

	}
}
