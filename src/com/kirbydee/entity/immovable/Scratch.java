package com.kirbydee.entity.immovable;

import com.kirbydee.entity.EntityStateType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.entity.Entity;
import com.kirbydee.gamestate.play.PlayState;

@Data
@EqualsAndHashCode(callSuper=false)
public class Scratch extends ImmovableEntity {
	
	private long startTime;
	private long lifeTime;
	
	// Builder
	public static class Builder extends ImmovableEntity.Builder {
		// mandatory fields
		private final long lifeTime;
				
		// constructor
		public Builder(PlayState gameState, long lifeTime) {
			super(gameState);
			this.lifeTime = lifeTime;
		}

		// build
		@Override
		public Entity build() throws Exception { return new Scratch(this); }
	}

	public Scratch(Builder builder) throws Exception {
		super(builder);
		this.lifeTime = builder.lifeTime;
		
		// start time
		this.startTime = System.nanoTime();
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
	public boolean getsHit(int damage, boolean l, boolean r, boolean t, boolean b) {
		return false;
	}
	
	@Override
	public void update() throws Exception {
		super.update();
		
		// if he lived his lifetime, destroy it
		if (System.nanoTime() - startTime >= lifeTime)
			destroy();
	}
}
