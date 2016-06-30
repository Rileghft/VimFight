package game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class BGM {
	
	private Sound BGM;
	private boolean soundStoped;
	
	public BGM(String source){
		BGM = Gdx.audio.newSound(Gdx.files.internal(source));
		soundStoped = true;
	}
	
	public void startBGM() {
		// TODO Auto-generated method stub
		if(soundStoped){
			BGM.loop();
			soundStoped = true;
		}
	}
	
	public void stopBGM() {
		// TODO Auto-generated method stub
		BGM.stop();
		soundStoped = true;
	}
}
