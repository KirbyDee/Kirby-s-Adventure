package com.kirbydee.animation;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

import com.kirbydee.animation.Animation.AnimationType;
import com.kirbydee.gamestate.GameState;
import com.kirbydee.main.Controlable;
import com.kirbydee.utils.Vector2f;

/*
 * Animation file:
 * > numAnimations
 * > name filename number height width1 width2 width3 ... delay
 */
@Data
public class Animations implements Controlable { // get rid of animations -> when implemented entityStates
	
	private Animatable animatable;
	private Map<AnimationType, Animation> animations;
	private Animation currentAnimation;
	private Animation previousAnimation;
	private AnimationType currentAnimationType;
	private AnimationType previousAnimationType;
	private int numAnimations;
	
	public Animations(Animatable animatable, GameState gameState) throws Exception {
		this.animatable = animatable;
		Class<? extends AnimationType> animationSet = animatable.getAnimationTypes();
		this.animations = new ConcurrentHashMap<>(animationSet.getEnumConstants().length);
		for (AnimationType animType : animationSet.getEnumConstants()) {
			String animFileName = animType.getAnimFileName();
			animations.put(animType, new Animation(animFileName, gameState));
		}
	}
	
	public boolean setAnimation(AnimationType anim) {
		return setCurrentAnimation(anim, true);
	}
	
	public boolean setAnimation(AnimationType anim, int delay) {
		boolean b = setCurrentAnimation(anim, false);
		currentAnimation.setDelay(delay);
		
		return b;
	}
	
	public boolean setCurrentAnimation(AnimationType anim, boolean useDefaultDelay) {
		if (currentAnimationType != anim) {
			previousAnimation = currentAnimation;
			previousAnimationType = currentAnimationType;
			currentAnimation = animations.get(anim);
			currentAnimationType = anim;
			currentAnimation.setUseDefaultDelay(useDefaultDelay);
			currentAnimation.start();
			
			return true;
		} else {
			return false;
		}
	}
	
	public void startCurrentAnimation() {
		currentAnimation.start();
	}
	
	public void stopCurrentAnimation() {
		currentAnimation.stop();
	}
	
	public void resumeCurrentAnimation() {
		currentAnimation.resume();
	}
	
	@Override
	public void update() {
		currentAnimation.update();
	}
	
	@Override
	public void render(Graphics2D g) {
		currentAnimation.render(g);
	}
	
	@Override
	public void destroy() {
		for (AnimationType type : animations.keySet()) {
			Animation animation = animations.remove(type);
			animation.destroy();
			animation = null;
		}
	}
	
	public boolean isPlayedOnce() {
		return currentAnimation.isPlayedOnce();
	}
	
	public void setPosition(Vector2f position) {
		currentAnimation.setPosition(position);
	}
	
	public void setFacingRight(boolean facingRight) {
		currentAnimation.setFacingRight(facingRight);
	}
}
