package com.kirbydee.entity.movable.living;

import com.kirbydee.entity.EntityStateType;
import com.kirbydee.entity.movable.living.player.state.EntityStatePlayer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.animation.Animation.AnimationType;
import com.kirbydee.animation.Animations;
import com.kirbydee.entity.Entity;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.hud.HealthBar;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public class Slugger extends LivingEntity {
	
	// stored velocity
	private Vector2f velocityStored;

    // animation actions
	public enum AnimationSlugger implements AnimationType {
		WALK("/sprites/entities/slugger/walk.anim");
		
		private String animFileName;
        AnimationSlugger(String animFileName) {
			this.animFileName = animFileName;
		}
		
		@Override
		public String getAnimFileName() {
			return animFileName;
		}
	}
	
	
	// Builder
	public static class Builder extends LivingEntity.Builder {
		// optional fields
		private boolean right = true;
		
		// constructor
		public Builder(PlayState gameState) {
			super(gameState);
		}
		
		// set fields
		public Builder right(boolean val) { right = val; return this; }
		
		// build
		@Override
		public Entity build() throws Exception { return new Slugger(this); }
	}
	
	public Slugger(Builder builder) throws Exception {
		super(builder);
		this.right = builder.right;
		this.velocityStored = new Vector2f();

//		// create animations
//		animations = new Animations(this, playState);
//		animations.setAnimation(AnimationSlugger.WALK);
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
    public void initBars() throws Exception {
        addBar(new HealthBar(this, playState));
    }
	
	@Override
	public void computeNextVelocity() {
		super.computeNextVelocity();
		
		// if hitting a wall in x-direction, turn around
		if (velocity.getX() == 0 && right) {
			velocity.set(velocityStored.negateX());
			facingRight = false;
			facingLeft = true;
			right = false;
			left = true;
		} else if (velocity.getX() == 0 && left) {
			velocity.set(velocityStored.negateX());
			facingRight = true;
			facingLeft = false;
			right = true;
			left = false;
		}
		
		// if hitting a wall in y-direction, turn around
		if (velocity.getY() == 0 && up) {
			velocity.set(velocityStored.negateY());
			facingDown = true;
			facingUp = false;
			down = true;
			up = false;
		} else if (velocity.getY() == 0 && down) {
			velocity.set(velocityStored.negateY());
			facingDown = false;
			facingUp = true;
			down = false;
			up = true;
		}
		
		// need to store velocity, because the velocity gets erased when hitting a wall
		velocityStored.set(velocity);
	}

    @Override
    public void setNextAnimation() throws Exception {

    }

    @Override
    public void setNextSound() throws Exception {

    }

    // defensive copy
	public Vector2f getVelocityStored() {
		return new Vector2f(velocityStored);
	}

	@Override
	public Class<? extends AnimationType> getAnimationTypes() {
		return AnimationSlugger.class;
	}
}
