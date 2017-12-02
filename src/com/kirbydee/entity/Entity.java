package com.kirbydee.entity;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kirbydee.collision.Collidable;
import com.kirbydee.collision.CollisionBox;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.main.Viewable;
import com.kirbydee.mvc.Viewer.DepthType;
import com.kirbydee.tilemap.TileMap;
import com.kirbydee.tilemap.Tile.TileType;
import com.kirbydee.utils.Vector2f;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class Entity extends Viewable implements Comparable<Entity> {
	
	// Entity Type
	public enum EntityType {
		FRIENDLY,
        NEUTRAL,
        ENEMY
	}
	
	// game State
	protected PlayState playState;
	
	// entity state
	protected Map<EntityStateType, EntityState<? extends Entity>> entityStates;
	protected EntityStateType currentEntityState;
	
	// map parameters
	protected Vector2f positionScreen;
	
	// mass
	protected float mass;
	
	// position
	protected Vector2f position;
	protected Vector2f positionTmp;

	// collision box
	protected Collidable collidable;
	protected int width;
	protected int height;
	
	//collision
	protected int currRow;
	protected int currCol;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	protected boolean collidedWithTile;
	
	// facings
	protected boolean facingRight;
	protected boolean facingLeft;
	protected boolean facingUp;
	protected boolean facingDown;
	
	// entity attributes
	protected int onTouchDamage;
	protected EntityType entityType;
	protected boolean isUpdated;
	
	public static boolean checkEntityCollision(Entity e1, Entity e2) {
		if (e1 == null)
			throw new NullPointerException("Entity is null!");
		
		return e1.checkEntityCollision(e2);
	}
	
	// Entity Builder
	public static abstract class Builder {
		// mandatory fields
		private final PlayState playState;
		
		// optional fields
		private Collidable collidable = new CollisionBox();
		private Vector2f position = new Vector2f();
		private int width = 45;
		private int height = 45;
		private float mass = 1;
		private int onTouchDamage = 0;
		private EntityType entityType = EntityType.NEUTRAL;
		
		// constructor
		public Builder(PlayState playState) {
			this.playState = playState;
		}
				
		// set fields
		public Builder collisionBox(Collidable val) { collidable = val; return this; }
		public Builder width(int val) { width = val; return this; }
		public Builder height(int val) { height = val; return this; }
		public Builder mass(float val) { mass = val; return this; }
		public Builder position(Vector2f val) { position = new Vector2f(val); return this; } // defensive copy
		public Builder onTouchDamage(int val) { onTouchDamage = val; return this; }
		public Builder entityType(EntityType val) { entityType = val; return this; }
		
		// builder
		public abstract Entity build() throws Exception;
	}
	
	public Entity(Builder builder) throws Exception {
		super(builder.playState);
		this.playState = builder.playState;
		this.width = builder.width;
		this.height = builder.height;
		this.position = builder.position;
		this.mass = builder.mass;
		this.entityType = builder.entityType;
		this.onTouchDamage = builder.onTouchDamage;
		this.collidable = builder.collidable;
		this.positionTmp = new Vector2f(this.position);
		this.positionScreen = new Vector2f();
		this.facingRight = true;
		this.facingLeft = false;
		this.facingUp = false;
		this.facingDown = false;
		this.collidedWithTile = false;
		this.isUpdated = false;
		
		// set collision box dimensions
		this.collidable.setPosition(this.position);
		this.collidable.setWidth(this.width);
		this.collidable.setHeight(this.height);
		
		// create states
		this.entityStates = new HashMap<>();
		initStates();
        this.currentEntityState = getDefaultState();
	}

	@Override
	public void update() throws Exception {
        // update current state
        getCurrentEntityState().update();

		// reset collide check with tile
		collidedWithTile = false;
		
		// update collidable position
		collidable.setPosition(position);
		
		// set position on map
		this.positionScreen = playState.getCameraPosition().add(position);
	}

    @Override
    public void render(Graphics2D g) throws Exception {
        // render current state
        getCurrentEntityState().render(g);
    }
	
	@Override
	public boolean isVisible() {
		return playState.onScreen(this);
	}
	
	@Override
	public int compareTo(Entity o) {
		if (getId() < o.getId())
			return -1;
		else if (this.getId() == o.getId())
			return 0;
		else
			return 1;
	}
	
	// gets hit with collision
	public abstract boolean getsHit(int damage, boolean l, boolean r, boolean t, boolean b);

	// add states
	public abstract void initStates() throws Exception;

    // set the default state
    public abstract EntityStateType getDefaultState();
	
	// add state
	public void addState(EntityState<? extends Entity> entityState) throws Exception {
		entityStates.put(entityState.getStateType(), entityState);
	}
	
	// check collision with entity
	public boolean checkEntityCollision(Entity e) {
		if (e == null)
			throw new NullPointerException("Entity is null!");
		
		Rectangle r1 = getCollisionRectangle();
		Rectangle r2 = e.getCollisionRectangle();
		return r1.intersects(r2);
	}
	
	// compute collision rectangle
	public Rectangle getCollisionRectangle() {
		return new Rectangle((int)position.getX() - width / 2, (int)position.getY() - height / 2, width, height);
	}
	
	// checks for collision with blocked tiles
	public void checkTileCollision(float x, float y) {
		TileMap tileMap = playState.getTileMap();
		int tileSize = tileMap.getTileSize();
		
		int width_halve = width / 2;
		int height_halve = height / 2;
		
		int leftTile = (int) (x - width_halve) / tileSize;
		int rightTile = (int) (x + width_halve - 1) / tileSize;
		int topTile = (int) (y - height_halve) / tileSize;
		int bottomTile = (int) (y + height_halve - 1) / tileSize;

		// check boundaries
		if(topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
            return;
        }
		
		// get tile type
		TileType tl = tileMap.getType(topTile, leftTile);
		TileType tr = tileMap.getType(topTile, rightTile);
		TileType bl = tileMap.getType(bottomTile, leftTile);
		TileType br = tileMap.getType(bottomTile, rightTile);
		
		// check if blocking tile
		topLeft = tl == TileType.BLOCKED;
		topRight = tr == TileType.BLOCKED;
		bottomLeft = bl == TileType.BLOCKED;
		bottomRight = br == TileType.BLOCKED;
		
		// set collided flag
		collidedWithTile |= topLeft | topRight | bottomLeft | bottomRight;
	}
	
	// defensive copy
	public Vector2f getPositionScreen() {
		return new Vector2f(positionScreen);
	}
	
	// defensive copy
	public Vector2f getPosition() {
		return new Vector2f(position);
	}
	
	// defensive copy
	public Vector2f getPositionTmp() {
		return new Vector2f(positionTmp);
	}
	
	public EntityState<? extends Entity> getCurrentEntityState() {
		return this.entityStates.get(this.currentEntityState);
	}
	
	@Override
	public void setDepthType() {
		setDepthType(DepthType.ENTITY);
	}
}
