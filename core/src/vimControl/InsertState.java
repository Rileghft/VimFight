/**
 *
 */
package vimControl;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

/**
 * @author 楊舜宇
 * @since 2016/5/29
 *
 */
public enum InsertState implements State<InsertMode>{
	TYPING() {
		@Override
		public void update(InsertMode insert) {
			int keycode = insert.getKey();
			if(keycode != Keys.ESCAPE) {
				insert.type(Keys.toString(keycode));
			}
		}
	};

	@Override
	public void enter(InsertMode entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(InsertMode entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(InsertMode entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMessage(InsertMode entity, Telegram telegram) {
		// TODO Auto-generated method stub
		return false;
	}

}
