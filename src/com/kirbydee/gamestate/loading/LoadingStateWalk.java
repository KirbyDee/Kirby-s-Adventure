package com.kirbydee.gamestate.loading;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.animation.Animation;
import com.kirbydee.gamestate.GameStateManager;
import com.kirbydee.main.GamePanel;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoadingStateWalk extends LoadingState {	

	private final static String ANIM_FILE = "/sprites/player/kirby_normal/walk.anim";
	private final static int SPAWN_PROB = 50;
	
	public LoadingStateWalk(GameStateManager gsm) {
		super(gsm);
	}
	
	@Override
	protected void loadAnimation() throws Exception {
		// facing right
		boolean facingRight = rand.nextBoolean();
		float velocity = rand.nextFloat()*2 + 1.0F;
		
		Animation animation = new Animation(ANIM_FILE, this);
		animation.setFacingRight(facingRight);
		animation.start();
		animations.add(animation);
		
		// kirby
		float x = facingRight ? 0 : GamePanel.WIDTH;
		float y = rand.nextFloat()*GamePanel.HEIGHT;
		Vector2f position = new Vector2f(x, y);
		animation.setPosition(position);
		positionsKirby.add(position);
		velocitiesKirby.add(new Vector2f(facingRight ? velocity : -velocity, 0));
		
		// loading
		positionLoading = new Vector2f(GamePanel.WIDTH * 0.02F, GamePanel.HEIGHT * 0.97F);
		velocityLoading = Vector2f.ZERO;
		
		// accumulate
		numAnimations++;
	}
	
	@Override
	public void update() throws Exception {
		if (rand.nextInt(SPAWN_PROB) == 0)
			loadAnimation();
		
		super.update();
	}
}
