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
			int key = vim.getKey();
			switch (key) {
			case Keys.I:
			case Keys.A:
			case Keys.O:
				vim.mode.changeState(INSERT);
				vim.modeSwitch(key);
				break;
			case Keys.SEMICOLON:
				vim.mode.changeState(COMMAND);
				vim.modeSwitch(key);
				break;
			default:
				vim.passThroughKey(key);
				break;
			}
		}
	},

	INSERT() {
		@Override
		public void update(VimControl vim) {
			int key = vim.getKey();
			switch (key) {
			case Keys.ESCAPE:
				vim.mode.changeState(NORMAL);
				vim.modeSwitch(key);
				break;
			default:
				vim.passThroughKey(key);
				break;
			}
		}
	},

	COMMAND() {
		@Override
		public void update(VimControl vim) {
			int key = vim.getKey();
			switch (key) {
			case Keys.ENTER:
			case Keys.ESCAPE:
				vim.mode.changeState(NORMAL);
				vim.modeSwitch(key);
				break;
			default:
				vim.passThroughKey(key);
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
