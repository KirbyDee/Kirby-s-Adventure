package com.kirbydee.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.HashMap;

import lombok.Data;

import com.kirbydee.gamestate.GameState.State;
import com.kirbydee.gamestate.editor.EditorState;
import com.kirbydee.gamestate.loading.LoadingState;
import com.kirbydee.gamestate.menu.MenuState;
import com.kirbydee.gamestate.play.PlayState;
import com.kirbydee.main.Controlable;
import com.kirbydee.main.IOListener;
import com.kirbydee.utils.Vector2f;

@Data
public class GameStateManager implements IOListener, Controlable {

	// game states
	private Map<State, GameState> gameStates;
	private State currentState;
	private boolean showLoading;
	private boolean stateLoaded;
	
	public GameStateManager() throws Exception {
		// not loaded yet
		stateLoaded = false;
		showLoading = false;
			
		// game states
		gameStates = new HashMap<>();
		
		// add states
		gameStates.put(State.MENU_STATE, new MenuState(this));
		gameStates.put(State.EDITOR_STATE, new EditorState(this));
		//gameStates.add(new OptionState(this));
		//gameStates.add(new HelpState(this));
		gameStates.put(State.PLAY_STATE, new PlayState.Builder(this, 1, 1).tileSet("grass_64x64.gif")
					.background("grassbg1.gif").backgroundMusic("green_greens.mp3")
					.playerPosition(new Vector2f(150, 300)).tween(0.3F)
					.build());

		// start with menu
		load(State.MENU_STATE);
	}
	
	public void showLoadingState() throws Exception {
		// loading state
		gameStates.put(State.LOADING_STATE, LoadingState.getRandomLoadingState(this));
		gameStates.get(State.LOADING_STATE).create();
		
		// show loading state
		showLoading = true;
	}
	
	public void dismissLoadingState() throws Exception {
		// don't show loadind state
		showLoading = false;
		
		// destroy loading state
		gameStates.get(State.LOADING_STATE).destroy();
	}
	
	public void load(State state) {
		try {
			// no state is loaded
			stateLoaded = false;
			
			// show loading state
			showLoadingState();
			
			// destroy previous game state
			if (currentState != null)
				gameStates.get(currentState).destroy();
			
			// set new game state
			currentState = state;
			
			// load new game state
			if (currentState != null)
				gameStates.get(currentState).create();
			
			// don't show loading state
			dismissLoadingState();
			
			// new state is loaded
			stateLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() throws Exception {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).update();
		else if (stateLoaded)
			gameStates.get(currentState).update();
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).render(g);
		else if (stateLoaded)
			gameStates.get(currentState).render(g);
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).keyPressed(key);
		else if (stateLoaded)
			gameStates.get(currentState).keyPressed(key);
	}
	
	@Override
	public void keyReleased(KeyEvent key) {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).keyReleased(key);
		else if (stateLoaded)
			gameStates.get(currentState).keyReleased(key);
	}
	
	@Override
	public void keyTyped(KeyEvent key) {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).keyTyped(key);
		else if (stateLoaded)
			gameStates.get(currentState).keyTyped(key);
	}
	
	@Override
	public void mouseClicked(MouseEvent mouse) {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).mouseClicked(mouse);
		else if (stateLoaded)
			gameStates.get(currentState).mouseClicked(mouse);
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).mousePressed(mouse);
		else if (stateLoaded)
			gameStates.get(currentState).mousePressed(mouse);
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).mouseReleased(mouse);
		else if (stateLoaded)
			gameStates.get(currentState).mouseReleased(mouse);
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).mouseEntered(mouse);
		else if (stateLoaded)
			gameStates.get(currentState).mouseEntered(mouse);
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
		if (showLoading)
			gameStates.get(State.LOADING_STATE).mouseExited(mouse);
		else if (stateLoaded)
			gameStates.get(currentState).mouseExited(mouse);
	}
}
