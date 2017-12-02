package com.kirbydee.entity.movable.living.player;

import java.util.Random;

import com.kirbydee.animation.Animations;
import com.kirbydee.entity.movable.living.player.state.*;
import com.kirbydee.sound.Sounds;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.animation.Animation.AnimationType;
import com.kirbydee.entity.Entity;
import com.kirbydee.entity.EntityStateType;
import com.kirbydee.entity.movable.FireBall;
import com.kirbydee.entity.movable.living.LivingEntity;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.hud.FireBar;
import com.kirbydee.hud.HealthBar;
import com.kirbydee.sound.Sound.SoundType;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public class Player extends LivingEntity {

	// player stuff
//	private int fire;
//	private int maxFire;

	// fireball
//	private boolean firing;
//	private int fireCost;
//	private int fireBallDamage;
//	private boolean spawnFireBall;

	// idle
//	private Random rand;
//	private boolean idle;

	// scratch
//	private boolean scratching;
//	private int scratchDamage;
//	private int scratchRange;
//	private boolean spawnScratch;

	// gliding
//	private boolean gliding;

//	// entity states
//	public enum EntityStatePlayer implements EntityStateType {
//		IDLE,
//        TURN,
//        WALK,
//        JUMP,
//        SALTO,
//        FALL
////        FIREBALL
//	}

	// animation actions
//	public enum AnimationPlayer implements AnimationType {
//		IDLE("/sprites/player/kirby_normal/idle.anim"),
//		TURN("/sprites/player/kirby_normal/turn.anim"),
//		WALK("/sprites/player/kirby_normal/walk.anim"),
//		JUMP("/sprites/player/kirby_normal/jump.anim"),
//		SALTO("/sprites/player/kirby_normal/salto.anim"),
//		FALL("/sprites/player/kirby_normal/fall.anim");
////		GLID("/sprites/player/kirby_normal/air_turn.anim"),
////		FIREBALL("/sprites/player/kirby_normal/suck.anim");
////		SCRATCH("/sprites/player/kirby_normal/suck_end.anim");
//
//		private String animFileName;
//		AnimationPlayer(String animFileName) {
//			this.animFileName = animFileName;
//		}
//
//		@Override
//		public String getAnimFileName() {
//			return animFileName;
//		}
//	}

	// sounds
//	public enum SoundPlayer implements SoundType {
//		JUMP("/sounds/player/jump.sound"),
//		FIREBALL("/sounds/player/fireball.sound"),
//		DIE("/sounds/player/die.sound"),
//		FALL("/sounds/player/fall.sound"),
//		HIT("/sounds/player/hit.sound"),
//		IDLE_ALMOST_DEAD("/sounds/player/idle_almost_dead.sound"),
//		LAND("/sounds/player/land.sound");
////		SCRATCH("/sounds/player/scratch.sound");
//
//		private String soundFileName;
//		SoundPlayer(String soundFileName) {
//			this.soundFileName = soundFileName;
//		}
//
//		@Override
//		public String getSoundFileName() {
//			return soundFileName;
//		}
//	}


	// Builder
	public static class Builder extends LivingEntity.Builder {
		// optional fields
//		private int fire = 2500;
//		private int maxFire = 2500;
//		private int fireCost = 500;
//		private int fireBallDamage = 1;
//		private int scratchDamage = 1;
//		private int scratchRange = 40;

		// constructor
		public Builder(PlayState gameState) {
			super(gameState);
		}

		// set fields
//		public Builder fire(int val) { fire = val; return this; }
//		public Builder maxFire(int val) { maxFire = val; return this; }
//		public Builder fireCost(int val) { fireCost = val; return this; }
//		public Builder fireBallDamage(int val) { fireBallDamage = val; return this; }
//		public Builder scratchDamage(int val) { scratchDamage = val; return this; }
//		public Builder scratchRange(int val) { scratchRange = val; return this; }

		// build
		@Override
		public Entity build() throws Exception { return new Player(this); }
	}

	public Player(Builder builder) throws Exception {
		super(builder);
//		this.fire = ((Builder) builder).fire;
//		this.maxFire = ((Builder) builder).maxFire;
//		this.fireCost = ((Builder) builder).fireCost;
//		this.fireBallDamage = ((Builder) builder).fireBallDamage;
//		this.scratchDamage = ((Builder) builder).scratchDamage;
//		this.scratchRange = ((Builder) builder).scratchRange;
//		this.spawnFireBall = false;
//		this.spawnScratch = false;
//		this.idle = false;
//		this.rand = new Random();

		// create animations
//        this.animations = new Animations(this, playState);
//        this.animations.setAnimation(AnimationPlayer.IDLE);
//
//		// create sounds
//        this.sounds = new Sounds(SoundPlayer.class, playState);
	}

	@Override
	public void initStates() throws Exception {
		addState(new PlayerIdleState(this, EntityStatePlayer.State.IDLE));
//		addState(new PlayerWalkState(this, EntityStatePlayer.TURN));
		addState(new PlayerWalkState(this, EntityStatePlayer.State.WALK));
		addState(new PlayerJumpState(this, EntityStatePlayer.State.JUMP));
		addState(new PlayerSaltoState(this, EntityStatePlayer.State.SALTO));
		addState(new PlayerFallState(this, EntityStatePlayer.State.FALL));
//		addState(new PlayerWalkState(this, EntityStatePlayer.FIREBALL));
	}

    @Override
    public EntityStateType getDefaultState() {
        return EntityStatePlayer.State.IDLE;
    }

    @Override
    public void initBars() throws Exception {
//        addBar(new FireBar(this, playState));
        addBar(new HealthBar(this, playState));
    }

	@Override
	public void update() throws Exception {
		super.update();

		// update fire
//		updateFire();

		// spawn fireball
//		if (spawnFireBall) {
//			spawnFireBall = false;
//			spawnFireBall();
//		}

//		// spawn scratch
//		if (spawnScratch) {
//			spawnScratch = false;
//			spawnScratch();
//		}
	}

	@Override
	public void computeNextAcceleration() {
		super.computeNextAcceleration();

//		// slower falling when gliding
//		if(falling && gliding)
//			acceleration.set(acceleration.getX(), acceleration.getY() * 0.1F);
	}

	@Override
	public void computeNextVelocity() {
		super.computeNextVelocity();

		// cannot move while attacking, except in air
//		if((animations.getCurrentAnimationType() == AnimationPlayer.SCRATCH || animations.getCurrentAnimationType() == AnimationPlayer.FIREBALL) && !inAir)
//		if(animations.getCurrentAnimationType() == AnimationPlayer.FIREBALL && !inAir)
//			velocity.set(0, velocity.getY());
	}

	@Override
	public void setNextAnimation() {
		// if scratching
//		if (scratching) {
//			if(!animations.setAnimation(AnimationPlayer.SCRATCH)) {
//				if (animations.isPlayedOnce()) {
//					scratching = false;
//				}
//			} else {
//				spawnScratch = true;
//			}
//		}

		// if firing
//		if (firing) {
//			if (fire > fireCost) {
//				if(!animations.setAnimation(AnimationPlayer.FIREBALL)) {
//					if (animations.isPlayedOnce()) {
//						firing = false;
//					}
//				} else {
//					spawnFireBall = true;
//				}
//			} else {
//				firing = false; // TODO
//			}
//		}

		// if velocity downwards
//		else if (velocity.getY() > 0) {
////			if (gliding) {
////				animations.setAnimation(AnimationPlayer.GLID);
////			} else {
//				animations.setAnimation(AnimationPlayer.FALL);
////			}
//		}

		// if velocity upwards
//		else if (velocity.getY() < 0) {
//			animations.setAnimation(AnimationPlayer.JUMP);
//
//			if (animations.isPlayedOnce())
//				animations.stopCurrentAnimation();
//		}

		// if walking
//		else if(left || right) {
//			animations.setAnimation(AnimationPlayer.WALK);
//		}

		// if idle
//		else {
//			if (animations.setAnimation(AnimationPlayer.IDLE))
//				idle = false;
//
//			// random idle animation
//			if (!idle) {
//				if (rand.nextInt(50) == 0) {
//					idle = true;
//					animations.startCurrentAnimation();
//				} else {
//					animations.stopCurrentAnimation();
//				}
//			} else if (animations.isPlayedOnce()) {
//				idle = false;
//			}
//		}

		// movement allowed?
//		if (firing || scratching) {
//        noFacingChange = firing;
	}

	@Override
	public void setNextSound() {
		// fireball
//		if (spawnFireBall)
//			sounds.playSound(SoundPlayer.FIREBALL);

		// jump
//		if (jumpStart)
//			sounds.playSoundRandom(SoundPlayer.JUMP);
//
//		// scratch
//		if (spawnScratch)
//			sounds.playSoundRandom(SoundPlayer.SCRATCH);
	}

	@Override
	public boolean getsHit(int damage, boolean l, boolean r, boolean t, boolean b) {
		boolean gotHit = super.getsHit(damage, l, r, t, b);

		if (gotHit) {
			// player bounces a little bit backwards
			if (l && !r)
				addExternalForce(new Vector2f(2*mass, -2*mass));
			else if (r && !l)
				addExternalForce(new Vector2f(-2*mass, -2*mass));
			else
				addExternalForce(new Vector2f(0, -2*mass));

			// TODO: set animation to hit
		}

		return gotHit;
	}

//	private void updateFire() {
//		fire++;
//		if (fire > maxFire)
//			fire = maxFire;
//	}

//	private void spawnFireBall() {
//		try {
//			fire -= fireCost;
//			new FireBall.Builder(playState, facingRight).velocity(velocity.add(2 * (facingRight ? 1 : -1), -0.5F)) // MovableEntity
//				.position(new Vector2f(position.add(facingRight ? width : -width, 0))).width(14).height(14).entityType(entityType).onTouchDamage(fireBallDamage).build(); // Entity
//		} catch(Exception e) {
//            playState.error("FireBall could not be created due to Exception at loading its sprites!", e);
//		}
//	}

    @Override
    public Class<? extends AnimationType> getAnimationTypes() {
        return null;
    }

//	@Override
//	public Class<? extends AnimationType> getAnimationTypes() {
//		return AnimationPlayer.class;
//	}

//	private void spawnScratch() {
//		try {
//			new Scratch.Builder(gameState, 1000*1000*50).onTouchDamage(scratchDamage).position(position.add(width * (facingRight ? 1 : -1), 0))
//				.width(scratchRange).height(scratchRange).entityType(entityType).build();
//		} catch(Exception e) {
//			System.err.println("Scratch could not be created due to Exception at loading its sprites!");
//			e.printStackTrace();
//		}
//	}
}
