package com.kirbydee.entity.movable.living;

import java.awt.Graphics2D;
import java.util.HashMap;

import com.kirbydee.hud.HUDBar;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.entity.movable.MovableEntity;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.hud.HUD;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class LivingEntity extends MovableEntity {
	// jumping
	protected boolean jump;
	protected boolean jumpStart;
	protected Vector2f jumpForce;
	
	// HUD
	protected HUD hud;
	
	// attributes
	protected int health;
	protected int maxHealth;
	protected int damage;
	protected boolean isEnemy;
	protected boolean isDead;
	protected boolean flinching;
	protected long flinchTimer;
	protected int numFlinched;
	protected boolean isHit;
	protected boolean isInvunarable;
	
	
	// Entity Builder
	public static abstract class Builder extends MovableEntity.Builder {
		// optional fields
		protected Vector2f jumpForce;
		private int health = 1;
		private boolean isEnemy = false;
		private int damage = 0;
		private boolean isInvunarable = false;
		
		// constructor
		public Builder(PlayState gameState) {
			super(gameState);
		}
		
		// set fields
		public Builder jumpForce(Vector2f val) { jumpForce = new Vector2f(val); return this; } // defensive copy
		public Builder health(int val) { health = val; return this; }
		public Builder enemy(boolean val) { isEnemy = val; return this; }
		public Builder invunarable(boolean val) { isInvunarable = val; return this; }
		public Builder damage(int val) { damage = val; return this; }
	}
	
	public LivingEntity(Builder builder) throws Exception {
		super(builder);
		
		this.jumpForce = builder.jumpForce;
		this.maxHealth = builder.health;
		this.health = builder.health;
		this.isEnemy = builder.isEnemy;
		this.damage = builder.damage;
		this.isInvunarable = builder.isInvunarable;
		this.isDead = false;
		this.jumpStart = false;
		this.numFlinched = 0;
		
		// living entity has a HUD
		this.hud = new HUD();
        initBars();
	}

    // add states
    public abstract void initBars() throws Exception;

    // add state
    public void addBar(HUDBar<? extends LivingEntity> bar) throws Exception {
        hud.addBar(bar);
    }
	
	@Override
	public void update() throws Exception {
		super.update();
		
		// does not jump
		jumpStart = false;
		
		// check if dead
		checkIsDead();
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {			
		// flinch entity if hit
		if(flinching) {
			if (numFlinched >= 20) {
				numFlinched = 0;
				flinching = false;
				isInvunarable = false;
			} else {
				long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
				if(elapsed / 50 % 3 == 0) {
					numFlinched++;
					return;
				}
			}
		}
		
		// only draw entity when not flinching
		super.render(g);
	}
	
	@Override
	public void computeNextAcceleration() {
		// add jumpforce to external force
		if (jump && !inAir) {
			jumpStart = true;
			externalForce.addLocal(jumpForce);
		}
		
		super.computeNextAcceleration();
	}
	
	@Override
	public boolean getsHit(int damage, boolean l, boolean r, boolean t, boolean b) {
		if (damage <= 0)
			return false;
		
		if(isDead || isInvunarable)
			return false;
		
		health -= damage;
		if (health <= 0) {
			health = 0;
			isDead = true;
		} else {
			flinching = true;
			isInvunarable = true;
			flinchTimer = System.nanoTime();
		}
		
		return true;
	}
	
	public void checkIsDead() throws Exception {
		if (isDead)
			destroy();
	}
	
	// defensive copy
	public Vector2f getJumpForce() {
		return new Vector2f(jumpForce);
	}
}
