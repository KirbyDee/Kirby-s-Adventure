package com.kirbydee.gamestate.loading;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.animation.Animation;
import com.kirbydee.gamestate.GameStateManager;
import com.kirbydee.main.GamePanel;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoadingStateSuck extends LoadingState {	

	private static String ANIM_FILE = "/sprites/player/kirby_normal/suck.anim";
	
	public LoadingStateSuck(GameStateManager gsm) {
		super(gsm);
		
		wiggle = true;
	}
	
	@Override
	protected void loadAnimation() throws Exception {
		Animation animation = new Animation(ANIM_FILE, this);
		animation.setFacingRight(true);
		animation.start();
		animations.add(animation);
		
		// kirby
		Vector2f position = new Vector2f(GamePanel.WIDTH * 0.1F, GamePanel.HEIGHT * 0.9F);
		animation.setPosition(position);
		positionsKirby.add(position);
		velocitiesKirby.add(Vector2f.ZERO);
		
		// loading
		positionLoading = new Vector2f(GamePanel.WIDTH * 0.15F, GamePanel.HEIGHT * 0.9F);
		velocityLoading = Vector2f.ZERO;
		
		// accumulate
		numAnimations++;
	}
}
