package game.managers;

import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {
	//private Player actor;
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		//�o��n�� int ���A��character �Ƕi�hactor�̭��h
		//System.out.printf("%c, %d\n", character, (int)character);
		return super.keyTyped(character);
	}
	/*public boolean setActor( Player act) {
		actor = act;
	}*/
}
