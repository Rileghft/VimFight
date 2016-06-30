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
 * @since 2016/5/28
 *
 */
public class VimControl {
	private int input;
	public StateMachine<VimControl, VimMode> mode;
	private Mode modeAdapter;
	private InsertMode insert;
	private NormalMode normal;
	private CommandMode command;
	private Status commandBar;

	public VimControl(Status commandBarRef) {
		input = Keys.ESCAPE;
		mode = new DefaultStateMachine<VimControl, VimMode>(this, VimMode.NORMAL);
		insert = new InsertMode(this);
		normal = new NormalMode(this);
		command = new CommandMode(this);
		modeAdapter = normal;
		commandBar = commandBarRef;
		command.cmdBar = commandBar;
	}

	public int getKey() {
		return input;
	}

	public void inputKey(char inputChar) {
		input = inputChar;
		mode.update();
	}

	public void passThroughKey(char inputChar) {
		modeAdapter.input(inputChar);
	}

	public VimMode getCurrentState() {
		return mode.getCurrentState();
	}

	public void modeSwitch(int endKey) {
		modeAdapter.exit(endKey);
		switch (mode.getCurrentState()) {
		case NORMAL:
			modeAdapter = normal;
			System.out.println("切換模式->[Normal Mode]");
			break;
		case INSERT:
			modeAdapter = insert;
			System.out.println("切換模式->[Insert Mode]");
			break;
		case COMMAND:
			commandBar.append(':');
			modeAdapter = command;
			System.out.println("切換模式->[Command Mode]");
			break;
		default:
			break;
		}
	}
}
