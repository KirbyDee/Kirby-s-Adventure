package com.kirbydee.entity.movable;

import java.awt.Graphics2D;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.animation.Animations;
import com.kirbydee.entity.Entity;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.sound.Sounds;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class MovableEntity extends Entity implements Movable {
	
	// animations
//	protected Animations animations;
	
	// sounds
	protected Sounds sounds;
	
	// position
	protected boolean inAir;
	
	// velocity
	protected Vector2f velocity;
	
	// movement
	protected boolean noFacingChange;
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean falling;
	protected boolean rising;
	protected float maxMoveSpeed;
	protected float maxFallSpeed;
	protected float maxRiseSpeed;
	
	// acceleration
	protected Vector2f acceleration;
	protected Vector2f accelerationValues;
	protected Vector2f decelerationValues;
	protected Vector2f externalForce;
	protected boolean obeysGravity;
	
	// Entity Builder
	public static abstract class Builder extends Entity.Builder {
		// optional fields
		private Vector2f acceleration = new Vector2f();
		private Vector2f deceleration = new Vector2f();
		private Vector2f velocity = new Vector2f();
		private Vector2f externalForce = new Vector2f();
		private float maxMoveSpeed = -1;
		private float maxRiseSpeed = -1;
		private float maxFallSpeed = -1;
		private boolean obeysGravity = true;
		
		// constructor
		public Builder(PlayState gameState) {
			super(gameState);
		}
		
		// set fields
		public Builder maxMoveSpeed(float val) { maxMoveSpeed = val; return this; }
		public Builder maxRiseSpeed(float val) { maxRiseSpeed = val; return this; }
		public Builder maxFallSpeed(float val) { maxFallSpeed = val; return this; }
		public Builder externalForce(Vector2f val) { externalForce = new Vector2f(val); return this; } // defensive copy
		public Builder obeysGravity(boolean val) { obeysGravity = val; return this; }
		public Builder acceleration(Vector2f val) { acceleration = new Vector2f(val); return this; } // defensive copy
		public Builder deceleration(Vector2f val) { deceleration = new Vector2f(val); return this; } // defensive copy
		public Builder velocity(Vector2f val) { velocity = new Vector2f(val); return this; } // defensive copy
	}
	
	public MovableEntity(Builder builder) throws Exception {
		super(builder);
		this.accelerationValues = builder.acceleration;
		this.decelerationValues = builder.deceleration;
		this.externalForce = builder.externalForce;
		this.acceleration = new Vector2f();
		this.maxMoveSpeed = builder.maxMoveSpeed;
		this.maxRiseSpeed = builder.maxRiseSpeed;
		this.velocity = builder.velocity;
		this.maxFallSpeed = builder.maxFallSpeed;
		this.obeysGravity = builder.obeysGravity;
		this.noFacingChange = false;
		this.falling = false;
		this.rising = false;
	}
	
	// update entity
	@Override
	public void update() throws Exception {
		super.update();
		
		// get next acceleration
		computeNextAcceleration();
		
		// get next velocity
		computeNextVelocity();
		
		// get next position
		computeNextPosition();
		
//		// set next Animation
//		setNextAnimation();
//		
//		// set next Sound
//		setNextSound();
//		
//		// update animation
//		updateAnimations();
	}
	
//	protected void updateAnimations() {
//		animations.setPosition(positionScreen);
//		animations.setFacingRight(facingRight);
//		animations.update();
//	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
        super.render(g);

		// draw animation
//		animations.render(g);
	}
	
	@Override
	public void computeNextAcceleration() {
		// external force
		Vector2f externalAcceleration = externalForce.div(mass);
		float ddx = externalAcceleration.getX();
		float ddy = externalAcceleration.getY();
		
		// movement x-direction
		if (left) {
			// active left acceleration
			ddx -= accelerationValues.getX();
		} else if (right) {
			// active right acceleration
			ddx += accelerationValues.getX();
		} else {
			// passive deceleration
			if(velocity.getX() > 0) {
				ddx -= decelerationValues.getX();
			} else if(velocity.getX() < 0) {
				ddx += decelerationValues.getX();
			}
		}
		
		// if in air: passive fall acceleration
		if(inAir)
			ddy += 0.2; // TODO
//			ddy += gameState.getTileMapController().getGravity();
		
		// get current acceleration
		acceleration.set(ddx, ddy);
		
		// reset external force for next interaction
		externalForce.zero();
	}
	
	@Override
	public void computeNextVelocity() {
		// expected velocity
		velocity.addLocal(acceleration);
		float dx = velocity.getX();
		float dy = velocity.getY();
		
		// cap velocity x-direction
		if (left) {
			// left movement
			if (maxMoveSpeed > 0 && dx < -maxMoveSpeed) {
				dx = -maxMoveSpeed;
			}
		} else if (right) {
			// right movement
			if (maxMoveSpeed > 0 && dx > maxMoveSpeed) {
				dx = maxMoveSpeed;
			}
		} else {
			// stopped moving
			if(acceleration.getX() < 0 && dx < 0 && dx > -decelerationValues.getX()) {
				dx = 0;
			} else if(acceleration.getX() > 0 && dx > 0 && dx < decelerationValues.getX()) {
				dx = 0;
			}
		}
		
		// cap velocity y-direction
		if (inAir) {
			if (falling) {
				// falling
				if(maxFallSpeed > 0 && dy > maxFallSpeed) {
					dy = maxFallSpeed;
				}
			} else {
				// rising
				if(maxRiseSpeed > 0 && dy > -maxRiseSpeed) {
					dy = -maxRiseSpeed;
				}
			}
		}
		
		// set velocity
		velocity.set(dx, dy);
	}
	
	@Override
	public void computeNextPosition() {
		int tileSize = playState.getTileSize();
		
		// get current row and col
		currCol = (int)position.getX() / tileSize;
		currRow = (int)position.getY() / tileSize;
		
		// expected position
		float x_now= position.getX();
		float y_now = position.getY();
		position.addLocal(velocity);
		float x_next = position.getX();
		float y_next = position.getY();
		float x = x_next;
		float y = y_next;
		
		// check for collision when moving in x-direction
		checkTileCollision(x_next, y_now);
		if (velocity.getX() < 0){
			if (topLeft || bottomLeft) {
				velocity.setX(0);
				x = currCol * tileSize + width / 2;
			}
		} else if (velocity.getX() > 0) {
			if (topRight || bottomRight) {
				velocity.setX(0);
				x = (currCol + 1) * tileSize - width / 2;
			}
		}
		
		// check for collision when moving in y-direction
		checkTileCollision(x_now, y_next);
		if (velocity.getY() < 0) {
			if (topLeft || topRight) {
				velocity.setY(0);
				y = currRow * tileSize + height / 2;
			}
		}else if (velocity.getY() > 0) {
			if (bottomLeft || bottomRight) {
				velocity.setY(0);
				y = (currRow + 1) * tileSize - height / 2;
			}
		}
		
		// check if in air
		checkTileCollision(x_now, y_next + 1);
		if (!bottomLeft && !bottomRight)
			inAir = true;
		else
			inAir = false;
		
		// check if falling
		if (inAir && velocity.getY() > 0)
			falling = true;
		else
			falling = false;
		
		// set point
		position.set(x, y);
	}
	
	@Override
	public void setLeft(boolean left) {
		this.left = left;
		if (!noFacingChange) {
			this.facingLeft = true;
			this.facingRight = false;
		}
	}

	@Override
	public void setRight(boolean right) {
		this.right = right;
		if (!noFacingChange) {
			this.facingRight = true;
			this.facingLeft = false;
		}
	}

	@Override
	public void setUp(boolean up) {
		this.up = up;
		if (!noFacingChange) {
			this.facingUp = true;
			this.facingUp = false;
		}
	}

	@Override
	public void setDown(boolean down) {
		this.down = down;
		if (!noFacingChange) {
			this.facingDown = true;
			this.facingUp = false;
		}
	}
	
	public void addExternalForce(Vector2f v) {
		this.externalForce.addLocal(v);
	}
	
	// defensive copy
	public Vector2f getAcceleration() {
		return new Vector2f(acceleration);
	}
	
	// defensive copy
	public Vector2f getAccelerationValues() {
		return new Vector2f(accelerationValues);
	}
	
	// defensive copy
	public Vector2f getDecelerationValues() {
		return new Vector2f(decelerationValues);
	}
	
	// defensive copy
	public Vector2f getExternalForce() {
		return new Vector2f(externalForce);
	}
}
