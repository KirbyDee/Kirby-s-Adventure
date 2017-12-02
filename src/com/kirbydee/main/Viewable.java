package com.kirbydee.main;

import lombok.Data;

import com.kirbydee.gamestate.GameState;
import com.kirbydee.mvc.Viewer.DepthType;

@Data
// TODO: rename: is no interface anymore
// TODO: add width, height, playState (instead of gameState?), position? screenPosition (what about background?)
public abstract class Viewable implements Controlable {
	
	private int id;
	private GameState gameState;
	private DepthType depthType;
	
	
	public Viewable(GameState gameState) throws Exception {
		this.gameState = gameState;
		
		setDepthType();
		create();
	}
	
	@Override
	public void create() throws Exception {
		gameState.create(this);
	}
	
	@Override
	public void destroy() throws Exception {
		gameState.destroy(this);
	}
	
	public abstract void setDepthType();
	public abstract boolean isVisible();
	
	
//	public abstract Vector2f getPosition();
//	public abstract void setPosition(Vector2f position);
//	public abstract Vector2f getPositionScreen();
//	public abstract void setPositionScreen(Vector2f position);
//	public abstract int getWidth();
//	public abstract int getHeight();
}
