package com.kirbydee.gamestate.loading;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.animation.Animation;
import com.kirbydee.gamestate.GameStateManager;
import com.kirbydee.main.GamePanel;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoadingStateTaunt extends LoadingState {	

	private static String ANIM_FILE = "/sprites/player/kirby_normal/taunt.anim";
	
	public LoadingStateTaunt(GameStateManager gsm) {
		super(gsm);
	}
	
	@Override
	protected void loadAnimation() throws Exception {
		Animation animation = new Animation(ANIM_FILE, this);
		animation.setFacingRight(true);
		animation.start();
		animations.add(animation);
		
		// kirby
		positionsKirby.add(new Vector2f(GamePanel.WIDTH *0.5F, GamePanel.HEIGHT * 0.5F));
		velocitiesKirby.add(Vector2f.ZERO);
		
		// loading
		positionLoading = new Vector2f(GamePanel.WIDTH * 0.02F, GamePanel.HEIGHT * 0.97F);
		velocityLoading = Vector2f.ZERO;
		
		// accumulate
		numAnimations++;
	}
}
