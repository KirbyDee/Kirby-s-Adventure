package com.kirbydee.animation;

import java.awt.Color;
import java.awt.Graphics2D;

import lombok.Data;

import com.kirbydee.utils.Vector2f;

@Data
public class SmartImageColor {

	// the color
	private Color color;
	
	// dimensions
	private int width;
	private int height;
	
	public SmartImageColor(Color color, int width, int height) {
		this.color = color;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics2D g, Vector2f position) {
		g.setColor(color);
        g.fillRect(
        (int) (position.getX() - width / 2),
		(int) (position.getY() - height / 2),
		width,
		height);
	}
}
