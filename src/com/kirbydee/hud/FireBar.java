package com.kirbydee.hud;

import java.awt.Color;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.entity.movable.living.player.Player;
import com.kirbydee.gamestate.play.PlayState;

@Data
@EqualsAndHashCode(callSuper=false)
public class FireBar extends HUDBar<Player> {
	private final static String ICON_PATH = "/sprites/hud/fire.gif";
	private final static Color DEFAULT_COLOR = Color.BLUE;

	public FireBar(Player player, PlayState playState) throws Exception {
		this(player, playState, DEFAULT_COLOR);
	}
	
	public FireBar(Player player, PlayState playState, Color color) throws Exception {
		super(player, playState, ICON_PATH, color);
	}
	
	@Override
	protected void updatePosition(Player player) {
		this.position = player.getPositionScreen();
	}

	@Override
	protected void updateMaxValue(Player player) {
		this.maxValue =  0; //player.getMaxFire();
	}

	@Override
	protected void updateValue(Player player) {
		this.value = 0; //player.getFire();
	}
}
