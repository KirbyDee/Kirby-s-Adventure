package com.kirbydee.background;

import java.awt.Graphics2D;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.animation.SmartImage;
import com.kirbydee.gamestate.GameState;
import com.kirbydee.main.GamePanel;
import com.kirbydee.main.Viewable;
import com.kirbydee.mvc.Viewer.DepthType;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public class Background extends Viewable {
	
	// visible
	private boolean visible;
	
	// image
	private SmartImage image;
	private SmartImage image2;
	
	// position
	private Vector2f position;
	
	// dim
	private int width;
	private int height;
	
	// translation
	private Vector2f velocity;
	private float moveScale;
	
	
	public Background(String s, GameState gameState) throws Exception {
		super(gameState);
		this.image = gameState.requestImage(s);
		this.moveScale = 1;
		this.width = GamePanel.WIDTH;
		this.height = GamePanel.HEIGHT;
		this.position = new Vector2f(width / 2, height / 2);
		this.velocity = new Vector2f();
		this.image.setWidth(width);
		this.image.setHeight(height);
		this.image2 = new SmartImage(image);
		this.visible = true;
	}
	
	public void setPosition(Vector2f p) {
		position.set((p.getX() * moveScale) % width, (p.getY() * moveScale) % height);
		position.addLocal(width / 2, height / 2);
	}
	
	@Override
	public void destroy() throws Exception {
		image = null;
		position = null;
		velocity = null;
		
		super.destroy();
	}
	
	@Override
	public void update() {
		// compute next position
		position.addLocal(velocity);
		
		// if out of image
		if (position.getX() < 0)
			position.addLocal(width, 0);
		if (position.getX() > width)
			position.subLocal(width, 0);
	}
	
	@Override
	public void render(Graphics2D g) {
		image.render(g, position, false);
		
		// missing part
		if (position.getX() < width / 2)
			image2.render(g, position.add(width, 0), false);
		if (position.getX() > width / 2)
			image2.render(g, position.sub(width, 0), false);
	}
	
	@Override
	public void setDepthType() {
		setDepthType(DepthType.BACKGROUND);
	}
	
	// defensive copy
	public Vector2f getVelocity() {
		return new Vector2f(velocity);
	}
	
	// defensive copy
	public Vector2f getPosition() {
		return new Vector2f(position);
	}
}
