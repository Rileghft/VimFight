/**
 *
 */
package vimControl;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

/**
 *
 * @author 楊舜宇
 * @since 2016/5/29
 */

public enum CommandState implements State<CommandMode> {
	NONE() {
		@Override
		public void update(CommandMode command) {
			int key = command.getKey();
			switch (key) {
			case GameKeys.ESC: case GameKeys.ENTER:
				command.exit(key);
				break;
			default:
				command.type(Keys.toString(key));
				break;
			}
		}
	};

	@Override
	public void enter(CommandMode entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(CommandMode entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(CommandMode entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMessage(CommandMode entity, Telegram telegram) {
		// TODO Auto-generated method stub
		return false;
	}

}
