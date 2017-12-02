package com.kirbydee.tilemap;

import java.awt.Graphics2D;

import com.kirbydee.animation.SmartImage;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.main.Viewable;
import com.kirbydee.mvc.Viewer.DepthType;
import com.kirbydee.utils.Vector2f;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Tile extends Viewable {
	
	// tile types
	public enum TileType {
		NORMAL,
		BLOCKED
	}
	
	// Play State
	private PlayState playState;
	
	// tile details
	private SmartImage tileImage;
	private TileType type;
	private Vector2f position;
	private Vector2f positionScreen;
	private int width;
	private int height;
	
	
	public Tile(SmartImage tileImage, TileType type, int size, Vector2f position, PlayState playState) throws Exception {
		super(playState);
		this.tileImage = tileImage;
		this.type = type;
		this.width = size;
		this.height = size;
		this.position = position;
		this.playState = playState;
	}
	
	@Override
	public void update() throws Exception {
		positionScreen = playState.getCameraPosition().add(position).add(width/2, height/2);
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
		tileImage.render(g, positionScreen, true);
	}

	@Override
	public boolean isVisible() {
		return playState.onScreen(this);
	}
	
	@Override
	public void setDepthType() {
		setDepthType(DepthType.TILEMAP_FOREGROUND);
	}
	
	// defensive copy
	public Vector2f getPosition() {
		return new Vector2f(position);
	}
	
	// defensive copy
	public Vector2f getPositionScreen() {
		return new Vector2f(positionScreen);
	}
}
