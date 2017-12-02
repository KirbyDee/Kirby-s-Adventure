package com.kirbydee.mvc;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import com.kirbydee.gamestate.GameState;
import com.kirbydee.main.Controlable;
import com.kirbydee.main.Viewable;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Viewer implements Controlable {
	
	public enum DepthType {
		BACKGROUND,
		TILEMAP_BACKGROUND,
		ENTITY,
		TILEMAP_FOREGROUND,
		GUI
	}

	private GameState gameState;
	private Controller controller;
    private Map<DepthType, List<Viewable>> map;
	
	public Viewer(GameState gameState, Controller controller) {
		this.gameState = gameState;
        this.controller = controller;
	}

    public void reset() throws Exception {
        destroy();
        create();
    }

	@Override
	public void create() throws Exception {
		// Use ConcurrentHashMap because of ConcurrentModificationException (array might change while iterating over it)
		map = new ConcurrentHashMap<>(DepthType.values().length);
		
		// create all lists
		for (DepthType depth : DepthType.values())
			map.put(depth, new ArrayList<>());
	}
	
	@Override
	public void destroy() throws Exception {
		// remove all lists in the map
		for (DepthType depth : map.keySet()) {
			// remove list from map
			List<Viewable> list = map.remove(depth);
			
			// clear list
			list.clear();
		}
		
		// remove map forever
		map = null;
	}
	
	@Override
	public void render(Graphics2D g) throws Exception {
		// render from depth types
		if (map != null)
			for (DepthType depth : DepthType.values())
				for (Viewable v : map.get(depth))
					v.render(g);
	}
	
	@Override
	public void update() throws Exception {
		// destroy map and create a new one
		reset();
		
		// add new viewabls from controller to viewer
        addAll(controller.getAllViewables());
	}
	
	public void addAll(Collection<Viewable> viewables) throws Exception {
        viewables.forEach(this::add);
	}
	
	public void add(Viewable v) {
		// if needs to be drawn
		if(!v.isVisible())
			return;
		
		// get depth
		DepthType depthType = v.getDepthType();
		
		// add element
		map.get(depthType).add(v);
	}
}
