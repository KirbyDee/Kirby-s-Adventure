package com.kirbydee.gamestate.editor;

import java.awt.Color;
import java.awt.Graphics2D;

import com.kirbydee.main.Controlable;

public class Grid implements Controlable {
	
	private Color color;
	private int gridWidth;
	private int gridHeight;
	private int width;
	private int height;
	private int numRows;
	private int numCols;
	
	public Grid(int gridWidth, int gridHeight) {
		this(gridWidth, gridHeight, 64, 64);
	}
	
	public Grid(int gridWidth, int gridHeight, int width, int height) {
		this(gridWidth, gridHeight, width, height, Color.WHITE);
	}
	
	public Grid(int gridWidth, int gridHeight, int width, int height, Color color) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	@Override
	public void update() throws Exception {
		numCols = gridWidth / width;
		numRows = gridHeight / height;
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {    
	    // set color
	    g.setColor(color);

	    // draw the rows
	    for (int i = 0; i < numRows; i++)
	      g.drawLine(0, (i + 1) * height, gridWidth, (i + 1) * height);

	    // draw the columns
	    for (int i = 0; i < numCols; i++)
	      g.drawLine((i + 1) * width, 0, (i + 1) * width, gridHeight);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
