package com.kirbydee.gamestate.loading;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.kirbydee.animation.Animation;
import com.kirbydee.background.BackgroundColor;
import com.kirbydee.gamestate.GameState;
import com.kirbydee.gamestate.GameStateManager;
import com.kirbydee.utils.Vector2f;

@ToString
@EqualsAndHashCode(callSuper=false)
public abstract class LoadingState extends GameState {
	
	// Loading State types
	public enum LoadingAnimation {
		SUCK,
		WALK,
		TAUNT,
		IDLE,
		SALTO,
		FALL
	}
	
	// background
	protected BackgroundColor background;
	
	// Kirby
	protected int numAnimations;
	protected List<Animation> animations;
	protected List<Vector2f> positionsKirby;
	protected List<Vector2f> velocitiesKirby;
	
	// loading
	protected static final String LOADING_STRING = "Loading";
	protected static final String LOADING_POINT = ".";
	protected static final int POINT_FREQ = 20;
	protected static final int MAX_NUM_POINTS = 3;
	protected int numFrames;
	protected String loading;
	protected int numPoints;
	protected boolean wiggle;
	protected Vector2f positionLoading;
	protected Vector2f velocityLoading;
	
	// random
	protected Random rand;
	
	public static LoadingState getRandomLoadingState(GameStateManager gsm) {
		// get random animation
		Random rand = new Random();
		LoadingAnimation type = LoadingAnimation.values()[rand.nextInt(LoadingAnimation.values().length)];
		
		// load kirby animation
		switch(type) {
		case SUCK:
			return new LoadingStateSuck(gsm);
		case WALK:
			return new LoadingStateWalk(gsm);
		case SALTO:
			return new LoadingStateSalto(gsm);
		case TAUNT:
			return new LoadingStateTaunt(gsm);
		case IDLE:
			return new LoadingStateIdle(gsm);
		case FALL:
			return new LoadingStateFall(gsm);
		default:
			return new LoadingStateTaunt(gsm);
		}
	}
	
	public LoadingState(GameStateManager gsm) {
		super(gsm);
	}
	
	@Override
	public void create() throws Exception {
		super.create();
		
		background = new BackgroundColor(Color.BLACK, this);
		rand = new Random();
		animations = new ArrayList<>();
		positionsKirby = new ArrayList<>();
		velocitiesKirby = new ArrayList<>();
		loading = LOADING_STRING;
		numAnimations = 0;
		numPoints = 0;
		numFrames = 0;
		
		// load animation
		loadAnimation();
	}
	
	protected abstract void loadAnimation() throws Exception;

	@Override
	public void update() throws Exception { // TODO: animation is viewable -> controller
		super.update();

		// change position Loading
		positionLoading.addLocal(velocityLoading);
		
		// kirby animations
		for (int i = 0; i < numAnimations; i++) {
			Animation animation = animations.get(i);
			Vector2f position = positionsKirby.get(i);
			Vector2f velocity = velocitiesKirby.get(i);
			
			// change position Kirby
			position.addLocal(velocity);
			
			// update animation
			animation.setPosition(position);
			animation.update();
		}
		
		// loading
		if (numFrames >= POINT_FREQ) {
			numFrames = 0;
			if (numPoints >= MAX_NUM_POINTS) {
				loading = LOADING_STRING;
				numPoints = 0;
			} else {
				loading += LOADING_POINT;
				numPoints++;
			}
		} else {
			numFrames++;
		}
	}

	@Override
	public void render(Graphics2D g) throws Exception { // TODO: animation is viewable -> controller
		super.render(g);
		
		// loading
		g.setColor(Color.WHITE);
		Vector2f position = new Vector2f(positionLoading);
		if (wiggle)
			position.addLocal(rand.nextInt(4), rand.nextInt(4));
		g.drawString(loading, (int) position.getX(), (int) position.getY());
		
		// kirby animations
		for (Animation animation : animations)
			animation.render(g);
	}
}
