package com.kirbydee.gamestate.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.background.Background;
import com.kirbydee.gamestate.GameState;
import com.kirbydee.gamestate.GameStateManager;
import com.kirbydee.main.GamePanel;
import com.kirbydee.sound.SmartClip;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public class MenuState extends GameState {

	// Konami code: up, up, down, down, left, right, left, right, b, a
	private Map<Integer, Integer> konamiCode;
	private int currentKonamiCodeIndex;
	private boolean konamiCodeActivated;
	
	// background
	private Background background;
	
	// music
	private SmartClip backgroundMusic;
	
	// choice of menu
	public enum Option {
		START("Start", 1),
		EDITOR("Editor", 2),
		OPTIONS("Options", 3),
		HELP("Help", 4),
		QUIT("Quit", 5);
		
		private static Option currentChoice;
		private String name;
		private int index;
		private Color color;
		
		private Option(String name, int index) {
			this.name = name;
			this.index = index;
			this.color = Color.BLACK;
		}
		
		public static void setCurrentChoice(Option c) {
			if (currentChoice != null)
				currentChoice.color = Color.BLACK;
			currentChoice = c;
			currentChoice.color = Color.RED;
		}
		
		public static Option getCurrentChoice() {
			return currentChoice;
		}
		
		public static void nextChoice() {
			switch(currentChoice) {
			case START:
				setCurrentChoice(EDITOR);
				break;
			case EDITOR:
				setCurrentChoice(OPTIONS);
				break;
			case OPTIONS:
				setCurrentChoice(HELP);
				break;
			case HELP:
				setCurrentChoice(QUIT);
				break;
			case QUIT:
				setCurrentChoice(START);
				break;
			default:
				// does not happen
			}
		}
		
		public static void previousChoice() {
			switch(currentChoice) {
			case START:
				setCurrentChoice(QUIT);
				break;
			case EDITOR:
				setCurrentChoice(START);
				break;
			case OPTIONS:
				setCurrentChoice(EDITOR);
				break;
			case HELP:
				setCurrentChoice(OPTIONS);
				break;
			case QUIT:
				setCurrentChoice(HELP);
				break;
			default:
				// does not happen
			}
		}
	}
	
	// title
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		
		// create Konami code
		konamiCode = new HashMap<>(10);
		konamiCode.put(0, KeyEvent.VK_UP);
		konamiCode.put(1, KeyEvent.VK_UP);
		konamiCode.put(2, KeyEvent.VK_DOWN);
		konamiCode.put(3, KeyEvent.VK_DOWN);
		konamiCode.put(4, KeyEvent.VK_LEFT);
		konamiCode.put(5, KeyEvent.VK_RIGHT);
		konamiCode.put(6, KeyEvent.VK_LEFT);
		konamiCode.put(7, KeyEvent.VK_RIGHT);
		konamiCode.put(8, KeyEvent.VK_B);
		konamiCode.put(9, KeyEvent.VK_A);
		currentKonamiCodeIndex = 0;
		konamiCodeActivated = false;
	}
	
	@Override
	public void create() throws Exception {
		super.create();
		
		// load background
		background = new Background("/backgrounds/menubg.gif", this);
		background.setVelocity(new Vector2f(-0.3F, 0.0F));
		
		// set title
		titleColor = new Color(128, 0, 0);
		titleFont = new Font("Century Gothic", Font.PLAIN, 28);
		font = new Font("Arial", Font.PLAIN, 12);
		
		// set current Option
		Option.setCurrentChoice(Option.START);
		
		// load background music
		backgroundMusic = requestClip("/sounds/backgrounds/zelda_title.mp3");
		backgroundMusic.play();
	}
	
	@Override
	public void destroy() throws Exception {
		super.destroy();
		
		// destroy background music
		backgroundMusic.stop();
		backgroundMusic = null;
				
		// destroy background
//		background.destroy();
//		background = null;
		
		// title
		titleColor = null;
		titleFont = null;
		font = null;
	}

//	@Override
//	public void update() throws Exception {
//		background.update();
//	}

	@Override
	public void render(Graphics2D g) throws Exception {
		super.render(g);
//		background.render(g);
		
		// set title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Kirby's Adventure", GamePanel.WIDTH / 3, GamePanel.HEIGHT / 3);
		
		// write choices
		g.setFont(font);
		for (Option option : Option.values()) {
			// set color
			g.setColor(option.color);
			
			// draw choice
			g.drawString(option.name, GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2 + option.index*15);
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {
		int k = key.getKeyCode();
		switch (k) {
		case KeyEvent.VK_ESCAPE:
			// ESCAPE
			System.exit(0);
		case KeyEvent.VK_ENTER:
			// ENTER
			select();
			break;
		case KeyEvent.VK_UP:
			// UP
			Option.previousChoice();
			break;
		case KeyEvent.VK_DOWN:
			// DOWN
			Option.nextChoice();
			break;
		default:
			break;
		}
		
		// check Konami Code
		if (konamiCode.get(currentKonamiCodeIndex) == k)
			currentKonamiCodeIndex++;
		else if (konamiCode.get(0) == k)
			currentKonamiCodeIndex = 1;
		else
			currentKonamiCodeIndex = 0;
		if (currentKonamiCodeIndex == konamiCode.size()) {
			konamiCodeActivated = true;
			currentKonamiCodeIndex = 0;
		}
	}
	
	private void select() {
		switch(Option.getCurrentChoice()) {
			case START: 
				// start
				gsm.load(State.PLAY_STATE);
				break;
			case EDITOR:
				// editor
				gsm.load(State.EDITOR_STATE);
				break;
			case OPTIONS:
				// options
				break;
			case HELP:
				// help
				break;
			case QUIT:
				// quit
				System.exit(0);
			default:
				break;
		}
	}
}
