/**
 *
 */
package vimControl;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.Input.Keys;

/**
 * @author 楊舜宇
 * @since 2016/5/28
 *
 */

public enum VimMode implements State<VimControl>{

	NORMAL() {
		@Override
		public void update(VimControl vim) {
			switch (vim.getKey()) {
			case Keys.I:
			case Keys.A:
			case Keys.O:
				vim.mode.changeState(INSERT);
				break;
			case Keys.SEMICOLON:
				vim.mode.changeState(COMMAND);
				break;
			default:
				break;
			}
		}
	},

	INSERT() {
		@Override
		public void update(VimControl vim) {
			switch (vim.getKey()) {
			case Keys.ESCAPE:
				vim.mode.changeState(NORMAL);
				break;
			default:
				break;
			}
		}
	},

	COMMAND() {
		@Override
		public void update(VimControl vim) {
			switch (vim.getKey()) {
			case Keys.ENTER:
				vim.proccessCommand();
			case Keys.ESCAPE:
				vim.mode.changeState(NORMAL);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void enter(VimControl vim) {

	}

	@Override
	public void update(VimControl entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(VimControl entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMessage(VimControl entity, Telegram telegram) {
		//not used
		return false;
	}

}
