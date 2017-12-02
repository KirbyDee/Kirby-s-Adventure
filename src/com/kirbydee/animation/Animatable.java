package com.kirbydee.animation;

import com.kirbydee.animation.Animation.AnimationType;
import com.kirbydee.main.Creatable;

public interface Animatable extends Creatable {
	public abstract Class<? extends AnimationType> getAnimationTypes();
}
