package com.kirbydee.gamestate.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.kirbydee.background.BackgroundColor;
import com.kirbydee.gamestate.GameState;
import com.kirbydee.gamestate.GameStateManager;
import com.kirbydee.main.GamePanel;
import com.kirbydee.utils.Vector2f;

public class EditorState extends GameState {
	
	// background
	protected BackgroundColor background;
	
	// mouse pointer
//	private SmartImage mousePointer;
	private Vector2f mousePosition;
	
	// grid
	private Grid grid;
	
	public EditorState(GameStateManager gsm) {
		super(gsm);
	}
	
	@Override
	public void create() throws Exception {
		super.create();
		
		// create background
		background = new BackgroundColor(Color.BLACK, this);
		
		// create mouse position
//		mousePointer = new SmartImage("/sprites/editor/mouse.png", this);
		mousePosition = new Vector2f();
		
		// create grid
		grid = new Grid(GamePanel.WIDTH, GamePanel.HEIGHT);
	}

	@Override
	public void destroy() throws Exception {
		super.destroy();
		
		// destroy background
		background.destroy();
		background = null;
		
		// destroy mouse
		mousePosition = null;
//		mousePointer = null;
	}
	
	@Override
	public void update() throws Exception {
		// update mouse position
		updateMousePosition();
		
		// update grid
		updateGrid();
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
		// draw background
		background.render(g);
		
		// draw grid
		grid.render(g);
		
//		// draw mouse
//		int w = mousePointer.getWidth();
//		int h = mousePointer.getHeight();
//		g.drawImage(mousePointer.getImage(),
//				(int) (mousePosition.getX() - w / 2),
//				(int) (mousePosition.getY() - h / 2),
//				w,
//				h,
//				null);
	}
	
	private void updateMousePosition() {
		mousePosition.set(MouseInfo.getPointerInfo().getLocation()).subLocal(GamePanel.SCREEN_LOCATION);
		
		if (mousePosition.getX() < 0)
			mousePosition.setX(0);
		if (mousePosition.getX() > GamePanel.WIDTH)
			mousePosition.setX(GamePanel.WIDTH);
		if (mousePosition.getY() < 0)
			mousePosition.setY(0);
		if (mousePosition.getY() > GamePanel.HEIGHT)
			mousePosition.setY(GamePanel.HEIGHT);
	}
	
	private void updateGrid() throws Exception {
		grid.update();
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
	}
	
	@Override
	public void keyReleased(KeyEvent key) {
	}
	
	@Override
	public void keyTyped(KeyEvent key) {
	}
	
	@Override
	public void mouseClicked(MouseEvent mouse) {
		int x=mouse.getX();
	    int y=mouse.getY();
	    System.out.println(x+","+y);//these co-ords are relative to the component
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
	}
}
