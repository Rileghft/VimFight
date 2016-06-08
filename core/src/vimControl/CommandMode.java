/**
 *
 */
package vimControl;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

/**
 * @author 楊舜宇
 * @since 2016/5/29
 *
 */
public class CommandMode implements Mode {
	public StateMachine<CommandMode, CommandState> command;
	public VimControl modeControl;
	public int inputKey;
	private StringBuilder commandBuilder;

	public CommandMode(VimControl mode) {
		commandBuilder = new StringBuilder();
		command = new DefaultStateMachine<CommandMode, CommandState>(this, CommandState.NONE);
		modeControl = mode;
	}
	/* (non-Javadoc)
	 * @see vimControl.Mode#input(int)
	 */
	@Override
	public void input(int keycode) {
		inputKey = keycode;
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
		commandBuilder = new StringBuilder();
	}
	public void type(String c) {
		commandBuilder.append(c);
	}

	public void cancel() {
	}

	public void processCommand() {
		String commandStr = commandBuilder.toString();
		System.out.println(String.format("命令： %s",  commandStr));

	}

}
