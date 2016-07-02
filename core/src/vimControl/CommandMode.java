/**
 *
 */
package vimControl;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

import game.component.Status;

/**
 * @author 楊舜宇
 * @since 2016/5/29
 *
 */
public class CommandMode implements Mode {
	public StateMachine<CommandMode, CommandState> command;
	public VimControl modeControl;
	public int inputKey;

	public CommandMode(VimControl mode) {
		command = new DefaultStateMachine<CommandMode, CommandState>(this, CommandState.NONE);
		modeControl = mode;
	}
	/* (non-Javadoc)
	 * @see vimControl.Mode#input(int)
	 */
	@Override
	public void input(char inputChar) {
		inputKey = inputChar;
		command.update();
	}

	/* (non-Javadoc)
	 * @see vimControl.Mode#getKey()
	 */
	@Override
	public int getKey() {
		return inputKey;
	}

	/* (non-Javadoc)
	 * @see vimControl.Mode#exit()
	 */
	@Override
	public void exit(int endKey) {
		if(endKey != Keys.ESCAPE) {
			processCommand();
		}
		inputKey = -1;
	}
	public void type(String c) {
	}

	public void cancel() {
	}

	public void processCommand() {
	}

}
