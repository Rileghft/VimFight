package game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class BGM {
	
	private Music BGM;
	private boolean soundStoped;
	
	public BGM(String source){
		BGM = Gdx.audio.newMusic(Gdx.files.internal(source));
		BGM.setLooping(true);
		
		soundStoped = true;
	}
	
	public void startBGM() {
		// TODO Auto-generated method stub
		if(soundStoped){
			BGM.play();
			
			soundStoped = true;
		}
	}
	
	public void stopBGM() {
		// TODO Auto-generated method stub
		BGM.stop();
		soundStoped = true;
	}
}
