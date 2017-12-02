package com.kirbydee.entity.movable;

import com.kirbydee.animation.Animatable;

public interface Movable extends Animatable {
	public abstract void computeNextAcceleration() throws Exception;
	public abstract void computeNextVelocity() throws Exception;
	public abstract void computeNextPosition() throws Exception;
	default public void setNextAnimation() throws Exception {}
	default public void setNextSound() throws Exception {}
	public abstract void setLeft(boolean left);
	public abstract void setRight(boolean right);
	public abstract void setUp(boolean up);
	public abstract void setDown(boolean down);
}