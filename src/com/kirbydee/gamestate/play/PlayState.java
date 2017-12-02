package com.kirbydee.gamestate.play;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.background.Background;
import com.kirbydee.entity.Entity.EntityType;
import com.kirbydee.entity.movable.living.Slugger;
import com.kirbydee.entity.movable.living.player.Player;
import com.kirbydee.gamestate.GameState;
import com.kirbydee.gamestate.GameStateManager;
import com.kirbydee.sound.SmartClip;
import com.kirbydee.tilemap.TileMap;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public class PlayState extends GameState {
	
	// camera
	protected Camera camera;
	
	// level
	protected int stageNumber;
	protected int levelNumber;
	protected Vector2f levelPosition;
	
	// player
	private Player player;
	protected Vector2f playerPosition;
	
	// tile map
	protected TileMap tileMap;
	protected String tileSetFileName;
	protected int tileSize;
	protected float tween;
	
	// background
	protected String backgroundFileName;
	protected Background background;
	
	// background music
	protected String backgroundMusicFileName;
	protected SmartClip backgroundMusic;
	
	// pause
	private boolean pause;
	
	
	// Level builder
	public static class Builder {
		// mandatory fields
		private final GameStateManager gsm;
		private final int stageNumber;
		private final int levelNumber;
		
		// optional fields
		private Vector2f levelPosition = Vector2f.ZERO;
		private String tileSetFileName = "grass_64x64.gif";
		private String backgroundFileName = "grassbg1.gif";
		private String backgroundMusicFileName = "green_greens.mp3";
		private float tween = 0.07F;
		private Vector2f playerPosition = Vector2f.ZERO;
		private int tileSize = 64;
		
		// constructor
		public Builder(GameStateManager gsm, int level, int stage) {
			this.gsm = gsm;
			this.levelNumber = level;
			this.stageNumber = stage;
		}
		
		// set fields
		public Builder tween(float val) { tween = val; return this; }
		public Builder levelPosition(Vector2f val) { levelPosition = val; return this; }
		public Builder tileSet(String val) { tileSetFileName = val; return this; }
		public Builder background(String val) { backgroundFileName = val; return this; }
		public Builder backgroundMusic(String val) { backgroundMusicFileName = val; return this; }
		public Builder tileSize(int val) { tileSize = val; return this; }
		public Builder playerPosition(Vector2f val) { playerPosition = val; return this; }
		
		// build
		public PlayState build() { return new PlayState(this); }
	}

	public PlayState(Builder builder) {
		super(builder.gsm);
		this.stageNumber = builder.stageNumber;
		this.levelNumber = builder.levelNumber;
		this.levelPosition = builder.levelPosition;
		this.tileSetFileName = builder.tileSetFileName;
		this.backgroundFileName = builder.backgroundFileName;
		this.backgroundMusicFileName = builder.backgroundMusicFileName;
		this.tween = builder.tween;
		this.tileSize = builder.tileSize;
		this.playerPosition = builder.playerPosition;
		this.pause = false;
	}
	
	public String stageNumberToString() {
		if (stageNumber < 10)
			return "0" + stageNumber; 
		else
			return "" + stageNumber;
	}
	
	public String levelNumberToString() {
		if (levelNumber < 10)
			return "0" + levelNumber; 
		else
			return "" + levelNumber;
	}
	
	public void createMap() throws Exception {
		createMap("/maps/stage_" + stageNumberToString() + "/level_" + this.levelNumberToString() + ".map");
	}
	
	public void createMap(String levelFileName) throws Exception {
		tileMap = new TileMap(this, tileSize, levelFileName, "/tilesets/" + tileSetFileName);
	}
	
	public void setBackGroundMusicVolume(float gain) {
		backgroundMusic.setVolume(gain);
	}
	
	public float getBackGroundMusicVolume() {
		return backgroundMusic.getVolume();
	}
	
	public void setMuteBackGroundMusic(boolean mute) {
		backgroundMusic.setMute(mute);
	}
	
	public boolean getMuteBackGroundMusic() {
		return backgroundMusic.getMute();
	}
	
	public void createBackground(String bg, float ms) throws Exception {	
		background = new Background(bg, this);
		background.setMoveScale(ms);
	}
	
	public Vector2f getCameraPosition() {
		return camera.getPosition();
	}
	
	@Override
	public void update() throws Exception {
		if (!pause) {
			super.update();
			
			// update camera position
			camera.update();
			
			// update background
			background.setPosition(camera.getPosition()); // TODO: do in background.update()
		}
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
		super.render(g);
	}
	
	@Override
	public void create() throws Exception {
		super.create();
		
		// create level
		createMap();
		
		// create player
		player = (Player) new Player.Builder(this).jumpForce(new Vector2f(0.0F, -8.0F)).health(3) // LivingEntity
				.acceleration(new Vector2f(0.5F, 0)).deceleration(new Vector2f(0.4F, 0.0F)).maxMoveSpeed(3.5F) // MovableEntity
				.position(playerPosition).width(45).height(45).entityType(EntityType.FRIENDLY).build(); // Entity
		
//		// TODO: add slugger as test
//		new Slugger.Builder(this).health(5) // LivingEntity
//			.velocity(new Vector2f(0.5F, 0)) // MovableEntity
//			.position(new Vector2f(400, 400)).width(42).height(30).entityType(EntityType.ENEMY).onTouchDamage(1).build(); // Entity
		
		// create background
		createBackground("/backgrounds/" + backgroundFileName, 0.1F);
		
		// create background music
		backgroundMusic = requestClip("/sounds/backgrounds/" + backgroundMusicFileName);
		backgroundMusic.play();
		
		// create camera
		camera = new Camera(this);
		camera.setPosition(playerPosition);
		camera.setTween(new Vector2f(tween, tween));
	}
	
	@Override
	public void destroy() throws Exception {
		super.destroy();
		
		// destroy background music
		backgroundMusic.destroy();
		backgroundMusic = null;
		
		// destroy camera
		camera.destroy();
		camera = null;
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			if (pause) {
				pause = false;
				setBackGroundMusicVolume(1.0F);
			} else {
				pause = true;
				setBackGroundMusicVolume(0.2F);
			}
			break;
		case KeyEvent.VK_BACK_SPACE:
			gsm.load(State.MENU_STATE);
			break;
		default:
			break;
		}
		
		if (!pause) {
            this.player.getCurrentEntityState().keyPressed(key);

//			switch(key.getKeyCode()) {
//			case KeyEvent.VK_LEFT:
//				player.setLeft(true);
//				break;
//			case KeyEvent.VK_RIGHT:
//				player.setRight(true);
//				break;
//			case KeyEvent.VK_DOWN:
//				player.setDown(true);
//				break;
//			case KeyEvent.VK_UP:
//				player.setUp(true);
//				break;
//			case KeyEvent.VK_SPACE:
//				player.setJump(true);
//				break;
////			case KeyEvent.VK_Q:
////				player.setFiring(true);
////				break;
//			default:
//				break;
//			}
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		if (!pause) {
            this.player.getCurrentEntityState().keyReleased(key);

//			switch(key.getKeyCode()) {
//			case KeyEvent.VK_LEFT:
//				player.setLeft(false);
//				break;
//			case KeyEvent.VK_RIGHT:
//				player.setRight(false);
//				break;
//			case KeyEvent.VK_DOWN:
//				player.setDown(false);
//				break;
//			case KeyEvent.VK_UP:
//				player.setUp(false);
//				break;
//			case KeyEvent.VK_SPACE:
//				player.setJump(false);
//				break;
//			default:
//				break;
//			}
		}
	}
	
	// defensive copy
	public Vector2f getLevelPosition() {
		return new Vector2f(levelPosition);
	}
	
	// defensive copy
	public Vector2f getPlayerPosition() {
		return new Vector2f(player.getPosition());
	}
}
