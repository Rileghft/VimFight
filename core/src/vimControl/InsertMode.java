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
public class InsertMode implements Mode{
	public StateMachine<InsertMode, InsertState> insert;
	public int inputKey;
	private VimControl modeControl;

	public InsertMode(VimControl mode) {
		insert = new DefaultStateMachine<InsertMode, InsertState>(this, InsertState.TYPING);
		modeControl = mode;
	}

	@Override
	public void input(char inputChar) {
		inputKey = inputChar;
		insert.update();
	}

	public int getKey() {
		return inputKey;
	}

	public void type(String inputChar) {
		System.out.println(String.format("輸入字元： %s",  inputChar));
	}

	public void exit(int endKey) {
		System.out.println("離開Insert Mode，回到Normal Mode");
		modeControl.inputKey((char)inputKey);
		inputKey = -1;
	}

}
