package com.kirbydee.collision;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.kirbydee.utils.Line2f;
import com.kirbydee.utils.Vector2f;

public class CollisionBox implements Collidable {
	
	private List<Line2f> gradients;
	private Vector2f mapPosition;
	private int width;
	private int height;
	
	public CollisionBox() {
		this(64 ,64);
	}
	
	public CollisionBox(int width, int height) {
		this.width = width;
		this.height = height;
		this.gradients = new ArrayList<>();
		
		init();
	}
	
	protected void init() {
		float w = (float) width / 2;
		float h = (float) height / 2;
		gradients.add(new Line2f(w, 	0, 		0, 		-w));
		gradients.add(new Line2f(w, 	height, 0, 		w));
		gradients.add(new Line2f(0,		h, 		h, 		0));
		gradients.add(new Line2f(width, h, 		-h, 	0));
	}
	
	@Override
	public boolean checkRectangleCollision(Collidable c) {
		if (c == null)
			throw new NullPointerException("Collidable is null!");
		
		// check if the normal bounding box even hits
		Rectangle r1 = getCollisionRectangle();
		Rectangle r2 = c.getCollisionRectangle();
		return !r1.intersects(r2);
	}

	@Override
	public Line2f checkGradientCollision(Collidable c) {
		// get gradients from the other collidable
		List<Line2f> g = c.getInterestGradients();
		
		// loop over gradients
		gradients.forEach((l1) -> {
			g.forEach((l2) -> {
				l2.getPoint();
			});
		});
		
		return null;
	}

	@Override
	public Rectangle getCollisionRectangle() {
		return new Rectangle((int)mapPosition.getX() - width / 2, (int)mapPosition.getY() - height / 2, width, height);
	}

	@Override
	public List<Line2f> getInterestGradients() {
		List<Line2f> g = new ArrayList<>(gradients.size());
		gradients.forEach((l) -> { g.add(l); });
		return g;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void setPosition(Vector2f position) {
		mapPosition = new Vector2f(position);
	}
}
