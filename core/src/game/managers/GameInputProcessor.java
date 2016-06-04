package game.managers;

import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {
	//private Player actor;
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		//這邊要把 int 型態的character 傳進去actor裡面去
		//System.out.printf("%c, %d\n", character, (int)character);
		return super.keyTyped(character);
	}
	/*public boolean setActor( Player act) {
		actor = act;
	}*/
}
