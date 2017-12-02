package com.kirbydee.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.kirbydee.gamestate.GameStateManager;
import com.kirbydee.utils.Vector2f;

public class GamePanel extends JPanel implements IOListener, Controlable {
	// serial version UID
	private static final long serialVersionUID = 2975010707368959863L;	
	
	// screen dimensions
//	public static final int WIDTH = 960;
//	public static final int HEIGHT = 720;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
//	public static final int WIDTH = 480;
//	public static final int HEIGHT = 320;
	public static final Vector2f MIDDLE_OF_SCREEN = new Vector2f(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2);
	public static final int SCALE = 1;
	
	// screen location
	public static Vector2f SCREEN_LOCATION;
	
	// thread parameters
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// graphics
	private BufferedImage image;
	private Graphics2D graphic;
	
	// game state manager
	private GameStateManager gsm;
	private boolean gsmLoaded;
	
	
	public GamePanel() {
		super();
		
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
		gsmLoaded = false;
		SCREEN_LOCATION = new Vector2f();
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if (!running) {
			addKeyListener(this);
			addMouseListener(this);
			new Thread(getRunnable()).start();
		}
	}
	
public Runnable getRunnable() {
  return () -> {
    try {
      init();
				
      long start;
      long elapsed;
      long wait;
      while (running) {
          start = System.nanoTime();
					
          getLocationOnScreen();
          update(); // update objects
          render(graphic); // draw objects
          drawToScreen(); // draw everything to the screen
					
          // get time elapsed
          elapsed = System.nanoTime() - start;
          wait = targetTime - elapsed / 1000000;
					
          // set reasonable wait for thread to sleep (suppress starvation when wait <= 0: NEVER USE Thread.sleep(0) - Joshua Bloch)
          if (wait <= 0)
          wait = 5;
					
          try {
            Thread.sleep(wait);
          } catch (InterruptedException t) {
            t.printStackTrace();
          }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  };
}
	
	private void init() throws Exception {
		// init screen
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		graphic = (Graphics2D) image.getGraphics();
		running = true;
		
		// create game state manager
		gsm = new GameStateManager();
		gsmLoaded = true;
	}
	
	private void drawToScreen() {
		// draw graphic to screen
		Graphics graphic2 = getGraphics();
		graphic2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		graphic2.dispose();
	}
	
	@Override
	public Point getLocationOnScreen() {
		Point p = super.getLocationOnScreen();
		SCREEN_LOCATION = new Vector2f(p);
		
		return p;
	}
	
	@Override
	public void update() throws Exception {
		if (gsmLoaded)
			gsm.update();
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
		if (gsmLoaded)
			gsm.render(g);
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		if (gsmLoaded)
			gsm.keyPressed(key);
	}

	@Override
	public void keyReleased(KeyEvent key) {
		if (gsmLoaded)
			gsm.keyReleased(key);
	}

	@Override
	public void keyTyped(KeyEvent key) {
		if (gsmLoaded)
			gsm.keyTyped(key);
	}

	@Override
	public void mouseClicked(MouseEvent mouse) {
		if (gsmLoaded)
			gsm.mouseClicked(mouse);
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
		if (gsmLoaded)
			gsm.mousePressed(mouse);
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
		if (gsmLoaded)
			gsm.mouseReleased(mouse);
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
		if (gsmLoaded)
			gsm.mouseEntered(mouse);
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
		if (gsmLoaded)
			gsm.mouseExited(mouse);
	}
}
