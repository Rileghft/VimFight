package game.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class CharacterAnimation {
	
	private TextureAtlas charset;
	private static float FRAME_DURATION = .05f;
	private Animation downingAnimation;
	private Animation upingAnimation;
	private Animation rightingAnimation;
	private Animation leftingAnimation;
	private TextureRegion currentFrame;
	private float elapsed_time = 0f;
	private float origin_x, origin_y;
	private SpriteBatch sb;
	public enum State {STANDING, DOWNING, UPING, RIGHTING, LEFTING};
	private State currentState;
	private State previousState;
	private float dstX, dstY, vx, vy;
	
	public CharacterAnimation(String atlas){
		charset = new TextureAtlas(Gdx.files.internal(atlas));
		
		Array<AtlasRegion> downFrames = charset.findRegions("downing");
		Array<AtlasRegion> upFrames = charset.findRegions("uping");
		Array<AtlasRegion> rightFrames = charset.findRegions("righting");
		Array<AtlasRegion> leftFrames = charset.findRegions("lefting");
		
		
		
		downingAnimation = new Animation(FRAME_DURATION, downFrames, PlayMode.NORMAL);
		upingAnimation = new Animation(FRAME_DURATION, upFrames, PlayMode.NORMAL);
		rightingAnimation = new Animation(FRAME_DURATION, rightFrames, PlayMode.NORMAL);
		leftingAnimation = new Animation(FRAME_DURATION, leftFrames, PlayMode.NORMAL);
	
		this.currentState = State.STANDING;
		this.previousState = State.STANDING;
		//calculate the x and y position to center the image	
		origin_x = (Gdx.graphics.getWidth()/4*3 );
		origin_y = (Gdx.graphics.getHeight()/4*3);
		dstX = origin_x;
		dstY = origin_y;
		vx = 0;
		vy = 0;
	}
	
	public void setSpriteBatch( SpriteBatch sb ){
		this.sb = sb;
	}
	
	public void draw(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float dt = Gdx.graphics.getDeltaTime();
		elapsed_time += dt;
		currentFrame = getFrame(elapsed_time);
		origin_x = (origin_x + dt*vx)> dstX ? dstX : origin_x+dt*vx;
		origin_y = (origin_y + dt*vy)< dstY ? dstY : origin_y+dt*vy;
		System.out.println("origin_x, origin_y = " + origin_x+", " + origin_y);
		sb.begin();
		sb.draw(currentFrame, origin_x, origin_y);
		sb.end();
	}
	
	public void setOrgPos(float x, float y){
		origin_x = x;
		origin_y = y;
	}
	
	public void setDstPos(float x, float y){
		dstX = x;
		dstY = y;
		vx = (dstX-origin_x)/(float)0.5;
		vy = (dstY-origin_y)/(float)0.5;
	}
	
	private TextureRegion getFrame(float dt){
		TextureRegion region;
		switch (currentState) {
		case STANDING:
			region = downingAnimation.getKeyFrame(FRAME_DURATION);
			break;
		case DOWNING:
			region = downingAnimation.getKeyFrame(dt);
			break;
		case UPING:
			region = upingAnimation.getKeyFrame(dt);
			break;
		case RIGHTING:
			region = rightingAnimation.getKeyFrame(dt);
			break;
		case LEFTING:
			region = leftingAnimation.getKeyFrame(dt);
			break;
		default:
			region = downingAnimation.getKeyFrame(FRAME_DURATION);
			break;
		}
		if(elapsed_time > FRAME_DURATION*3)
			this.currentState = State.STANDING;
		return region;
	}
	
	public void startDown(){
		elapsed_time = 0;
		this.currentState = State.DOWNING;
	}
	
	public void startUp(){
		elapsed_time = 0;
		this.currentState = State.UPING;
	}

	public void startRight(){
		elapsed_time = 0;
		this.currentState = State.RIGHTING;
	}

	public void startLeft(){
			elapsed_time = 0;
			this.currentState = State.LEFTING;
	}
}
