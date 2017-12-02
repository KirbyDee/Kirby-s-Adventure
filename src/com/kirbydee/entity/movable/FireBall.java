package com.kirbydee.entity.movable;

import com.kirbydee.entity.EntityStateType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.animation.Animation.AnimationType;
import com.kirbydee.animation.Animations;
import com.kirbydee.entity.Entity;
import com.kirbydee.gamestate.play.PlayState;

@Data
@EqualsAndHashCode(callSuper=false)
public class FireBall extends MovableEntity {
	
	// fireball stuff
	private boolean hit;
	
	// animation actions
	public enum FireBallAnimation implements AnimationType {
		FIRE("/sprites/spells/fireball/fire.anim"),
		EXPLODE("/sprites/spells/fireball/explode.anim");
		
		String animFileName;
		private FireBallAnimation(String animFileName) {
			this.animFileName = animFileName;
		}
		
		@Override
		public String getAnimFileName() {
			return animFileName;
		}
	}
	
	// Builder
	public static class Builder extends MovableEntity.Builder {
		// mandatory fields
		private final boolean facingRight;
		
		// constructor
		public Builder(PlayState gameState, boolean facingRight) {
			super(gameState);
			this.facingRight = facingRight;
		}
		
		// build
		@Override
		public Entity build() throws Exception { return new FireBall(this); }
	}
	
	public FireBall(Builder builder) throws Exception {
		super(builder);
		this.facingRight = ((Builder) builder).facingRight;
		this.right = facingRight;
		this.left = !facingRight;
		this.hit = false;
		
		// create animations
//		animations = new Animations(this, playState);
//
		// set default animation
//		animations.setAnimation(FireBallAnimation.FIRE);
	}
	
	@Override
	public void initStates() throws Exception {
		// TODO
	}

    @Override
    public EntityStateType getDefaultState() {
        return null;
    }

    @Override
	public void update() throws Exception {
		super.update();
		
		// check if hit a tile
		checkTileCollision();
	}
	
	@Override
	public void setNextAnimation() throws Exception {		
//		// when hitting something
//		if (hit && animations.getCurrentAnimationType() != FireBallAnimation.EXPLODE) {
//			// set animation
//			animations.setAnimation(FireBallAnimation.EXPLODE);
//
//			// does no damage while exploding
//			onTouchDamage = 0;
//
//			// stop direction
//			right = left = false;
//
//			// make it slower in x-direction
//			if (velocity.getX() != 0)
//				decelerationValues.set((float) Math.abs(velocity.getX()*0.15), 0);
//		}
//
//		// destroy fireball when hit and played animation
//		if (hit && animations.isPlayedOnce())
//			destroy();
	}
	
	@Override
	public void setNextSound() {
		// do nothing
	}
	
	@Override
	public boolean getsHit(int damage, boolean left, boolean right, boolean top, boolean bottom) {
		if (!hit)
			hit = true;
		
		return true;
	}
	
	public void checkTileCollision() {
		if (!hit && collidedWithTile)
			hit = true;
	}

	@Override
	public Class<? extends AnimationType> getAnimationTypes() {
		return FireBallAnimation.class;
	}
}
