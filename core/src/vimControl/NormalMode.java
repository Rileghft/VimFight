/**
 *
 */
package vimControl;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

/**
 * @author 楊舜宇
 * @since 2016/5/29
 *
 */
public class NormalMode implements Mode{
	public StateMachine<NormalMode, NormalState> normal;
	public int inputKeycode;
	public VimControl modeControl;

	public NormalMode(VimControl mode) {
		normal = new DefaultStateMachine<NormalMode, NormalState>(this, NormalState.NONE);
		modeControl = mode;
	}

	@Override
	public void input(int keycode) {
		inputKeycode = keycode;
		normal.update();
	}

	@Override
	public int getKey() {
		return inputKeycode;
	}

	@Override
	public void exit(int endKey) {
		// TODO Auto-generated method stub

	}

}
