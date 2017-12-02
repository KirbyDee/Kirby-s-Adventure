package com.kirbydee.mvc;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import com.kirbydee.gamestate.GameState;
import com.kirbydee.main.Creatable;
import com.kirbydee.main.Viewable;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Controller implements Creatable {

	private GameState gameState;
	private Map<Integer, Viewable> map;
	private Stack<Integer> stack;
	
	public Controller(GameState gameState) {
		this.gameState = gameState;
	}
	
	@Override
	public void create() throws Exception {
		// Use ConcurrentHashMap because of ConcurrentModificationException (array might change while iterating over it)
		map = new ConcurrentHashMap<>();
		stack = new Stack<>();
	}
	
	public int create(Viewable v) throws Exception {	
		// check if there are reusable ids available
		int id;
		if (stack.size() > 0)
			id = stack.pop();
		else
			id = map.size();
		
		// add element
		if (!map.containsKey(id)) // just check
			map.put(id, v);
		else
			throw new ConcurrentModificationException(v + " could not be added to Control Map due to already-existing id!");
	
		// set id
		v.setId(id);
		
		return id;
	}
	
	@Override
	public void destroy() throws Exception {
		// destroy all elements in map
		for (Viewable v : map.values())
			destroy(v);
		
		// remove map forever
		map = null;
		
		// pop the whole stack
		while (stack.size() > 0)
			stack.pop();
		
		// remove stack forever
		stack = null;
	}
	
	public int destroy(Viewable v) throws Exception {
		// remove elements from map
		if (map.containsKey(v.getId())) // just check
			map.remove(v.getId());
		else
			throw new ConcurrentModificationException(v + " could not be removed from Control Map due to non-existing id!");
		
		// get id
		int id = v.getId();
		
		// remove forever
		v = null;
		
		// add id to reusable stack
		stack.push(id);
		
		// return id
		return id;
	}
	
	public void update() throws Exception {
		// update all viewables
		for (Viewable v : map.values())
			v.update();
	}
	
	public Collection<Viewable> getAllViewables() throws Exception {
		return map.values();
	}
}
