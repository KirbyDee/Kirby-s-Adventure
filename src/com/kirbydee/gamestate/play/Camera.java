package com.kirbydee.gamestate.play;

import java.awt.Graphics2D;

import lombok.Data;

import com.kirbydee.main.Controlable;
import com.kirbydee.main.GamePanel;
import com.kirbydee.utils.Vector2f;

@Data
public class Camera implements Controlable {

	private PlayState playState;
	private Vector2f position;
	private Vector2f boundMax;
	private Vector2f boundMin;
	private Vector2f tween = Vector2f.ZERO;
	
	public Camera(PlayState playState) throws Exception {
		this.playState = playState;
		
		create();
	}
	
	@Override
	public void create() throws Exception {
		int width = playState.getTileMap().getWidth();
		int height = playState.getTileMap().getHeight();
		
		boundMax = new Vector2f(0, 0);
		boundMin = new Vector2f(GamePanel.WIDTH - width, GamePanel.HEIGHT - height);
	}
	
	@Override
	public void destroy() throws Exception {
		position = null;
		boundMax = null;
		boundMin = null;
		tween = null;
	}
	
	@Override
	public void update() throws Exception {
		Vector2f playerPosition = playState.getPlayerPosition();
		Vector2f p = GamePanel.MIDDLE_OF_SCREEN.sub(playerPosition);
		
		Vector2f v = new Vector2f(tween);
		if (Math.abs(position.getX() - p.getX()) < 1)
			// smooth change in x-direction
			v.setX(0);
		if (Math.abs(position.getY() - p.getY()) < 1)
			// smooth change in y-direction
			v.setY(0);
		
		// set position
		position.addLocal(p.sub(position).mult(v));
		
		// fix bounds
		fixBounds();
	}
	
	public void fixBounds() {
		float x = position.getX();
		float y = position.getY();
		if (x < boundMin.getX())
			x = boundMin.getX();
		if (x > boundMax.getX())
			x = boundMax.getX();
		if (y < boundMin.getY())
			y = boundMin.getY();
		if (y > boundMax.getY())
			y = boundMax.getY();
		position.set(x, y);
	}
	
	// defensive copy
	public Vector2f getPosition() {
		return new Vector2f(position);
	}

	@Override
	public void render(Graphics2D g) throws Exception {
		// do nothing
	}
}
