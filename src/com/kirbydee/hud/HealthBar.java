package com.kirbydee.hud;

import java.awt.Color;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.entity.movable.living.LivingEntity;
import com.kirbydee.gamestate.play.PlayState;

@Data
@EqualsAndHashCode(callSuper=false)
public class HealthBar extends HUDBar<LivingEntity> {
	private final static String ICON_PATH = "/sprites/hud/heart.gif";
	private final static Color DEFAULT_COLOR = Color.RED;

	public HealthBar(LivingEntity livingEntity, PlayState playState) throws Exception {
		this(livingEntity, playState, DEFAULT_COLOR);
	}
	
	public HealthBar(LivingEntity livingEntity, PlayState playState, Color color) throws Exception {
		super(livingEntity, playState, ICON_PATH, color);
	}
	
	@Override
	protected void updatePosition(LivingEntity livingEntity) {
		this.position = livingEntity.getPositionScreen();
	}

	@Override
	protected void updateMaxValue(LivingEntity livingEntity) {
		this.maxValue = livingEntity.getMaxHealth();
	}

	@Override
	protected void updateValue(LivingEntity livingEntity) {
		this.value = livingEntity.getHealth();
	}
}
