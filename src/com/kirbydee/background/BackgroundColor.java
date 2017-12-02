package com.kirbydee.background;

import java.awt.Color;
import java.awt.Graphics2D;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.gamestate.GameState;
import com.kirbydee.main.GamePanel;
import com.kirbydee.main.Viewable;
import com.kirbydee.mvc.Viewer.DepthType;

@Data
@EqualsAndHashCode(callSuper=false)
public class BackgroundColor extends Viewable {
	
	// visible
	private boolean visible;
	
	// image
	private Color color;
	
	// dim
	private int width;
	private int height;
	
	
	public BackgroundColor(Color color, GameState gameState) throws Exception {
		super(gameState);
		this.color = color;
		this.width = GamePanel.WIDTH;
		this.height = GamePanel.HEIGHT;
		this.visible = true;
	}
	
	@Override
	public void update() throws Exception {
		// do nothing
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect(0, 0, width, height);
	}
	
	@Override
	public void destroy() throws Exception {
		color = null;
		super.destroy();
	}

	@Override
	public void setDepthType() {
		setDepthType(DepthType.BACKGROUND);
	}
}
